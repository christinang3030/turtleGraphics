package turtleGraphics;

import java.awt.*;
import java.io.Serializable;

public class Point implements Serializable {
    int x, y;
    Color color;
    boolean endPoint;

    Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        endPoint = false;
    }
/*
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }
    public boolean isEndPoint() { return endPoint; }

 */
}
