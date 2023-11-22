package maze_generation.furnitures.items;

import java.time.LocalTime;

import maze_generation.FullMazeGenerator;
import maze_generation.Player;

public class DisableBush extends Item {
    private int time =10;

    public DisableBush(){
        super("disable bush");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player = maze.getPlayer();
        player.setPossibilityToBush(false);
        player.setTimerBush(LocalTime.now().plusSeconds(time));
        maze.setPlayer(player);
    }
}
