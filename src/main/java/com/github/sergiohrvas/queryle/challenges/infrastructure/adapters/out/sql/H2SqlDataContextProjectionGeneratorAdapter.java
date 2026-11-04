package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.DataContextProjectionGeneratorPort;
import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.ColumnSchemaView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetTableView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.SchemaView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.TableSchemaView;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql.exceptions.SqlQueryExecutionException;


@Component
public class H2SqlDataContextProjectionGeneratorAdapter implements DataContextProjectionGeneratorPort {
    
    private static final long PROJECTION_VERSION = 1L;
    
    private final H2SqlScriptExecutor scriptExecutor;

    public H2SqlDataContextProjectionGeneratorAdapter(H2SqlScriptExecutor scriptExecutor) {
        this.scriptExecutor = scriptExecutor;
    }

    @Override
    public DataContextProjection generate(
            DataContext dataContext) {
        String memDbUrl = "jdbc:h2:mem:eval_" + UUID.randomUUID().toString().replace("-", "") + ";DB_CLOSE_DELAY=0";

        try (Connection connection = DriverManager.getConnection(memDbUrl)){

            scriptExecutor.execute(connection.createStatement(), dataContext.getSchema().schema());
            scriptExecutor.execute(connection.createStatement(), dataContext.getPublicDataset().dataFixture());

            SchemaView schema = extractSchema(connection);
            DatasetView dataset = extractDataset(connection, schema);
            return new DataContextProjection(dataContext.getId(), schema, dataset, PROJECTION_VERSION);
        } catch (SQLException e) {
            throw new SqlQueryExecutionException(
                "Could not generate projection for DataContext " + dataContext.getId(), e
            );
        }
    }

    /**
     * Extracts the schema from the database using the provided Connection and
     * Statement objects.
     * 
     * @param conn
     * @return
     */
    private SchemaView extractSchema(Connection conn) throws SQLException {
        List<String> tables = getTables(conn);
        List<TableSchemaView> tableSchemas = new ArrayList<>();

        for (int index = 0; index < tables.size(); index++) {
            String tableName = tables.get(index);
            List<ColumnSchemaView> columns = getColumns(conn, tableName);
            tableSchemas.add(new TableSchemaView(tableName, columns));
        }

        return new SchemaView(tableSchemas);
    }

    /**
     * Extracts the dataset from the database using the provided Connection and SchemaView.
     * @param connection
     * @param schema
     * @return
     * @throws SQLException
     */
    private DatasetView extractDataset(
            Connection connection,
            SchemaView schema
    ) throws SQLException {

        List<DatasetTableView> tables = new ArrayList<>();

        for (TableSchemaView table : schema.tables()) {
            tables.add(extractDatasetTable(
                connection,
                table.name(),
                table.columns()
            ));
        }

        return new DatasetView(tables);
    }

    /**
     * Extracts the dataset table from the database using the provided Connection,
     * table name, and list of columns.
     * @param connection
     * @param tableName
     * @param columns
     * @return
     * @throws SQLException
     */
    private DatasetTableView extractDatasetTable(
            Connection connection,
            String tableName,
            List<ColumnSchemaView> columns) throws SQLException {

        List<String> columnNames = columns.stream()
                .map(ColumnSchemaView::name)
                .toList();

        String selectedColumns = columnNames.stream()
                .map(this::quoteIdentifier)
                .collect(Collectors.joining(", "));

        String sql = "SELECT " + selectedColumns
                + " FROM " + quoteIdentifier("PUBLIC")
                + "." + quoteIdentifier(tableName);

        List<List<Object>> rows = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();

                for (int index = 1; index <= columnNames.size(); index++) {

                    Object value = resultSet.getObject(index);
                    row.add(toProjectionValue(value));
                }

                rows.add(row);
            }
        }

        return new DatasetTableView(
                tableName,
                columnNames,
                rows);
    }

    /**
     * Converts the given value to a suitable representation for the projection.
     * @param value
     * @return
     */
    private String quoteIdentifier(String identifier) {
        if (!identifier.matches("[A-Za-z_][A-Za-z0-9_]*")) {
            throw new IllegalArgumentException(
                "Invalid SQL identifier: " + identifier
            );
        }

        return "\"" + identifier + "\"";
    }

    /**
     * Converts the given value to a suitable representation for the projection.
     * @param value
     * @return
     */
    private Object toProjectionValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        if (value instanceof Date date) {
            return date.toLocalDate();
        }

        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }

        if (value instanceof Time time) {
            return time.toLocalTime();
        }

        if (value instanceof Number
                || value instanceof String
                || value instanceof Boolean) {
            return value;
        }

        return value.toString();
    }

    /**
     * Retrieves the list of table names from the database using the provided
     * Connection object.
     * 
     * @param conn
     * @return
     * @throws SQLException
     */
    private List<String> getTables(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        List<String> tableNames = new ArrayList<>();

        try (ResultSet tables = metaData.getTables(null, "PUBLIC", "%", new String[] { "TABLE" })) {
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        }

        tableNames.sort(String::compareTo);

        return tableNames;
    }

    List<ColumnSchemaView> getColumns(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        Set<String> primaryKeyColumns = findPrimaryKeyColumns(
                metaData,
                tableName);
        List<ColumnSchemaView> columns = new ArrayList<>();

        try (ResultSet rs = metaData.getColumns(null, "PUBLIC", tableName, "%")) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");

                boolean isNullable = "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"));
                int position = rs.getInt("ORDINAL_POSITION");
                columns.add(
                        new ColumnSchemaView(
                                columnName,
                                columnType,
                                isNullable,
                                primaryKeyColumns.contains(columnName),
                                position));
            }
        }
        
        columns.sort(Comparator.comparingInt(ColumnSchemaView::position));
        return columns;
    }

    private Set<String> findPrimaryKeyColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        Set<String> primaryKeyColumns = new HashSet<>();
        try (ResultSet rs = metaData.getPrimaryKeys(null, "PUBLIC", tableName)) {
            while (rs.next()) {
                primaryKeyColumns.add(rs.getString("COLUMN_NAME"));
            }
        }
        return primaryKeyColumns;
    }

}
