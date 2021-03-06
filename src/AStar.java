import java.util.ArrayList;
import java.util.HashMap;

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

    private double distance(Cube c1, Cube c2) {
        return Math.sqrt((c1.x() - c2.x()) * (c1.x() - c2.x()) + (c1.y() - c2.y()) * (c1.y() - c2.y()));
    }

    private double heuristic(Cube neighbour, Cube end) {
        if (this.allowDiagonal) {
            return distance(neighbour, end);
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
            for (int i = 0; i < this.openSet.size(); i++) {
                if (this.openSet.get(i).getFScore() < this.openSet.get(lowestF).getFScore()) {
                    lowestF = i;

                }

            }
            Cube current = this.openSet.get(lowestF);
            if (current.equals(end)) {
                this.reconstructPath(current);
                return true;
            }

            this.openSet.remove(current);
            this.closedSet.add(current);

            for (Cube neighbor : current.getNeighbours()) {
                if (closedSet.contains(neighbor))
                    continue;
                double tempG = neighbor.getGScore() + this.distance(current,neighbor);
                if(!openSet.contains(neighbor))
                    openSet.add(neighbor);
                else if(tempG>=neighbor.getGScore())
                    continue;


                neighbor.setHScore(this.heuristic(neighbor, end));
                neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());
                this.cameFrom.put(neighbor, current);


            }

        }
        return false;
    }


}
