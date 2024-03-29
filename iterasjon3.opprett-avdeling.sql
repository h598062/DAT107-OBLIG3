DROP SCHEMA IF EXISTS OBLIG3 CASCADE;
CREATE SCHEMA OBLIG3;
SET search_path TO OBLIG3;

-- Vi får en høna eller egget situasjon her hvor hver aveling må ha minst en ansatt,
-- og hver ansatt må ha en avdeling. Vi må legge de inn en etter en og kan da ikkje oppfylle kravene

-- Henter insiprasjon fra F6, legger inn constraint etter å ha laget en avdeling og en ansatt.

-- Tenker ansatt nr 1 og avdeling nr 1 kan være en slags "default"
-- som vi kan bruke underveis ved oppretting av andre nye avdelinger etc...
-- Da kan vi og evt. legge in tester/sjekker som ser om de dukker opp i diverse utskrifter de ikke skal
-- som vil da vise at det har oppstått en feil i systemet.
-- de kan lett filtreres ut med et where statement: **** WHERE id > 1
-- siden de skal ha id 1 begge to

CREATE TABLE Avdeling
(
    id             SERIAL PRIMARY KEY,
    navn           VARCHAR(20) UNIQUE NOT NULL,
    avdelingsleder INTEGER            NOT NULL
);

CREATE TABLE Ansatt
(
    id              SERIAL PRIMARY KEY,
    brukernavn      VARCHAR(5) UNIQUE NOT NULL,
    fornavn         VARCHAR(20)       NOT NULL,
    etternavn       VARCHAR(30)       NOT NULL,
    ansettelsesdato DATE              NOT NULL,
    stilling        VARCHAR(20)       NOT NULL,
    maanedslonn     INTEGER           NOT NULL CHECK ( maanedslonn > 0 ),
    avdeling        INTEGER           NOT NULL,                           -- ny i iterasjon 3
    CONSTRAINT AvdelingFK FOREIGN KEY (avdeling) REFERENCES Avdeling (id) -- ny i iterasjon 3
);

-- default tmp avdeling
INSERT INTO Avdeling(navn, avdelingsleder)
VALUES ('default', 1);

-- default tmp ansatt
INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn, avdeling)
VALUES ('tmp', 'DoNotUse', 'DoNotUse', '2023-04-01', 'default', 1, 1);

-- lag referanse fra avdelig til ansatt
ALTER TABLE Avdeling
    ADD CONSTRAINT AnsattFK FOREIGN KEY (avdelingsleder) REFERENCES Ansatt (id);

-- test avdeling
INSERT INTO Avdeling
    (navn, avdelingsleder)
VALUES ('Beste avdeling', 1),
       ('Værste avdeling', 1);

-- test ansatte
INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn, avdeling)
VALUES ('bjhe', 'Bjørnar', 'Helgeland', '2021-08-15', 'code-monkey', 30000, 2),
       ('krbe', 'Kristian', 'Bell', '2021-08-15', 'maskot', 25000, 3),
       ('stsa', 'Storm', 'Sangolt', '2021-08-15', '"var tilstede"', 50000, 2);

-- oppdater avdelingsleder i test avdeling til en av test ansatte
UPDATE Avdeling
SET avdelingsleder = 2
WHERE navn = 'Beste avdeling';

UPDATE Avdeling
SET avdelingsleder = 3
WHERE navn = 'Værste avdeling';

-- skriv ut alle ansatte
SELECT *
FROM Ansatt;

-- skriv ut alle avdelinger og dens avdelingsleder
SELECT *
FROM Avdeling
         INNER JOIN Ansatt A ON A.id = Avdeling.avdelingsleder;
