package primitives;

import java.util.List;

/**
 * Created by gervasio on 25/04/15.
 */
public class DiscreteAttributeValue extends DiscreteAttribute {
    String value;
    public DiscreteAttributeValue(String n, String p, int pos, String v) {
        super(n,p, pos);
        value = v;
    }

    public DiscreteAttributeValue(String n, List<String> pv, int pos, String v){
        super(n,pv,pos);
        value = v;
    }

    public String getValue(){return value;}
}
