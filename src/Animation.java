import java.util.ArrayList;

import static com.raylib.Jaylib.*;

public class Animation extends Thread {
    private final ArrayList<Cube> closedSet, finalPath;
    private final int delay;
    private Cube start, end;

    public Animation(ArrayList closedSet, ArrayList finalPath, int delay, Cube start, Cube end) {
        this.closedSet = closedSet;
        this.finalPath = finalPath;
        this.delay = delay;
        this.start = start;
        this.end = end;
    }


    @Override
    public void run() {
        closedSet.remove(start);
        closedSet.remove(end);
        finalPath.remove(start);
        finalPath.remove(end);
        for (Cube cube : closedSet) {

            cube.setColor(MAGENTA);
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        for (Cube cube : finalPath) {

            cube.setColor(BLACK);
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
