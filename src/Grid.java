import static com.raylib.Jaylib.*;


public class Grid {

    private final int rows;
    private final int columns;
    Cube [][]grid;
    public Grid(int rows,int columns){
        this.rows = rows;
        this.columns =columns;
        grid = new Cube[columns][rows];


    }



    public void createGrid(){
        int size =20;
        int gap = 4;

        for(int x=0; x<columns; x++){

            for(int y=0; y<rows; y++){
                Cube cube =  new Cube(true,WHITE);
                cube.x(x*(size+gap)).y(y*(size+gap)).width(size).height(size);
                cube.setParent(null);
                cube.setFScore(0);
                cube.setHScore(0);
                cube.setFScore(0);
                grid[x][y]= cube;

            }


        }
    }

    public Cube[][] getGrid(){
        return this.grid;
    }


    public void drawGrid() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {

                DrawRectangleRec(grid[x][y], grid[x][y].getColor());
            }


        }

    }

    public void updateNeighbours(){
        for(int i=0; i<columns; i++){
            for(int j=0; j<rows; j++){
                if (j>0 && grid[i][j-1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i][j-1]);
                if (j<columns-1 && grid[i][j+1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i][j+1]);
                if (i>0 && grid[i-1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i-1][j]);
                if (i<columns - 1 && grid[i+1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i+1][j]);
            }
        }
    }

}
