public class Metabolite extends Case {
    public static int distance_max = 1;

    public Metabolite(int coord_x, int coord_y, int joueur, String color) {
        super(coord_x, coord_y, joueur, color);
    }

    public static void set_distance_metabol(int nombre) {
        distance_max = nombre;
    }

    public String get_type() {
        return ("M");
    }
}
