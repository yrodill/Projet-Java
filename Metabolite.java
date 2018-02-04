public class Metabolite extends Case{
    public Metabolite(int coord_x, int coord_y, int joueur) {
        super(coord_x, coord_y,joueur);
    }

    public String get_type() {
        return("M");
    }
}
