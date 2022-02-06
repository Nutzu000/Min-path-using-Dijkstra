import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Vector;

public class mySAXParser extends DefaultHandler {
    int minLongitude = Integer.MAX_VALUE;
    int minLatitude = Integer.MAX_VALUE;
    Vector<Integer> iduri = new Vector<>();
    Vector<Integer> longitudini = new Vector<>();
    Vector<Integer> latitudini = new Vector<>();
    Vector<Integer> fromV = new Vector<>();
    Vector<Integer> toV = new Vector<>();
    Vector<Integer> costV = new Vector<>();

    public void startDocument() {
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (qName == "node") {
        int id = Integer.parseInt(attrs.getValue(0));
        int longitude = Integer.parseInt(attrs.getValue(1));
        int latitude = Integer.parseInt(attrs.getValue(2));
            if (minLongitude > longitude) {
                minLongitude = longitude;
            }

            if (minLatitude > latitude) {
                minLatitude = latitude;
            }
            iduri.add(id);
            longitudini.add(longitude);
            latitudini.add(latitude);

        }else
        if (qName == "arc") {
            int from = Integer.parseInt(attrs.getValue(0));
            int to = Integer.parseInt(attrs.getValue(1));
            int cost = Integer.parseInt(attrs.getValue(2));
            fromV.add(from);
            toV.add(to);
            costV.add(cost);
        }
    }

    public void characters(char ch[], int start, int len) {
    }

    public void endElement(String uri, String localName, String qName) {
    }

    public void endDocument() {
        MyPanel.minLongitude = minLongitude;
        MyPanel.minLatitude = minLatitude;
        MyPanel.iduri = iduri;
        MyPanel.longitudini = longitudini;
        MyPanel.latitudini = latitudini;
        MyPanel.from = fromV;
        MyPanel.to = toV;
        MyPanel.cost = costV;
    }
}
