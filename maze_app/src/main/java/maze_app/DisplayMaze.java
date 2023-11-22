package maze_app;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import maze_generation.FullMazeGenerator;
import maze_generation.furnitures.Chest;
import maze_generation.furnitures.Furniture;

public class DisplayMaze {

    private GridPane grid = new GridPane();
    
    private enum Direction {
        NORTH(1, 0, -1), SOUTH(2, 0, 1), EAST(4, 1, 0), WEST(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private Direction opposite;

        static {
            NORTH.opposite = SOUTH;
            SOUTH.opposite = NORTH;
            EAST.opposite = WEST;
            WEST.opposite = EAST;
        }

        Direction(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

    public DisplayMaze (FullMazeGenerator maze){

        // Load les images
        Image e_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/E_wall.png")));
        Image empty = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/empty.png")));
        Image ew_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/EW_wall.png")));
        Image n_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/N_wall.png")));
        Image ne_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NE_wall.png")));
        Image new_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NEW_wall.png")));
        Image ns_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NS_wall.png")));
        Image nse_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NSE_wall.png")));
        Image nsw_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NSW_wall.png")));
        Image nw_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/NW_wall.png")));
        Image s_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/S_wall.png")));
        Image se_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/SE_wall.png")));
        Image sew_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/SEW_wall.png")));
        Image sw_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/SW_wall.png")));
        Image w_wall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/wall_assets/W_wall.png")));
        Image chest = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/chest.png")));
        Image bush = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/furnitures/bush.png")));

        // Ajoute les wall dans la grid
        for (int i = 0; i < maze.getX(); i++) {
            for (int j = 0; j < maze.getY(); j++) {
                ImageView imageView;
                int cellValue = maze.getMaze()[j][i];

                //special case for the entry
                if (i==0 && j==0){
                    if (cellValue==2){
                        imageView = new ImageView(ew_wall);
                    }
                    else if (cellValue==4){
                        imageView = new ImageView(sw_wall);
                    }
                    else{
                        imageView = new ImageView(w_wall);
                    }
                }

                //special case for the exit
                else if (i==maze.getX()-1 && j==maze.getY()-1){
                    if (cellValue==1){
                        imageView = new ImageView(ew_wall);
                    }
                    else if (cellValue==8){
                        imageView = new ImageView(ne_wall);
                    }
                    else{
                        imageView = new ImageView(e_wall);
                    }
                }
                
                //general case
                else{
                    if ((cellValue & Direction.NORTH.bit) == 0) { //north wall
                        if ((cellValue & Direction.SOUTH.bit) == 0) { //also south wall
                            if ((cellValue & Direction.EAST.bit) == 0) { //also east wall
                                imageView = new ImageView(nse_wall);
                            }
                            else if ((cellValue & Direction.WEST.bit) == 0) { //also west wall
                                imageView = new ImageView(nsw_wall);
                            }
                            else { //only south
                                imageView = new ImageView(ns_wall);
                            }
                        }
                        else if ((cellValue & Direction.EAST.bit) == 0){ //also east but not south
                            if ((cellValue & Direction.WEST.bit) == 0){ //also west
                                imageView = new ImageView(new_wall);
                            }
                            else { //only east
                                imageView = new ImageView(ne_wall);
                            }
                        }
                        else if ((cellValue & Direction.WEST.bit) == 0){ //also west but not south nor east
                                imageView = new ImageView(nw_wall);
                        }
                        else{//only north
                                imageView = new ImageView(n_wall);
                        }
                    } 
                    else if ((cellValue & Direction.SOUTH.bit) == 0) { //south wall
                        if  ((cellValue & Direction.EAST.bit) == 0){ //also east
                            if ((cellValue & Direction.WEST.bit) == 0){ //also west
                                imageView = new ImageView(sew_wall);
                            }
                            else{ //only east
                                imageView = new ImageView(se_wall);
                            }
                        }
                        else if ((cellValue & Direction.WEST.bit) == 0){ //also west but not east
                            imageView = new ImageView(sw_wall);
                        }
                        else { //only south
                            imageView = new ImageView(s_wall);
                        }
                    } 
                    else if ((cellValue & Direction.EAST.bit) == 0) { //east wall
                        if ((cellValue & Direction.WEST.bit) == 0){ //also west
                            imageView = new ImageView(ew_wall);
                        }
                        else{ //only east
                            imageView = new ImageView(e_wall);
                        }
                    } 
                    else if ((cellValue & Direction.WEST.bit) == 0) { //west wall only
                        imageView = new ImageView(w_wall);
                    } 
                    else {
                        imageView = new ImageView(empty); // Empty cell
                    }
                }

                this.grid.add(imageView, j, i);
            }
        }
        for (Furniture furniture : maze.getFurnitures()) {
            if (furniture instanceof Chest){
                ImageView imageView = new ImageView(chest);
                this.grid.add(imageView, furniture.getX(), furniture.getY());
            }
            else{
                ImageView imageView = new ImageView(bush);
                this.grid.add(imageView, furniture.getX(), furniture.getY());
            }
        }
    }

    public GridPane getGrid(){
        return this.grid;
    }
}
