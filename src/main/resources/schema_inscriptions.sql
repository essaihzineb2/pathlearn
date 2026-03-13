-- ============================================================
-- Script SQL pour la base de données du projet
-- À exécuter dans votre base MySQL (ex: CREATE DATABASE projet; USE projet;)
-- ============================================================

-- Table des formations (cours disponibles)
CREATE TABLE IF NOT EXISTS formations (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre       VARCHAR(255) NOT NULL,
    description TEXT,
    duree_heures INT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Table des inscriptions (User <-> Formation)
CREATE TABLE IF NOT EXISTS inscriptions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    formation_id    BIGINT NOT NULL,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut          VARCHAR(50) DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id)      REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (formation_id) REFERENCES formations(id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_formation (user_id, formation_id)
);

-- Table de progression (avancement d'un utilisateur dans une inscription)
CREATE TABLE IF NOT EXISTS progressions (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    inscription_id      BIGINT NOT NULL UNIQUE,
    pourcentage         INT DEFAULT 0,
    derniere_activite   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    notes               TEXT,
    FOREIGN KEY (inscription_id) REFERENCES inscriptions(id) ON DELETE CASCADE
);

-- ============================================================
-- Données de test (optionnel)
-- ============================================================
INSERT IGNORE INTO formations (titre, description, duree_heures) VALUES
  ('Spring Boot Avancé', 'Maîtriser Spring Boot avec JPA, Security et JWT', 30),
  ('React Moderne', 'Composants, Hooks, Context API et Redux', 25),
  ('Base de données SQL', 'Conception et optimisation des bases relationnelles', 20);
