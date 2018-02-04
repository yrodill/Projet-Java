public class Enzyme extends Case{
    public Enzyme(int coord_x, int coord_y,int joueur) {
        super(coord_x, coord_y,joueur);
    }

    public String get_type() {
        return("E");
    }
}
