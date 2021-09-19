package turtleGraphics;

import tools.Bean;
import java.awt.*;
import java.util.ArrayList;

public class Turtle extends Bean {
    private Point location = new Point(100,100, Color.RED); // initial location
    private ArrayList<Point> path = new ArrayList<>();
    private Color color = Color.BLACK;
    private boolean penUp = false;
    public static Integer WORLD_SIZE = 250; // height & width of the world (& view)

    Turtle() { path.add(location); }

    public Point getLocation() { return location; }
    public ArrayList<Point> getPath() { return path; }
    public Color getColor() { return color; }
    public boolean isPenUp() { return penUp; }

    public void changeColor(Color color) {
        this.color = color;
    }

    public void move(int distance, String direction) {
        Point oldLocation = location;
        int x = location.x;
        int y = location.y;
        if (direction == "NORTH") {
            y -= distance;
            if (y < 0) {
                Point p1 = new Point(x, 0, color);
                path.add(p1);
                p1.endPoint = true;
                Point p2 = new Point(x, WORLD_SIZE, color);
                path.add(p2);
                if(penUp) { p2.endPoint = true; }
                while (y < 0) { y = WORLD_SIZE + y; }
            }
        } else if (direction == "EAST") {
            x += distance;
            if (x > WORLD_SIZE) {
                Point p1 = new Point(WORLD_SIZE, y, color);
                path.add(p1);
                p1.endPoint = true;
                Point p2 = new Point(0, y, color);
                path.add(p2);
                if(penUp) { p2.endPoint = true; }
                x = x % WORLD_SIZE;
            }
        } else if (direction == "SOUTH") {
            y += distance;
            if (y > WORLD_SIZE) {
                Point p1 = new Point(x, WORLD_SIZE, color);
                path.add(p1);
                p1.endPoint = true;
                Point p2 = new Point(x, 0, color);
                path.add(p2);
                if(penUp) { p2.endPoint = true; }
                y = y % WORLD_SIZE;
            }
        } else if (direction == "WEST") {
            x -= distance;
            if (x < 0) {
                Point p1 = new Point(0, y, color);
                path.add(p1);
                p1.endPoint = true;
                Point p2 = new Point(WORLD_SIZE, y, color);
                path.add(p2);
                if(penUp) { p2.endPoint = true; }
                while (x < 0) { x = WORLD_SIZE + x; }
            }
        }
        location = new Point(x, y, color);
        path.add(location);
        setPenUp(penUp);
        firePropertyChange("location", oldLocation, location);
    }

    public void clearPath() {
        ArrayList<Point> oldPath = path;
        path = new ArrayList<>();
        path.add(location);
        firePropertyChange("path", oldPath, path);
    }

    public void setPenUp(Boolean penUp) {
        boolean oldPenUp = this.penUp;
        this.penUp = penUp;
        if (penUp) {
            location.endPoint = true;
        } else {
            location.endPoint = false;
            //path.add(location);
        }
        firePropertyChange("penUp", oldPenUp, penUp);
    }
}
