package menu_page;

import java.io.IOException;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import maze_generation.FullMazeGenerator;

public class MenuPage {
    private Button level1Button;
    private Button level2Button;
    private Button level3Button;
    private VBox root;
    private Scene scene;
    private Stage stage;

    public MenuPage(Stage stage, FullMazeGenerator maze) throws IOException{
        
        Level1Button button1 = new Level1Button();
        this.level1Button = button1.createButton();
        Level2Button button2 = new Level2Button();
        this.level2Button = button2.createButton();
        Level3Button button3 = new Level3Button();
        this.level3Button = button3.createButton();

        this.root = new VBox();
        this.scene = new Scene(root, 800, 832);

        BackgroundImage myBI= new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/menu_background.png")),800,800,true,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        this.stage = stage;

        startPage();
    }

    void startPage() throws IOException{
        root.getChildren().clear();
        Label label = new Label("MENU");
        label.setStyle("-fx-font-size: 60; -fx-text-fill: black;");

        root.getChildren().addAll(label,level1Button,level2Button,level3Button);
        stage.setScene(scene);
        stage.show();
    }
}
