INSERT INTO data_contexts (id, name, description, seed_script_schema, seed_script_data_fixture) VALUES (
    'db14245f-7334-4268-a821-4663d5529da8',
    'Pizza shop',
    'Pizza shop description',
    'CREATE TABLE pedidos_pizza (
        pedido_id SERIAL PRIMARY KEY,
        cliente VARCHAR(50) NOT NULL,
        pizza VARCHAR(50) NOT NULL,
        tamano VARCHAR(20) NOT NULL,
        tipo_masa VARCHAR(30) DEFAULT ''Tradicional'',
        anadidos TEXT,
        precio NUMERIC(5, 2) NOT NULL,
        estado VARCHAR(20) DEFAULT ''Pendiente'',
        fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    ',
    'INSERT INTO pedidos_pizza (cliente, pizza, tamano, tipo_masa, anadidos, precio, estado, fecha_pedido) VALUES
        (''Carlos Gómez'', ''Margarita'', ''Mediana'', ''Tradicional'', ''Extra queso'', 9.50, ''Entregado'', ''2026-08-02 19:15:00''),
        (''Ana Martínez'', ''Barbacoa'', ''Familiar'', ''Rellena de queso'', ''Bacon extra, Cebolla caramelizada'', 14.50, ''En camino'', ''2026-08-02 20:00:00''),
        (''Luis Fernández'', ''Cuatro Quesos'', ''Mediana'', ''Fina'', ''Bordes rellenos de queso'', 12.50, ''En preparación'', ''2026-08-02 20:20:00''),
        (''Sofía López'', ''Pepperoni'', ''Personal'', ''Tradicional'', ''Jalapeños'', 10.50, ''Pendiente'', ''2026-08-02 20:35:00''),
        (''Diego Torres'', ''Hawaiana'', ''Familiar'', ''Gruesa'', ''Sin piña, Extra jamón'', 13.00, ''Entregado'', ''2026-08-02 18:45:00''),
        (''María Rodríguez'', ''Vegetariana'', ''Mediana'', ''Integral'', ''Champiñones extra, Aceitunas'', 10.00, ''En camino'', ''2026-08-02 20:10:00''),
        (''Javier Ruiz'', ''Carbonara'', ''Familiar'', ''Fina'', ''Salsa de ajo extra'', 13.50, ''En preparación'', ''2026-08-02 20:25:00''),
        (''Elena Gómez'', ''Margarita'', ''Personal'', ''Tradicional'', ''Orégano extra'', 8.50, ''Entregado'', ''2026-08-02 19:30:00''),
        (''Pablo Navarro'', ''Barbacoa'', ''Mediana'', ''Rellena de queso'', ''Extra carne picada'', 12.50, ''Pendiente'', ''2026-08-02 20:40:00''),
        (''Lucía Blanco'', ''Cuatro Estaciones'', ''Familiar'', ''Tradicional'', ''Extra pepperoni'', 14.00, ''Entregado'', ''2026-08-02 19:00:00'');'
);

-- EASY: filtro simple (4 filas: Carlos, Diego, Elena, Lucía)
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id) VALUES (
    'e8d68ad2-3907-4d1a-8d68-8976176cfa0e',
    'Lista todos los pedidos que ya han sido entregados.',
    'SELECT * FROM pedidos_pizza WHERE estado = ''Entregado'';',
    'EASY',
    CURRENT_DATE,
    'db14245f-7334-4268-a821-4663d5529da8'
);

-- MEDIUM: proyección + filtro + ORDER BY
-- Familiar: Ana 14.50, Lucía 14.00, Javier 13.50, Diego 13.00
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id) VALUES (
    'a1b2c3d4-1111-4222-8333-444455556666',
    'Muestra el cliente, la pizza y el precio de los pedidos de tamaño Familiar, ordenados de más caro a más barato.',
    'SELECT cliente, pizza, precio FROM pedidos_pizza WHERE tamano = ''Familiar'' ORDER BY precio DESC;',
    'MEDIUM',
    CURRENT_DATE,
    'db14245f-7334-4268-a821-4663d5529da8'
);

-- HARD: agregación
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id) VALUES (
    'b2c3d4e5-2222-4333-8444-555566667777',
    'Calcula cuántos pedidos hay por estado, ordenados alfabéticamente por estado.',
    'SELECT estado, COUNT(*) AS total FROM pedidos_pizza GROUP BY estado ORDER BY estado;',
    'HARD',
    CURRENT_DATE,
    'db14245f-7334-4268-a821-4663d5529da8'
);

-- Datos de prueba para PlayerStatsQueryRepository.
-- Player ID: 11111111-1111-1111-1111-111111111111
-- Resultado esperado:
-- wins=6, losses=2, total_games=8, current_streak=2, longest_streak=3.
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id) VALUES
    ('00000000-0000-0000-0000-000000000010', 'Mock challenge 10', 'SELECT 10', 'EASY', DATEADD('DAY', -6, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000011', 'Mock challenge 11', 'SELECT 11', 'EASY', DATEADD('DAY', -5, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000012', 'Mock challenge 12', 'SELECT 12', 'EASY', DATEADD('DAY', -4, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000013', 'Mock challenge 13', 'SELECT 13', 'EASY', DATEADD('DAY', -3, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000014', 'Mock challenge 14', 'SELECT 14', 'EASY', DATEADD('DAY', -2, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000015', 'Mock challenge 15', 'SELECT 15', 'EASY', DATEADD('DAY', -1, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000016', 'Mock challenge 16', 'SELECT 16', 'EASY', CURRENT_DATE, 'db14245f-7334-4268-a821-4663d5529da8'),
    ('00000000-0000-0000-0000-000000000009', 'Mock late challenge', 'SELECT 9', 'EASY', DATEADD('DAY', -7, CURRENT_DATE), 'db14245f-7334-4268-a821-4663d5529da8');

INSERT INTO games (id, player_id, daily_challenge_id, started_at, status) VALUES
    ('10000000-0000-0000-0000-000000000010', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000010', DATEADD('DAY', -6, CURRENT_DATE), 'COMPLETED_FAILED'),
    ('10000000-0000-0000-0000-000000000011', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000011', DATEADD('DAY', -5, CURRENT_DATE), 'COMPLETED_WON'),
    ('10000000-0000-0000-0000-000000000012', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000012', DATEADD('DAY', -4, CURRENT_DATE), 'COMPLETED_WON'),
    ('10000000-0000-0000-0000-000000000013', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000013', DATEADD('DAY', -3, CURRENT_DATE), 'COMPLETED_WON'),
    ('10000000-0000-0000-0000-000000000014', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000014', DATEADD('DAY', -2, CURRENT_DATE), 'COMPLETED_FAILED'),
    ('10000000-0000-0000-0000-000000000015', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000015', DATEADD('DAY', -1, CURRENT_DATE), 'COMPLETED_WON'),
    ('10000000-0000-0000-0000-000000000016', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000016', CURRENT_DATE, 'COMPLETED_WON'),
    ('10000000-0000-0000-0000-000000000009', '11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000009', CURRENT_DATE, 'COMPLETED_WON');

INSERT INTO attempts (
    id, game_id, query, submitted_at, columns_names_match_level,
    columns_count_match_level, rows_count_match_level,
    rows_data_match_level, rows_order_match_level
) VALUES
    ('20000000-0000-0000-0000-000000000010', '10000000-0000-0000-0000-000000000010', 'SELECT 10', DATEADD('DAY', -6, CURRENT_DATE), 'NONE', 'NONE', 'NONE', 'NONE', 'NONE'),
    ('20000000-0000-0000-0000-000000000011', '10000000-0000-0000-0000-000000000011', 'SELECT 11', DATEADD('DAY', -5, CURRENT_DATE), 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT'),
    ('20000000-0000-0000-0000-000000000012', '10000000-0000-0000-0000-000000000012', 'SELECT 12', DATEADD('DAY', -4, CURRENT_DATE), 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT'),
    ('20000000-0000-0000-0000-000000000013', '10000000-0000-0000-0000-000000000013', 'SELECT 13', DATEADD('DAY', -3, CURRENT_DATE), 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT'),
    ('20000000-0000-0000-0000-000000000014', '10000000-0000-0000-0000-000000000014', 'SELECT 14', DATEADD('DAY', -2, CURRENT_DATE), 'NONE', 'NONE', 'NONE', 'NONE', 'NONE'),
    ('20000000-0000-0000-0000-000000000015', '10000000-0000-0000-0000-000000000015', 'SELECT 15', DATEADD('DAY', -1, CURRENT_DATE), 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT'),
    ('20000000-0000-0000-0000-000000000016', '10000000-0000-0000-0000-000000000016', 'SELECT 16', CURRENT_DATE, 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT'),
    ('20000000-0000-0000-0000-000000000009', '10000000-0000-0000-0000-000000000009', 'SELECT 9', CURRENT_DATE, 'EXACT', 'EXACT', 'EXACT', 'EXACT', 'EXACT');
