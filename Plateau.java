import java.io.*;
import java.util.*;

public class Plateau {
    public static Vector<Case> plateau = new Vector<Case>();
    public static int taille_plateau = 15;
    public static int nb_metabolite = 40;



    public Plateau(Vector nom) {
        plateau = nom;
    }

    public static Vector GeneratedNumbers = new Vector<Vector>();

    public static Vector<Integer> RandomPlace() {
        Vector<Integer> randompoz = new Vector();
        randompoz.clear();
        Random randomGenerator = new Random();
        Integer x = 4 + randomGenerator.nextInt(7);
        Integer y = randomGenerator.nextInt(taille_plateau);

        randompoz.addElement(x);
        randompoz.addElement(y);

        while (GeneratedNumbers.contains(randompoz)) {
            //  System.out.println(randompoz + "DEJA PRESENT!");
            randompoz.clear();
            x = 4 + randomGenerator.nextInt(7);
            y = randomGenerator.nextInt(taille_plateau);

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
        for (int row = 0; row < taille_plateau; row++) {
            for (int col = 0; col < taille_plateau; col++) {
                int index = (row * taille_plateau + col);
                //System.out.println(index);
                if (row == 0 && (col % 2 == 0)) {
                    //int joueur = 1;
                    Case enzyme = new Enzyme(row, col, 1, couleursEnzyme.get(pickColor));
                    //enzyme.set_color(couleursEnzyme.get(pickColor));
                    pickColor++;
                    //enzyme.set_joueur(joueur);
                    String quelcouleur = enzyme.get_color();
                    plateau.add(index, enzyme);
                } else if (row == (taille_plateau - 1) && (col % 2 == 0)) {
                    //int joueur = 2;
                    Case enzyme = new Enzyme(row, col, 2, couleursEnzyme.get(pickColor2));
                    enzyme.set_color(couleursEnzyme.get(pickColor2));
                    pickColor2++;
                    //enzyme.set_joueur(joueur);
                    plateau.add(index, enzyme);
                }

                else if ((Arrays.asList(1).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(2).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))) {
                    //int joueur = 1;
                    Case lipid = new Lipid(row, col, 1);
                    //lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                } else if ((Arrays.asList(13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
                    //int joueur = 2;
                    Case lipid = new Lipid(row, col, 2);
                    //lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                }

                else {
                    int joueur = 0;
                    Case vide = new Case(row, col);
                    vide.set_joueur(joueur);
                    plateau.add(index, vide);
                }
            }
        }
        for (int nbmetabolite = 0; nbmetabolite < nb_metabolite; nbmetabolite++) {
            Vector<Integer> randomcoord = new Vector();
            randomcoord = RandomPlace();
            Integer new_row = randomcoord.get(0);
            Integer new_col = randomcoord.get(1);
            int index = new_row * taille_plateau + new_col;
            //int joueur = 0;
            Case metabolite = new Metabolite(new_row, new_col, 0, couleursMetabolite.get(pickColor3));
            //metabolite.set_color(couleursMetabolite.get(pickColor3));
            pickColor3++;
            plateau.set(index, metabolite);
        }
    }

    public static boolean test_move(int move) {
        if (move >= 0 && move < taille_plateau * taille_plateau) {
            return true;
        }
        return false;
    }

    public static void move_lipides(Case clicked) {

        Case selected = clicked;

        if (selected instanceof Lipid) {
            int row = selected.get_row();
            int col = selected.get_col();
            int possible_case = 0;
            //System.out.println(row + "," + col);
            for (int i = 1; i <= Lipid.distance_max; i++) {
                if (selected.get_Player() == 1) {
                    possible_case = row + i;
                } else if (selected.get_Player() == 2) {
                    possible_case = row - i;
                }
                int index = possible_case * taille_plateau + col;
                String type = Plateau.plateau.get(index).get_type();
                if (type.equals(" ")) {
                    Plateau.plateau.get(index).set_reachable(true);
                } else {
                    Plateau.plateau.get(index).set_reachable(false);
                    break;
                }
            }
        }
    }

    public static void move_enzyme(Case clicked) {

        Case selected = clicked;

        if (selected instanceof Enzyme) {
            List<Integer> move_possible_enzyme = new ArrayList<Integer>();
            int row = selected.get_row();
            int col = selected.get_col();
            int move, move2, move4, move5, move7, move8;
            move = -1; //initialisé à 1 pour éviter que la case 0 soit proposée sur un déplacement impossible.
            move2 = -1;
            move4 = -1;
            move5 = -1;
            move7 = -1;
            move8 = -1;
            if (col != 0) {
                move = row * taille_plateau - 1 + col; //gauche
                move4 = (row - 1) * taille_plateau + col - 1; //diagonale haut gauche
                move7 = (row + 1) * taille_plateau + col - 1; // diagonale bas gauche
            }
            if (col != 14) {
                move2 = row * taille_plateau + 1 + col; //droite
                move5 = (row - 1) * taille_plateau + col + 1; // diagonale haut droite
                move8 = (row + 1) * taille_plateau + col + 1; //diagonale bas droite

            }
            int move3 = (row - 1) * taille_plateau + col; //haut
            int move6 = (row + 1) * taille_plateau + col; // bas
            if (test_move(move)) {
                move_possible_enzyme.add(move);
            }
            if (test_move(move2)) {
                move_possible_enzyme.add(move2);
            }
            if (test_move(move3)) {
                move_possible_enzyme.add(move3);
            }
            if (test_move(move4)) {
                move_possible_enzyme.add(move4);
            }
            if (test_move(move5)) {
                move_possible_enzyme.add(move5);
            }
            if (test_move(move6)) {
                move_possible_enzyme.add(move6);
            }
            if (test_move(move7)) {
                move_possible_enzyme.add(move7);
            }
            if (test_move(move8)) {
                move_possible_enzyme.add(move8);
            }

            for (int j = 0; j < move_possible_enzyme.size(); j++) {
                String type = Plateau.plateau.get(move_possible_enzyme.get(j)).get_type();
                if (!type.equals("E")) {
                    Plateau.plateau.get(move_possible_enzyme.get(j)).set_reachable(true);
                } else {
                    Plateau.plateau.get(move_possible_enzyme.get(j)).set_reachable(false);
                }
            }
        }
    }

    public static void move_all_metabolite() {
        Vector<Integer> randomcasepicker = new Vector();
        for (int nbdecase = 0; nbdecase < plateau.size(); nbdecase++) {
            randomcasepicker.add(nbdecase, nbdecase);
        }
        Collections.shuffle(randomcasepicker);
        for (int j = 0; j < randomcasepicker.size(); j++) {
            Case metaboSelected = plateau.get(randomcasepicker.get(j));
            if (metaboSelected instanceof Metabolite) {
                move_metabolite(metaboSelected);//TESTINGGGGGGGGGGGGGG
            }

        }
    }

    public static void move_metabolite(Case metaboSelected) {

        Vector<Vector> possible_move = new Vector();
        Case selected = metaboSelected;
        //System.out.println(cases_selected.next());
        if (selected instanceof Metabolite) {

            int row = selected.get_row();
            int col = selected.get_col();
            int index = row * taille_plateau + col;

            for (int i = 1; i <= Metabolite.distance_max; i++) {
                int possible_col = col + i; //droite
                int possible_index = row * taille_plateau + possible_col;
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

            for (int i = 1; i <= Metabolite.distance_max; i++) {
                int possible_col = col - i; //gauche
                int possible_index = row * taille_plateau + possible_col;
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
            for (int i = 1; i <= Metabolite.distance_max; i++) {
                int possible_row = row + i; //bas
                int possible_index = possible_row * taille_plateau + col;
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
            for (int i = 1; i <= Metabolite.distance_max; i++) {
                int possible_row = row - i; //haut
                int possible_index = possible_row * taille_plateau + col;
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
                System.out.println("\n------------------------------\n");
                System.out.println("\nrow: " + row + " col: " + col + "\n");
                printvector(possible_move);
                Collections.shuffle(possible_move);
                Random r = new Random();
                int randomindex = r.nextInt(possible_move.size());
                Vector<Integer> coord_choisie = possible_move.get(randomindex);
                Integer row_choisie = coord_choisie.get(0);
                Integer col_choisie = coord_choisie.get(1);
                Integer index_choisie = row_choisie * taille_plateau + col_choisie;
                System.out.println("\nrow_choosed: " + row_choisie + " col_choosed: " + col_choisie + "\n");
                selected.set_x(row_choisie);
                selected.set_y(col_choisie);
                Case vide = new Case(row, col);
                plateau.set(index, vide);
                plateau.set(index_choisie, selected);
            }
        }

    }

    public static boolean bornee(int coord_xy) {
        if (coord_xy < taille_plateau && coord_xy > -1) {
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
