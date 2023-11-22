package maze_app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.Objects;
import maze_generation.FullMazeGenerator;
import maze_generation.furnitures.Chest;
import maze_generation.furnitures.Furniture;
import maze_generation.furnitures.items.Item;

public class DisplayItem {
        private GridPane grid;

        public DisplayItem(GridPane grid,FullMazeGenerator maze){
            this.grid=grid;
            int x = maze.getPlayer().getX();
            int y = maze.getPlayer().getY();
            for (Furniture furniture : maze.getFurnitures()) {
                if ((furniture instanceof Chest) && furniture.getX()==x && furniture.getY()==y){
                    Chest chest = (Chest) furniture;
                    Item item = chest.getContent();
                    if (item!=null){
                        if (item.getName().equals("blindness trap")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/blindness_trap.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("disable bush")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/disable_bush.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("gem")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/gem.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("goblin")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/goblin.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("piece")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/piece.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("speed potion")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/speed_potion.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                        else if (item.getName().equals("weapon")){
                            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/items/weapon.png"))));
                            imageView.setTranslateX(x*32);
                            imageView.setTranslateY(y*32);
                            grid.getChildren().add(imageView);
                        }
                    }
                }
            }
        }

        public GridPane getGrid(){
            return this.grid;
        }
}
