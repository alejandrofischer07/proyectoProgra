CREATE DATABASE hotel;


USE hotel;

CREATE TABLE user(
id INT auto_increment,
name VARCHAR (75) NOT NULL,


CONSTRAINT pk_user_id PRIMARY KEY (id)
);


CREATE TABLE hotel.reservaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(100) NOT NULL,
    nombre_cliente VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    numero_personas INT NOT NULL
);


INSERT INTO user(name)VALUES("Alejandro 1");
INSERT INTO user(name)VALUES("Alejandro 2");
INSERT INTO user(name)VALUES("Alejandro 3");

