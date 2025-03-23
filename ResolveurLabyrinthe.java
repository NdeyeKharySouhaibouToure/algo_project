import java.util.*;

public class ResolveurLabyrinthe {
    private Labyrinthe labyrinthe;
    private char[][] grille;
    private boolean[][] visite;
    private int lignes, colonnes;
    private List<int[]> chemin;

    public ResolveurLabyrinthe(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.grille = labyrinthe.getGrille();
        this.lignes = grille.length;
        this.colonnes = grille[0].length;
        this.visite = new boolean[lignes][colonnes];
        this.chemin = new ArrayList<>();
    }

    public boolean resoudreDFS() {
        reinitialiserVisite();
        Stack<int[]> pile = new Stack<>();
        pile.push(new int[]{labyrinthe.getLigneDebut(), labyrinthe.getColonneDebut()});
        visite[labyrinthe.getLigneDebut()][labyrinthe.getColonneDebut()] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!pile.isEmpty()) {
            int[] courant = pile.pop();
            int ligne = courant[0], colonne = courant[1];
            chemin.add(courant);

            if (ligne == labyrinthe.getLigneFin() && colonne == labyrinthe.getColonneFin()) {
                return true;
            }

            for (int[] dir : directions) {
                int nouvelleLigne = ligne + dir[0];
                int nouvelleColonne = colonne + dir[1];

                if (estValide(nouvelleLigne, nouvelleColonne) && !visite[nouvelleLigne][nouvelleColonne] && grille[nouvelleLigne][nouvelleColonne] != '#') {
                    pile.push(new int[]{nouvelleLigne, nouvelleColonne});
                    visite[nouvelleLigne][nouvelleColonne] = true;
                }
            }
        }
        return false;
    }

    public boolean resoudreBFS() {
        reinitialiserVisite();
        Queue<int[]> file = new LinkedList<>();
        file.add(new int[]{labyrinthe.getLigneDebut(), labyrinthe.getColonneDebut()});
        visite[labyrinthe.getLigneDebut()][labyrinthe.getColonneDebut()] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int[][] parent = new int[lignes][colonnes];

        while (!file.isEmpty()) {
            int[] courant = file.poll();
            int ligne = courant[0], colonne = courant[1];
            chemin.add(courant);

            if (ligne == labyrinthe.getLigneFin() && colonne == labyrinthe.getColonneFin()) {
                reconstruireChemin(parent, ligne, colonne);
                return true;
            }

            for (int[] dir : directions) {
                int nouvelleLigne = ligne + dir[0];
                int nouvelleColonne = colonne + dir[1];

                if (estValide(nouvelleLigne, nouvelleColonne) && !visite[nouvelleLigne][nouvelleColonne] && grille[nouvelleLigne][nouvelleColonne] != '#') {
                    file.add(new int[]{nouvelleLigne, nouvelleColonne});
                    visite[nouvelleLigne][nouvelleColonne] = true;
                    parent[nouvelleLigne][nouvelleColonne] = ligne * colonnes + colonne;
                }
            }
        }
        return false;
    }

    private boolean estValide(int ligne, int colonne) {
        return ligne >= 0 && ligne < lignes && colonne >= 0 && colonne < colonnes;
    }

    private void reinitialiserVisite() {
        for (int i = 0; i < lignes; i++) {
            Arrays.fill(visite[i], false);
        }
        chemin.clear();
    }

    private void reconstruireChemin(int[][] parent, int ligne, int colonne) {
        int pos = ligne * colonnes + colonne;
        while (pos != labyrinthe.getLigneDebut() * colonnes + labyrinthe.getColonneDebut()) {
            grille[ligne][colonne] = '+';
            int posParent = parent[ligne][colonne];
            ligne = posParent / colonnes;
            colonne = posParent % colonnes;
            pos = posParent;
        }
        grille[labyrinthe.getLigneDebut()][labyrinthe.getColonneDebut()] = 'S';
    }

    public List<int[]> getChemin() {
        return chemin;
    }
}