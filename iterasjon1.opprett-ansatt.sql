DROP SCHEMA IF EXISTS OBLIG3 CASCADE;
CREATE SCHEMA OBLIG3;
SET search_path TO OBLIG3;

CREATE TABLE Ansatt
(
    ansattid        INT,
    brukernavn      VARCHAR(4),
    fornavn         VARCHAR(20),
    etternavn       VARCHAR(30),
    ansettelsesdato DATE,
    stilling        VARCHAR(20),
    maanedslonn     INT
);

INSERT INTO
  Ansatt(ansattid, brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES
    (1, 'bjhe', 'Bjørnar', 'Helgeland', '2021-08-15', 'code-monkey', 30000),
    (2, 'krbe', 'Kristian', 'Bell', '2021-08-15', 'maskot', 25000),
    (3, 'stsa', 'Storm', 'Sangolt', '2021-08-15', '"var tilstede"', 50000);
SELECT * FROM Ansatt;