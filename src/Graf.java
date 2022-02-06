import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Graf {
    public Graf() {
    }

    private static void initUI() {
        JFrame f = new JFrame("Algoritmica Grafurilor");
        f.setDefaultCloseOperation(3);
        f.add(new MyPanel());
        f.setSize(1900, 1000);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Graf.initUI();
            }
        });
    }
}
