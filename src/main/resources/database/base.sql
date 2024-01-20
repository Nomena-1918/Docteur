create extension if not exists btree_gist;

create table patients(
    id serial primary key,
    nom varchar(100) not null
);

create table symptomes(
    id serial primary key,
    nom varchar(100) not null
);


create table patient_symptomes(
    id serial primary key,
    id_patient int references patients(id),
    id_symptome int references symptomes(id),
    intensite int check (intensite >= 0 and intensite <= 10),
    date_consultation timestamp default now(),
    unique (id_patient, id_symptome, intensite, date_consultation)
);


create table maladies(
    id serial primary key,
    nom varchar(100) not null
);

create table maladie_parametres(
    id serial primary key,
    id_maladie int references maladies(id),
    id_symptome int references symptomes(id),
    plage_intensite int4range,
    EXCLUDE USING gist (plage_intensite WITH &&, id_symptome WITH =, id_maladie WITH =)
);

create table medicaments(
    id serial primary key,
    nom varchar(100) not null,
    prix_unitaire decimal not null check (prix_unitaire>0)
);

create table medicament_symptomes(
    id serial primary key,
    id_medicament int references medicaments(id),
    id_symptome int references symptomes(id),
    effet int check (effet <= 10),
    unique (id_medicament, id_symptome, effet)
);


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
