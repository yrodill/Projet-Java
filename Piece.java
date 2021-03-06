import java.util.Vector;

public class Piece {
    public Vector Pion_Piece = new Vector();
    private int x;
    private int y;
    private int player;
    private String couleur;
    private boolean highlighted = false;
    private boolean reachable = false;

    public void set_x(int coord_x) {
        x = coord_x;
    }

    public void set_y(int coord_y) {
        y = coord_y;
    }

    public void set_joueur(int joueur) {
        player = joueur;
    }

    public void set_color(String color) {
        couleur = color;
    }

    public int get_row() {
        return x;
    }

    public int get_col() {
        return y;
    }

    public int get_Player() {
        return player;
    }

    public String get_color() {
        return couleur;
    }

    public Vector get_coordinate() {
        Vector coordinate = new Vector();
        coordinate.add(x);
        coordinate.add(y);
        return coordinate;
    }

    public Piece(int row, int col, int joueur, String color) {
        x = row;
        y = col;
        player = joueur;
        couleur = color;
    }

    public Piece(int row, int col, int joueur) {
        x = row;
        y = col;
        player = joueur;
        couleur = "";
    }

    public Piece(int row, int col) {
        x = row;
        y = col;
        player = 0;
        couleur = "";
    }

    public void affiche() {
        System.out.println("X:" + x + ", Y: " + y);
    }

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

    public boolean is_thesameposition(Piece tested) {
        if (tested.get_row() == x && tested.get_col() == y) {
            return true;
        }
        return false;
    }

}
