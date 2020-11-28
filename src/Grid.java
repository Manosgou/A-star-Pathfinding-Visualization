import static com.raylib.Jaylib.*;


public class Grid {

    private final int rows;
    private final int columns;
    Cube [][]grid;
    public Grid(int rows,int columns){
        this.rows = rows;
        this.columns =columns;
        grid = new Cube[rows][columns];


    }



    public void createGrid(){
        int size =20;
        int gap = 4;

        for(int x=0; x<rows; x++){

            for(int y=0; y<columns; y++){
                Cube cube =  new Cube(false,false,WHITE);
                cube.x(x*(size+gap)).y(y*(size+gap)).width(size).height(size);
                cube.setAccessible(true);
                grid[x][y]= cube;

            }


        }
    }

    public Cube[][] getGrid(){
        return this.grid;
    }


    public void drawGrid() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {

                DrawRectangleRec(grid[x][y], grid[x][y].getColor());
            }


        }

    }

    public void updateNeighbours(){
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){

            }
        }

    }

}
