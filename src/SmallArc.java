import java.util.Comparator;

class ArcComparator implements Comparator<SmallArc> {

    public int compare(SmallArc arc1, SmallArc arc2) {
        if (arc1.valoare > arc2.valoare)
            return 1;
        else if (arc1.valoare < arc2.valoare)
            return -1;
        return 0;
    }
}
public class SmallArc {
    public Integer nodeNr;
    public Integer valoare;
    SmallArc(){

    }

    public SmallArc(Integer nodeNr, Integer valoare) {
        this.nodeNr = nodeNr;
        this.valoare = valoare;
    }
}
