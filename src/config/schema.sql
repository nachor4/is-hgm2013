START TRANSACTION;

DROP TABLE IF EXISTS users;


CREATE TABLE users(
  id  VARCHAR(20) PRIMARY KEY,
  password VARCHAR(60) NOT NULL,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  ganadas int(6) NOT NULL, 
  perdidas int(6) NOT NULL,
  abandonadas int(6) NOT NULL  
);

COMMIT;
