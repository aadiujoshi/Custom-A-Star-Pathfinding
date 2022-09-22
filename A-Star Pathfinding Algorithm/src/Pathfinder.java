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
        pathfind(grid, startNode, end, 0);
        drawPathToGrid(startNode, grid);
        System.out.println("path found with no errors :^)");
        return grid;
    }
    
    private void drawPathToGrid(Node node, byte[][] grid){
        Node n = node;

        while(n != null){
            if(n.child != null){
                int sx = n.p.x;
                int sy = n.p.y;
                int ex = n.child.p.x;
                int ey = n.child.p.y;

                //vertically aligned
                if(sx == ex){
                    for(int y = sy<ey?sy:ey; y < (sy>ey?sy:ey); y++){
                        grid[y][sx] = 4;
                    }
                }

                double m = ((double)sy-ey)/((double)sx-ex);
                double b = -1*((double)m*sx-sy);

                double x = sx<ex?(int)sx:(int)ex;
                int endX = sx>ex?(int)sx:(int)ex;

                for( ;x <= endX; x+=0.01){
                    grid[(int)Math.round((m*x)+b)][(int)Math.round(x)] = 4;
                }
            }
            n = n.child;
        }
    }


    private boolean pathfind(byte[][] grid, Node node, Point end, int stack){
        ArrayList<Node> accesible = new ArrayList<>();

        for(int r  = 0; r < grid.length; r++){
            for(int c = 0; c < grid[0].length; c++){
                if((grid[r][c] == 3 || grid[r][c] == 0) && Node.accesible(new Point(c,r), node.p, grid)){
                    if(grid[r][c] == 3){
                        node.child = new Node(new Point(c,r), node.p, end);
                        return true;   
                    }
                    accesible.add(new Node(new Point(c,r), node.p, end));
                }
            }
        }

        if(accesible.size() == 0){
            return false;
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

        for(; n_ind < accesible.size() && 
            !pathfind(grid, accesible.get(n_ind), end, stack+1) /*&&
            accesible.get(n_ind).hCost <= node.hCost*/; n_ind++){
            
            // grid[accesible.get(n_ind).p.y][accesible.get(n_ind).p.x] = 5;
        }

        if(n_ind == accesible.size()){
            return false;
        }

        // System.out.println(n_ind);

        //path found
        
        // System.out.println(stack);
        node.child = accesible.get(n_ind);
        return true; 
    }
}
