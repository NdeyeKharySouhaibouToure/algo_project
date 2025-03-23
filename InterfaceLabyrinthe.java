import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfaceLabyrinthe extends JFrame {
    private Labyrinthe labyrinthe;
    private ResolveurLabyrinthe resolveur;
    private JPanel panneauLabyrinthe;
    private int tailleCase = 40;
    private long tempsDFS, tempsBFS;
    private int etapesDFS, etapesBFS;
    private JSlider vitesseSlider;
    private JLabel labelDFS; // Label pour afficher les performances de DFS
    private JLabel labelBFS; // Label pour afficher les performances de BFS

    public InterfaceLabyrinthe() {
        labyrinthe = new Labyrinthe(11, 11);
        resolveur = new ResolveurLabyrinthe(labyrinthe);

        setTitle("Resolution de Labyrinthe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panneauLabyrinthe = new PanneauLabyrinthe();
        add(panneauLabyrinthe, BorderLayout.CENTER);

        // Panneau pour les performances (labels)
        JPanel panneauPerformances = new JPanel();
        panneauPerformances.setLayout(new FlowLayout(FlowLayout.CENTER));
        labelDFS = new JLabel("DFS : - ms, - etapes");
        labelBFS = new JLabel("BFS : - ms, - etapes");
        panneauPerformances.add(labelDFS);
        panneauPerformances.add(labelBFS);
        add(panneauPerformances, BorderLayout.NORTH);

        // Panneau pour les boutons et le curseur
        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton boutonDFS = new JButton("Resoudre avec DFS");
        JButton boutonBFS = new JButton("Resoudre avec BFS");
        JButton boutonComparer = new JButton("Comparer DFS et BFS");
        JButton boutonFichier = new JButton("Charger depuis fichier");
        JButton boutonNouveau = new JButton("Nouveau Labyrinthe"); // Bouton pour réinitialiser
        vitesseSlider = new JSlider(JSlider.HORIZONTAL, 50, 500, 100);
        vitesseSlider.setMajorTickSpacing(50);
        vitesseSlider.setPaintTicks(true);
        vitesseSlider.setLabelTable(vitesseSlider.createStandardLabels(50));
        vitesseSlider.setPaintLabels(true);
        panneauBoutons.add(boutonDFS);
        panneauBoutons.add(boutonBFS);
        panneauBoutons.add(boutonComparer);
        panneauBoutons.add(boutonFichier);
        panneauBoutons.add(boutonNouveau);
        panneauBoutons.add(new JLabel("Vitesse (ms):"));
        panneauBoutons.add(vitesseSlider);
        add(panneauBoutons, BorderLayout.SOUTH);

        boutonDFS.addActionListener(e -> resoudreEtAnimer("DFS"));
        boutonBFS.addActionListener(e -> resoudreEtAnimer("BFS"));
        boutonComparer.addActionListener(e -> comparerPerformances());
        boutonFichier.addActionListener(e -> chargerFichier());
        boutonNouveau.addActionListener(e -> reinitialiserLabyrinthe());

        setSize(labyrinthe.getGrille()[0].length * tailleCase + 20, labyrinthe.getGrille().length * tailleCase + 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resoudreEtAnimer(String algorithme) {
        new Thread(() -> {
            long debut = System.nanoTime();
            boolean resolu = algorithme.equals("DFS") ? resolveur.resoudreDFS() : resolveur.resoudreBFS();
            long fin = System.nanoTime();
            long tempsMs = (fin - debut) / 1_000_000;
            List<int[]> chemin = new ArrayList<>(resolveur.getChemin());
            int etapes = chemin.size();

            if (algorithme.equals("DFS")) {
                tempsDFS = tempsMs;
                etapesDFS = etapes;
                labelDFS.setText("DFS : " + tempsMs + " ms, " + etapes + " etapes"); // Mise à jour du label
            } else {
                tempsBFS = tempsMs;
                etapesBFS = etapes;
                labelBFS.setText("BFS : " + tempsMs + " ms, " + etapes + " etapes"); // Mise à jour du label
            }

            if (resolu) {
                for (int[] position : chemin) {
                    int ligne = position[0], colonne = position[1];
                    if (labyrinthe.getGrille()[ligne][colonne] != 'S' && labyrinthe.getGrille()[ligne][colonne] != 'E') {
                        labyrinthe.getGrille()[ligne][colonne] = '+';
                    }
                    panneauLabyrinthe.repaint();
                    try {
                        Thread.sleep(vitesseSlider.getValue());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(this, algorithme + " a resolu le labyrinthe en " + tempsMs + " ms et " + etapes + " etapes.");
            } else {
                JOptionPane.showMessageDialog(this, "Aucune solution trouvee avec " + algorithme);
            }
        }).start();
    }

    private void comparerPerformances() {
        String message = "Comparaison DFS vs BFS :\n" +
                "DFS : " + tempsDFS + " ms, " + etapesDFS + " etapes\n" +
                "BFS : " + tempsBFS + " ms, " + etapesBFS + " etapes";
        JOptionPane.showMessageDialog(this, message);
    }

    private void chargerFichier() {
        JFileChooser selecteur = new JFileChooser();
        if (selecteur.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            labyrinthe = new Labyrinthe(selecteur.getSelectedFile().getAbsolutePath());
            resolveur = new ResolveurLabyrinthe(labyrinthe);
            tempsDFS = 0; tempsBFS = 0; etapesDFS = 0; etapesBFS = 0;
            labelDFS.setText("DFS : - ms, - etapes");
            labelBFS.setText("BFS : - ms, - etapes");
            setSize(labyrinthe.getGrille()[0].length * tailleCase + 20, labyrinthe.getGrille().length * tailleCase + 200);
            panneauLabyrinthe.repaint();
            revalidate();
        }
    }

    private void reinitialiserLabyrinthe() {
        labyrinthe = new Labyrinthe(11, 11);
        resolveur = new ResolveurLabyrinthe(labyrinthe);
        tempsDFS = 0; tempsBFS = 0; etapesDFS = 0; etapesBFS = 0;
        labelDFS.setText("DFS : - ms, - etapes");
        labelBFS.setText("BFS : - ms, - etapes");
        setSize(labyrinthe.getGrille()[0].length * tailleCase + 20, labyrinthe.getGrille().length * tailleCase + 200);
        panneauLabyrinthe.repaint();
        revalidate();
    }

    private class PanneauLabyrinthe extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            char[][] grille = labyrinthe.getGrille();
            for (int i = 0; i < grille.length; i++) {
                for (int j = 0; j < grille[0].length; j++) {
                    if (grille[i][j] == '#') g.setColor(Color.BLACK);
                    else if (grille[i][j] == 'S') g.setColor(Color.GREEN);
                    else if (grille[i][j] == 'E') g.setColor(Color.RED);
                    else if (grille[i][j] == '+') g.setColor(Color.BLUE);
                    else g.setColor(Color.WHITE);
                    g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    g.setColor(Color.GRAY);
                    g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceLabyrinthe::new);
    }
}