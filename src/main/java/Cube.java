import com.raylib.Raylib.*;

import java.util.ArrayList;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawText;

public class Cube extends Rectangle {
    private boolean access;
    private Color color;
    private final ArrayList<Cube> neighbours;
    private double gScore, fScore, hScore;

    public Cube(boolean access, Color color) {
        this.access = access;
        this.color = color;
        this.neighbours = new ArrayList<>();

    }

    public void drawCube(float zoom) {

        DrawRectangleRec(this, this.color);
        if (zoom >= 1.2f) {
            DrawText(String.valueOf(Math.round(this.gScore)), (int) (this.x() + 2), (int) (this.y() + 2), 2, RED);
            DrawText(String.valueOf(Math.round(this.fScore)), (int) (this.x() + 2), (int) (this.y() + this.height() - 12), 2, RED);
            DrawText(String.valueOf(Math.round(this.hScore)), (int) (this.x() + this.width() - 18), (int) (this.y() + this.height() - 12), 2, RED);
        }


    }


    public boolean isAccessible() {
        return this.access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }


    public void pushNeighbours(Cube neighbour) {
        this.neighbours.add(neighbour);

    }

    public ArrayList<Cube> getNeighbours() {
        return this.neighbours;
    }

    public void setGScore(double score) {
        this.gScore = score;
    }

    public double getGScore() {
        return this.gScore;
    }

    public void setHScore(double score) {
        this.hScore = score;
    }

    public double getHScore() {
        return this.hScore;
    }

    public void setFScore(double score) {
        this.fScore = score;
    }

    public double getFScore() {
        return this.fScore;
    }
}
