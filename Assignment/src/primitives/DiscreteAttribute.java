package primitives;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gervasio on 25/04/15.
 */
public class DiscreteAttribute implements Attribute{
    String name;
    int linePosition;
    List<String> possibleValues;

    public DiscreteAttribute(String n, String pv, int pos){
        name = n;
        linePosition = pos;

        pv = pv.replaceAll("\\s+", ""); //removes blank spaces
        pv = pv.replace("{",""); //replaces left bracket
        pv = pv.replace("}",""); //replaces right bracket

        String[] valuesAsStrings = pv.split(",");
        possibleValues = Arrays.asList(valuesAsStrings);
    }

    public DiscreteAttribute(String n, List<String> pv, int pos){
        name = n;
        possibleValues = pv;
        linePosition = pos;
    }

    @Override
    public boolean isBoolean(){return false;}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLinePosition() {
        return linePosition;
    }

    @Override
    public boolean isReal() {
        return false;
    }

    @Override
    public boolean isDiscrete() {
        return true;
    }

    public List<String> getPossibleValues(){return possibleValues;}
}
