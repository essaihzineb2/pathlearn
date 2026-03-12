-- Script PostgreSQL pour enrollment_db

CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    mdp VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'APPRENTI',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inscription (
    id BIGSERIAL PRIMARY KEY,
    id_utilisateur BIGINT NOT NULL,
    id_formation BIGINT NOT NULL,
    date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    statut VARCHAR(20) DEFAULT 'EN_COURS',
    pourcentage_progression INT DEFAULT 0,
    date_fin TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id) ON DELETE CASCADE,
    UNIQUE (id_utilisateur, id_formation)
);

INSERT INTO utilisateur (email, mdp, nom, prenom, role)
VALUES 
    ('formateur1@pathlearn.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Dupont', 'Marie', 'FORMATEUR'),
    ('apprenti1@pathlearn.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Bernard', 'Sophie', 'APPRENTI'),
    ('admin@pathlearn.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin', 'System', 'ADMIN');

INSERT INTO inscription (id_utilisateur, id_formation, pourcentage_progression, statut)
VALUES 
    (2, 1, 35, 'EN_COURS'),
    (2, 3, 100, 'TERMINE');