import com.raylib.Raylib;

import java.awt.event.KeyEvent;

import static com.raylib.Jaylib.*;

public class Main {
    public static void main(String[] args) {
        final int screenWidth = 800;
        final int screenHeight = 450;

        InitWindow(screenWidth, screenHeight, "A* Pathfinding");
        SetTargetFPS(60);

        final int ROWS = 30;
        final int COLUMNS = 30;

        Grid grid = new Grid(ROWS, COLUMNS);

        grid.createGrid();

        Raylib.Vector2 mouse;
        Cube start = null;
        Cube end = null;

        boolean isRunning =false;
        while (!WindowShouldClose()) {
            mouse = GetMousePosition();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (CheckCollisionPointRec(mouse, grid.getGrid()[i][j])) {
                        if (IsMouseButtonDown(MOUSE_LEFT_BUTTON)) {
                            if (!(grid.getGrid()[i][j].isEnd()) && !(grid.getGrid()[i][j].isStart())) {
                                grid.getGrid()[i][j].setColor(GRAY);
                                grid.getGrid()[i][j].setAccessible(false);
                            }
                        } else if (IsMouseButtonDown(MOUSE_RIGHT_BUTTON)) {
                            grid.getGrid()[i][j].setColor(WHITE);
                            if (grid.getGrid()[i][j].isStart()) {
                                grid.getGrid()[i][j].setStart(false);
                                start =null;
                            }
                            if (grid.getGrid()[i][j].isEnd()) {
                                grid.getGrid()[i][j].setEnd(false);
                                end =null;
                            }
                        } else if (IsKeyPressed(KEY_S)) {
                            if (start == null && !(grid.getGrid()[i][j].isEnd())) {
                                grid.getGrid()[i][j].setColor(GREEN);
                                grid.getGrid()[i][j].setStart(true);
                                start = grid.getGrid()[i][j];
                            }

                        } else if (IsKeyPressed(KEY_E)) {
                            if (end == null && !(grid.getGrid()[i][j].isStart())) {
                                grid.getGrid()[i][j].setColor(RED);
                                grid.getGrid()[i][j].setEnd(true);
                                end = grid.getGrid()[i][j];
                            }

                        }


                    }
                }
            }

            if(IsKeyDown(KEY_SPACE)){
                isRunning =true;
            }


            BeginDrawing();
            ClearBackground(BLACK);


            grid.drawGrid();


            EndDrawing();

        }
        Raylib.CloseWindow();
    }
}
