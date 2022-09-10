import com.raylib.Raylib;
import org.apache.commons.lang3.Range;

import java.util.Random;

import static com.raylib.Jaylib.*;


public class Main {

    public static void main(String[] args) {
        final int screenWidth = 800;
        InitWindow(screenWidth, screenWidth, "A* Pathfinding");
        SetTargetFPS(60);

        final int ROWS = 20;
        final boolean allowDiagonal = true;
        final boolean animated = true;


        final Grid grid = new Grid(ROWS, screenWidth);

        grid.createGrid();

        final AStar algorithm = new AStar(grid.getGrid(), allowDiagonal);

        Raylib.Vector2 mouse;
        Cube start = null;
        Cube end = null;

        Thread animationThread = null;
        while (!WindowShouldClose()) {
            mouse = GetMousePosition();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < ROWS; j++) {
                    if (CheckCollisionPointRec(mouse, grid.getGrid()[i][j])) {
                        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
                            if (grid.getGrid()[i][j] != end && grid.getGrid()[i][j] != start) {
                                grid.getGrid()[i][j].setColor(GRAY);
                                grid.getGrid()[i][j].setAccess(false);
                            }
                        } else if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT)) {
                            grid.getGrid()[i][j].setColor(WHITE);
                            grid.getGrid()[i][j].setAccess(true);
                            if (grid.getGrid()[i][j] == start) {
                                start = null;
                            }
                            if (grid.getGrid()[i][j] == end) {
                                end = null;
                            }
                        } else if (IsKeyPressed(KEY_S)) {
                            if (start == null && grid.getGrid()[i][j] != end && grid.getGrid()[i][j].isAccessible()) {
                                grid.getGrid()[i][j].setColor(GREEN);
                                start = grid.getGrid()[i][j];
                                System.out.println("INFO:Start point set at x: " + i + " and y: " + j);
                            }

                        } else if (IsKeyPressed(KEY_E)) {
                            if (end == null && grid.getGrid()[i][j] != start && grid.getGrid()[i][j].isAccessible()) {
                                grid.getGrid()[i][j].setColor(RED);
                                end = grid.getGrid()[i][j];
                                System.out.println("INFO:End point set at x:" + i + " and y: " + j);
                            }

                        }


                    }
                }
            }
            try {
                if (IsKeyPressed(KEY_SPACE)) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        if (start == null || end == null) {
                            System.out.println("ERROR:You need to add a Start point and an Endpoint in order for the algorithm to start.");
                        } else {
                            grid.updateNeighbours(allowDiagonal);
                            if (algorithm.solveAStar(start, end)) {
                                System.out.println("INFO:Path was found.Total steps: " + algorithm.getSteps());
                            } else {
                                System.out.println("INFO:Path not found");
                            }
                            final Animation animation = new Animation(grid.getGrid(), algorithm.getClosedSet(), algorithm.getOpenSet(), algorithm.getFinalPath(), animated, start, end);
                            animationThread = new Thread(() -> {
                                animation.animate();
                            });
                            animationThread.start();


                        }
                    }
                }

                if (IsKeyPressed(KEY_R)) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        grid.createGrid();
                        algorithm.getOpenSet().clear();
                        algorithm.getClosedSet().clear();
                        algorithm.getFinalPath().clear();
                        start = null;
                        end = null;
                        System.out.println("INFO:Everything was cleared.");

                    }
                }

                if (IsKeyPressed(KEY_G)) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        Random rand = new Random();
                        Range<Integer> range = Range.between(grid.getGrid().length / 2, grid.getGrid().length);
                        for (int i = 0; i < range.getMaximum() * grid.getGrid().length / 2; i++) {
                            int x = rand.nextInt(grid.getGrid().length);
                            int y = rand.nextInt(grid.getGrid().length);
                            if (grid.getGrid()[x][y] != end && grid.getGrid()[x][y] != start) {
                                grid.getGrid()[x][y].setColor(GRAY);
                                grid.getGrid()[x][y].setAccess(false);
                            }
                        }
                    }


                }


            } catch (NullPointerException e) {
                System.out.println(e);
            }


            BeginDrawing();
            ClearBackground(BLACK);
            grid.drawGrid();
            DrawText("Steps: " + algorithm.getSteps(), 5, screenWidth - 50, 20, BLACK);
            EndDrawing();

        }
        Raylib.CloseWindow();
    }
}
