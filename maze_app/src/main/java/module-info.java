module maze_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens maze_app to javafx.fxml;
    exports maze_app;
    exports ia;
    exports maze_generation;
    exports victory_page;
    exports defeat_page;
}
