CREATE DATABASE forage;
USE forage;

CREATE TABLE client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(200),
    mail VARCHAR(200),
    role VARCHAR(50),
    mot_de_passe VARCHAR(200)
);

CREATE TABLE statut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(200)
);

CREATE TABLE demande (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle_demande VARCHAR(200),
    id_demandeur INT,
    reference VARCHAR(200) UNIQUE,
    lieu_demande VARCHAR(200),
    id_commune INT,
    date_demande DATETIME NOT NULL,
    FOREIGN KEY (id_commune) REFERENCES commune(id),
    FOREIGN KEY (id_demandeur) REFERENCES client(id)
);

CREATE TABLE demande_statut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type_demande INT,
    type_statut INT,
    date_debut DATETIME NOT NULL,
    date_fin DATETIME,
    observation TEXT,
    duree_travail_minute FLOAT,
    FOREIGN KEY (type_demande) REFERENCES demande(id),
    FOREIGN KEY (type_statut) REFERENCES statut(id)
);

INSERT INTO client (nom, mail, role, mot_de_passe)
VALUES
('Admin', 'admin@forage.mg', 'ADMIN', 'admin123'),
('Rabe', 'rabe@forage.mg', 'DEMANDEUR', 'rabe123');

INSERT INTO statut (libelle)
VALUES
('Nouvelle'),
('Demande de devis d\'étude créée'),
('Demande de devis d\'étude refusée'),
('Demande de devis de forage créée'),
('Demande de devis de forage refusée');

INSERT INTO demande_statut (type_demande, type_statut, date_debut, date_fin)
VALUES
(1, 1, '2026-05-12', NULL);

CREATE TABLE devis (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idDemande INT,
    type VARCHAR(50),
    date DATETIME NOT NULL,
    description TEXT,
    FOREIGN KEY (idDemande) REFERENCES demande(id)
);

CREATE TABLE devis_detail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idDevis INT,
    libelle VARCHAR(200),
    quantite DECIMAL(10, 2),
    prix_unitaire DECIMAL(10, 2),
    FOREIGN KEY (idDevis) REFERENCES devis(id)
);

CREATE TABLE parametre (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_statut1 INT,
    id_statut2 INT,
    duree_minute INT,
    alerte VARCHAR(50),
    FOREIGN KEY (id_statut1) REFERENCES statut(id),
    FOREIGN KEY (id_statut2) REFERENCES statut(id)
);

INSERT INTO parametre (id_statut1, id_statut2, duree_minute, alerte)
VALUES
-- Nouvelle -> Devis étude créée
(1, 2, 120, 'Jaune'),
(1, 2, 360, 'Rouge'),

-- Nouvelle -> Devis forage créée
(1, 4, 240, 'Jaune'),
(1, 4, 480, 'Rouge'),

-- Devis étude créée -> Devis étude refusée
(2, 3, 180, 'Jaune'),
(2, 3, 420, 'Rouge'),

-- Devis forage créée -> Devis forage refusée
(4, 5, 180, 'Jaune'),
(4, 5, 420, 'Rouge');

INSERT INTO parametre (id_statut1, id_statut2, duree_minute, alerte)
VALUES
(2, 5, 180, 'Jaune'),
(2, 5, 420, 'Rouge');

ALTER TABLE demande
MODIFY COLUMN date_demande DATETIME NOT NULL;

ALTER TABLE demande_statut
MODIFY COLUMN date_debut DATETIME NOT NULL,
MODIFY COLUMN date_fin DATETIME;

ALTER TABLE devis
MODIFY COLUMN date DATETIME NOT NULL;

ALTER TABLE demande_statut
ADD COLUMN observation TEXT;

ALTER TABLE demande_statut
ADD COLUMN duree_travail_minute FLOAT;

-- Désactiver temporairement les contraintes
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE devis_detail;
TRUNCATE TABLE devis;
TRUNCATE TABLE demande_statut;
TRUNCATE TABLE demande;
TRUNCATE TABLE client;

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;


forage_exam