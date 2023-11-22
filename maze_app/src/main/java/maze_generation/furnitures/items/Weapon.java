package maze_generation.furnitures.items;

import maze_generation.Player;
import maze_generation.FullMazeGenerator;

public class Weapon extends Item {

    public Weapon(){
        super("weapon");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        Player player=maze.getPlayer();
        player.setWeapon(true);
        maze.setPlayer(player);
    }
}
