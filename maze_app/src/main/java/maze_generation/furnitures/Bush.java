package maze_generation.furnitures;

public class Bush extends Furniture {
    static int index=0;
    
    public Bush(int x,int y){
        super(x, y, "bush_"+index);
        index++;
    }
}
