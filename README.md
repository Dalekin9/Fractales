# Fractales

Projet de L3 Informatique  
Semestre 5  
CPOO5

**Pauline ADAM et Ugo TORIS**

###Comment compiler le projet

./gradlew build

###Comment lancer les deux versions du programme

Graphique : ./gradlew run --args="-G"

Terminal : ./gradlew run --args="-C ..." où ... = options

Pour Julia et Mandlebrot : 
    * necessaires : option -cst (forme x;y), -p, -r (forme x1;x2;y1;y2) , -t (J ou M)
    * facultatif : option -fi, -fo (forme x2+c), -col, -it

=> exemple : "-C -cst 0.285;0.01 -p 0.01 -r -15;1;-1;1 -fi Julia -fo x2+c -col 3 -t J"

Pour Sierpinski :
    * necessaires : option -o
    * facultatif : option -fi, -col

=> exemple : "-C -o 4 -fi Tapis "


###Les principaux choix techniques (considérer comme des points fort)

Utilisation d'Apache Commons Cli pour gérer les arguments en ligne de commande

Classe abstraite pour les Fractales -> Julia, Mandelbrot et Sierpinski l'étendent donc

Builder pour une Fractale 

Fonction de Premiere classe pour une fonction (étend Fonction)

###Rapport sur performance des versions parallèles

###References aux sources