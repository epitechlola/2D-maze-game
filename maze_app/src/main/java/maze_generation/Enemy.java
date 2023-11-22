package maze_generation;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import maze_app.EventHandler;
import maze_generation.MazeGenerator.Direction;
import maze_generation.furnitures.Bush;
import maze_generation.furnitures.Furniture;
import ia.ShortestPath;
import ia.MazeToGraph;

public class Enemy {
    private int speed=3;
    private LocalTime timerSpeed = LocalTime.now();
    private int x;
    private int y;
    protected Image spriteSheet;
    protected ImageView sprite;

    public Enemy(int x,int y){
        this.x=x;
        this.y=y;
        this.spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_front.png")));
        this.sprite = new ImageView(spriteSheet);
        this.sprite.setTranslateX(x*32);
        this.sprite.setTranslateY(y*32);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSpeed(){
        return speed;
    }
    public LocalTime getTimerSpeed() {
        return timerSpeed;
    }
    public ImageView getSprite() {
        return this.sprite;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setSpeed(int speed){
        this.speed=speed;
    }
    public void setTimerSpeed(LocalTime timerSpeed) {
        this.timerSpeed = timerSpeed;
    }

    public void moving(EventHandler eventHandler,FullMazeGenerator maze,int count) {
        if (count<=speed){
            boolean playerInbush = false;
            if (maze.getPlayer().isPossibilityToBush()){
                for (Furniture furniture : maze.getFurnitures()) {
                    if (furniture instanceof Bush && furniture.getX() == maze.getPlayer().getX() && furniture.getY() == maze.getPlayer().getY()){
                        playerInbush = true;
                    }
                }
            }

            if (!playerInbush){
                MazeToGraph graph = new MazeToGraph(maze);
                int enemyPosition = maze.getX() * this.y + this.x;
                int playerPosition = maze.getX() * maze.getPlayer().getY() + maze.getPlayer().getX();
                ShortestPath shortestPath = new ShortestPath(graph, enemyPosition, playerPosition);
                List<String> path = shortestPath.getPath();
                String[] move = path.get(path.size()-2).split(",");
                int moveX = Integer.parseInt(move[0]);
                int moveY = Integer.parseInt(move[1]);

                if (moveY==this.y-1){
                    this.sprite.setTranslateY(this.sprite.getTranslateY() - 32);
                    this.y=(int) this.sprite.getTranslateY()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_back.png"))));
                }
                else if (moveY==this.y+1){
                    this.sprite.setTranslateY(this.sprite.getTranslateY() + 32);
                    this.y=(int) this.sprite.getTranslateY()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_front.png"))));
                }
                else if (moveX==this.x+1){
                    this.sprite.setTranslateX(this.sprite.getTranslateX() + 32);
                    this.x=(int) this.sprite.getTranslateX()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_right.png"))));
                }
                else if (moveX==this.x-1){
                    this.sprite.setTranslateX(this.sprite.getTranslateX() - 32);
                    this.x=(int) this.sprite.getTranslateX()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_left.png"))));
                }
            }
            else{
                List<Integer> movesPossible = new ArrayList<>();
                int cellValue = maze.getMaze()[this.x][this.y];
                if ((cellValue & Direction.NORTH.bit) != 0){
                    movesPossible.add(1);
                }
                if ((cellValue & Direction.SOUTH.bit) != 0){
                    movesPossible.add(2);
                }
                if ((cellValue & Direction.EAST.bit) != 0){
                    movesPossible.add(3);
                }
                if ((cellValue & Direction.WEST.bit) != 0){
                    movesPossible.add(4);
                }
                Collections.shuffle(movesPossible, new Random(System.nanoTime()));
                int move = movesPossible.get(0);

                if (move == 1){
                    this.sprite.setTranslateY(this.sprite.getTranslateY() - 32);
                    this.y=(int) this.sprite.getTranslateY()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_back.png"))));
                }
                else if (move == 2){
                    this.sprite.setTranslateY(this.sprite.getTranslateY() + 32);
                    this.y=(int) this.sprite.getTranslateY()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_front.png"))));
                }
                else if (move == 3){
                    this.sprite.setTranslateX(this.sprite.getTranslateX() + 32);
                    this.x=(int) this.sprite.getTranslateX()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_right.png"))));
                }
                else if (move==4){
                    this.sprite.setTranslateX(this.sprite.getTranslateX() - 32);
                    this.x=(int) this.sprite.getTranslateX()/32;
                    this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_left.png"))));
                }
            }
        }
    }

    public void death(FullMazeGenerator maze){
        this.setX(maze.getX()/2);
        this.setY(maze.getY()/2);
        this.sprite.setTranslateX(maze.getX()/2*32);
        this.sprite.setTranslateY(maze.getY()/2*32);
        this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/enemy_front.png"))));
    }
}
