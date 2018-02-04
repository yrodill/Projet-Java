public class Metabolite extends Case{
    public Metabolite(int coord_x, int coord_y) {
        super(coord_x, coord_y);
    }

    public String get_type() {
        return("M");
    }
}