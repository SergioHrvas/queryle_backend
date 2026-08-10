package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.adapters;

import com.github.sergiohrvas.queryle.challenges.domain.ports.QueryEvaluatorPort;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class QueryEvaluatorAdapter implements QueryEvaluatorPort {
    @Override
    public Feedback evaluate(Query query, Query solutionQuery, DataContext dataContext) {
        String memDbUrl = "jdbc:h2:mem:eval_" + UUID.randomUUID().toString().replace("-", "") + ";DB_CLOSE_DELAY=0";
        
        try (Connection connection = DriverManager.getConnection(memDbUrl);
             Statement stmt = connection.createStatement()) {

            // 1. Ejecutar el script DDL/DML del DataContext
            stmt.execute(dataContext.getSeedScript());

            // 2. Ejecutar la solución esperada
            QueryResult expectedResult = executeQuery(stmt, solutionQuery.value());
 
            // 3. Ejecutar la query del usuario de forma protegida
            QueryResult userResult = executeQuery(stmt, query.value());

            // Si la consulta del usuario falló por error de sintaxis SQL
            if (!userResult.isSuccess()) {
                return new Feedback(MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE);
            }

            // 4. Evaluar las 5 dimensiones
            MatchLevel columnsNamesMatchLevel = evaluateColumnsNamesMatchLevel(userResult.columns(), expectedResult.columns());
            MatchLevel columnsCountMatchLevel = evaluateColumnsCountMatchLevel(userResult.columns(), expectedResult.columns());
            MatchLevel rowsCountMatchLevel = evaluateRowsCountMatchLevel(userResult.rows().size(), expectedResult.rows().size());
            MatchLevel rowsDataMatchLevel = evaluateRowsDataMatchLevel(userResult.rows(), expectedResult.rows());
            MatchLevel rowsOrderMatchLevel = evaluateRowsOrderMatchLevel(userResult.rows(), expectedResult.rows());

            return new Feedback(columnsNamesMatchLevel, columnsCountMatchLevel, rowsCountMatchLevel, rowsDataMatchLevel, rowsOrderMatchLevel);

        } catch (SQLException e) {
            // Error de conexión o script corrupto en DataContext
            return new Feedback(MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE, MatchLevel.NONE);
        }
    }

    /**
     * Procesa la consulta SQL en una sola lectura de ResultSet, capturando excepciones de sintaxis.
     */
    private QueryResult executeQuery(Statement stmt, String sql) {
        try (ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(metaData.getColumnLabel(i).toLowerCase());
            }

            List<Map<String, Object>> rows = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    // Normalizamos a String para evitar incompatibilidades numéricas en equals (ej. Integer vs Long)
                    row.put(metaData.getColumnLabel(i).toLowerCase(), value != null ? value.toString() : "NULL");
                }
                rows.add(row);
            }

            return new QueryResult(columns, rows, true);
        } catch (SQLException e) {
            return new QueryResult(List.of(), List.of(), false);
        }
    }

    private MatchLevel evaluateColumnsCountMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }
        return columns.size() == columns2.size() ? MatchLevel.EXACT : MatchLevel.NONE;
    }

    private MatchLevel evaluateColumnsNamesMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }

        // Exacto: Mismos nombres y exactamente en el mismo orden
        if (columns.equals(columns2)) {
            return MatchLevel.EXACT;
        }

        // Parcial: Al menos una columna del usuario está entre las esperadas
        Set<String> expectedSet = columns2.stream().collect(Collectors.toSet());
        boolean hasAnyMatch = columns.stream().anyMatch(expectedSet::contains);

        return hasAnyMatch ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsCountMatchLevel(int userRowCount, int expectedRowCount) {
        if (userRowCount == expectedRowCount && expectedRowCount > 0) {
            return MatchLevel.EXACT;
        }
        return userRowCount > 0 ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsDataMatchLevel(List<Map<String, Object>> userRows, List<Map<String, Object>> expectedRows) {
        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }

        Set<Map<String, Object>> userSet = userRows.stream().collect(Collectors.toSet());
        Set<Map<String, Object>> expectedSet = expectedRows.stream().collect(Collectors.toSet());

        // Mismo conjunto de filas (sin importar el orden)
        if (userSet.equals(expectedSet)) {
            return MatchLevel.EXACT;
        }

        // Coincidencia parcial: Al menos una fila es idéntica
        boolean hasCommonRows = userSet.stream().anyMatch(expectedSet::contains);
        return hasCommonRows ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsOrderMatchLevel(
        List<Map<String, Object>> userRows, 
        List<Map<String, Object>> expectedRows) {

        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }

        if (userRows.equals(expectedRows)) {
            return MatchLevel.EXACT;
        }

        // PARTIAL: el orden relativo se conserva aunque haya filas intermedias
        List<Map<String, Object>> shorter = userRows.size() <= expectedRows.size() ? userRows : expectedRows;
        List<Map<String, Object>> longer = userRows.size() <= expectedRows.size() ? expectedRows : userRows;

        return isRelativeOrderPreserved(shorter, longer) ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    /**
     * Comprueba si {@code subsequence} aparece en {@code sequence} en el mismo orden relativo
     * (no tiene que ser contiguo).
     */
    private boolean isRelativeOrderPreserved(
            List<Map<String, Object>> subsequence,
            List<Map<String, Object>> sequence) {
        int fromIndex = 0;
        for (Map<String, Object> row : subsequence) {
            int foundAt = -1;
            for (int i = fromIndex; i < sequence.size(); i++) {
                if (sequence.get(i).equals(row)) {
                    foundAt = i;
                    break;
                }
            }
            if (foundAt == -1) {
                return false;
            }
            fromIndex = foundAt + 1;
        }
        return true;
    }

    private record QueryResult(
            List<String> columns,
            List<Map<String, Object>> rows,
            boolean isSuccess
    ) {}
}