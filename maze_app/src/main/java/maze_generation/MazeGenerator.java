package maze_generation;

import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

public abstract class MazeGenerator {
    protected final int x;
    protected final int y;
    protected final int[][] maze;

    public MazeGenerator(int x, int y,int level) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
        generateLoops(x,y,level);
    }

    public void generateMaze(int cx, int cy) {
        Direction[] dirs = Direction.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (Direction dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    enum Direction {
        NORTH(1, 0, -1), SOUTH(2, 0, 1), EAST(4, 1, 0), WEST(8, -1, 0);
        final int bit;
        final int dx;
        final int dy;
        Direction opposite;

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

    public void generateLoops(int x,int y,int level){
        Random random=new Random();
        float randomNumber = random.nextFloat();
        int numberOfLoops;
        if (level==1){
            numberOfLoops = (int) (((x*y)/2)*randomNumber);
        }
        else{
            numberOfLoops = (int) (((x*y)/4)*randomNumber);
        }
        for (int i = 0; i < numberOfLoops; i++) {
            int cx=random.nextInt(x);
            int cy=random.nextInt(y);
            Direction[] dirs = Direction.values();
            Collections.shuffle(Arrays.asList(dirs));
            for (Direction dir : dirs) {
                int nx = cx + dir.dx;
                int ny = cy + dir.dy;
                if (between(nx, x) && between(ny, y) && ((maze[cx][cy] | dir.bit)!=maze[cx][cy])){
                    maze[cx][cy] |= dir.bit;
                    maze[nx][ny] |= dir.opposite.bit;
                    break;
                }
            }
        }
    }

    public int[][] getMaze() {
        return maze;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
