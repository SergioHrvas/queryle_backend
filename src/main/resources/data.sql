INSERT INTO data_contexts (id, name, description, seed_script) VALUES (
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

    INSERT INTO pedidos_pizza (cliente, pizza, tamano, tipo_masa, anadidos, precio, estado, fecha_pedido) VALUES
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
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id, sequence) VALUES (
    'e8d68ad2-3907-4d1a-8d68-8976176cfa0e',
    'Lista todos los pedidos que ya han sido entregados.',
    'SELECT * FROM pedidos_pizza WHERE estado = ''Entregado'';',
    'EASY',
    DATE '2026-08-10',
    'db14245f-7334-4268-a821-4663d5529da8',
    1
);

-- MEDIUM: proyección + filtro + ORDER BY
-- Familiar: Ana 14.50, Lucía 14.00, Javier 13.50, Diego 13.00
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id, sequence) VALUES (
    'a1b2c3d4-1111-4222-8333-444455556666',
    'Muestra el cliente, la pizza y el precio de los pedidos de tamaño Familiar, ordenados de más caro a más barato.',
    'SELECT cliente, pizza, precio FROM pedidos_pizza WHERE tamano = ''Familiar'' ORDER BY precio DESC;',
    'MEDIUM',
    DATE '2026-08-10',
    'db14245f-7334-4268-a821-4663d5529da8',
    2
);

-- HARD: agregación
INSERT INTO daily_challenges (id, formulation, query, difficulty, publication_date, data_context_id, sequence) VALUES (
    'b2c3d4e5-2222-4333-8444-555566667777',
    'Calcula cuántos pedidos hay por estado, ordenados alfabéticamente por estado.',
    'SELECT estado, COUNT(*) AS total FROM pedidos_pizza GROUP BY estado ORDER BY estado;',
    'HARD',
    DATE '2026-08-10',
    'db14245f-7334-4268-a821-4663d5529da8',
    3
);
