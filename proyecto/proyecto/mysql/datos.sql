CREATE DATABASE hotel;


USE hotel;

CREATE TABLE user(
id INT auto_increment,
name VARCHAR (75) NOT NULL,


CONSTRAINT pk_user_id PRIMARY KEY (id)
);

INSERT INTO user(name)VALUES("Alejandro 1");
INSERT INTO user(name)VALUES("Alejandro 2");
INSERT INTO user(name)VALUES("Alejandro 3");