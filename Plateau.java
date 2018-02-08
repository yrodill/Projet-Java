import java.io.*;
import java.util.*;

public class Plateau {
    public static Vector<Case> plateau = new Vector<Case>();

    public Plateau(Vector nom) {
        plateau = nom;
    }

    public static Vector GeneratedNumbers = new Vector<Vector>();

    public static Vector<Integer> RandomPlace() {
        Vector<Integer> randompoz = new Vector();
        randompoz.clear();
        Random randomGenerator = new Random();
        Integer x = 4 + randomGenerator.nextInt(7);
        Integer y = randomGenerator.nextInt(15);

        randompoz.addElement(x);
        randompoz.addElement(y);

        while (GeneratedNumbers.contains(randompoz)) {
            //  System.out.println(randompoz + "DEJA PRESENT!");
            randompoz.clear();
            x = 4 + randomGenerator.nextInt(7);
            y = randomGenerator.nextInt(15);

            randompoz.addElement(x);
            randompoz.addElement(y);
        }

        //System.out.println("NUMBER GENERATED" + randompoz.get(0) + " , " + randompoz.get(1));
        //System.out.println("LIST OF COORDINATES:");
        //printvector(GeneratedNumbers);

        GeneratedNumbers.addElement(randompoz);
        //System.out.println(randompoz);
        return randompoz;
    }

    public static void printvector(Vector vecteur) {
        Enumeration vEnum = vecteur.elements();

        while (vEnum.hasMoreElements()) {
            System.out.print(vEnum.nextElement() + " ");
        }
    }

    public static void set_element() {
        String color = "";
        int pickColor = 0;
        int pickColor2 = 0;
        int pickColor3 = 0;
        ArrayList<String> couleursEnzyme = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            couleursEnzyme.add("Rouge");
            couleursEnzyme.add("Bleu");
            couleursEnzyme.add("Vert");
            couleursEnzyme.add("Rose");
        }
        ArrayList<String> couleursMetabolite = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            couleursMetabolite.add("Rouge");
            couleursMetabolite.add("Vert");
            couleursMetabolite.add("Bleu");
            couleursMetabolite.add("Rose");
        }

        Collections.shuffle(couleursEnzyme);
        Collections.shuffle(couleursMetabolite);
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int index = (row * 15 + col);
                //System.out.println(index);
                if (row == 0 && (col % 2 == 0)) {
                    int joueur = 1;
                    Case enzyme = new Enzyme(row, col, joueur, color);
                    enzyme.set_color(couleursEnzyme.get(pickColor));
                    pickColor++;
                    enzyme.set_joueur(joueur);
                    String quelcouleur = enzyme.get_color();
                    plateau.add(index, enzyme);
                } else if (row == 14 && (col % 2 == 0)) {
                    int joueur = 2;
                    Case enzyme = new Enzyme(row, col, joueur, color);
                    enzyme.set_color(couleursEnzyme.get(pickColor2));
                    pickColor2++;
                    enzyme.set_joueur(joueur);
                    plateau.add(index, enzyme);
                }

                else if ((Arrays.asList(1).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(2).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))) {
                    int joueur = 1;
                    Case lipid = new Lipid(row, col, joueur, color);
                    lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                } else if ((Arrays.asList(13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
                    int joueur = 2;
                    Case lipid = new Lipid(row, col, joueur, color);
                    lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                }

                else {
                    int joueur = 0;
                    Case vide = new Case_vide(row, col, joueur, color);
                    vide.set_joueur(joueur);
                    plateau.add(index, vide);
                }
            }
        }
        for (int nbmetabolite = 0; nbmetabolite < 40; nbmetabolite++) {
            Vector<Integer> randomcoord = new Vector();
            randomcoord = RandomPlace();
            Integer new_row = randomcoord.get(0);
            Integer new_col = randomcoord.get(1);
            int index = new_row * 15 + new_col;
            int joueur = 0;
            Case metabolite = new Metabolite(new_row, new_col, joueur, color);
            metabolite.set_color(couleursMetabolite.get(pickColor3));
            pickColor3++;
            plateau.set(index, metabolite);
        }
    }

    public static void move_metabolite() {

        Iterator<Case> cases_selected = plateau.iterator();
        while (cases_selected.hasNext()) {
            Vector<Vector> possible_move = new Vector();
            Case selected = cases_selected.next();
            //System.out.println(cases_selected.next());            
            if (selected instanceof Metabolite) {

                int row = selected.get_row();
                int col = selected.get_col();
                int index = row * 15 + col;

                for (int i = 1; i < 4; i++) {
                    int possible_col = col + i; //droite
                    int possible_index = row * 15 + possible_col;
                    if (bornee(possible_col)) {
                        if (!plateau.get(possible_index).get_type().equals(" ")) {
                            break;
                        }
                        Vector<Integer> possible_coordinate = new Vector();
                        possible_coordinate.add(row);
                        possible_coordinate.add(possible_col);
                        possible_move.add(possible_coordinate);
                    }
                }
                
                for (int i = 1; i < 4; i++) {
                    int possible_col = col - i; //gauche
                    int possible_index = row * 15 + possible_col;
                    if (bornee(possible_col)) {
                        if (!plateau.get(possible_index).get_type().equals(" ")) {
                            break;
                        }
                        Vector<Integer> possible_coordinate = new Vector();
                        possible_coordinate.add(row);
                        possible_coordinate.add(possible_col);
                        possible_move.add(possible_coordinate);
                    }
                }
                for (int i = 1; i < 4; i++) {
                    int possible_row = row + i; //bas
                    int possible_index = possible_row * 15 + col;
                    if (bornee(possible_row)) {
                        if (!plateau.get(possible_index).get_type().equals(" ")) {
                            break;
                        }
                        Vector<Integer> possible_coordinate = new Vector();
                        possible_coordinate.add(possible_row);
                        possible_coordinate.add(col);
                        possible_move.add(possible_coordinate);
                    }
                }
                for (int i = 1; i < 4; i++) {
                    int possible_row = row - i; //haut
                    int possible_index = possible_row * 15 + col;
                    if (bornee(possible_row)) {
                        if (!plateau.get(possible_index).get_type().equals(" ")) {
                            break;
                        }
                        Vector<Integer> possible_coordinate = new Vector();
                        possible_coordinate.add(possible_row);
                        possible_coordinate.add(col);
                        possible_move.add(possible_coordinate);
                    }
                }
                if (!possible_move.isEmpty()) {
                    printvector(possible_move);
                    Random r = new Random();
                    int randomindex = r.nextInt(possible_move.size());
                    Vector<Integer> coord_choisie = possible_move.get(randomindex);
                    Integer row_choisie = coord_choisie.get(0);
                    Integer col_choisie = coord_choisie.get(1);
                    Integer index_choisie = row_choisie * 15 + col_choisie;
                    System.out.println("\nrow: " + row_choisie);
                    System.out.println("col: " + col_choisie);
                    selected.set_x(row_choisie);
                    selected.set_y(col_choisie);
                    Case vide = new Case_vide(row, col, 0, "blanc");
                    plateau.set(index, vide);
                    plateau.set(index_choisie, selected);
                }
            }

        }
    }

    public static boolean bornee(int coord_xy) {
        if (coord_xy < 15 && coord_xy > -1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        set_element();
        for (int i = 0; i < plateau.size(); i++) {
            Vector ext_case = plateau.get(i).get_coordinate();
            String type = plateau.get(i).get_type();
            System.out.println(ext_case);
        }
    }

}
