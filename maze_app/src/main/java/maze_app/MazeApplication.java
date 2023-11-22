package maze_app;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import defeat_page.DefeatPage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import maze_generation.FullMazeGenerator;
import menu_page.MenuPage;
import victory_page.VictoryPage;

public class MazeApplication extends Application {
    private Scene scene;
    private GridPane grid;
    private Rectangle[][] cells;
    private EventHandler eventHandler;
    private FullMazeGenerator maze;
    private Label timerLabel; 
    private Label pointLabel;
    private Label lifeLabel;
    private Timeline gameLoop;
    private static int count=1;
    private boolean gameStarted = false;

    @Override
    public void start(Stage stage) throws IOException {
        MenuPage menu = new MenuPage(stage, maze);
    }

    public void startGame(Stage stage, int level) {
        if (!gameStarted) {
            gameStarted = true;
            initialize(stage, level);
            launchGameLoop(stage);
        }
    }

    public void initialize(Stage stage, int level) {
        this.maze = new FullMazeGenerator(25,25,level);
        DisplayMaze mazeGrid=new DisplayMaze(this.maze);
        this.grid = mazeGrid.getGrid();
        this.cells = new Rectangle[maze.getX()][maze.getY()];
        this.scene = new Scene(grid, 800, 832);
        this.eventHandler = new EventHandler();
        eventHandler.pollEvents(scene);
        grid.getChildren().add(this.maze.getPlayer().getSprite());
        for (int i = 0; i < maze.getEnemies().size(); i++) {
            grid.getChildren().add(this.maze.getEnemies().get(i).getSprite());
        }

        for (int i = 0; i < maze.getX(); i++) {
            for (int j = 0; j < maze.getY(); j++) {
                Rectangle cell = new Rectangle(32, 32);
                cell.setFill(Color.BLACK);
                cells[i][j] = cell;
                grid.add(cell, i, j);
            }
        }


        timerLabel = new Label();
        timerLabel.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        grid.add(timerLabel, 0, maze.getY()+1, 5, maze.getY()+2);
        
        pointLabel = new Label();
        pointLabel.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        grid.add(pointLabel, 6, maze.getY()+1, 10, maze.getY()+2);

        lifeLabel = new Label();
        lifeLabel.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        grid.add(lifeLabel, 11, maze.getY()+1, 15, maze.getY()+2);

        stage.setScene(scene);
        stage.setTitle("LabyGames");
        stage.show();

        scene.setOnKeyPressed(eventHandler::handleKeyPressed);
    }

    public void launchGameLoop(Stage stage) {
        Duration frameDuration = Duration.millis(200);
        KeyFrame keyFrame = new KeyFrame(frameDuration, event -> {
            try {
                update(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Timeline gameLoop = new Timeline(keyFrame);
        this.gameLoop = gameLoop;
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public void update(Stage stage) throws IOException {
        count++;
        if (count==6){
            count=1;
        }

        if (eventHandler.isDPressed || eventHandler.isQPressed || eventHandler.isSPressed || eventHandler.isZPressed){
            this.maze.getPlayer().moving(eventHandler,maze);
        }
        else if (eventHandler.isEPressed){
            DisplayItem mazeGridUpdate = new DisplayItem(this.grid,this.maze);
            this.grid = mazeGridUpdate.getGrid();
            this.maze.getPlayer().action(eventHandler, maze, stage, grid, this);
        }

        for (int i = 0; i < maze.getEnemies().size(); i++) {
            if (maze.getEnemies().get(i).getX()==maze.getPlayer().getX() && maze.getEnemies().get(i).getY()==maze.getPlayer().getY()){
                if (!maze.getPlayer().isWeapon()){
                    maze.getPlayer().setLife(maze.getPlayer().getLife()-1);
                    if(maze.getPlayer().getLife()<=0){
                        maze.setStopTime(LocalTime.now());
                        gameLoop.stop();
                        DefeatPage page = new DefeatPage(stage, maze);
                        break;
                    }
                    else{
                        maze.getPlayer().death(maze);
                    }
                }
                else{
                    this.maze.getEnemies().get(i).death(maze);
                    this.maze.getPlayer().setWeapon(false);
                }
            }
            this.maze.getEnemies().get(i).moving(eventHandler, maze, count);
        }

        LocalTime currentTime = LocalTime.now();
        long elapsedSeconds = ChronoUnit.SECONDS.between(maze.getStartTime(), currentTime);
        int second=((int) elapsedSeconds)%60;
        int minute=((int) elapsedSeconds)/60;
        timerLabel.setText("Time: "+minute+"min " + second + "s");

        pointLabel.setText("Point: "+maze.getPlayer().getPoint());

        lifeLabel.setText("Life: "+maze.getPlayer().getLife());

        if (maze.getPlayer().getTimerBush().compareTo(currentTime)<0){
            maze.getPlayer().setPossibilityToBush(true);
        }
        if (maze.getPlayer().getTimerView().compareTo(currentTime)<0){
            maze.getPlayer().setViewRangeX(maze.getX()/2);
            maze.getPlayer().setViewRangeY(maze.getY()/2);
        }
        if (maze.getEnemies().get(0).getTimerSpeed().compareTo(currentTime)<0){
            for (int i = 0; i < maze.getEnemies().size(); i++) {
                maze.getEnemies().get(i).setSpeed(3);
            }
        }

        updateVisibleRange(cells, maze);
    }

    private void updateVisibleRange(Rectangle[][] cells,FullMazeGenerator maze) {
        for (int i = 0; i < maze.getX(); i++) {
            for (int j = 0; j < maze.getY(); j++) {
                cells[i][j].setFill(Color.BLACK);
            }
        }
        for (int i = Math.max(0, maze.getPlayer().getX() - maze.getPlayer().getViewRangeX()/2); i <= Math.min(cells.length - 1, maze.getPlayer().getX() + maze.getPlayer().getViewRangeX()/2); i++) {
            for (int j = Math.max(0, maze.getPlayer().getY() - maze.getPlayer().getViewRangeY()/2); j <= Math.min(cells[0].length - 1, maze.getPlayer().getY() + maze.getPlayer().getViewRangeY()/2); j++) {
                cells[i][j].setFill(Color.TRANSPARENT);
            }
        }
    }

    public void victory(Stage stage){
        gameLoop.stop();
        VictoryPage page = new VictoryPage(stage, maze);
    }

    public static void main(String[] args) {
        launch(args);
    }
}