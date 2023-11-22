package maze_generation.furnitures;

import maze_generation.furnitures.items.*;

public class Chest extends Furniture {
    private Item content;
    static int index=0;

    public Chest (int x,int y, Item content){
        super(x, y, "chest_"+index);
        this.content=content;
        index++;
    }

    public Item getContent() {
        return content;
    }
    public void setContent(Item item){
        this.content=item;
    }
}
