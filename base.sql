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
    date_demande DATE,
    FOREIGN KEY (id_commune) REFERENCES commune(id),
    FOREIGN KEY (id_demandeur) REFERENCES client(id)
);

CREATE TABLE demande_statut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type_demande INT,
    type_statut INT,
    date_debut DATE,
    date_fin DATE,
    FOREIGN KEY (type_demande) REFERENCES demande(id),
    FOREIGN KEY (type_statut) REFERENCES statut(id)
);

INSERT INTO client (nom, mail, role, mot_de_passe)
VALUES
('Admin', 'admin@forage.mg', 'ADMIN', 'admin123'),
('Rabe', 'rabe@forage.mg', 'DEMANDEUR', 'rabe123');