package primitives;

/**
 * Created by gervasio on 25/04/15.
 */
public class RealAttributeValue extends RealAttribute {
    double value;
    public RealAttributeValue(String n, int pos, int numReal, double v) {
        super(n, pos, numReal);
        value = v;
        super.setMaximumObservedValue(v);
        super.setMinimumObservedValue(v);
    }
    public double getValue(){return value;}

    public double getNormalizedValue(){
        double min = super.getMinimumObservedValue();
        double max = super.getMaximumObservedValue();
        return (value - min)/(max-min);
    }
}
