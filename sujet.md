# Docteur

## Objectif
-  Donner une liste de médicaments avec leurs prix à partir des symptômes d'un patient et de son budget

## Données de départ : 
- Niveau d'intensité (0:normal à 10:très grave) par symptômes
<br>Ex : 
  - Température : 6
  - Selles : 8
  - Toux : 0
  - etc
- Budget
- Date de naissance

## Résultats
- Liste des maladies communes possibles :
  <br>Ex :
  - Fatigue
  - Grippe
- Médicaments traitant les symptômes par rapport au budget (les - chers)
  - Doliprane : 4000 Ar
  - Fervex : 2000 Ar
- Etat de santé à la fin

## Règles de gestion
### 1ere étape : Deviner les maladies
- Une maladie est lié avec plusieurs symptômes avec leur intensité : 
- Un paramètre = symptômes + intensité + âge
- Manifestation des maladies différentes selon l'âge
<br>Ex : Grippe pour [0; 15] ans
    - Maux de tête : [6; 7]
    - Morves : [4; 8] 
    - Température : [38; 40] -> A changer entre 0 et 10
- Exemples de maladies communes : 
  - Indigestion
  - Fatigue
  - Grippe

### 2ème étape : Deviner les médicaments
- ActionMedicamentParamètre :
  - 1 médicament peut + ou - (0 à 10) guérir un paramètre
- Médicament : nom + prix
- Unité médicament = 1 
- La maladie est vaincue si l'effet des médicaments est supérieur à l'état de la maladie

### Etape sup : Possible aléa
- Contre-indication :
- Efficace avec des paramètres mais provoquent des effets secondaires : 
  <br> Ex : 
  - Un doliprane a une action de 3 contre les maux de tête et une action de -2 avec les selles et -1 avec morves
- On donne les médicaments des maladies venant des effets secondaires : soigner les contres indications









