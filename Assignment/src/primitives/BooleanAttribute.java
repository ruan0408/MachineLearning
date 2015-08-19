package primitives;

/**
 * Created by gervasio on 22/05/15.
 */
public class BooleanAttribute implements Attribute {
    String name;
    int linePosition;

    public BooleanAttribute(String name, int linePosition){
        this.name = name;
        this.linePosition = linePosition;
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
    public boolean isBoolean(){return true;}

    @Override
    public boolean isReal() {
        return false;
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }
}
