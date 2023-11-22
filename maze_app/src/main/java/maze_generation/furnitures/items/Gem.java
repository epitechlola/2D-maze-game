package maze_generation.furnitures.items;

import maze_generation.Player;
import maze_generation.FullMazeGenerator;

public class Gem extends Item {
    private int value =100;

    public Gem(){
        super("gem");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player = maze.getPlayer();
        player.addPoint(value);
        maze.setPlayer(player);
    }
}
