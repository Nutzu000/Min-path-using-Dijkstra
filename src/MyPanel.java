import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

public class MyPanel extends JPanel {
    private int nodeNr = 1;
    private int node_diam = 2;
    private Vector<Node> listaNoduri = new Vector();
    private Vector<Arc> listaArce = new Vector();
    public Vector<Vector<Integer>> listaDeAdiacenta = new Vector<>();
    public Vector<Vector<Integer>> costuri = new Vector<>();
    Point pointStart = null;
    Point pointEnd = null;
    int nrOfClicks = 0;
    boolean isDragging = false;
    static public int minLongitude;
    static public int minLatitude;
    static public Vector<Integer> iduri = new Vector<>();
    static public Vector<Integer> longitudini = new Vector<>();
    static public Vector<Integer> latitudini = new Vector<>();
    static public Vector<Integer> from = new Vector<>();
    static public Vector<Integer> to = new Vector<>();
    static public Vector<Integer> cost = new Vector<>();
    PriorityQueue<SmallArc> pq = new PriorityQueue<>(10, new ArcComparator());
    int startNr = -1;
    int endNr = -1;
    Vector<Integer> arceDeColorat = new Vector<>();

    void readXML(String fisier) {
        try {
            SAXParser p = SAXParserFactory.newInstance().newSAXParser();
            p.parse(new FileInputStream(fisier), new mySAXParser());
        } catch (Exception ex) {
            System.out.println("Nu-i buna calea");
        }
    }

    void djikstra() {
        long startTime = System.nanoTime();
        Vector<Boolean> vizitat = new Vector<>();
        Vector<Integer> dist = new Vector<>();
        Vector<Integer> parinte = new Vector<>();
        for (int i = 0; i < listaNoduri.size(); i++) {
            vizitat.add(false);
            dist.add(Integer.MAX_VALUE);
            parinte.add(-1);
        }
        dist.setElementAt(0, startNr);
        pq.clear();
        pq.add(new SmallArc(startNr, 0));
        while (!pq.isEmpty()) {
            int index = pq.peek().nodeNr;
            int min = pq.peek().valoare;
            pq.remove();
            vizitat.setElementAt(true, index);
            if (dist.elementAt(index) < min) {
                continue;
            }
            for (int i = 0; i < listaDeAdiacenta.elementAt(index).size(); i++) {
                if (vizitat.elementAt(listaDeAdiacenta.elementAt(index).elementAt(i))) {
                    continue;
                }
                int newDist = dist.elementAt(index) + costuri.elementAt(index).elementAt(i);
                if (newDist < dist.elementAt(listaDeAdiacenta.elementAt(index).elementAt(i))) {
                    parinte.setElementAt(index, listaDeAdiacenta.elementAt(index).elementAt(i));
                    dist.setElementAt(newDist, listaDeAdiacenta.elementAt(index).elementAt(i));
                    pq.add(new SmallArc(listaDeAdiacenta.elementAt(index).elementAt(i), newDist));
                }
            }
        }
        arceDeColorat.clear();
        int curent = endNr;
        while (curent != startNr) {
            //System.out.println(parinte.elementAt(curent));
            for (int i = 0; i < listaArce.size(); i++) {
                if (listaArce.elementAt(i).from == parinte.elementAt(curent) &&
                        listaArce.elementAt(i).to == curent) {
                    arceDeColorat.add(i);
                }
            }
            curent = parinte.elementAt(curent);
        }
        repaint();
        long endTime = System.nanoTime();
        double totalTime = endTime - startTime;
        System.out.println("Total seconds: " + totalTime / 1000000000);
    }

    public MyPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        readXML("./hartaLuxembourg.xml");
        //adaugam nodurile
        for (int i = 0; i < iduri.size(); i++) {
            listaDeAdiacenta.add(new Vector<>());
            costuri.add(new Vector<>());
            addNode((longitudini.elementAt(i) - minLongitude) / 50 + 200,
                    (latitudini.elementAt(i) - minLatitude) / 85 + 20,
                    iduri.elementAt(i));
        }
        //adaugam arcele
        for (int i = 0; i < from.size(); i++) {
            Point start = new Point(listaNoduri.elementAt(from.elementAt(i)).getCoordX(),
                    listaNoduri.elementAt(from.elementAt(i)).getCoordY());
            Point end = new Point(listaNoduri.elementAt(to.elementAt(i)).getCoordX(),
                    listaNoduri.elementAt(to.elementAt(i)).getCoordY());
            Arc aux = new Arc(start, end);
            aux.from = from.elementAt(i);
            aux.to = to.elementAt(i);
            aux.cost = cost.elementAt(i);
            listaDeAdiacenta.elementAt(aux.from).add(aux.to);
            costuri.elementAt(aux.from).add(aux.cost);
            listaArce.add(aux);
        }
        addMouseListener(new MouseAdapter() {
            // evenimentul care se produce la apasarea mouse-ului
            public void mousePressed(MouseEvent e) {
                if (nrOfClicks % 2 == 0) {
                    pointStart = e.getPoint();
                    double minDis = Double.MAX_VALUE;
                    for (Node n : listaNoduri) {
                        if (minDis > n.getDistance(pointStart)) {
                            minDis = n.getDistance(pointStart);
                            startNr = n.getNumber();
                        }
                    }
                    repaint();
                } else {
                    pointEnd = e.getPoint();
                    double minDis = Double.MAX_VALUE;
                    for (Node n : listaNoduri) {
                        if (minDis > n.getDistance(pointEnd)) {
                            minDis = n.getDistance(pointEnd);
                            endNr = n.getNumber();
                        }
                    }
                    repaint();
                }
                if (nrOfClicks > 0) {
                    djikstra();
                }
                nrOfClicks++;

            }
        });
    }

    void addNode(int x, int y, int nodeNr) {
        Node node = new Node(x, y, nodeNr);
        this.listaNoduri.add(node);
        ++this.nodeNr;
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my Graph!", 10, 20);
        Iterator var3 = this.listaArce.iterator();

        while (var3.hasNext()) {
            Arc a = (Arc) var3.next();
            a.drawArc(g, Color.BLACK);
        }
        for (int i = 0; i < this.listaNoduri.size(); ++i) {
            ((Node) this.listaNoduri.elementAt(i)).drawNode(g, this.node_diam, Color.BLACK);
        }
        for (int i = 0; i < listaArce.size(); ++i) {
            boolean isPartOfPath = false;
            for (Integer j : arceDeColorat) {
                if (i == j) {
                    isPartOfPath = true;
                    break;
                }
            }
            if (isPartOfPath) {
                listaArce.elementAt(i).drawArc(g, Color.RED);
            }

        }
        if (startNr != -1) {
            ((Node) this.listaNoduri.elementAt(startNr)).drawNode(g, this.node_diam * 5, Color.GREEN);
        }
        if (endNr != -1) {
            ((Node) this.listaNoduri.elementAt(endNr)).drawNode(g, this.node_diam * 5, Color.GREEN);
        }
    }
}

