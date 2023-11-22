package maze_generation.furnitures.items;

import maze_generation.FullMazeGenerator;
import maze_generation.Player;

public class Goblin extends Item{
    private int pieceStole = 100;

    public Goblin(){
        super("goblin");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player=maze.getPlayer();
        player.addPoint(-pieceStole);
        maze.setPlayer(player);
    }
}
