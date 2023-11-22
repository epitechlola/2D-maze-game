package maze_generation.furnitures;

public abstract class Furniture {
    protected int x;
    protected int y;
    protected String name;
    protected Furniture (int x , int y, String name){
        this.x=x;
        this.y=y;
        this.name=name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
