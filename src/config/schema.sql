START TRANSACTION;

CREATE DATABASE IF NOT EXISTS Ingenieria;

USE Ingenieria;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
  id  VARCHAR(20) PRIMARY KEY,
  password VARCHAR(32) NOT NULL,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  won int(6) NOT NULL DEFAULT 0, 
  lost int(6) NOT NULL DEFAULT 0,
  abandoned int(6) NOT NULL DEFAULT 0
);

TRUNCATE users;

-- Usuario de ejemplo
INSERT INTO users (id, password, name, email,won, lost, abandoned) 
VALUES ('nico', MD5('13588') ,'nicolas','nico@reversi.com',1,1,1);

COMMIT;
