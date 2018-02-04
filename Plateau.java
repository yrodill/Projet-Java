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

        while (vEnum.hasMoreElements())
            System.out.print(vEnum.nextElement() + " ");
    }




    public static void set_element() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int index = (row * 15 + col);
                //System.out.println(index);
                if (row == 0 && (col % 2 == 1)) {
                    int joueur = 1;
                    Case enzyme = new Enzyme(row, col,joueur);
                    enzyme.set_joueur(joueur);
                    plateau.add(index, enzyme);
                }
                else if  (row == 14 && (col % 2 == 1)) {
                    int joueur =2;
                    Case enzyme = new Enzyme(row, col,joueur);
                    enzyme.set_joueur(joueur);
                    plateau.add(index, enzyme);
                }

                else if ((Arrays.asList(1).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(2).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))) {
                    int joueur = 1;
                    Case lipid = new Lipid(row, col,joueur);
                    lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                }
                else if ((Arrays.asList(13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
                    int joueur = 2;
                    Case lipid = new Lipid(row, col,joueur);
                    lipid.set_joueur(joueur);
                    plateau.add(index, lipid);
                }

                else{
                    int joueur = 0;
                    Case vide=new Case_vide(row,col,joueur);
                    vide.set_joueur(joueur);
                    plateau.add(index,vide);
                }
            }
        }
        for (int nbmetabolite=0;nbmetabolite<40;nbmetabolite++){
            Vector<Integer> randomcoord= new Vector();
            randomcoord=RandomPlace();
            Integer new_row= randomcoord.get(0);
            Integer new_col= randomcoord.get(1);
            int index= new_row*15+new_col;
            int joueur = 0 ;
            Case metabolite=new Metabolite(new_row, new_col,joueur);
            plateau.set(index, metabolite);
        }
    }

    public static void main(String[] args) {
       set_element();
        for(int i=0; i<plateau.size();i++){
            Vector ext_case=plateau.get(i).get_coordinate();
            String type=plateau.get(i).get_type();
            System.out.println(ext_case);
        }
    }

}
