public class Metabolite extends Case{
    public Metabolite(int coord_x, int coord_y, int joueur,String color) {
        super(coord_x, coord_y,joueur,color);
    }

    public String get_type() {
        return("M");
    }
}
