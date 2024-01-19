-- Insertion des symptômes
insert into symptomes(nom) values
('Température'),
('Toux'),
('Morves'),
('Maux de tête'),
('Maux de ventre'),
('Selles'),
('Force');

-- Insertion des maladies communes
insert into maladies(nom) values
('Grippe'),
('Diarrhée'),
('Migraine');

-- Insertion des médicaments
insert into medicaments(nom, prix_unitaire) values
('Smecta', 600),
('Doliprane', 1500),
('Fervex', 2000),
('Sirop pour la toux', 3200),
('Vitamine C', 1000),
('Antibiotique', 6000);


-- Insertion des relations entre maladies et paramètres
insert into maladie_parametres(id_maladie, id_symptome, plage_intensite) values
(1, 1, int4range(2,11)),
(1, 2, int4range(7,10)),
(1, 3, int4range(3, 9)),
(1, 4, int4range(4, 8)),
(2, 5, int4range(8,11)),
(2, 6, int4range(6,11)),
(3, 7, int4range(5,11)),
(3, 4, int4range(7,11));


-- Insertion d'un patient
insert into patients(nom) values
('Patient 1');


-- Insertion des symptômes ddu patient
insert into patient_symptomes(id_patient, id_symptome, intensite, date_consultation) values
(1, 1, 0, '2024-01-19 21:51:43'),
(1, 2, 7, '2024-01-19 21:51:43'),
(1, 3, 5, '2024-01-19 21:51:43'),
(1, 4, 4, '2024-01-19 21:51:43'),
(1, 5, 0, '2024-01-19 21:51:43'),
(1, 6, 6, '2024-01-19 21:51:43'),
(1, 7, 8, '2024-01-19 21:51:43');

SET TIMEZONE = 'Indian/Antananarivo';
select now();

-- Nombre de symptômes du malade par maladie
create view v_patient_nombre_symptome_maladie as
select ps.id_patient, mp.id_maladie, count(ps.id_symptome) as nbr_symptome, ps.date_consultation
from patient_symptomes ps
    join maladie_parametres mp
        on ps.id_symptome = mp.id_symptome
               and ps.intensite <@ mp.plage_intensite
group by mp.id_maladie, ps.date_consultation, ps.id_patient;

-- Nombre de symptôme par maladie
create view v_nombre_symptome_maladie as
select id_maladie, count(id_symptome) as nbr_symptome
from maladie_parametres mp
group by id_maladie
order by id_maladie;

-- Vue affichant les maladies d'un patient a une date donnée
create view v_maladies_patient as
select v1.id_patient, v1.id_maladie, m.nom, v1.date_consultation
from v_patient_nombre_symptome_maladie v1
         join v_nombre_symptome_maladie v2 ON v1.id_maladie = v2.id_maladie
         join maladies m ON v1.id_maladie = m.id
where v1.nbr_symptome = v2.nbr_symptome;

-- Test
select id_maladie, nom
from v_maladies_patient
where date_consultation = '2024-01-17 06:08:32'
and id_patient = 1;

select *
from patient_symptomes
where date_consultation = '2024-01-17 06:08:32'
  and id_patient = 1;



-- Insertion des paramètres liés aux médicaments
insert into medicament_symptomes(id_medicament, id_symptome, effet) values
(1, 1, 0),
(1, 2, 0),
(1, 3, 0),
(1, 4, 0),
(1, 5, 3),
(1, 6, 1),
(1, 7, 0),


(2, 1, 3),
(2, 2, 2),
(2, 3, 2),
(2, 4, 3),
(2, 5, 0),
(2, 6, 0),
(2, 7, 4),


(3, 1, 7),
(3, 2, 2),
(3, 3, 2),
(3, 4, 4),
(3, 5, 0),
(3, 6, 0),
(3, 7, 6),


(4, 1, 0),
(4, 2, 6),
(4, 3, 0),
(4, 4, 0),
(4, 5, 0),
(4, 6, 0),
(4, 7, 1),


(5, 1, 2),
(5, 2, 2),
(5, 3, 2),
(5, 4, 1),
(5, 5, 1),
(5, 6, 1),
(5, 7, 3),


(6, 1, 6),
(6, 2, 4),
(6, 3, 5),
(6, 4, 4),
(6, 5, 4),
(6, 6, 9),
(6, 7, 2);