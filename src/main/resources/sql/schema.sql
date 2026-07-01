- Creación de la base de datos
CREATE DATABASE desguaces;

-- Selección de la base de datos
USE desguaces;

-- Tabla de coches (cars)
CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    engine_specification VARCHAR(100) NOT NULL
);

-- Tabla de piezas (parts)
CREATE TABLE parts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL -- Tipo de pieza: motor, turbo, refrigeración, etc.
);

-- Tabla de desguaces (scrap_yards)
CREATE TABLE scrap_yards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL -- Ubicación del desguace
);

-- Relación entre coches y piezas (parts_cars)
CREATE TABLE parts_cars (
    car_id INT,
    part_id INT,
    PRIMARY KEY (car_id, part_id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (part_id) REFERENCES parts(id)
);

-- Relación entre piezas y desguaces (parts_scrap_yard)
CREATE TABLE parts_scrap_yard (
    part_id INT,
    scrap_yard_id INT,
    wear_level VARCHAR(50) NOT NULL, -- Desgaste de la pieza
    price DECIMAL(10,2) NOT NULL,    -- Precio de la pieza
    PRIMARY KEY (part_id, scrap_yard_id),
    FOREIGN KEY (part_id) REFERENCES parts(id),
    FOREIGN KEY (scrap_yard_id) REFERENCES scrap_yards(id)
);

-- Tabla para almacenar el tipo de piezas (por ejemplo: motor, turbo, refrigeración, etc.)
CREATE TABLE parts_category(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
); 