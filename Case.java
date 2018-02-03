import java.io.*;
import java.util.*;

public class Case {
    public Vector Pion_Case = new Vector();
    private int x;
    private int y;
    private boolean highlighted = false;
    private boolean reachable = false;    

    public void set_x(int coord_x) {
        x = coord_x;
    }

    public void set_y(int coord_y) {
        y = coord_y;
    }

    public int get_row() {
        return x;
    }

    public int get_col() {
        return y;
    }

    public Vector get_coordinate() {
        Vector coordinate = new Vector();
        coordinate.add(x);
        coordinate.add(y);
        return coordinate;
    }

    public Case(int row, int col) {
        x = row;
        y = col;
    }

    public void affiche() {
        System.out.println("X:" + x + ", Y: " + y);
    }
    // public Case(int coord_x, int coord_y, String type) {
    //     x = coord_x;
    //     y = coord_y;
    //     type="default";
    // }

    public String get_type() {
        return (" ");
    }

    public void highlight(boolean trueorfalse) {
        highlighted = trueorfalse;
    }

    public void set_reachable(boolean trueorfalse) {
        reachable = trueorfalse;
    }

    public boolean is_highlighted() {
        return highlighted;
    }
    public boolean is_reachable() {
        return reachable;
    }
}