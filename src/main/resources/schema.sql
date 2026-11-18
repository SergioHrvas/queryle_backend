CREATE TABLE IF NOT EXISTS data_context_read_models (
    data_context_id UUID PRIMARY KEY,
    version BIGINT NOT NULL,
    generated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS data_context_tables (
    id UUID PRIMARY KEY,
    data_context_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_projection_table_context
        FOREIGN KEY (data_context_id)
        REFERENCES data_context_read_models(data_context_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_projection_table_name
        UNIQUE (data_context_id, name)
);

CREATE TABLE IF NOT EXISTS data_context_columns (
    id UUID PRIMARY KEY,
    table_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    nullable BOOLEAN NOT NULL,
    primary_key BOOLEAN NOT NULL,
    position INTEGER NOT NULL,
    CONSTRAINT fk_projection_column_table
        FOREIGN KEY (table_id)
        REFERENCES data_context_tables(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_projection_column_position
        UNIQUE (table_id, position),
    CONSTRAINT uq_projection_column_name
        UNIQUE (table_id, name)
);

CREATE TABLE IF NOT EXISTS data_context_rows (
    id UUID PRIMARY KEY,
    table_id UUID NOT NULL,
    position INTEGER NOT NULL,
    row_data JSONB NOT NULL,
    CONSTRAINT fk_projection_row_table
        FOREIGN KEY (table_id)
        REFERENCES data_context_tables(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_projection_row_position
        UNIQUE (table_id, position)
);
