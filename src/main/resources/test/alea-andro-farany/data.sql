-- Insertion des symptômes
insert into symptomes(nom) values
('Kibo'),
('Caca'),
('Andoha'),
('Temperature'),
('Fatigue'),
('Lelo');

insert into patients(nom) values
('Patient 1');
/*
-- Insertion des symptômes ddu patient
insert into patient_symptomes(id_patient, id_symptome, intensite, date_consultation) values
(1, 1, 6, '2024-01-22 09:39:00'),
(1, 3, 6, '2024-01-22 09:39:00'),
(1, 4, 5, '2024-01-22 09:39:00'),
(1, 6, 7, '2024-01-22 09:39:00');
 */

-- Insertion des symptômes ddu patient
insert into patient_symptomes(id_patient, id_symptome, intensite, date_consultation) values
(1, 1, 4, '2024-01-22 09:39:00'),
(1, 3, 4, '2024-01-22 09:39:00'),
(1, 4, 4, '2024-01-22 09:39:00'),
(1, 5, 4, '2024-01-22 09:39:00'),
(1, 6, 4, '2024-01-22 09:39:00');

select ps.id, ps.id_patient, s.*, ps.intensite
    from patient_symptomes ps
join symptomes s on s.id = ps.id_symptome;

-- Insertion des maladies communes
insert into maladies(nom) values
('Grippe'),
('Vavony'),
('Indigestion');

-- Insertion des relations entre maladies et paramètres
insert into maladie_parametres(id_maladie, id_symptome, plage_intensite) values
(1, 3, int4range(5,8)),
(1, 4, int4range(3,6)),
(1, 6, int4range(5,9)),

(2, 1, int4range(5,8)),
(2, 4, int4range(3,7)),

(3, 1, int4range(5,9)),
(3, 2, int4range(6,9)),
(3, 5, int4range(3,8));

select m.*, s.*, mp.plage_intensite from maladie_parametres mp
join symptomes s on s.id = mp.id_symptome
join maladies m on m.id = mp.id_maladie;


-- Insertion des médicaments
insert into medicaments(nom, prix_unitaire) values
('F1', 20000),
('F2', 15000),
('F3', 40000);

-- Insertion des paramètres liés aux médicaments
insert into medicament_symptomes(id_medicament, id_symptome, effet) values
(1, 1, 1),
(1, 2, 0),
(1, 3, 3),
(1, 4, 2),
(1, 5, 0),
(1, 6, 3),

(2, 1, 3),
(2, 2, 2),
(2, 3, 1),
(2, 4, 0),
(2, 5, 0),
(2, 6, 0),

(3, 1, 2),
(3, 2, 0),
(3, 3, 3),
(3, 4, 2),
(3, 5, 2),
(3, 6, 2);

create or replace view  v_medicament_symptome_complet as
select ms.id, ms.id_medicament, m.nom as nom_medicament, ms.id_symptome, s.nom as nom_symptome, ms.effet
from medicament_symptomes ms
join medicaments m on ms.id_medicament = m.id
join symptomes s on ms.id_symptome = s.id;