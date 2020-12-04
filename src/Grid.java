import static com.raylib.Jaylib.*;


public class Grid {

    private final int rows,columns;
    private final int width,height;
    Cube [][]grid;
    public Grid(int rows,int columns,int sWidth,int sHeight){
        this.rows = rows;
        this.columns =columns;
        this.width = sWidth/columns;
        this.height = sHeight/rows;
        grid = new Cube[columns][rows];


    }



    public void createGrid(){
        int size =25;

        for(int x=0; x<columns; x++){

            for(int y=0; y<rows; y++){
                Cube cube =  new Cube(true,WHITE);
                cube.x(this.width*x).y(this.height*y).width(size).height(size);
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
                if (j<rows-1 && grid[i][j+1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i][j+1]);
                if (i>0 && grid[i-1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i-1][j]);
                if (i<columns - 1 && grid[i+1][j].isAccessible())
                    grid[i][j].pushNeighbours(grid[i+1][j]);

                if(i>0 && j>0 && grid[i-1][j-1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i-1][j-1]);
                if(i<columns-1 && j>0 && grid[i+1][j-1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i+1][j-1]);
                if(i>0 && j<rows-1 && grid[i-1][j+1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i-1][j+1]);
                if(i<columns-1 && j<rows-1 && grid[i+1][j+1].isAccessible())
                    grid[i][j].pushNeighbours(grid[i+1][j+1]);
            }
        }
    }

}
