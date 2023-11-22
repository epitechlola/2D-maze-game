package victory_page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import maze_generation.FullMazeGenerator;
import maze_generation.furnitures.Chest;
import maze_generation.furnitures.Furniture;

public class VictoryPage {
    private Button level1Button;
    private Button level2Button;
    private Button level3Button;
    private TextField field;
    private VBox root;
    private Scene scene;
    private Stage stage;

    public VictoryPage(Stage stage, FullMazeGenerator maze){
        
        Level1Button button1 = new Level1Button();
        this.level1Button = button1.createButton();
        Level2Button button2 = new Level2Button();
        this.level2Button = button2.createButton();
        Level3Button button3 = new Level3Button();
        this.level3Button = button3.createButton();
        int score = calculateScore(maze);
        TextFieldInput fieldInput = new TextFieldInput();
        this.field = fieldInput.createField(this,score);

        this.root = new VBox();
        this.scene = new Scene(root, 800, 832);
        this.stage = stage;

        whatName();
    }

    private int calculateScore(FullMazeGenerator maze){
        int score = 0;
        boolean adventureBonus = true;
        score += maze.getPlayer().getPoint()+maze.getPlayer().getLife()*100+maze.getLevel()*200;
        for (Furniture furniture : maze.getFurnitures()) {
            if (furniture instanceof Chest){
                Chest chest = (Chest) furniture;
                if (chest.getContent()!=null){
                    adventureBonus=false;
                }
            }
        }
        if (adventureBonus){
            score += 100;
        }
        int elapsedMillis =(int) ChronoUnit.MILLIS.between(maze.getStartTime(), maze.getStopTime());
        int numberTime = 15-elapsedMillis/20000;
        if (numberTime<0){
            numberTime=0;
        }
        score += numberTime*30;
        return score;
    }

    void whatName(){
        Label label = new Label("VICTORY!!");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
        root.getChildren().clear();
        root.getChildren().addAll(label,field);
        stage.setScene(scene);
        stage.show();
    }

    void endPage() throws IOException{
        root.getChildren().clear();
        Label label = new Label("VICTORY!!");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: black;");

        String content = Files.readString(Paths.get("score.txt"));
        String[] table = content.split("\n");

        TableView tableView = new TableView();
        TableColumn<ScoreBoard, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ScoreBoard, String> column2 = new TableColumn<>("Score");       
        column2.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        for (String line : table) {
            String name = line.split("/")[0];
            int score = Integer.parseInt(line.split("/")[1]);
            tableView.getItems().add(new ScoreBoard(name, score));
        }

        BackgroundImage myBI= new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/menu_background.png")),800,800,true,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        root.getChildren().addAll(label,tableView,level1Button,level2Button,level3Button);
        stage.setScene(scene);
        stage.show();
    }
}
