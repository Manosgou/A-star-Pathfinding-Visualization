import java.util.ArrayList;
import java.util.HashMap;

import static com.raylib.Colors.*;

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
            while(this.cameFrom.containsKey(current)){
                current = this.cameFrom.get(current);
                this.finalPath.add(current);
            }
    }

    public boolean solveAStar(Cube start,Cube end){
            this.openSet.add(start);


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
                    for(Cube cube : this.finalPath){
                        cube.setColor(BLACK);
                    }
                    return true;
                }

                this.openSet.remove(current);
                this.closedSet.add(current);

                for(Cube n :current.getNeighbours()){
                    if(!closedSet.contains(n)){
                        double  tempG = n.getGScore() +1;

                        if(openSet.contains(n)){
                            if(tempG<n.getGScore()){
                                n.setGScore(tempG);
                            }
                        }else{
                            n.setGScore(tempG);
                            openSet.add(n);
                        }
                        this.cameFrom.put(n,current);
                        n.setHScore(this.heuristic(n,end));
                        n.setFScore(n.getGScore()+n.getHScore());


                    }

                }




                for(Cube cube : this.openSet){
                    cube.setColor(PURPLE);
                }

                for(Cube cube : this.closedSet){
                    cube.setColor(YELLOW);
                }



            }



            return false;
        }



}
