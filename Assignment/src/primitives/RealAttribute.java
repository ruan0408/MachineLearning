package primitives;

/**
 * Created by gervasio on 25/04/15.
 */
public class RealAttribute implements Attribute {
    String name;
    int linePosition;
    int numReal;
    static boolean initializedMinimum;
    static boolean initializedMaximum;
    static int totalNumberOfRealAttributes = 0;
    static double[] minimumObservedValue;
    static double[] maximumObservedValue;

    public RealAttribute(String n, int pos, int numReal){
        name = n;
        linePosition = pos;
        this.numReal = numReal;
    }

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
        return true;
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }

    @Override
    public boolean isBoolean(){return false;}

    public int getNumReal(){return numReal;}

    public double getMinimumObservedValue(){return minimumObservedValue[numReal];}
    public double getMaximumObservedValue(){return maximumObservedValue[numReal];}

    public void setMinimumObservedValue(double value){
        if(!initializedMinimum){
            minimumObservedValue = new double[totalNumberOfRealAttributes];
            for(int i = 0; i < totalNumberOfRealAttributes; i++)
                minimumObservedValue[i] = Double.MAX_VALUE;
            initializedMinimum = true;
        }

        if(value < minimumObservedValue[numReal])
            minimumObservedValue[numReal] = value;
    }

    public void setMaximumObservedValue(double value){
        if(!initializedMaximum){
            maximumObservedValue = new double[totalNumberOfRealAttributes];
            for(int i = 0; i < totalNumberOfRealAttributes; i++)
                maximumObservedValue[i] = Double.MIN_VALUE;
            initializedMaximum = true;
        }

        if(value > maximumObservedValue[numReal])
            maximumObservedValue[numReal] = value;
    }
}
