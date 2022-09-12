import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class AStar {

    Cube[][] grid;
    private final ArrayList<Cube> closedSet, openSet, finalPath;
    private final HashMap<Cube, Cube> cameFrom;
    private final boolean allowDiagonal;

    public AStar(Cube[][] grid, boolean allowDiagonal) {
        this.grid = grid;
        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();
        this.finalPath = new ArrayList<>();
        this.cameFrom = new HashMap<>();
        this.allowDiagonal = allowDiagonal;

    }

    private double euclieanDistance(Cube c1, Cube c2) {
        return Math.sqrt(Math.pow((c1.x() - c2.x()) * (c1.x() - c2.x()),2.0f) + Math.pow((c1.y() - c2.y()) * (c1.y() - c2.y()),2.0f));
    }

    private double heuristic(Cube neighbour, Cube end) {
        if (this.allowDiagonal) {
            return euclieanDistance(neighbour, end);
        } else {
            return Math.abs(neighbour.x() - end.x()) + Math.abs(neighbour.y() - end.y());
        }

    }

    private void reconstructPath(Cube current) {
        this.finalPath.add(current);
        while (this.cameFrom.containsKey(current)) {
            current = this.cameFrom.get(current);
            this.finalPath.add(current);
        }
    }

    public ArrayList<Cube> getOpenSet() {
        return this.openSet;
    }

    public ArrayList<Cube> getClosedSet() {
        return this.closedSet;
    }

    public ArrayList<Cube> getFinalPath() {
        return this.finalPath;
    }

    public String getSteps() {
        return String.valueOf(finalPath.size());
    }

    public boolean solveAStar(Cube start, Cube end) {
        this.openSet.add(start);
        start.setGScore(0.0);
        start.setHScore(this.heuristic(start, end));
        start.setFScore(start.getGScore() + start.getHScore());


        int lowestF = 0;
        while (!this.openSet.isEmpty()) {
            Cube current = this.openSet.stream().min(Comparator.comparing(Cube::getFScore)).orElseThrow(NoSuchElementException::new);
            if (current.equals(end)) {
                this.reconstructPath(current);
                return true;
            }

            this.openSet.remove(current);
            this.closedSet.add(current);

            for (Cube neighbor : current.getNeighbours()) {
                if (closedSet.contains(neighbor))
                    continue;
                double tempG = neighbor.getGScore() + this.euclieanDistance(current, neighbor);
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);
                else if (tempG >= neighbor.getGScore())
                    continue;


                neighbor.setHScore(this.heuristic(neighbor, end));
                neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());
                this.cameFrom.put(neighbor, current);


            }

        }
        return false;
    }


}
