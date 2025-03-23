import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class Labyrinthe {
    private char[][] grille;
    private int lignes, colonnes;
    private int ligneDebut, colonneDebut;
    private int ligneFin, colonneFin;

    public Labyrinthe(int lignes, int colonnes) {
        this.lignes = lignes % 2 == 0 ? lignes + 1 : lignes;
        this.colonnes = colonnes % 2 == 0 ? colonnes + 1 : colonnes;
        this.grille = new char[this.lignes][this.colonnes];
        genererLabyrintheAleatoire();
    }

    public Labyrinthe(String cheminFichier) {
        chargerDepuisFichier(cheminFichier);
    }

    private void genererLabyrintheAleatoire() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = '#';
            }
        }

        Random aleatoire = new Random();
        Stack<int[]> pile = new Stack<>();
        int ligne = 1, colonne = 1;
        grille[ligne][colonne] = ' ';
        pile.push(new int[]{ligne, colonne});

        int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};

        while (!pile.isEmpty()) {
            int[] courant = pile.peek();
            ligne = courant[0];
            colonne = courant[1];
            boolean aVoisinNonVisite = false;

            int[][] directionsMelangees = melangerDirections(directions, aleatoire);
            for (int[] dir : directionsMelangees) {
                int nouvelleLigne = ligne + dir[0];
                int nouvelleColonne = colonne + dir[1];

                if (estValide(nouvelleLigne, nouvelleColonne) && grille[nouvelleLigne][nouvelleColonne] == '#') {
                    aVoisinNonVisite = true;
                    grille[nouvelleLigne][nouvelleColonne] = ' ';
                    grille[ligne + dir[0] / 2][colonne + dir[1] / 2] = ' ';
                    pile.push(new int[]{nouvelleLigne, nouvelleColonne});
                    break;
                }
            }
            if (!aVoisinNonVisite) {
                pile.pop();
            }
        }

        grille[1][1] = 'S';
        ligneDebut = 1;
        colonneDebut = 1;
        grille[lignes - 2][colonnes - 2] = 'E';
        ligneFin = lignes - 2;
        colonneFin = colonnes - 2;
    }

    private void chargerDepuisFichier(String cheminFichier) {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            int numLigne = 0;
            while ((ligne = lecteur.readLine()) != null) {
                if (numLigne == 0) {
                    colonnes = ligne.length();
                    grille = new char[10][colonnes];
                }
                grille[numLigne] = ligne.toCharArray();
                for (int j = 0; j < colonnes; j++) {
                    if (grille[numLigne][j] == 'S') {
                        ligneDebut = numLigne;
                        colonneDebut = j;
                    } else if (grille[numLigne][j] == 'E') {
                        ligneFin = numLigne;
                        colonneFin = j;
                    }
                }
                numLigne++;
            }
            lignes = numLigne;
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
            this.lignes = 11;
            this.colonnes = 11;
            this.grille = new char[lignes][colonnes];
            genererLabyrintheAleatoire();
        }
    }

    private int[][] melangerDirections(int[][] directions, Random aleatoire) {
        int[][] copie = directions.clone();
        for (int i = copie.length - 1; i > 0; i--) {
            int j = aleatoire.nextInt(i + 1);
            int[] temp = copie[i];
            copie[i] = copie[j];
            copie[j] = temp;
        }
        return copie;
    }

    private boolean estValide(int ligne, int colonne) {
        return ligne > 0 && ligne < lignes - 1 && colonne > 0 && colonne < colonnes - 1;
    }

    public void afficherLabyrinthe() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char[][] getGrille() { return grille; }
    public int getLigneDebut() { return ligneDebut; }
    public int getColonneDebut() { return colonneDebut; }
    public int getLigneFin() { return ligneFin; }
    public int getColonneFin() { return colonneFin; }
}