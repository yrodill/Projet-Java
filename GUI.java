import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.util.*;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

//import javax.lang.model.util.SimpleTypeVisitor6;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JColorChooser;

/**
* https://stackoverflow.com/questions/7702697/how-to-get-x-and-y-index-of-element-inside-gridlayout
 */

public class GUI {

    private static final int N = 15;
    private final List<JButton> list = new ArrayList<JButton>();
    public int click = 0;
    public Case clicked;
    public Case target;

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
        b = new JButton(type);
        if (index%2==0){
            b.setBackground(Color.WHITE);
            b.setForeground(Color.BLACK);
            b.setOpaque(true);
        }
        if (index%2==1){
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);            
            b.setOpaque(true);
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
                    clicked.highlight(true);
                    listOfpossibilities(clicked);
                    updatedisplay();
                }
                if (click == 2) {
                    clicked.highlight(false);
                    unreachable();
                    target = Plateau.plateau.get(index);
                    Plateau.plateau.set(index, clicked);
                    int old_row = clicked.get_row();
                    int old_col = clicked.get_col();
                    int old_index = (old_row * 15 + old_col);
                    Plateau.plateau.set(old_index, target);
                    updatedisplay();
                    click = 0;
                }
            }
        });
        return b;
    }

    public void listOfpossibilities(Case selected) {
        if (selected instanceof Lipid) {
            int row = selected.get_row();
            int col = selected.get_col();
            System.out.println(row + "," + col);
            for (int i = 1; i <= 3; i++) {
                int possible_case = row + i;
                int index=possible_case*15+col;
                String type=Plateau.plateau.get(index).get_type();
                if (type.equals(" ")) {
                    Plateau.plateau.get(index).set_reachable(true);
                }
                else{
                    return;
                }
            }
        }
    }

    public void unreachable() {
        for (int i = 0; i < Plateau.plateau.size(); i++) {
            Plateau.plateau.get(i).set_reachable(false);
    }
}

    public Vector GeneratedNumbers = new Vector<int[]>();

    public Vector RandomPlace() {
        Random randomGenerator = new Random();
        Vector randompoz = new Vector();
        randompoz.clear();
        int x = 4 + randomGenerator.nextInt(7);
        int y = randomGenerator.nextInt(15);

        randompoz.add(x);
        randompoz.add(y);

        while (GeneratedNumbers.contains(randompoz)) {
            //  System.out.println(randompoz + "DEJA PRESENT!");
            randompoz.clear();
            x = 4 + randomGenerator.nextInt(7);
            y = randomGenerator.nextInt(15);

            randompoz.add(x);
            randompoz.add(y);
        }

        //System.out.println("NUMBER GENERATED" + randompoz.get(0) + " , " + randompoz.get(1));
        //System.out.println("LIST OF COORDINATES:");
        //printvector(GeneratedNumbers);

        GeneratedNumbers.add(randompoz);
        return randompoz;
    }

    public static void printvector(Vector vecteur) {
        Enumeration vEnum = vecteur.elements();

        while (vEnum.hasMoreElements())
            System.out.print(vEnum.nextElement() + " ");
    }

    public JPanel createGridPanel() {
        JPanel p = new JPanel(new GridLayout(N, N));
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

    JFrame f = new JFrame("GridButton");

    public boolean init = true;

    public void display() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGridPanel());
        f.pack();
        f.setSize(800, 800);
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