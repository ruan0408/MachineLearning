package primitives;

/**
 * Created by gervasio on 22/05/15.
 */
public class BooleanAttributeValue extends BooleanAttribute{
    int value;
    public BooleanAttributeValue(String name, int linePosition, int value) {
        super(name, linePosition);
        this.value = value;
    }

    int getValue(){return value;}
}
