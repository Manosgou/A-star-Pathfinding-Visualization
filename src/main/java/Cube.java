
import com.raylib.Jaylib;

import java.util.ArrayList;

import static com.raylib.Jaylib.BLUE;
import static com.raylib.Jaylib.PURPLE;
import static com.raylib.Jaylib.LIME;
import static com.raylib.Raylib.*;


public class Cube extends Rectangle {
    private boolean access;
    private boolean isInfoVisible = false;
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
        if (zoom >= 1.2f && this.isInfoVisible) {
            String fScore2String = String.valueOf(Math.round(this.fScore));
            DrawTextEx(GetFontDefault(),fScore2String, new Jaylib.Vector2(this.x() + 2,  this.y() + 2), zoom/this.width(),0,BLUE);
        }


    }

    public void setInfoVisible(boolean infoVisible) {
        isInfoVisible = infoVisible;
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
