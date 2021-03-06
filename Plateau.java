import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Plateau {
    public static Vector<Piece> plateau = new Vector<Piece>();
    public static int taille_plateau = 15;
    public static int nb_metabolite = 40;

    public Plateau(Vector nom) {
        plateau = nom;
    }

    public static Vector GeneratedNumbers = new Vector<Vector>();

    public static int get_nb_metabol() {
        return nb_metabolite;
    }

    public static void set_nb_metabol(int nombre) {
        nb_metabolite = nombre;
    }

    /*création de coordonnées aléatoires stockées dans un vecteur
    pour positionner les métabolites sur le plateau de jeu.
    La fonction renvoie un vecteur contenant des listes de coordonnées.*/
    public static Vector<Integer> RandomPlace() {
        Vector<Integer> randompoz = new Vector();
        randompoz.clear();
        Random randomGenerator = new Random();
        Integer x = 4 + randomGenerator.nextInt(7);
        Integer y = randomGenerator.nextInt(taille_plateau);

        randompoz.addElement(x);
        randompoz.addElement(y);

        while (GeneratedNumbers.contains(randompoz)) {
            randompoz.clear();
            x = 4 + randomGenerator.nextInt(7);
            y = randomGenerator.nextInt(taille_plateau);

            randompoz.addElement(x);
            randompoz.addElement(y);
        }

        GeneratedNumbers.addElement(randompoz);
        return randompoz;
    }

    /*fonction permettant de créer les éléments au début d'une partie et de les
    positionner sur le plateau selon les règles décrites dans le sujet.*/
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
                if (row == 0 && (col % 2 == 0)) {
                    Piece enzyme = new Enzyme(row, col, 1, couleursEnzyme.get(pickColor));
                    pickColor++;
                    String quelcouleur = enzyme.get_color();
                    plateau.add(index, enzyme);
                } else if (row == (taille_plateau - 1) && (col % 2 == 0)) {
                    Piece enzyme = new Enzyme(row, col, 2, couleursEnzyme.get(pickColor2));
                    enzyme.set_color(couleursEnzyme.get(pickColor2));
                    pickColor2++;
                    plateau.add(index, enzyme);
                }

                else if ((Arrays.asList(1).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(2).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))) {
                    Piece lipid = new Lipid(row, col, 1);
                    plateau.add(index, lipid);
                } else if ((Arrays.asList(13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
                    Piece lipid = new Lipid(row, col, 2);
                    plateau.add(index, lipid);
                }

                else {
                    int joueur = 0;
                    Piece vide = new Piece(row, col);
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
            Piece metabolite = new Metabolite(new_row, new_col, 0, couleursMetabolite.get(pickColor3));
            pickColor3++;
            plateau.set(index, metabolite);
        }
    }

    /*fonction permettant de tester les mouvements et
    faire en sorte qu'il ne sorte pas des limites physiques du plateau de jeu*/
    public static boolean test_move(int move) {
        if (move >= 0 && move < taille_plateau * taille_plateau) {
            return true;
        }
        return false;
    }

    public static void move_lipides(Piece clicked) {

        Piece selected = clicked;

        if (selected instanceof Lipid) {
            int row = selected.get_row();
            int col = selected.get_col();
            int possible_case = 0;
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

    public static void move_enzyme(Piece clicked) {
        Piece selected = clicked;
        if (selected instanceof Enzyme) {
            List<Integer> move_possible_enzyme = new ArrayList<Integer>();
            int row = selected.get_row();
            int col = selected.get_col();

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int test_row = row + i;
                    int test_col = col + j;
                    if (bornee(test_row) && bornee(test_col)) {
                        int test_index = test_row * taille_plateau + test_col;
                        move_possible_enzyme.add(test_index);
                    }
                }
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

    /*Fonction permettant de créer un vecteur qui contiendra toutes les positions du plateau
    dans un ordre aléatoire pour déplacer les métabolites dans un ordre lui aussi aléatoire
    et éviter qu'ils "s'entassent" sur la partie gauche du plateau.*/
    public static void move_all_metabolite() {
        Vector<Integer> randompiecepicker = new Vector();
        for (int nbdepiece = 0; nbdepiece < plateau.size(); nbdepiece++) {
            randompiecepicker.add(nbdepiece, nbdepiece);
        }
        Collections.shuffle(randompiecepicker);
        for (int j = 0; j < randompiecepicker.size(); j++) {
            Piece metaboSelected = plateau.get(randompiecepicker.get(j));
            if (metaboSelected instanceof Metabolite) {
                move_metabolite(metaboSelected);
            }

        }
    }

    public static void move_metabolite(Piece metaboSelected) {

        Vector<Vector> possible_move = new Vector();
        Piece selected = metaboSelected;
        if (selected instanceof Metabolite) {

            int row = selected.get_row();
            int col = selected.get_col();
            int index = row * taille_plateau + col;

            for (int i = 1; i <= Metabolite.distance_max; i++) {
                int possible_col = col + i; //droite
                int possible_index = row * taille_plateau + possible_col;
                if (bornee(possible_col)) { //on regarde si la position n'est pas hors plateau
                    if (!plateau.get(possible_index).get_type().equals(" ")) { // on regarde si la case est libre sinon on empêche les déplacements qui suivent avec un break.
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
                Collections.shuffle(possible_move);
                Random r = new Random();
                int randomindex = r.nextInt(possible_move.size());
                Vector<Integer> coord_choisie = possible_move.get(randomindex);
                Integer row_choisie = coord_choisie.get(0);
                Integer col_choisie = coord_choisie.get(1);
                Integer index_choisie = row_choisie * taille_plateau + col_choisie;
                selected.set_x(row_choisie);
                selected.set_y(col_choisie);
                Piece vide = new Piece(row, col);
                plateau.set(index, vide);
                plateau.set(index_choisie, selected);
            }
        }

    }

    /*fonction empêchant d'obtenir des coordonées hors du plateau
    lors du calcul des déplacements possibles pour un métabolite sélectionné*/
    public static boolean bornee(int coord_xy) {
        if (coord_xy < taille_plateau && coord_xy > -1) {
            return true;
        }
        return false;
    }

}
