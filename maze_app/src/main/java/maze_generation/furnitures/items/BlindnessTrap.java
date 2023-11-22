package maze_generation.furnitures.items;

import java.time.LocalTime;

import maze_generation.FullMazeGenerator;
import maze_generation.Player;

public class BlindnessTrap extends Item {
    private int time=5;

    public BlindnessTrap(){
        super("blindness trap");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player = maze.getPlayer();
        player.setViewRangeX(player.getViewRangeX()/2);
        player.setViewRangeY(player.getViewRangeY()/2);
        player.setTimerView(LocalTime.now().plusSeconds(time));
        maze.setPlayer(player);
    }
}
