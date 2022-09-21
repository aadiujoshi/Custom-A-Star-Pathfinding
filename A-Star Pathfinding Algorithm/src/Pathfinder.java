import java.awt.Point;
import java.util.ArrayList;

public class Pathfinder 
{
    public Pathfinder(){}
    
    public byte[][] findBestPath(byte[][] grid)
    {
        //FIND START AND END OF PATH
        Point start = null;
        Point end = null;
        for(int r = 0; r < grid.length; r++)
            for(int c = 0; c < grid[0].length; c++)
            {
                if(grid[r][c] == 2)
                    start = new Point(c, r);
                if(grid[r][c] == 3)
                    end = new Point(c, r);
            }
        if(start == null || end == null)
            return grid;
        
        //--------------------------------------------------------------------------------------------
        //pathfinding
        //--------------------------------------------------------------------------------------------
        
        Node startNode = new Node(start, null, end);

        pathfind(grid, startNode, end);
        return grid;
    }
    
    private boolean pathfind(byte[][] grid, Node node, Point end){
        ArrayList<Node> accesible = new ArrayList<>();

        for(int r  = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(grid[r][c] == 0 && Node.accesible(new Point(c,r), node.p, grid)){
                    if(c == end.x && r == end.y){
                        node.child = new Node(new Point(c,r), node.p, end);
                        return true;   
                    }
                    accesible.add(new Node(new Point(c,r), node.p, end));
                    // grid[r][c] = 5;
                }
            }
        }
        
        //sort accesible
        for(int i = 0; i < accesible.size(); i++){
            int b_ind = i;
            for(int j = i+1; j < accesible.size(); j++){
                if(accesible.get(j).hCost < accesible.get(b_ind).hCost){
                    b_ind = j;
                }
            }
            Node temp = accesible.get(i);
            accesible.set(i, accesible.get(b_ind));
            accesible.set(b_ind, temp);
        }

        //deviating from end point, return false, least optimal path as of current evaluation
        if(accesible.get(0).hCost > node.hCost){
            return false;
        }

        grid[accesible.get(0).p.y][accesible.get(0).p.x] = 5;
        
        //pathfind node with least hcost

        int n_ind = 0;
        boolean searching = true;
        while(searching = !pathfind(grid, accesible.get(n_ind), end)){
            n_ind++;
            if(n_ind == accesible.size())
                return false;
        }

        //path found
        if(!searching){
            node.child = accesible.get(n_ind-1);
            return true;
        }
        
        return false;
    }
}
