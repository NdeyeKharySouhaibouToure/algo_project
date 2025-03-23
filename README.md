# Résolution de Labyrinthe - Examen d'Algorithmique-Groupe-16

## Contexte
Ce projet a été réalisé dans le cadre de l'examen d'algorithmique pour le cours de Programmation et Algorithmique Avancée à l'École Supérieure Polytechnique (ESP) de Dakar, en Master 1. Il vise à mettre en pratique nos compétences en algorithmique et structures de données, en implémentant des algorithmes de génération et de résolution de labyrinthes.

## Objectifs
L'objectif principal du projet est de créer un programme Java capable de :
1. **Générer un labyrinthe** : soit aléatoirement, soit à partir d'un fichier texte.
2. **Résoudre le labyrinthe** : trouver un chemin du départ à la sortie en utilisant deux algorithmes, DFS (Recherche en Profondeur) et BFS (Recherche en Largeur).
3. **Comparer les performances** : analyser et comparer les performances des algorithmes DFS et BFS en termes de temps d'exécution et de nombre d'étapes.
4. **Interface graphique (bonus)** : fournir une interface utilisateur interactive pour visualiser la génération, la résolution, et l'animation des chemins.

## Fonctionnalités
- **Génération aléatoire** : Génération d'un labyrinthe 11x11 parfait (avec un chemin unique entre deux points) en utilisant l'algorithme de backtracking récursif.
- **Chargement depuis fichier** : Possibilité de charger un labyrinthe à partir d'un fichier texte (exemple : `labyrinthe.txt`).
- **Résolution avec DFS et BFS** : Implémentation des algorithmes DFS (utilisant une pile) et BFS (utilisant une file) pour trouver un chemin du départ (`S`) à la sortie (`E`).
- **Animation** : Visualisation en temps réel du chemin trouvé, avec une vitesse d'animation ajustable via un curseur.
- **Comparaison des performances** : Affichage des temps d'exécution et du nombre d'étapes pour DFS et BFS.
- **Interface graphique** : Interface utilisateur avec Java Swing, incluant des labels pour afficher les performances en temps réel, des boutons pour résoudre, comparer, charger un fichier, et réinitialiser le labyrinthe.

## Algorithmes Utilisés
1. **Backtracking Récursif (Génération)** :
   - Utilisé dans `Labyrinthe.java` pour générer un labyrinthe parfait.
   - Fonctionnement : Part d'une grille remplie de murs, creuse des passages aléatoires de deux cases, et utilise une pile pour revenir en arrière en cas d'impasse.
   - Garantit un labyrinthe parfait (un chemin unique entre deux points).

2. **DFS (Recherche en Profondeur)** :
   - Implémenté dans `ResolveurLabyrinthe.java`.
   - Utilise une pile (`Stack`) pour explorer un chemin jusqu'au bout avant de revenir en arrière.
   - Rapide mais ne garantit pas le chemin le plus court.

3. **BFS (Recherche en Largeur)** :
   - Implémenté dans `ResolveurLabyrinthe.java`.
   - Utilise une file (`Queue`) pour explorer niveau par niveau, garantissant le chemin le plus court.
   - Plus lent que DFS en temps, mais optimal en termes de nombre d'étapes.

## Structure du Code
- **`Labyrinthe.java`** : Gère la génération et le chargement des labyrinthes.
- **`ResolveurLabyrinthe.java`** : Contient les algorithmes de résolution (DFS et BFS).
- **`InterfaceLabyrinthe.java`** : Gère l'interface graphique et l'interaction utilisateur.

## Prérequis
- Java 8 ou supérieur installé.
- Un IDE comme IntelliJ IDEA ou Eclipse pour compiler et exécuter le code (facultatif, peut être exécuté via la ligne de commande).
## Compiler le programme 
javac *.java
## Exécuter le programme
java InterfaceLabyrinthe
## Utilisation :
L'interface graphique s'ouvre avec un labyrinthe généré aléatoirement (11x11).
Cliquez sur "Resoudre avec DFS" ou "Resoudre avec BFS" pour voir l'animation du chemin.
Cliquez sur "Comparer DFS et BFS" pour voir une comparaison des performances.
Cliquez sur "Charger depuis fichier" pour charger un labyrinthe personnalisé (exemple : labyrinthe.txt).
Cliquez sur "Nouveau Labyrinthe" pour générer un nouveau labyrinthe.
Ajustez la vitesse de l'animation avec le curseur "Vitesse (ms)".
## Exemple de Fichier de Labyrinthe
Voici un exemple de fichier labyrinthe.txt que vous pouvez utiliser :
########
#S     #
# ###  #
#   #  #
### # ##
#    E #
########
# représente un mur.
S représente le départ.
E représente la sortie.
Un espace représente un passage.
## Équipe
Ndèye Khary Souhaibou TOURE
Sokhna Maimounatou Kabyr NDIAYE
