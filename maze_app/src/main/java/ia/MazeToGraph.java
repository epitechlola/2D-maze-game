package ia;

import maze_generation.FullMazeGenerator;

public class MazeToGraph {
    private Integer[][] adjacencyMatrix;
    private int x;
    private int y;

    public MazeToGraph(FullMazeGenerator maze){
        this.x=maze.getX();
        this.y=maze.getY();
        this.adjacencyMatrix = new Integer[x * y][x * y];
        for (int i = 0; i < x * y; i++) {
            for (int j = 0; j < x * y; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
        makeArcs(maze.getX(),maze.getY(),maze.getMaze());
    }

    private void makeArcs(int x, int y, int[][] maze){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int cellValue = maze[i][j];
                int nodeIndex = j * x + i;

                if ((cellValue & Direction.NORTH.bit) != 0) {
                    int neighborIndex = nodeIndex - x;
                    adjacencyMatrix[nodeIndex][neighborIndex] = 1;
                    adjacencyMatrix[neighborIndex][nodeIndex] = 1;
                }

                if ((cellValue & Direction.SOUTH.bit) != 0) {
                    int neighborIndex = nodeIndex + x;
                    adjacencyMatrix[nodeIndex][neighborIndex] = 1;
                    adjacencyMatrix[neighborIndex][nodeIndex] = 1;
                }

                if ((cellValue & Direction.EAST.bit) != 0) {
                    int neighborIndex = nodeIndex + 1;
                    adjacencyMatrix[nodeIndex][neighborIndex] = 1;
                    adjacencyMatrix[neighborIndex][nodeIndex] = 1;
                }

                if ((cellValue & Direction.WEST.bit) != 0) {
                    int neighborIndex = nodeIndex - 1;
                    adjacencyMatrix[nodeIndex][neighborIndex] = 1;
                    adjacencyMatrix[neighborIndex][nodeIndex] = 1;
                }
            }
        }
    }

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

    public Integer[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
