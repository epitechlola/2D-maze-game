package maze_generation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maze_generation.furnitures.*;
import maze_generation.furnitures.items.*;

public class FullMazeGenerator extends MazeGenerator{
    private List<Furniture> furnitures=new ArrayList<>();
    private Player player;
    private List<Enemy> enemies=new ArrayList<>();
    private LocalTime startTime;
    private LocalTime stopTime;
    private int level;

    public FullMazeGenerator(int x, int y, int level){
        super(x, y, level);
        createFurnitures(x,y,this.maze,level);
        this.player=new Player(x/2, y/2,4-level);
        this.enemies.add(new Enemy(x/2, y/2));
        this.startTime = LocalTime.now();
        this.level = level;
    }

    private void createFurnitures(int x, int y, int[][] maze, int level){
        List<String> corner= new ArrayList<String>();
        Random random=new Random();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (maze[i][j]==1 || maze[i][j]==2 || maze[i][j]==4 || maze[i][j]==8){ //checks if the box is a corner
                    corner.add(i+","+j);
                }
            }
        }
        for (int i = 0; i < 6; i++) { //chest creation
            int box=random.nextInt(corner.size());
            String coord=corner.get(box);
            int cx = Integer.parseInt(coord.split(",")[0]);
            int cy = Integer.parseInt(coord.split(",")[1]);
            Item item = createItem(i);
            furnitures.add(new Chest(cx, cy, item));
            corner.remove(box);
        }
        if (level==1){
            List<String> angle= new ArrayList<String>();
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (maze[i][j]==10 || maze[i][j]==9 || maze[i][j]==5 || maze[i][j]==6){ //checks if the box is an angle
                        angle.add(i+","+j);
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
                int box=random.nextInt(angle.size());
                String coord=angle.get(box);
                int cx = Integer.parseInt(coord.split(",")[0]);
                int cy = Integer.parseInt(coord.split(",")[1]);
                furnitures.add(new Bush(cx, cy));
                angle.remove(box);
            }
        }
        else if (level==2){
            int numberOfBush;
            if (corner.size()<5){
                numberOfBush=corner.size();
            }
            else{
                numberOfBush=5;
            }
            for (int i = 0; i < numberOfBush; i++) {
                int box=random.nextInt(corner.size());
                String coord=corner.get(box);
                int cx = Integer.parseInt(coord.split(",")[0]);
                int cy = Integer.parseInt(coord.split(",")[1]);
                furnitures.add(new Bush(cx, cy));
                corner.remove(box);
            }
            int whichEnemyCorner = random.nextInt(2);
            if (whichEnemyCorner==0){
                this.enemies.add(new Enemy(0, y-1));
            }
            else{
                this.enemies.add(new Enemy(x-1, 0));
            }
        }
        else if (level==3){
            int numberOfBush;
            if (corner.size()<3){
                numberOfBush=corner.size();
            }
            else{
                numberOfBush=3;
            }
            for (int i = 0; i < numberOfBush; i++) {
                int box=random.nextInt(corner.size());
                String coord=corner.get(box);
                int cx = Integer.parseInt(coord.split(",")[0]);
                int cy = Integer.parseInt(coord.split(",")[1]);
                furnitures.add(new Bush(cx, cy));
                corner.remove(box);
            }
            this.enemies.add(new Enemy(0, y-1));
            this.enemies.add(new Enemy(x-1, 0));
        }
    }

    private static Item createItem(int i){
        Random random = new Random();
        int itemNumber = random.nextInt(3);
        if (i<3){
            return new Piece();
        }
        else if (i<5){
            if (itemNumber==0){
                return new BlindnessTrap();
            }
            else if (itemNumber==1){
                return new Goblin();
            }
            else {
                return new DisableBush();
            }
        }
        else {
            if (itemNumber==0){
                return new Gem();
            }
            else if (itemNumber==1){
                return new Weapon();
            }
            else {
                return new SpeedPotion();
            }
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public Player getPlayer() {
        return player;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public List<Furniture> getFurnitures(){
        return furnitures;
    }
    public int getLevel(){
        return level;
    }
    public LocalTime getStopTime(){
        return this.stopTime;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setEnemies(List<Enemy> enemies){
        this.enemies=enemies;
    }
    public void setFurnitures(List<Furniture> furnitures){
        this.furnitures = furnitures;
    }
    public void setStopTime(LocalTime time){
        this.stopTime=time;
    }
}
