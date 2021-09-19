package turtleGraphics;

import javax.swing.JPanel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class TurtleView extends JPanel implements PropertyChangeListener {

    private Turtle model;

    public TurtleView(Turtle model) {
        this.model = model;
        //model.subscribe(this);
        model.addPropertyChangeListener(this);
    }

    public void setModel(Turtle model) {
        this.model.removePropertyChangeListener(this);
        this.model = model;
        this.model.initSupport();
        this.model.addPropertyChangeListener(this);
        repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Graphics2D gc2 = (Graphics2D) gc;
        gc2.setStroke(new BasicStroke(2));
        Color oldColor = gc2.getColor();
        ArrayList<Point> path = model.getPath();
        Point p = path.get(0);
        for(int i = 1; i < path.size(); i++) {
            Point nextP = path.get(i);
            if(!p.endPoint) {
                gc2.setColor(nextP.color);
                gc2.drawLine(p.x, p.y, nextP.x, nextP.y);
            }
            p = nextP;
        }
        if (!model.isPenUp()) { // draw turtle
            gc2.setColor(Color.BLACK);
            gc2.fillOval(p.x-2, p.y-2, 5, 5);
        }
        gc.setColor(oldColor);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
