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
    public ImageIcon mito = new ImageIcon("./mito_sized.png");
    public ImageIcon lipid = new ImageIcon("./lipid.png");
    public ImageIcon enzyme = new ImageIcon("./enzyme.png");



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

        if (index % 2 == 0) {
            // b.setBackground(Color.WHITE);
            // b.setForeground(Color.BLACK);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (index % 2 == 1) {
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
            b.setOpaque(true);
        }
        if (type.equals("M")) {
            b.setIcon(mito);
        }
        if (type.equals("L")) {
            b.setIcon(lipid);
        }
        if (type.equals("E")) {
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
                        click = 1;
                    }

                    updatedisplay();
                }
            }
        });
        return b;
    }

    public void listOfpossibilities(Case selected) {
        if (selected instanceof Lipid && selected.get_Player()==1) {
            int row = selected.get_row();
            int col = selected.get_col();
            //System.out.println(row + "," + col);
            for (int i = 1; i <= 3; i++) {
                int possible_case = row + i;
                int index = possible_case * 15 + col;
                String type = Plateau.plateau.get(index).get_type();
                if (type.equals(" ")) {
                    Plateau.plateau.get(index).set_reachable(true);
                }
              }
            }
         else if (selected instanceof Lipid && selected.get_Player()==2) {
                int row = selected.get_row();
                int col = selected.get_col();
                //System.out.println(row + "," + col);
                  for (int i = 1; i <= 3; i++) {
                    int possible_case = row - i;
                    int index = possible_case*15+col;
                    String type = Plateau.plateau.get(index).get_type();
                    if (type.equals(" ")) {
                        Plateau.plateau.get(index).set_reachable(true);
                    }
                  }
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
        f.add(createGridPanel());
        f.pack();
        //ImageIcon imageIcon = new ImageIcon("./background.jpg"); // load the image to a imageIcon
        //Image image = imageIcon.getImage(); // transform it
        //Image newimg = image.getScaledInstance(800, 800,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        //imageIcon = new ImageIcon(newimg);
        //JLabel background = new JLabel(imageIcon);
        //f.add(background);

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
