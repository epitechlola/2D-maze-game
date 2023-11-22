package victory_page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.UnaryOperator;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TextFieldInput {
    private VictoryPage page;

    public TextField createField(VictoryPage page, int score){
        this.page = page;
        TextField field = new TextField("name");
        field.setTextFormatter(setFormat());
        field.setOnAction(e -> {
            try {
                saveScore((Stage) field.getScene().getWindow(), score,field);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return field;
    }

    private TextFormatter<String> setFormat(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getText().contains("/")) {
                change.setText("");
            }
            return change;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }
 
    public void saveScore(Stage stage, int score, TextField field) throws IOException{                        
        try {
            String text = field.getText()+"/"+score+"\n";
            Files.write(Paths.get("score.txt"), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException error) {
            System.out.println("An error occurred.");
            error.printStackTrace();
        }

            field.clear();
 
        page.endPage();
    }
}