import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

//import javax.lang.model.util.SimpleTypeVisitor6;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* https://stackoverflow.com/questions/7702697/how-to-get-x-and-y-index-of-element-inside-gridlayout
 */

public class GUI {

    private static final int N = 15;
    private final List<JButton> list = new ArrayList<JButton>();

    private JButton getGridButton(int r, int c) {
        int index = r * N + c;
        return list.get(index);
    }

    private JButton createGridButton(final int row, final int col) {
        final JButton b;

        int index = (row * 10 + col);
        String type=Plateau.plateau.get(index).get_type();
        b = new JButton(type);
        // if ((row == 0 || row == 14) && (col % 2 == 1)) {
        //     b = new JButton("E");
        // } else if ((Arrays.asList(1, 13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
        //         || (Arrays.asList(2, 12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
        //         || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))
        //         || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
        //     b = new JButton("L");
        // } else {
        //     b = new JButton(" ");
        // }
        
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton gb = GUI.this.getGridButton(row, col);
                System.out.println("r" + row + ",c" + col + " " + (b == gb) + " " + (b.equals(gb)));
                updatedisplay();

                //                RandomPlace();
            }

        });
        return b;
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

    public void metabolite() {
        //     for (int i = 0; i < 40; i++) {
        //         Vector coordinate=RandomPlace();
        //         int x = coordinate.get(0);
        //         int y = coordinate.get(1);   
        //     }

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