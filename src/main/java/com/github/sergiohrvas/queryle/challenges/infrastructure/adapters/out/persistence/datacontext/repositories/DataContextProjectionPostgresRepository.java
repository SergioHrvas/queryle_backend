package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.ColumnSchemaView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetTableView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.SchemaView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.TableSchemaView;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Repository
public class DataContextProjectionPostgresRepository implements DataContextProjectionRepository {

    private final JdbcClient jdbcClient;
    private final ObjectMapper objectMapper;

    public DataContextProjectionPostgresRepository(JdbcClient jdbcClient, ObjectMapper objectMapper) {
        this.jdbcClient = jdbcClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(DataContextProjection dataContextProjection) {
        deleteByDataContextId(dataContextProjection.dataContextId());

        jdbcClient.sql(
        """
            INSERT INTO data_context_read_models (
                data_context_id,
                version,
                generated_at
            )
            VALUES (
                :dataContextId,
                :version,
                CURRENT_TIMESTAMP
            )
        """)
        .param("dataContextId", dataContextProjection.dataContextId())
        .param("version", dataContextProjection.version())
        .update();

        for (TableSchemaView table : dataContextProjection.schema().tables()) {
            UUID tableId = UUID.randomUUID();

            jdbcClient.sql(
            """
                INSERT INTO data_context_tables (
                    id,data_context_id,
                    name
                )
                VALUES (
                    :id,
                    :dataContextId,
                    :tableName
                )
            """)
            .param("id", tableId)
            .param("dataContextId", dataContextProjection.dataContextId())
            .param("tableName", table.name())
            .update();

            for (ColumnSchemaView column : table.columns()) {
                jdbcClient.sql(
                """
                    INSERT INTO data_context_columns (
                        id,
                        table_id,
                        name,
                        type,
                        nullable,
                        primary_key,
                        position
                    )
                    VALUES (
                        :id,
                        :tableId,
                        :name,
                        :type,
                        :nullable,
                        :primaryKey,
                        :position
                    )
                """)
                .param("id", UUID.randomUUID())
                .param("tableId", tableId)
                .param("name", column.name())
                .param("type", column.type())
                .param("nullable", column.isNullable())
                .param("primaryKey", column.isPrimaryKey())
                .param("position", column.position())
                .update();
            }

            DatasetTableView datasetTable = dataContextProjection.dataset()
                    .tables().stream()
                    .filter(t -> t.name().equals(table.name()))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalStateException(
                                    "Dataset table not found for table: " + table.name()));

            for (int rowPosition = 0; rowPosition < datasetTable.rows().size(); rowPosition++) {
                String rowDataJson;

                try {
                    rowDataJson = objectMapper.writeValueAsString(datasetTable.rows().get(rowPosition));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to serialize row data to JSON", e);
                }

                jdbcClient.sql(
                """
                    INSERT INTO data_context_rows (
                        id,
                        table_id,
                        position,
                        row_data
                    )
                    VALUES (
                        :id,
                        :tableId,
                        :position,
                        CAST(:rowData AS jsonb)
                    )
                """)
                .param("id", UUID.randomUUID())
                .param("tableId", tableId)
                .param("rowData", rowDataJson)
                .param("position", rowPosition)
                .update();
            }
        }
    }

    @Override
    public Optional<DataContextProjection> findByDataContextId(UUID dataContextId) {
        Optional<DataContextProjectionHeader> header = jdbcClient.sql("""
            SELECT data_context_id, version
            FROM data_context_read_models
            WHERE data_context_id = :dataContextId
            """)
            .param("dataContextId", dataContextId)
            .query((row, rowNum) -> new DataContextProjectionHeader(
                row.getObject("data_context_id", UUID.class),
                row.getLong("version")
            ))
            .optional();

        if (header.isEmpty()) {
            return Optional.empty();
        }

        List<TableSchemaView> schemaTables = new ArrayList<>();
        List<DatasetTableView> datasetTables = new ArrayList<>();

        List<TableRow> tables = jdbcClient.sql("""
            SELECT id, name
            FROM data_context_tables
            WHERE data_context_id = :dataContextId
            ORDER BY name
            """)
            .param("dataContextId", dataContextId)
            .query((row, rowNum) -> new TableRow(
                row.getObject("id", UUID.class),
                row.getString("name")
            ))
            .list();

        for (TableRow table : tables) {
            List<ColumnSchemaView> columns = jdbcClient.sql("""
                SELECT name, type, nullable, primary_key, position
                FROM data_context_columns
                WHERE table_id = :tableId
                ORDER BY position
                """)
                .param("tableId", table.id())
                .query((row, rowNum) -> new ColumnSchemaView(
                    row.getString("name"),
                    row.getString("type"),
                    row.getBoolean("nullable"),
                    row.getBoolean("primary_key"),
                    row.getInt("position")
                ))
                .list();

            schemaTables.add(new TableSchemaView(table.name(), columns));

            List<List<Object>> rows = jdbcClient.sql("""
                SELECT row_data
                FROM data_context_rows
                WHERE table_id = :tableId
                ORDER BY position
                """)
                .param("tableId", table.id())
                .query((row, rowNum) -> {
                    String rowData = row.getString("row_data");

                    try {
                        return objectMapper.readValue(
                            rowData,
                            new TypeReference<List<Object>>() {}
                        );
                    } catch (Exception exception) {
                        throw new IllegalStateException(
                            "Could not deserialize projection row for table: "
                                + table.name(),
                            exception
                        );
                    }
                })
                .list();

            List<String> columnNames = columns.stream()
                .map(ColumnSchemaView::name)
                .toList();

            datasetTables.add(new DatasetTableView(
                table.name(),
                columnNames,
                rows
            ));
        }

        DataContextProjection projection = new DataContextProjection(
            header.get().dataContextId(),
            new SchemaView(schemaTables),
            new DatasetView(datasetTables),
            header.get().version()
        );

        return Optional.of(projection);
    }

    @Override
    public void deleteByDataContextId(UUID dataContextId) {
        jdbcClient.sql(
        """
            DELETE FROM data_context_read_models
            WHERE data_context_id = :dataContextId
        """)
        .param("dataContextId", dataContextId)
        .update();
    }

    private record DataContextProjectionHeader(
            UUID dataContextId,
            long version
    ) {}
    private record TableRow(
            UUID id,
            String name
    ) {}
}
