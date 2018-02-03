import java.io.*;
import java.util.*;

public class Case {
    public Vector Pion_Case = new Vector();
    private int x;
    private int y;

    public void set_x(int coord_x) {
        x = coord_x;
    }

    public void set_y(int coord_y) {
        y = coord_y;
    }

    public Vector get_coordinate() {
        Vector coordinate = new Vector();
        coordinate.add(x);
        coordinate.add(y);
        return coordinate;
    }

    public Case(int coord_x, int coord_y) {
        x = coord_x;
        y = coord_y;
    }

    public void affiche(){
        System.out.println("X:"+x+", Y: "+y);
    }
    // public Case(int coord_x, int coord_y, String type) {
    //     x = coord_x;
    //     y = coord_y;
    //     type="default";
    // }

    public String get_type() {
        return("Default");
    }

}