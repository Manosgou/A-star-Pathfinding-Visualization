import com.raylib.Jaylib;
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
        int animationDelay = 1;


        final Grid grid = new Grid(ROWS, screenWidth);

        grid.createGrid();

        final AStar algorithm = new AStar(grid.getGrid(), allowDiagonal);

        Raylib.Vector2 mouse;
        Raylib.Vector2 mousePosition;

        Camera2D camera2D = new Camera2D();
        camera2D.target(new Jaylib.Vector2((screenWidth + (grid.getGrid().length * grid.getGrid()[0][0].x() * ROWS)) / 2, (screenWidth + (grid.getGrid().length * grid.getGrid()[0][0].y() * ROWS)) / 2));
        camera2D.offset(new Jaylib.Vector2(screenWidth / 2.0f, screenWidth / 2.0f));
        camera2D.zoom(1.0f);


        Cube start = null;
        Cube end = null;

        Thread animationThread = null;

        boolean freeMove = false;

        while (!WindowShouldClose()) {
            mouse = GetMousePosition();
            mousePosition = GetScreenToWorld2D(mouse, camera2D);


            camera2D.zoom(camera2D.zoom() + GetMouseWheelMove() * 0.05f);
            if (camera2D.zoom() > 3.0f) camera2D.zoom(3.0f);
            else if (camera2D.zoom() < 0.1f) camera2D.zoom(0.1f);

            if (freeMove) {
                camera2D.target(mouse);
            }

            if (IsKeyPressed(KEY_F)) {
                freeMove = !freeMove;
            }


            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < ROWS; j++) {
                    if (CheckCollisionPointRec(mousePosition, grid.getGrid()[i][j])) {
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
                            final Animation animation = new Animation(grid.getGrid(), algorithm.getClosedSet(), algorithm.getOpenSet(), algorithm.getFinalPath(), animated, animationDelay, start, end);
                            animationThread = new Thread(animation::animate);
                            animationThread.start();


                        }
                    }
                }
                if (IsKeyPressed(KEY_UP) && animated) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        if (animationDelay < 8) {
                            animationDelay++;
                        }
                    }
                }

                if (IsKeyPressed(KEY_DOWN) && animated) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        if (animationDelay > 1) {
                            animationDelay--;
                        }
                    }
                }
                if (IsKeyPressed(KEY_R)) {
                    if (animationThread == null || animationThread.getState().equals(Thread.State.TERMINATED)) {
                        camera2D.zoom(1.0f);
                        camera2D.target(new Jaylib.Vector2((screenWidth + (grid.getGrid().length * grid.getGrid()[0][0].x() * ROWS)) / 2, (screenWidth + (grid.getGrid().length * grid.getGrid()[0][0].y() * ROWS)) / 2));
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
            BeginMode2D(camera2D);
            ClearBackground(BLACK);
            grid.drawGrid(camera2D.zoom());
            EndMode2D();
            DrawText("Steps: " + animationDelay, 5, screenWidth - 50, 20, BLACK);
            EndDrawing();

        }
        Raylib.CloseWindow();
    }

}
