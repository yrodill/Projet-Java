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
* https://stackoverflow.com/questions/7702697/how-to-get-x-and-y-index-of-element-inside-gridlayout
 */

public class GUI {

    private static final int N = 15;
    private final List<JButton> list = new ArrayList<JButton>();
    public int click = 0;
    public Case clicked;
    public ImageIcon mito = new ImageIcon("./mito_red.png");
    public ImageIcon lipid = new ImageIcon("./lipid.png");
    public ImageIcon enzyme = new ImageIcon("./enzyme_red.png");
    public ImageIcon redc = new ImageIcon(new ImageIcon("./circlered.png").getImage().getScaledInstance(52, 52, Image.SCALE_DEFAULT));

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
            b.setBackground(new Color(110, 186, 29));
            b.setOpaque(true);
            // b.setContentAreaFilled(false);
            // b.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (pieceaposer.get_Player()  == 2) {
            b.setBackground(new Color(145, 187, 255));
            b.setOpaque(true);
        }
        if (type.equals("M")) {
            b.setIcon(mito);
        }
        if (type.equals("L")) {
            b.setIcon(lipid);
        }
        if (type.equals("E")) {
            //b.setBorder(null);          
            //JLabel picLabel = new JLabel();
            //picLabel.setIcon(redc); 
            //JLabel picIcon = new JLabel();
            //picIcon.setIcon(enzyme); 
            //picIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//
            //b.add(picIcon);
            //b.add(picLabel);
            b.setIcon(enzyme);
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
                click++;
                JButton gb = GUI.this.getGridButton(row, col);
                System.out.println("r" + row + ",c" + col + " " + (b == gb) + " " + (b.equals(gb)));

                if (click == 1) {
                    //int index = (row * 15 + col);

                    clicked = Plateau.plateau.get(index);
                    int joueur = clicked.get_Player();
                    System.out.println("Joueur ="+joueur);
                    clicked.highlight(true);
                    listOfpossibilities(clicked);
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
                        int joueur = clicked.get_Player();
                        System.out.println("Joueur ="+joueur);
                        Case vide = new Case_vide(old_row, old_col,joueur);

                        int target_row = target.get_row();
                        int target_col = target.get_col();
                        int new_index = (target_row * 15 + target_col);

                        clicked.set_x(target_row);
                        clicked.set_y(target_col);
                        Plateau.plateau.set(new_index, clicked);
                        Plateau.plateau.set(old_index, vide);

                        clicked.highlight(false);
                        unreachable();
                        click = 0;
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

    public void listOfpossibilities(Case selected) {
        if (selected instanceof Lipid) {
            int row = selected.get_row();
            int col = selected.get_col();
            int possible_case = 0;
            //System.out.println(row + "," + col);
            for (int i = 1; i <= 3; i++) {
                if (selected.get_Player() == 1) {
                    possible_case = row + i;
                } else if (selected.get_Player() == 2) {
                    possible_case = row - i;
                }
                int index = possible_case * 15 + col;
                String type = Plateau.plateau.get(index).get_type();
                if (type.equals(" ")) {
                    Plateau.plateau.get(index).set_reachable(true);
                } else {
                    Plateau.plateau.get(index).set_reachable(false);
                    break;
                }
            }
            // if(selected instanceof Enzyme){
            //   int row = selected.get_row();
            //   int col = selected.get_col();
            //   int[] possible_case = new int[];
            //   for(int i = 1 ; i<3 ; i++){
            //     int move = row-i;
            //     int move2 = row+i;
            //     possible_case.add(move);
            //     possible_case.add(move2);
            //
            //   }
            //
            // }
        } else {
            return;
        }

    }

    public void unreachable() {
        for (int i = 0; i < Plateau.plateau.size(); i++) {
            Plateau.plateau.get(i).set_reachable(false);
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
    }

    JFrame f = new JFrame("JPlusPlus");

    public boolean init = true;

    public void display() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setResizable(false);
        //f.add(createGridPanel());
        //ImageIcon imageIcon = new ImageIcon("./background.jpg"); // load the image to a imageIcon
        //Image image = imageIcon.getImage(); // transform it
        //Image newimg = image.getScaledInstance(800, 800,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        //imageIcon = new ImageIcon(newimg);
        //JLabel background = new JLabel(imageIcon);
        //f.add(background);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setPreferredSize(new Dimension(300, 800));
        JLabel titre = new JLabel();
        titre.setText("JPlusPlus");
        titre.setFont(new Font("Serif", Font.PLAIN, 44));
        titre.setBounds(20, 50, 300, 50);

        JLabel joueur1 = new JLabel();
        joueur1.setText("Score joueur 1: ");
        joueur1.setFont(new Font("Serif", Font.PLAIN, 20));
        joueur1.setBounds(20, 150, 200, 100);

        JLabel joueur2 = new JLabel();
        joueur2.setText("Score joueur 1: ");
        joueur2.setFont(new Font("Serif", Font.PLAIN, 20));
        joueur2.setBounds(20, 250, 200, 100);

        p2.add(titre);
        p2.add(joueur1);
        p2.add(joueur2);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp.setResizeWeight(0.7);
        sp.setEnabled(false);
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
