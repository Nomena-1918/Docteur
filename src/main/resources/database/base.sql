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
    intensite int check (intensite >= 0 and intensite <= 10)
);

create table parametres(
    id serial primary key,
    id_symptome int references symptomes(id),
    intensite int check (intensite >= 0 and intensite <= 10)
);

create table maladies(
    id serial primary key,
    nom varchar(100) not null
);

create table maladie_parametres(
    id serial primary key,
    id_maladie int references maladies(id),
    id_parametre int references parametres(id)
);

create table medicaments(
    id serial primary key,
    nom varchar(100) not null
);

create table medicament_parametres(
    id serial primary key,
    id_medicament int references medicaments(id),
    id_parametre int references parametres(id),
    effet int check (effet >= 0 and effet <= 10)
);
