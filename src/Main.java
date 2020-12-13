import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

public class Main {

    public static void main(String[] args){
        final int screenWidth = 600;
        InitWindow(screenWidth, screenWidth, "A* Pathfinding");
        SetTargetFPS(60);

        final int ROWS = 20;
        final boolean allowDiagonal =true;
        final int animationDelay = 50;



        Grid grid = new Grid(ROWS,screenWidth);

        grid.createGrid();

        AStar algorithm = new AStar(grid.getGrid(),allowDiagonal);

        Raylib.Vector2 mouse;
        Cube start = null;
        Cube end = null;


        Animation animation;
        Thread animationThread = null;
        while (!WindowShouldClose()) {
            mouse = GetMousePosition();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < ROWS; j++) {
                    if (CheckCollisionPointRec(mouse, grid.getGrid()[i][j])) {
                        if (IsMouseButtonDown(MOUSE_LEFT_BUTTON)) {
                            if (grid.getGrid()[i][j] != end && grid.getGrid()[i][j] != start) {
                                grid.getGrid()[i][j].setColor(GRAY);
                                grid.getGrid()[i][j].setAccess(false);
                            }
                        } else if (IsMouseButtonDown(MOUSE_RIGHT_BUTTON)) {
                            grid.getGrid()[i][j].setColor(WHITE);
                            grid.getGrid()[i][j].setAccess(true);
                            if (grid.getGrid()[i][j] == start) {
                                start = null;
                            }
                            if (grid.getGrid()[i][j] == end) {
                                end = null;
                            }
                        } else if (IsKeyPressed(KEY_S)) {
                            if (start == null && grid.getGrid()[i][j] != end) {
                                grid.getGrid()[i][j].setColor(GREEN);
                                start = grid.getGrid()[i][j];
                                System.out.println("INFO:Start point set at x: "+i+" and y: "+j);
                            }

                        } else if (IsKeyPressed(KEY_E)) {
                            if (end == null && grid.getGrid()[i][j] != start) {
                                grid.getGrid()[i][j].setColor(RED);
                                end = grid.getGrid()[i][j];
                                System.out.println("INFO:End point set at x:"+i+" and y: "+j);
                            }

                        }


                    }
                }
            }
            if (IsKeyPressed(KEY_SPACE)) {
                if (start == null || end == null) {
                    System.out.println("ERROR:You need to add a Start point and an Endpoint in order for the algorithm to start.");
                } else {
                    grid.updateNeighbours(allowDiagonal);
                    if(algorithm.solveAStar(start, end)){
                        System.out.println("INFO:Path was found.Total steps: "+algorithm.getSteps());
                    }else
                    {
                        System.out.println("INFO:Path not found");
                    }
                    animation= new Animation(algorithm.getClosedSet(), algorithm.getFinalPath(),animationDelay,start,end);
                    animationThread = new Thread(animation);
                    animationThread.start();
                }

        }

        if (IsKeyPressed(KEY_R)) {
            grid.createGrid();
            algorithm.getOpenSet().clear();
            algorithm.getClosedSet().clear();
            algorithm.getFinalPath().clear();
            start = null;
            end = null;
            System.out.println("INFO:Everything was cleared.");
        }

        BeginDrawing();
        ClearBackground(BLACK);
        grid.drawGrid();
        DrawText("Steps: " + algorithm.getSteps(), 10, screenWidth - 50, 20, BLACK);
        EndDrawing();

    }
        Raylib.CloseWindow();
}
}
