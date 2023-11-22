package maze_app;

import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class EventHandler {
    public boolean isZPressed = false;
    public boolean isSPressed = false;
    public boolean isDPressed = false;
    public boolean isQPressed = false;
    public boolean isEPressed = false;

    public void pollEvents(Scene scene) {
        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.getRoot().requestFocus(); // Request focus to receive key events
    }

    public void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.Z || keyCode == KeyCode.UP) {
            isZPressed = true;
        } else if (keyCode == KeyCode.S || keyCode == KeyCode.DOWN) {
            isSPressed = true;
        } else if (keyCode == KeyCode.D || keyCode == KeyCode.RIGHT) {
            isDPressed = true;
        } else if (keyCode == KeyCode.Q || keyCode == KeyCode.LEFT) {
            isQPressed = true;
        } else if (keyCode == KeyCode.E || keyCode == KeyCode.SPACE) {
            isEPressed = true;
        }
    }
}