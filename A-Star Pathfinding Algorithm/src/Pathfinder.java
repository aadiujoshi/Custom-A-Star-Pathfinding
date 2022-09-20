package src;
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
        
        return pathfind(grid, start, end);
        
    }

    private byte[][] pathfind(byte[][] grid, Point start, Point end){
    
        /*
         * make vars for currrent node and constructed path
        */
        
        

        // for()

        return grid;
    }
    
    private boolean searchNode(byte[][] grid, Node start, Point end){
        ArrayList<Node> accesible = new ArrayList<>();

        for(int r  = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if(Node.accesible(new Point(c,r), start, grid)){
                    accesible.add(new Node(new Point(c,r), start, end));
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
        
        //add checking for end point in accesible loop and start of recurs case
    }
}
