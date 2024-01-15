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
    unique (id_patient, id_symptome, intensite)
);

create table parametres(
    id serial primary key,
    id_symptome int references symptomes(id),
    plage_intensite int4,
    EXCLUDE USING gist (plage_intensite WITH &&, id_symptome WITH =)
);

create table maladies(
    id serial primary key,
    nom varchar(100) not null
);

create table maladie_parametres(
    id serial primary key,
    id_maladie int references maladies(id),
    id_parametre int references parametres(id),
    unique (id_maladie, id_parametre)
);

create table medicaments(
    id serial primary key,
    nom varchar(100) not null
);

create table medicament_parametres(
    id serial primary key,
    id_medicament int references medicaments(id),
    id_parametre int references parametres(id),
    effet int check (effet >= 0 and effet <= 10),
    unique (id_medicament, id_parametre, effet)
);
