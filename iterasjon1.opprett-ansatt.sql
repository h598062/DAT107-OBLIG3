DROP SCHEMA IF EXISTS OBLIG3 CASCADE;
CREATE SCHEMA OBLIG3;
SET search_path TO OBLIG3;

CREATE TABLE Ansatt
(
    ansattid        SERIAL PRIMARY KEY,
    brukernavn      VARCHAR(5) UNIQUE NOT NULL,
    fornavn         VARCHAR(20) NOT NULL,
    etternavn       VARCHAR(30) NOT NULL,
    ansettelsesdato DATE NOT NULL,
    stilling        VARCHAR(20) NOT NULL,
    maanedslonn     INT NOT NULL
);

INSERT INTO
  Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES
    ('bjhe', 'Bjørnar', 'Helgeland', '2021-08-15', 'code-monkey', 30000),
    ('krbe', 'Kristian', 'Bell', '2021-08-15', 'maskot', 25000),
    ('stsa', 'Storm', 'Sangolt', '2021-08-15', '"var tilstede"', 50000);
SELECT * FROM Ansatt;