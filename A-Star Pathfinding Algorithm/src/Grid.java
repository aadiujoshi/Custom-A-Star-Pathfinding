import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Grid extends JPanel{

    private byte grid[][];

    public static long lastUpdated = System.currentTimeMillis();

    //use for thread locking if needed 
    private final static Object LOCK = new Object();
    // private boolean searching = false;
    /*
    grid key:
    0 - traversable (WHITE)
    1 - obstacle (RED)
    2 - start (GREEN)
    3 - end (PURPLE)
    4 - pathfinder searched area (YELLOW)
    5 - pathfinder finalized path (BLUE)
    100 - clear all (WHITE)
    */

    private JFrame frame;
    private JFrame buttons;
    private GridButtons gb;
    private int width;
    private int height;
    private int fWidth;
    private int fHeight;
    private int gridSpace;
    private Pathfinder pathfinder;

    private Thread graphicsThread;

    private boolean mouseDown;
    private int frameLocationX;
    private int frameLocationY;

    public Grid(int gridSpace, int width, int height) //G*+RID DIMENSIONS
    {
        grid = new byte[height][width];
        for(int r = 0; r < grid.length; r++)
            for(int c = 0; c < grid[0].length; c++) //ROW = X, COL = Y
                grid[r][c] = 0;
        this.width = width;
        this.height = height;
        this.fWidth = width*gridSpace;
        this.fHeight = height*gridSpace;
        this.gridSpace = gridSpace;
        this.mouseDown = false;
        pathfinder = new Pathfinder();

        frame = new JFrame("Modified A* Pathfinding");
        frame.setSize(fWidth+15, fHeight+38);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gb = new GridButtons();
        buttons = new JFrame("Build Tools");
        buttons.setSize(200, 200);
        buttons.add(gb);
        buttons.pack();

        this.setLayout(null);

        frame.add(this);

        graphicsThread = new Thread(new Runnable(){

            @Override
            public void run() {
                while(true){
                    if(mouseDown){
                        // System.out.println(mouseDown);
                        int r = ((int)MouseInfo.getPointerInfo().getLocation().getY()-frameLocationY)/(fHeight/height);  //in accordance to grid array
                        int c = ((int)MouseInfo.getPointerInfo().getLocation().getX()-frameLocationX)/(fWidth/width);
                        
                        try{ grid[r][c] = gb.getBuildType(); } 
                        catch(IndexOutOfBoundsException e) {}

                        if(gb.getBuildType() == 100){
                            clearGrid();
                        }
                    }

                    repaint();
                }
            }
        });

        frame.addMouseListener(new MouseListener(){
            public void mousePressed(MouseEvent e) { mouseDown = true;}
            public void mouseReleased(MouseEvent e) { mouseDown = false;}
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        frame.addComponentListener(new ComponentListener(){
            public void componentResized(ComponentEvent e) {
                try{
                    frameLocationX = (int)getLocationOnScreen().getX();
                    frameLocationY = (int)getLocationOnScreen().getY();
                } catch(java.awt.IllegalComponentStateException err) {}
            }

            public void componentMoved(ComponentEvent e) {
                try{
                    frameLocationX = (int)getLocationOnScreen().getX();
                    frameLocationY = (int)getLocationOnScreen().getY();
                } catch(java.awt.IllegalComponentStateException err) {}
            }

            public void componentShown(ComponentEvent e) {}
            public void componentHidden(ComponentEvent e) {}
        });
        
        frame.setVisible(true);
        buttons.setVisible(true);

        this.graphicsThread.start();

        try { this.mainloop();
        } catch (InterruptedException e1) {}
    }

    public void mainloop() throws InterruptedException {
        while(true){
            Thread.sleep(1);
            if(mouseDown){
                grid = pathfinder.findBestPath(grid);
                lastUpdated = System.currentTimeMillis();
                Thread.sleep(1000);
            }
        }
    }


    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        for(int r = 0; r < grid.length; r++) //draw grid
            for(int c = 0; c < grid[0].length; c++)
            {
                if(grid[r][c] == 0) //traversable
                    g.setColor(Color.WHITE);
                if(grid[r][c] == 1) //obstacle
                    g.setColor(Color.RED);
                if(grid[r][c] == 2) //start
                    g.setColor(Color.GREEN);
                if(grid[r][c] == 3) //end
                    g.setColor(Color.MAGENTA);
                if(grid[r][c] == 4) //pathfinder path
                    g.setColor(Color.YELLOW);
                if(grid[r][c] == 5)
                    g.setColor(Color.BLUE);
                if(grid[r][c] == 100)
                    g.setColor(Color.WHITE);
                
                g.fillRect(c*(gridSpace), r*(gridSpace), 
                c*(gridSpace)+gridSpace, r*(gridSpace)+gridSpace);
                
                //BY ROW COLUMN NOT X Y COORDINATES

                g.setColor(Color.BLACK);
                g.setFont(new Font(null, Font.PLAIN, (int)(gridSpace/2.5)));
                g.drawString(r + "," + c, c*(gridSpace), r*(gridSpace)-3+gridSpace);
            }
        
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.BLACK);
        for(int w = 0; w < fWidth; w+=gridSpace){ //draw vertical lines
            g.drawLine(w, 0, w, fHeight);}
        for(int h = 0; h < fHeight; h+=gridSpace){ //draw horizontal lines
            g.drawLine(0, h, fWidth, h);}
        g.dispose();
    }

    public void clearGrid() {
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[0].length; j++)
                grid[i][j] = 0;
    }
}
