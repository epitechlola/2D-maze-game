package menu_page;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import maze_app.MazeApplication;

public class Level1Button {
    private boolean gameStarted = false;

    public Button createButton() {
        Button startButton = new Button("level 1");
        startButton.setOnAction(e -> startGame((Stage) startButton.getScene().getWindow()));
        return startButton;
    }

    private void startGame(Stage stage) {
        if (!gameStarted) {
            gameStarted = true;

            MazeApplication mazeApp = new MazeApplication();
            mazeApp.startGame(stage,1);
        }
    }
}
