package maze_generation.furnitures.items;

import java.time.LocalTime;
import java.util.List;

import maze_generation.Enemy;
import maze_generation.FullMazeGenerator;

public class SpeedPotion extends Item{
    private int time = 5;
    private int speed = 1;
    
    public SpeedPotion(){
        super("speed potion");
    }

    @Override
    public void update(FullMazeGenerator maze) {
        List<Enemy> enemies = maze.getEnemies();
        for (int i =0; i<enemies.size(); i++) {
            enemies.get(i).setSpeed(enemies.get(i).getSpeed()-speed);
            enemies.get(i).setTimerSpeed(LocalTime.now().plusSeconds(time));
        }
        maze.setEnemies(enemies);
    }
}
