package src;
import javax.swing.*;
import java.awt.event.*;

public class GridButtons extends JPanel implements ActionListener
{
    private byte buildType = 0;
    private JButton clear;
    private JButton clearAll;
    private JButton obstacle;
    private JButton start;
    private JButton end;

    public GridButtons()
    {
        clear = new JButton("Clear");
        clearAll = new JButton("Clear All");
        obstacle = new JButton("Obstacle");
        start = new JButton("Start");
        end = new JButton("End");

        clear.addActionListener(this);
        clearAll.addActionListener(this);
        obstacle.addActionListener(this);
        start.addActionListener(this);
        end.addActionListener(this);
        
        clear.setFocusable(false);
        clearAll.setFocusable(false);
        obstacle.setFocusable(false);
        start.setFocusable(false);
        end.setFocusable(false);

        this.add(clear);
        this.add(clearAll);
        this.add(obstacle);
        this.add(start);
        this.add(end);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == clear)
            buildType = 0;
        if(e.getSource() == obstacle)
            buildType = 1;
        if(e.getSource() == start)
            buildType = 2;
        if(e.getSource() == end)
            buildType = 3;
        if(e.getSource() == clearAll)
            buildType = 100;
    }

    public byte getBuildType() { return buildType; } 
}
