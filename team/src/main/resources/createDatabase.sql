CREATE DATABASE ingWeb;
GRANT ALL ON ingWeb.* TO 'mapyoururl'@localhost identified BY 'toor';
USE ingWeb;
CREATE TABLE urls (
  id int(11) AUTO_INCREMENT NOT NULL,
  url VARCHAR(500) DEFAULT NULL ,
  urlAcortada VARCHAR(100) DEFAULT NULL,
  clicks INTEGER DEFAULT NULL,
  latitud FLOAT (10,6) DEFAULT NULL,
  longitud FLOAT (10,6) DEFAULT NULL,
  PRIMARY KEY (id)
);