public class Enzyme extends Piece{
    public static int distance_max = 1;
    private int nbMetaboCatched = 0;

    public Enzyme(int coord_x, int coord_y,int joueur,String color) {
        super(coord_x, coord_y,joueur,color);
    }

    public String get_type() {
        return("E");
    }

    public int get_nbMetaboCacthed() {
        return nbMetaboCatched;
    }

    public void increase_nbMetaboCatched() {
        nbMetaboCatched++;
    }

    public void set_nbMetaboCatched(int nbMetaboCatched) {
        nbMetaboCatched = this.nbMetaboCatched;
    }

}
