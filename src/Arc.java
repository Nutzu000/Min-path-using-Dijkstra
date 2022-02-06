import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc {
    private Point start;
    private Point end;
    public int from;
    public int to;
    public int cost;

    public Arc(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Arc(Point start, Point end, int from, int to) {
        this.start = start;
        this.end = end;
        this.from = from;
        this.to = to;
    }

    public void drawArc(Graphics g, Color color) {
        if (this.start != null) {
            g.setColor(color);
            g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
        }

    }
}
