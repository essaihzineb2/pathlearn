-- Script PostgreSQL pour course_db

CREATE TABLE IF NOT EXISTS formation (
    id BIGSERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    domaine VARCHAR(100) NOT NULL,
    niveau INT NOT NULL,
    duree INT NOT NULL,
    prix DECIMAL(10,2) NOT NULL,
    id_formateur BIGINT NOT NULL,
    note DECIMAL(3,2) DEFAULT 0.00,
    statut VARCHAR(20) DEFAULT 'BROUILLON',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modif TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS module (
    id BIGSERIAL PRIMARY KEY,
    id_formation BIGINT NOT NULL,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    numero_ordre INT NOT NULL,
    duree_minutes INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_formation) REFERENCES formation(id) ON DELETE CASCADE
);

INSERT INTO formation (titre, description, domaine, niveau, duree, prix, id_formateur, note, statut) 
VALUES 
    ('Développement Web avec React', 'Apprenez React et TypeScript', 'Développement', 2, 1440, 79.99, 1, 4.8, 'PUBLIE'),
    ('Intelligence Artificielle', 'IA avec TensorFlow', 'IA', 3, 1200, 89.99, 2, 4.5, 'PUBLIE'),
    ('Design UX/UI', 'Principes du design', 'Design', 1, 960, 49.99, 1, 4.5, 'PUBLIE');

INSERT INTO module (id_formation, titre, description, numero_ordre, duree_minutes)
VALUES 
    (1, 'Introduction React', 'Les bases', 1, 120),
    (1, 'Composants', 'Props et State', 2, 180),
    (2, 'Intro ML', 'Concepts', 1, 150),
    (3, 'Design Basics', 'Fondamentaux', 1, 120);