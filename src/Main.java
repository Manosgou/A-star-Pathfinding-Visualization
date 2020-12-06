import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int screenWidth = 600;
        final int screenHeight = 600;

        InitWindow(screenWidth, screenHeight, "A* Pathfinding");
        SetTargetFPS(60);

        final int ROWS =20;
        final int COLUMNS =20;

        Grid grid = new Grid(ROWS, COLUMNS,screenWidth,screenHeight);

        grid.createGrid();

        Raylib.Vector2 mouse;
        Cube start = null;
        Cube end = null;

        AStar algorithm = new AStar(grid.getGrid());

        while (!WindowShouldClose()) {
            mouse = GetMousePosition();


            for (int i = 0; i < COLUMNS; i++) {
                for (int j = 0; j < ROWS; j++) {
                    if (CheckCollisionPointRec(mouse, grid.getGrid()[i][j])) {
                        if (IsMouseButtonDown(MOUSE_LEFT_BUTTON)) {
                            if (grid.getGrid()[i][j] !=end && grid.getGrid()[i][j] !=start) {
                                grid.getGrid()[i][j].setColor(GRAY);
                                grid.getGrid()[i][j].setAccess(false);
                            }
                        } else if (IsMouseButtonDown(MOUSE_RIGHT_BUTTON)) {
                            grid.getGrid()[i][j].setColor(WHITE);
                            grid.getGrid()[i][j].setAccess(true);
                            if (grid.getGrid()[i][j] == start) {
                                start =null;
                            }
                            if (grid.getGrid()[i][j] == end) {
                                end =null;
                            }
                        } else if (IsKeyPressed(KEY_S)) {
                            if (start == null && grid.getGrid()[i][j] !=end) {
                                grid.getGrid()[i][j].setColor(GREEN);
                                start = grid.getGrid()[i][j];
                            }

                        } else if (IsKeyPressed(KEY_E)) {
                            if (end == null && grid.getGrid()[i][j] !=start) {
                                grid.getGrid()[i][j].setColor(RED);
                                end = grid.getGrid()[i][j];
                            }

                        }


                    }
                }
            }

            if(IsKeyPressed(KEY_SPACE)){

                if(start == null && end ==null){
                    System.out.println("Bruh");
                }else{
                    grid.updateNeighbours();
                    algorithm.solveAStar(start,end);

                    for(Cube c : algorithm.getOpenSet()){
                        c.setColor(MAGENTA);

                    }
                    for(Cube c : algorithm.getClosedSet()){
                            c.setColor(YELLOW);

                    }
                    for(Cube c : algorithm.getFinalPath()){
                        c.setColor(BLACK);

                    }
                }

            }
            if(IsKeyPressed(KEY_R)){
                for (int i = 0; i < COLUMNS; i++) {
                    for (int j = 0; j < ROWS; j++) {
                        grid.createGrid();
                        algorithm.getOpenSet().clear();
                        algorithm.getClosedSet().clear();
                        algorithm.getFinalPath().clear();
                        start =null;
                        end = null;
                    }
                }

            }



            BeginDrawing();
            ClearBackground(BLACK);


            grid.drawGrid();
            DrawText("Steps: "+algorithm.getSteps(),10,screenHeight-50,20,BLACK);



            EndDrawing();

        }
        Raylib.CloseWindow();
    }
}
