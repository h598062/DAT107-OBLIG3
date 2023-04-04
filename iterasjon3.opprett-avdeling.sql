DROP SCHEMA IF EXISTS OBLIG3 CASCADE;
CREATE SCHEMA OBLIG3;
SET search_path TO OBLIG3;

CREATE TABLE Ansatt
(
    id              SERIAL PRIMARY KEY,
    brukernavn      VARCHAR(5) UNIQUE NOT NULL,
    fornavn         VARCHAR(20)       NOT NULL,
    etternavn       VARCHAR(30)       NOT NULL,
    ansettelsesdato DATE              NOT NULL,
    stilling        VARCHAR(20)       NOT NULL,
    maanedslonn     INT               NOT NULL CHECK ( maanedslonn > 0 )
);

CREATE TABLE Avdeling
(
    id             SERIAL PRIMARY KEY,
    navn           VARCHAR(20) UNIQUE NOT NULL,
    avdelingsleder INT                NOT NULL,
    CONSTRAINT AnsattFK FOREIGN KEY (avdelingsleder) REFERENCES Ansatt (id)
);

INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES ('bjhe', 'Bjørnar', 'Helgeland', '2021-08-15', 'code-monkey', 30000),
       ('krbe', 'Kristian', 'Bell', '2021-08-15', 'maskot', 25000),
       ('stsa', 'Storm', 'Sangolt', '2021-08-15', '"var tilstede"', 50000);

INSERT INTO Avdeling
    (navn, avdelingsleder)
VALUES ('Beste avdeling', 1);

SELECT *
FROM Ansatt;

SELECT *
FROM Avdeling;
