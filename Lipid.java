public class Lipid extends Case {
	public static int distance_max= 3;
    public Lipid(int coord_x, int coord_y, int joueur,String color) {
        super(coord_x, coord_y,joueur,color);
    }
    public Lipid(int coord_x, int coord_y, int joueur) {
        super(coord_x, coord_y,joueur);
    }
    
    
    public String get_type() {
        return("L");
    }

}
