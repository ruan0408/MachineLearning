package primitives;

public interface Attribute{
    String name = null;
    int linePosition = 0;
    public abstract String getName();
    public abstract int getLinePosition();
    public abstract boolean isReal();
    public abstract boolean isDiscrete();
    public abstract boolean isBoolean();
}