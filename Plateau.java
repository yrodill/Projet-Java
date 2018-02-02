import java.io.*;
import java.util.*;

public class Plateau {
    public static Vector plateau = new Vector();

    public Plateau(Vector nom) {
        plateau = nom;
    }

    public static void set_element() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                int index = (row * 10 + col);
                System.out.println(index);
                if ((row == 0 || row == 14) && (col % 2 == 1)) {
                    Enzyme enzyme = new Enzyme(row, col);
                    plateau.add(index, enzyme);
                } 
                if ((Arrays.asList(1, 13).contains(row) && Arrays.asList(2, 4, 6, 8, 10, 12).contains(col))
                        || (Arrays.asList(2, 12).contains(row) && Arrays.asList(1, 3, 5, 7, 9, 11, 13).contains(col))
                        || (row == 3 && Arrays.asList(0, 2, 4, 6, 8, 10, 12).contains(col))
                        || (row == 11 && Arrays.asList(2, 4, 6, 8, 10, 12, 14).contains(col))) {
                    Lipid lipid = new Lipid(row, col);
                    plateau.add(index, lipid);
                }
                else{
                    plateau.add(index,"");
                }
            }
        }
    }

    public static void main(String[] args) {
       set_element();
        for(int i=0; i<plateau.size();i++){
            System.out.println(plateau.get(i));
        }
    }

}