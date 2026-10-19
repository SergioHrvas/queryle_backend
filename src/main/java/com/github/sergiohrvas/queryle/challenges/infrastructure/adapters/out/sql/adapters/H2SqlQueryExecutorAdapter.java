package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql.adapters;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql.exceptions.SqlQueryExecutionException;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryComparisonResult;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryExecutionStatus;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.ports.SqlQueryExecutorPort;

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
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class H2SqlQueryExecutorAdapter implements SqlQueryExecutorPort {
    @Override
    public QueryComparisonResult compare(
        Query submittedQuery,
        Query expectedQuery,
        Schema schema,
        Dataset dataset
    ) {
        String memDbUrl = "jdbc:h2:mem:eval_" + UUID.randomUUID().toString().replace("-", "") + ";DB_CLOSE_DELAY=0";
        
        try (Connection connection = DriverManager.getConnection(memDbUrl);
             Statement stmt = connection.createStatement()) {

            executeScript(stmt, schema.schema());
            executeScript(stmt, dataset.dataFixture());

            QueryResult expected = executeQuery(stmt, expectedQuery.value());
            QueryResult actual = executeQuery(stmt, submittedQuery.value());

            return new QueryComparisonResult(expected, actual);
        } catch (SQLException e) {
            throw new SqlQueryExecutionException(
                "Could not prepare SQL execution environment",
                e
            );
        }
    }

    /**
     * Executes a SQL script using the provided Statement object.
     */
    private void executeScript(Statement stmt, String sql) throws SQLException {
        // Split the script into individual statements based on semicolons
        String[] statements = sql.split(";");
        for (String statement : statements) {
            String trimmedStatement = statement.trim();
            if (!trimmedStatement.isEmpty()) {
                stmt.execute(trimmedStatement);
            }
        }
    }

    /**
     * Process the SQL query and handle syntax errors.
     */
    private QueryResult executeQuery(Statement stmt, String sql) {
        try {
            stmt.setQueryTimeout(2);
            try (ResultSet rs = stmt.executeQuery(sql)) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                List<String> columns = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columns.add(metaData.getColumnLabel(i).toLowerCase());
                }

                List<Map<String, String>> rows = new ArrayList<>();
                while (rs.next()) {
                    Map<String, String> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        row.put(metaData.getColumnLabel(i).toLowerCase(), Objects.nonNull(value) ? value.toString() : "NULL");
                    }
                    rows.add(row);
                }

                return new QueryResult(columns, rows, QueryExecutionStatus.SUCCESS);
            }
        } catch (SQLException e) {
            return new QueryResult(List.of(), List.of(), QueryExecutionStatus.INVALID_QUERY);
        }
    }
}