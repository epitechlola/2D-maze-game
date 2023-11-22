package maze_generation;

import java.time.LocalTime;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import maze_app.EventHandler;
import maze_generation.MazeGenerator.Direction;
import java.util.List;
import java.util.Objects;

import maze_generation.furnitures.*;
import maze_generation.furnitures.items.*;
import maze_app.MazeApplication;

public class Player {
    private int x;
    private int y;
    private boolean weapon=false;
    private int point=0;
    private int viewRangeX;
    private int viewRangeY;
    private int life;
    private boolean possibilityToBush=true;
    private LocalTime timerView = LocalTime.now();
    private LocalTime timerBush = LocalTime.now();
    protected Image spriteSheet;
    protected ImageView sprite;

    public Player (int viewRangeX, int viewRangeY, int life){
        this.x=0;
        this.y=0;
        this.viewRangeX=viewRangeX;
        this.viewRangeY=viewRangeY;
        this.life=life;
        this.spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_front.png")));
        this.sprite = new ImageView(spriteSheet);
        this.sprite.setTranslateX(x);
        this.sprite.setTranslateY(y);
    }

    public int getPoint() {
        return point;
    }
    public int getViewRangeX() {
        return viewRangeX;
    }
    public int getViewRangeY() {
        return viewRangeY;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLife() {
        return life;
    }
    public ImageView getSprite() {
        return this.sprite;
    }
    public boolean isPossibilityToBush() {
        return possibilityToBush;
    }
    public boolean isWeapon() {
        return weapon;
    }
    public LocalTime getTimerBush() {
        return timerBush;
    }
    public LocalTime getTimerView() {
        return timerView;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setViewRangeX(int viewRangeX) {
        this.viewRangeX = viewRangeX;
    }
    public void setViewRangeY(int viewRangeY) {
        this.viewRangeY = viewRangeY;
    }
    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }
    public void addPoint(int points){
        this.point+=points;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public void setPossibilityToBush(boolean possibilityToBush) {
        this.possibilityToBush = possibilityToBush;
    }
    public void setTimerBush(LocalTime timerBush) {
        this.timerBush = timerBush;
    }
    public void setTimerView(LocalTime timerView) {
        this.timerView = timerView;
    }

    public void moving(EventHandler eventHandler,FullMazeGenerator maze) {
        int cellValue = maze.getMaze()[this.x][this.y];
        if (eventHandler.isZPressed) {
            if ((cellValue & Direction.NORTH.bit) != 0){
                this.sprite.setTranslateY(this.sprite.getTranslateY() - 32);
                this.y=(int) this.sprite.getTranslateY()/32;
                this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_back.png"))));
            }
            eventHandler.isZPressed=false;
        } else if (eventHandler.isSPressed) {
            if ((cellValue & Direction.SOUTH.bit) != 0){
                this.sprite.setTranslateY(this.sprite.getTranslateY() + 32);
                this.y=(int) this.sprite.getTranslateY()/32;
                this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_front.png"))));
            }
            eventHandler.isSPressed=false;
        } else if (eventHandler.isDPressed) {
            if ((cellValue & Direction.EAST.bit) != 0){
                this.sprite.setTranslateX(this.sprite.getTranslateX() + 32);
                this.x=(int) this.sprite.getTranslateX()/32;
                this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_right.png"))));
            }
            eventHandler.isDPressed=false;
        } else if (eventHandler.isQPressed) {
            if ((cellValue & Direction.WEST.bit) != 0){
                this.sprite.setTranslateX(this.sprite.getTranslateX() - 32);
                this.x=(int) this.sprite.getTranslateX()/32;
                this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_left.png"))));
            }
            eventHandler.isQPressed=false;
        }
    }
        
    public void action(EventHandler eventHandler,FullMazeGenerator maze, Stage stage, GridPane root, MazeApplication app){
        if (eventHandler.isEPressed) {
            List<Furniture> furnitures = maze.getFurnitures();
            furnitures.toArray();
            for (int i=0; i<furnitures.size();i++) {
                if ((furnitures.get(i) instanceof Chest) && furnitures.get(i).getX()==this.x && furnitures.get(i).getY()==this.y){
                    Chest chest = (Chest) furnitures.get(i);
                    Item item = chest.getContent();
                    if (item!=null){
                        item.update(maze);
                        chest.setContent(null);
                        furnitures.set(i, chest);
                        maze.setFurnitures(furnitures);
                    }
                }
            }
            if (this.x==maze.getX()-1 && this.y==maze.getY()-1){
                maze.setStopTime(LocalTime.now());
                app.victory(stage);
            }
            eventHandler.isEPressed=false;
        }
    }

    public void death(FullMazeGenerator maze){
        this.setX(0);
        this.setY(0);
        this.sprite.setTranslateX(0);
        this.sprite.setTranslateY(0);
        this.sprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player_front.png"))));
    }
}
