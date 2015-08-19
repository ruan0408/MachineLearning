package primitives;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gervasio on 25/04/15.
 */
public class DataPoint {
    Attribute[] attributes;

    public List<Attribute> getAttributes(){return Arrays.asList(attributes);}

    public DataPoint(Integer size) {
        this.attributes = new Attribute[size];
    }

    void addRealAttribute(String attributeName, int attributePos, int numReal, double attributeValue){
        attributes[attributePos] = new RealAttributeValue(attributeName,attributePos,numReal,attributeValue);
    }

    void addDiscreteAttribute(String attributeName, List<String> allowedValues, int attPos, String value){
        attributes[attPos] = new DiscreteAttributeValue(attributeName,allowedValues,attPos,value);
    }

    void addBooleanAttribute(String attributeName, int attributePos, int value){
        attributes[attributePos] = new BooleanAttributeValue(attributeName,attributePos,value);
    }

    public void printSelf(){
        for (int i = 0; i < attributes.length; i++){
            if(attributes[i].isDiscrete()) {
                DiscreteAttributeValue dav = (DiscreteAttributeValue) attributes[i];
                System.out.println(dav.getName() + " = "+dav.getValue());
            }
            else if(attributes[i].isReal()){
                RealAttributeValue rav = (RealAttributeValue) attributes[i];
                System.out.print(rav.getName() + " = " + rav.getValue());
                System.out.print(" (max val = "+ rav.getMaximumObservedValue()+" )");
                System.out.println(" (min val = " + rav.getMinimumObservedValue() + " )");
            }
            else{
                BooleanAttributeValue bav = (BooleanAttributeValue) attributes[i];
                System.out.println(bav.getName() + " = "+bav.getValue());
            }
        }
        System.out.print("\n");
    }

    public double[] getInput(int size){
        double[] inputs = new double[size];
        List<Attribute> attributes = this.getAttributes();
        inputs[0] = 1;
        int j = 1;
        for(int i = 0; i < attributes.size() - 1; i++){
            Attribute att = attributes.get(i);
            if (att.isReal()){
                RealAttributeValue rav = (RealAttributeValue) att;
                inputs[j] = rav.getValue();
                j++;
            }
            else if (att.isDiscrete()){
                DiscreteAttributeValue dav = (DiscreteAttributeValue) att;
                int index = dav.getPossibleValues().indexOf(dav.getValue());
                inputs[j+index] = 1;
                j += dav.getPossibleValues().size();
            }
            else if(att.isBoolean()){
                BooleanAttributeValue bav = (BooleanAttributeValue) att;
                inputs[j] = bav.getValue();
                j++;
            }
        }
        return  inputs ;
    }

    public double[] getNormalizedInput(int size){
        double[] normalizedInputs = new double[size];
        List<Attribute> attributes = this.getAttributes();
        normalizedInputs[0] = 1;
        int j = 1;
        for(int i = 0; i < attributes.size() - 1; i++){
            Attribute att = attributes.get(i);
            if (att.isReal()){
                RealAttributeValue rav = (RealAttributeValue) att;
                normalizedInputs[j] = rav.getNormalizedValue();
                j++;
            }
            else if(att.isBoolean()){
                BooleanAttributeValue bav = (BooleanAttributeValue) att;
                normalizedInputs[j] = bav.getValue();
                j++;
            }
            else {
                DiscreteAttributeValue dav = (DiscreteAttributeValue) att;
                int index = dav.getPossibleValues().indexOf(dav.getValue());
                normalizedInputs[j+index] = 1;
                j += dav.getPossibleValues().size();
            }
        }
        return  normalizedInputs;
    }

    public double getTargetValue(){
        int i = this.attributes.length - 1;
        double target = 0;
        if(attributes[i].isReal()){
            RealAttributeValue rav = (RealAttributeValue) attributes[i];
            target =  rav.getValue();
        }
        else if(attributes[i].isBoolean()){
            BooleanAttributeValue bav = (BooleanAttributeValue) attributes[i];
            target =  bav.getValue();
        }
        return target;
    }

}
