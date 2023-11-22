package maze_generation.furnitures.items;

import maze_generation.FullMazeGenerator;

public abstract class Item {
    protected String name;

    protected Item(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void update(FullMazeGenerator maze){}
}
