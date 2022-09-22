import java.awt.Point;

public class Node 
{
    /*
     *    1  2  3
     *    4  N  5
     *    6  7  8
    */

    Node child;

    Point p; // X IS COLUMNS AND Y IS ROWS
    Point end;

    int gCost; //start value
    int hCost; //end value

    public Node(Point p, Point parentNode, Point end) {
        this.p = p; 
            int dx1 = Math.abs(p.x-end.x);
            int dy1 = Math.abs(p.y-end.y);
        this.hCost = 10*(dx1+dy1) + (14-2*10) * Math.min(dx1,dy1);

        if(parentNode != null){
                int dx2 = Math.abs(p.x-parentNode.x);
                int dy2 = Math.abs(p.y-parentNode.y);
            this.gCost = 10*(dx2+dy2) + (14-2*10) * Math.min(dx2,dy2);
        } else {
            gCost = 0;
        }

    }

    /**
     * check if accesible to parentNode: else return
     * checks for obstacles between  
     */
    public static boolean accesible(Point p,  Point parentNode, byte[][] grid){
        int sx = parentNode.x;
        int sy = parentNode.y;
        int ex = p.x;
        int ey = p.y;

        //vertically aligned
        if(p.x == parentNode.x){
            for(int y = sy<ey?sy:ey; y < (sy>ey?sy:ey); y++){
                if(grid[y][sx] == 1){
                    return false;
                }
            }
        }

        double m = ((double)sy-ey)/((double)sx-ex);
        double b = -1*((double)m*sx-sy);

        double x = sx<ex?(int)sx:(int)ex;
        int endX = sx>ex?(int)sx:(int)ex;

        for( ;x <= endX; x+=0.01){
            if(grid[(int)Math.round((m*x)+b)][(int)Math.round(x)] == 1){ 
                return false;
            }
        }

        return true;
    }   

    public void setChildNode(Node child){ this.child = child; }

    public String toString() {
        return p + "  " + gCost + "  " + hCost + "";
    }
}
