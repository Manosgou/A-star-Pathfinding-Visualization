import java.util.ArrayList;
import java.util.HashMap;
public class AStar {

    Cube [][] grid;
     private ArrayList<Cube> finalPath;

    private ArrayList <Cube> openSet,closedSet;

    private HashMap<Cube,Cube> cameFrom;

    public AStar(Cube [][]grid){
        this.grid =grid;
        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();
        this.finalPath = new ArrayList<>();
        this.cameFrom =  new HashMap<>();

    }

    private float heuristic(Cube neighbour,Cube end){
        return Math.abs(neighbour.x() - end.x()) + Math.abs(neighbour.y() - end.y());

    }

    private void reconstructPath(Cube current){
        this.finalPath.add(current);
            while(this.cameFrom.containsKey(current)){
                current = this.cameFrom.get(current);
                this.finalPath.add(current);
            }
    }

    public ArrayList<Cube> getOpenSet() {
        return openSet;
    }

    public ArrayList<Cube> getClosedSet() {
        return closedSet;
    }

    public ArrayList<Cube> getFinalPath() {
        return finalPath;
    }

    public String getSteps(){
        return String.valueOf(finalPath.size());
    }
    public boolean solveAStar(Cube start, Cube end){
            this.openSet.add(start);
            start.setHScore(this.heuristic(start,end));
            start.setFScore(start.getGScore()+start.getHScore());

            Cube current;
            int lowestF =0;
            while (!this.openSet.isEmpty()){
                for(int i=0; i<this.openSet.size(); i++){
                    if(this.openSet.get(i).getFScore()<this.openSet.get(lowestF).getFScore()){
                        lowestF =i;

                    }

                }
                current = this.openSet.get(lowestF);
                if(current == end){
                    this.reconstructPath(current);
                    return true;
                }

                this.openSet.remove(current);
                this.closedSet.add(current);

                for(Cube n :current.getNeighbours()){
                    if(!closedSet.contains(n)){
                        double  tempG = n.getGScore() +this.heuristic(current,n);
                        boolean betterPath = false;
                        if(openSet.contains(n)){
                            if(tempG<n.getGScore()){
                                n.setGScore(tempG);
                                betterPath =true;
                            }

                        }else{
                            n.setGScore(tempG);
                            betterPath=true;
                            openSet.add(n);
                        }
                        
                        if(betterPath){
                            n.setHScore(this.heuristic(n,end));
                            n.setFScore(n.getGScore()+n.getHScore());
                            this.cameFrom.put(n,current);
                        }



                    }

                }

            }
            return false;
        }



}
