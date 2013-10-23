START TRANSACTION;

DROP TABLE IF EXISTS users;


CREATE TABLE users(
  id  VARCHAR(20) PRIMARY KEY,
  password VARCHAR(60) NOT NULL,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  won int(6) NOT NULL, 
  lost int(6) NOT NULL,
  abandoned int(6) NOT NULL
);

--Usuario de ejemplo
INSERT INTO users (id, password, name, email,won, lost, abandoned) 
VALUES ('nico', MD5('13588'),'nicolas','nico@reversi.com',0,0,0);

COMMIT;
