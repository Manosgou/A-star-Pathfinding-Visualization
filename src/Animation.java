import java.util.ArrayList;
import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.MAGENTA;

public class Animation implements Runnable {
    private final ArrayList<Cube> closedSet,finalPath;
    private final int delay;
    public Animation(ArrayList closedSet,ArrayList finalPath,int delay) {
        this.closedSet = closedSet;
        this.finalPath =finalPath;
        this.delay = delay;
    }

    @Override
    public void run() {

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
