
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.*;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;

//import javax.lang.model.util.SimpleTypeVisitor6;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JColorChooser;

import javax.imageio.ImageIO;

/**
  Classe de rendu graphique
  @author Pierre JACQUET & Benoit Bothorel
  @version 1
**/

public class GUI {
    private static final int N = 15;
    public static int scoreJ1;
    public static int scoreJ2;
    private final List<JButton> list = new ArrayList<JButton>();
    public int click = 0;
    public Case clicked;
    public ImageIcon mitoRed = new ImageIcon("./img/mito_red.png");
    public ImageIcon mitoBlue = new ImageIcon("./img/mito_blue.png");
    public ImageIcon mitoGreen = new ImageIcon("./img/mito_green.png");
    public ImageIcon mitoRose = new ImageIcon("./img/mito_rose.png");
    public ImageIcon lipid = new ImageIcon("./img/lipid.png");
    public ImageIcon enzymeRed = new ImageIcon("./img/enzyme_red.png");
    public ImageIcon enzymeBlue = new ImageIcon("./img/enzyme_blue.png");
    public ImageIcon enzymeGreen = new ImageIcon("./img/enzyme_green.png");
    public ImageIcon enzymeRose = new ImageIcon("./img/enzyme_rose.png");
    public Color J1 = new Color(0, 183, 2);
    public Color J2 = new Color(91, 154, 255);

    public int etape_tour = 1; //0:metabolite, 1: joueur1, joueur2,
    public int nb_tour = 1;

    private JButton getGridButton(int r, int c) {
        int index = r * N + c;
        return list.get(index);
    }

    private JButton createGridButton(final int row, final int col) {
        final JButton b;
        int index = (row * 15 + col);
        Case pieceaposer = Plateau.plateau.get(index);
        String type = pieceaposer.get_type();
        Boolean highlighted = pieceaposer.is_highlighted();
        Boolean reachable = pieceaposer.is_reachable();

        b = new JButton();

        if (pieceaposer.get_Player() == 1) {
            b.setBackground(J1);
            b.setOpaque(true);
            // b.setContentAreaFilled(false);
            // b.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (pieceaposer.get_Player() == 2) {
            b.setBackground(J2);
            b.setOpaque(true);
        }
        if (type.equals("M") && pieceaposer.get_color().equals("Rouge")) {
            b.setIcon(mitoRed);
        }
        if (type.equals("M") && pieceaposer.get_color().equals("Bleu")) {
            b.setIcon(mitoBlue);
        }
        if (type.equals("M") && pieceaposer.get_color().equals("Vert")) {
            b.setIcon(mitoGreen);
        }
        if (type.equals("M") && pieceaposer.get_color().equals("Rose")) {
            b.setIcon(mitoRose);
        }
        if (type.equals("L")) {
            b.setIcon(lipid);
        }
        if (type.equals(" ")) {
            // b.setText(row + "," + col);
            // b.setFont(new Font("Arial", Font.PLAIN, 9));
        }
        if (type.equals("E") && pieceaposer.get_color().equals("Rouge")) {
            b.setIcon(enzymeRed);
        }
        if (type.equals("E") && pieceaposer.get_color().equals("Bleu")) {
            b.setIcon(enzymeBlue);
        }
        if (type.equals("E") && pieceaposer.get_color().equals("Vert")) {
            b.setIcon(enzymeGreen);
        }
        if (type.equals("E") && pieceaposer.get_color().equals("Rose")) {
            b.setIcon(enzymeRose);
        }
        if (highlighted) {
            b.setBackground(Color.GREEN);
            b.setOpaque(true);
        }
        if (reachable) {
            b.setBackground(Color.ORANGE);
            b.setOpaque(true);
        }
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //   Iterator<Case> cases_selected = Plateau.plateau.iterator();
                //   while (cases_selected.hasNext()) {

                //   }

                click++;
                JButton gb = GUI.this.getGridButton(row, col);
                System.out.println("r" + row + ",c" + col + " " + (b == gb) + " " + (b.equals(gb)));

                if (click == 1) {
                    //int index = (row * 15 + col);

                    clicked = Plateau.plateau.get(index);
                    int joueur = clicked.get_Player();
                    System.out.println("Joueur =" + joueur);
                    clicked.highlight(true);

                    //VERIFICATION DU JOUEUR !
                    if (joueur != etape_tour) {
                        System.out.println("Ce pion n'est pas à vous !");
                        clicked.highlight(false);
                        click = 0;
                    }

                    else if (clicked.get_type().equals("L")) {
                        Plateau.move_lipides(clicked);
                    } else if (clicked.get_type().equals("E")) {
                        Plateau.move_enzyme(clicked);
                    }
                    updatedisplay();
                }
                if (click == 2) {
                    Case target = Plateau.plateau.get(index);
                    System.out.println("Atteignable: " + target.is_reachable());

                    if (target.is_thesameposition(clicked)) {
                        clicked.highlight(false);
                        unreachable();
                        click = 0;
                    } else if (target.is_reachable()) {
                        int old_row = clicked.get_row();
                        int old_col = clicked.get_col();
                        int old_index = (old_row * 15 + old_col);
                        //int joueur = clicked.get_Player();
                        //String color = clicked.get_color();
                        //System.out.println("Joueur =" + joueur);
                        Case vide = new Case(old_row, old_col);

                        int target_row = target.get_row();
                        int target_col = target.get_col();
                        int new_index = (target_row * 15 + target_col);
                        String type = Plateau.plateau.get(new_index).get_type();
                        if (type.equals("M")
                                && Plateau.plateau.get(new_index).get_color().equals(clicked.get_color())) {
                            if (clicked.get_Player() == 1) {
                                scoreJ1++;
                                if (clicked.get_nbMetaboCacthed() >= 5) {
                                    scoreJ1--;
                                    System.out.println(
                                            "Votre enzyme est pleine, le métabolite est détruit, votre score est inchangé !");
                                }
                            } else if (clicked.get_Player() == 2) {
                                scoreJ2++;
                                if (clicked.get_nbMetaboCacthed() >= 5) {
                                    scoreJ2--;
                                    System.out.println(
                                            "Votre enzyme est pleine, le métabolite est détruit, votre score est inchangé !");
                                }
                            }
                            clicked.increase_nbMetaboCatched();
                            int nbMetaboCatched = clicked.get_nbMetaboCacthed();
                            System.out.println("Nbmétabo dans cette enzyme : " + nbMetaboCatched);
                        }
                        clicked.set_x(target_row);
                        clicked.set_y(target_col);
                        Plateau.plateau.set(new_index, clicked);
                        Plateau.plateau.set(old_index, vide);

                        clicked.highlight(false);
                        unreachable();
                        click = 0;
                        changer_etape(); //CHANGE LE JOUEUR

                    } else {
                        System.out.println("Case inaccessible!");
                        clicked.highlight(false);
                        unreachable();
                        click = 0;
                    }
                    updatedisplay();
                }
            }
        });
        return b;
    }

    public void unreachable() {
        for (int i = 0; i < Plateau.plateau.size(); i++) {
            Plateau.plateau.get(i).set_reachable(false);
        }
    }

    public void changer_etape() {
        etape_tour++;
        if (etape_tour > 2) {
            etape_tour = 0;
            nb_tour++;
        }
        if (etape_tour == 0) {
            Plateau.move_all_metabolite();
            updatedisplay();
            changer_etape();
        }
    }

    public JPanel createGridPanel() {
        JPanel p = new JPanel(new GridLayout(N, N));
        p.setPreferredSize(new Dimension(810, 810));
        p.setVisible(true);
        p.setOpaque(false);
        for (int i = 0; i < N * N; i++) {
            int row = i / N;
            int col = i % N;
            JButton gb = createGridButton(row, col);
            list.add(gb);
            p.add(gb);
        }
        return p;
    }

    public void updatedisplay() {
        f.getContentPane().removeAll();
        list.clear();
        display();
        // printboard();
    }

    public void printboard() {
        for (int i = 0; i < Plateau.plateau.size(); i++) {
            String ext_case = Plateau.plateau.get(i).get_type();
            if (ext_case.equals(" ")) {
                ext_case = "_";
            }
            if (i % 15 == 0) {
                System.out.println("\n");
            }
            System.out.print(ext_case + "\t");
        }
    }

    JFrame f = new JFrame("JPlusPlus");

    public boolean init = true;

    public void display() {
        Font font = new Font("Serif", Font.PLAIN, 20);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("ka1.ttf"));
        } catch (Exception e) {
        }

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setPreferredSize(new Dimension(400, 800));
        JLabel titre = new JLabel();
        titre.setText("JPlusPlus");
        titre.setFont(font.deriveFont(Font.PLAIN, 45));
        titre.setForeground(new Color(99, 0, 119));
        titre.setBounds(10, 50, 400, 50);

        JLabel etape = new JLabel();

        String quijoue = "Joueur 1";
        etape.setForeground(J1);
        if (etape_tour == 2) {
            quijoue = "Joueur 2";
            etape.setForeground(J2);
        }
        etape.setText("Au tour de: " + quijoue);
        etape.setFont(font.deriveFont(Font.PLAIN, 20));
        etape.setBounds(20, 150, 400, 100);

        JLabel numero_tour = new JLabel();
        numero_tour.setText("Tour " + nb_tour);
        numero_tour.setForeground(Color.ORANGE);
        numero_tour.setFont(font.deriveFont(Font.PLAIN, 20));
        numero_tour.setBounds(20, 200, 200, 100);

        JLabel joueur1 = new JLabel();
        joueur1.setText("Score J1   " + scoreJ1);
        joueur1.setForeground(J1);
        joueur1.setFont(font.deriveFont(Font.PLAIN, 20));
        joueur1.setBounds(20, 350, 200, 100);

        JLabel joueur2 = new JLabel();
        joueur2.setForeground(J2);
        joueur2.setText("Score J2   " + scoreJ2);
        joueur2.setFont(font.deriveFont(Font.PLAIN, 20));
        joueur2.setBounds(20, 400, 200, 100);

        p2.add(titre);
        p2.add(etape);
        p2.add(numero_tour);
        p2.add(joueur1);
        p2.add(joueur2);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // sp.setResizeWeight(0.7);
        //sp.setEnabled(false);
        sp.setDividerSize(10);

        sp.add(createGridPanel());
        sp.add(p2);

        f.add(sp, BorderLayout.CENTER);
        f.pack();

        if (init) {
            f.setLocationRelativeTo(null);
        }
        f.setVisible(true);
        init = false;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                Plateau.set_element();
                new GUI().display();
            }
        });
    }
}
