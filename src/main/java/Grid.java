import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;


public class Grid {

    private final int rows;
    private final int width;
    Cube[][] grid;

    public Grid(int rows,int sWidth) {
        this.rows = rows;
        this.width = sWidth / rows;
        this.grid = new Cube[rows][rows];
    }


    public void createGrid() {
        for (int x = 0; x < rows; x++) {

            for (int y = 0; y < rows; y++) {
                Cube cube = new Cube(true, WHITE);
                cube.x(this.width * x).y(this.width * y).width(width - 1).height(width - 1);
                cube.setFScore(0);
                cube.setHScore(0);
                cube.setFScore(0);
                this.grid[x][y] = cube;

            }
        }
    }

    public Cube[][] getGrid() {
        return this.grid;
    }

    public void drawGrid() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y].drawCube();
            }
        }
    }

    public void updateNeighbours(boolean allowDiagonal) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {

                if (j > 0 && grid[i][j - 1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i][j - 1]);
                if (j < rows - 1 && grid[i][j + 1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i][j + 1]);
                if (i > 0 && grid[i - 1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i - 1][j]);
                if (i < rows - 1 && grid[i + 1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i + 1][j]);
                if (allowDiagonal) {
                    if (i > 0 && j > 0 && grid[i - 1][j - 1].isAccessible())
                        grid[i][j].pushNeighbours(grid[i - 1][j - 1]);
                    if (i < rows - 1 && j > 0 && grid[i + 1][j - 1].isAccessible())
                        grid[i][j].pushNeighbours(grid[i + 1][j - 1]);
                    if (i > 0 && j < rows - 1 && grid[i - 1][j + 1].isAccessible())
                        grid[i][j].pushNeighbours(grid[i - 1][j + 1]);
                    if (i < rows - 1 && j < rows - 1 && grid[i + 1][j + 1].isAccessible())
                        grid[i][j].pushNeighbours(grid[i + 1][j + 1]);
                }
            }
        }
    }

}
