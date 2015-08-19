package linear;

/**
 * Created by gervasio on 08/05/15.
 */
public class MathFunctions {
    public static double dotProduct(double[] x, double[] y){
        double value = 0;
        for(int i = 0; i < x.length; i++)
            value += x[i]*y[i];
        return value;
    }

    public static double[] add(double[] x, double[] y){
        double[] z = new double[x.length];
        for(int i = 0; i < x.length; i++)
            z[i] = x[i]+y[i];
        return z;
    }
}
