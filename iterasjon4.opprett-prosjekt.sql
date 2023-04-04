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

CREATE TABLE Prosjekt
(
    id          SERIAL PRIMARY KEY,
    navn        VARCHAR(20) UNIQUE NOT NULL,
    beskrivelse TEXT               NOT NULL
);

-- Koblingstabell for Prosjekt-Ansatt, inspirasjon hentet fra f5_eks3a
CREATE TABLE Timetabell
(
    ansattid   INT,
    prosjektid INT,
    timer      INT         NOT NULL CHECK ( timer > 0 ),
    rolle      VARCHAR(30) NOT NULL,
    CONSTRAINT TimetabellPK PRIMARY KEY (ansattid, prosjektid), -- Sammensatt nøkkel av ansattid og prosjektid, blir en unik nøkkel
    CONSTRAINT AnsattFK FOREIGN KEY (ansattid) REFERENCES Ansatt (id),
    CONSTRAINT ProsjektFK FOREIGN KEY (prosjektid) REFERENCES Prosjekt (id)
);

INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES ('bjhe', 'Bjørnar', 'Helgeland', '2021-08-15', 'code-monkey', 30000),
       ('krbe', 'Kristian', 'Bell', '2021-08-15', 'maskot', 25000),
       ('stsa', 'Storm', 'Sangolt', '2021-08-15', '"var tilstede"', 50000);

INSERT INTO Avdeling
    (navn, avdelingsleder)
VALUES ('Beste avdeling', 1);

INSERT INTO Prosjekt
    (navn, beskrivelse)
VALUES ('Finne beste pokemon',
        'Etter lang diskusjon har vi funnet ut at vi trenger å sette av tid til å finne ut hva som er den beste pokemonen. Dette er vår nr. 1 prioritet framover.');

INSERT INTO Timetabell(ansattid, prosjektid, timer, rolle)
VALUES (1, 1, 200, 'Hoved-etterforsker'),
       (2, 1, 4, 'Sitter der og e fin'),
       (3, 1, 1, 'Møtte opp ene dagen');

SELECT *
FROM Ansatt;

SELECT *
FROM Avdeling;
