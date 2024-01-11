# Docteur

## Objectif
-  Donner une liste de médicaments avec leurs prix à partir des symptômes d'un patient et de son budget

## Données de départ : 
- Niveau d'intensité (0 : OK à 10 : très grave) par symptômes
<br>Ex : 
  - Température : 6
  - Selles : 8
  - Toux : 0
  - etc
- Budget

## Résultats
- Liste des maladies communes possibles :
  <br>Ex :
  - Fatigue : 6
  - Selles : 8
- Médicaments traitant les symptômes par rapport au budget

## Règles de gestion
- Une maladie est lié avec plusieurs symptômes avec leur intensité : 
- Un paramètre = symptômes + intensité
<br>Ex : Grippe
    - Maux de tête : [6; 7]
    - Morves : [4; 8] 
    - Température : [38; 40] -> A changer entre 0 et 10

- 