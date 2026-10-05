CREATE DATABASE IF NOT EXISTS codejavu;
USE codejavu;

CREATE TABLE IF NOT EXISTS persona (
    id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    profesion VARCHAR(100) NOT NULL,
    telefono BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO persona (id, nombre, edad, profesion, telefono) VALUES
(1, 'Juan Perez', 28, 'Ingeniero de Software', 3001234567),
(2, 'Maria Gomez', 25, 'Disenadora Grafica', 3109876543),
(3, 'Carlos Rodriguez', 32, 'Arquitecto de Soluciones', 3205551234)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), edad=VALUES(edad), profesion=VALUES(profesion), telefono=VALUES(telefono);
