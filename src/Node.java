import java.awt.*;

public class Node {
    private int coordX;
    private int coordY;
    private int number;

    public Node(int coordX, int coordY, int number) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void drawNode(Graphics g, int node_diam, Color color) {
        g.setColor(color);
        g.fillOval(this.coordX, this.coordY, node_diam, node_diam);
        g.setColor(color);
        g.drawOval(this.coordX, this.coordY, node_diam, node_diam);

    }
    double getDistance(Point point) {
        return Math.sqrt(Math.pow((coordX - point.x), 2) + Math.pow((coordY - point.y), 2));
    }
}
