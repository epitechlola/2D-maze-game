package maze_generation.furnitures.items;

import maze_generation.FullMazeGenerator;
import maze_generation.Player;

public class Piece extends Item {
    private int value=200;

    public Piece(){
        super("piece");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player = maze.getPlayer();
        player.addPoint(value);
        maze.setPlayer(player);
    }
}
