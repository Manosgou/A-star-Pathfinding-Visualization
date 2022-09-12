import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class Animation {

    private final Cube[][] grid;
    private final ArrayList<Cube> closedSet, openSet, finalPath;
    private final boolean animated;
    private Cube start, end;

    public Animation(Cube[][] grid, ArrayList closedSet, ArrayList openSet, ArrayList finalPath, boolean animated, Cube start, Cube end) {
        this.grid = grid;
        this.closedSet = closedSet;
        this.openSet = openSet;
        this.finalPath = finalPath;
        this.animated = animated;
        this.start = start;
        this.end = end;
    }


    public void animate() {
        closedSet.remove(start);
        closedSet.remove(end);
        finalPath.remove(start);
        finalPath.remove(end);


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != start && grid[i][j] != end) {
                    if (openSet.contains(grid[i][j])) {
                        grid[i][j].setColor(YELLOW);
                        grid[i][j].setInfoVisible(true);
                    } else if (closedSet.contains(grid[i][j])) {
                        grid[i][j].setColor(MAGENTA);
                        grid[i][j].setInfoVisible(true);

                    }
                }
                if (animated) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Cube cube : finalPath) {
            cube.setColor(BLACK);
            if (animated) {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
