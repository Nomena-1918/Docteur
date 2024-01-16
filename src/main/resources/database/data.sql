-- Insertion des symptômes
insert into symptomes values
(1, 'Fièvre'),
(2, 'Toux'),
(3, 'Maux de tête'),
(4, 'Fatigue'),
(5, 'Nausées'),
(6, 'Douleurs abdominales');

-- Insertion des maladies communes
insert into maladies values
(1, 'Grippe'),
(2, 'Rhume'),
(3, 'Migraine'),
(4, 'Gastro-entérite');

-- Insertion des médicaments
insert into medicaments values
(1, 'Doliprane'),
(2, 'Vitamine C'),
(3, 'Smecta'),
(4, 'Ibuprofène'),
(5, 'Antibiotique');

-- Insertion des paramètres liés aux symptômes
insert into parametres values
(1, 1, 2),   -- Fièvre, intensité 2
(2, 2, 3),   -- Toux, intensité 3
(3, 3, 4),   -- Maux de tête, intensité 4
(4, 4, 2),   -- Fatigue, intensité 2
(5, 5, 5),   -- Nausées, intensité 5
(6, 6, 3);   -- Douleurs abdominales, intensité 3

-- Insertion des relations entre maladies et paramètres
insert into maladie_parametres values
(1, 1, 1),   -- Grippe - Fièvre
(1, 2, 2),   -- Grippe - Toux
(2, 2, 1),   -- Rhume - Fièvre
(3, 3, 3),   -- Migraine - Maux de tête
(4, 4, 5),   -- Gastro-entérite - Nausées

-- Insertion des paramètres liés aux médicaments
insert into medicament_parametres values
(1, 1, 1, 7),   -- Doliprane - Fièvre - Effet 7
(1, 1, 2, 5),   -- Doliprane - Toux - Effet 5
(2, 2, 3, 8),   -- Vitamine C - Maux de tête - Effet 8
(3, 3, 5, 6),   -- Smecta - Nausées - Effet 6
(4, 4, 2, 9),   -- Ibuprofène - Toux - Effet 9
(5, 5, 1, 7);   -- Antibiotique - Fièvre - Effet 7
