-- Données pour maladie
INSERT INTO maladie (nom) VALUES
    ('Grippe'),
    ('Diabète'),
    ('Hypertension'),
    ('Rhume');

-- Données pour symptome
INSERT INTO symptome (nom) VALUES
    ('Toux'),
    ('Fièvre'),
    ('Fatigue'),
    ('Mal de tête'),
    ('Nez qui coule'),
    ('Gorge irrite'),
    ('Vision flou'),
    ('polyurie'),
    ('infection frequante'),
    ('picotement'),
    ('Vertige'),
    ('palpitation'),
    ('Frissons'),
    ('Mal a la gorge');

-- Données pour medicament
INSERT INTO medicament (nom) VALUES
    ('antitussifs'),
    ('Revitalose'),
    ('Paracetamol'),
    ('Ibuproflene'),
    ('Aspirine'),
    ('Sinulab'),
    ('Lysopaine'),
    ('Difrarel'),
    ('Solifenacine'),
    ('Amoxicilinne'),
    ('Pregabalin'),
    ('Acetyluicine'),
    ('Biocard'),
    ('Anti-inflamatoire');

-- Données pour categorie
INSERT INTO categorie (nom, age_max, age_min) VALUES
    ('Enfant', 12, 0),
    ('Adolescent', 18, 13),
    ('Adulte', 65, 19),
    ('Personne âgée', 120, 66);

-- Données pour personne
INSERT INTO personne (nom, prenom, naissance) VALUES
    ('Dupont', 'zaza', '2018-05-15'),
    ('Martin', 'ado', '2008-02-28'),
    ('Dufresne', 'adulte', '2003-08-10');

-- Données pour defmaladie
INSERT INTO defmaladie (idmaladie, idcategorie, idsymptome, valeur_min, valeur_max) VALUES
-- Grippe pour enfant
    (1, 1, 1, 7, 9),
    (1, 1, 2, 7, 8),
    (1, 1, 3, 6, 7),
    (1, 1, 4, 5, 6),
    (1, 1, 5, 4, 5),
    (1, 1, 6, 5, 6),

-- Diabète pour adolescent
    (2, 2, 7, 7, 8),
    (2, 2, 8, 5, 6),
    (2, 2, 9, 8, 9),
    (2, 2, 10, 5, 6),

-- Hypertension pour adulte
    (3, 3, 4, 6, 7),
    (3, 3, 11, 7, 8),
    (3, 3, 5, 4, 5),
    (3, 3, 12, 8, 9),

-- Diabète pour personne âgées
    (4, 4, 5, 6, 7),
    (4, 4, 1, 7, 8),
    (4, 4, 4, 5, 6),
    (4, 4, 3, 5, 6),
    (4, 4, 13, 7, 8),
    (4, 4, 14, 7, 8);

-- Données pour defremede
INSERT INTO defremede (idmedicament, idsymptome, effet) VALUES
    (1, 1, 1),

    (3, 2, 1),
    (5, 2, 2),

    (2, 3, 3),
    (3, 3, 2),
    (4, 3, 2),

    (3, 4, 2),
    (4, 4, 4),

    (6, 5, 3),

    (7, 6, 2),

    (8, 7, 2),

    (9, 8, 2),

    (10, 9, 2),

    (11, 10, 3),

    (12, 11, 2),

    (5, 12, 3),

    (14, 13, 3);


-- Ajout de données pour la table prixdocs
INSERT INTO prixdocs (idmedicament, prix) VALUES
    (1, 12.5),
    (2, 20.0),
    (3, 10.75),
    (4, 16.25),
    (5, 19.23),
    (6, 14.65),
    (7, 28.28),
    (8, 35.90),
    (9, 55.15),
    (10, 25.13),
    (11, 17.19),
    (12, 23.0),
    (13, 68.13),
    (14, 12.13);
