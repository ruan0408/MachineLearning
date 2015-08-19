package primitives.tests;

import org.junit.Test;
import primitives.Attribute;
import primitives.DataPoint;
import primitives.Dataset;

import static org.junit.Assert.*;

public class DataPointTest {

    @Test
    public void testGetInputWithBools() throws Exception{
        Dataset d = new Dataset("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/bool_test.arff");
        System.out.println("BOOLEAN ATTRIBUTES");
        for(DataPoint dp : d.getData()) {
            dp.printSelf();
            double[] input = dp.getInput(d.getNecessaryNumberOfStates());
            System.out.println("Non-normalized input:");
            for (int i = 0; i < input.length; i++) {
                System.out.print(input[i] + " ");
            }
            System.out.println("Target: " + dp.getTargetValue());
            System.out.print("\n");
        }
    }

    @Test
    public void testGetInput() throws Exception {
        Dataset d = new Dataset("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/test.arff");
        System.out.println("NUM ATTS = " + d.getNumAttributes());

        for(Attribute attribute : d.getAttributes())
            System.out.println(attribute.getName());
        System.out.println("VARIOUS ATTRIBUTES");

        for(DataPoint dp : d.getData()) {
            dp.printSelf();
            double[] input = dp.getInput(d.getNecessaryNumberOfStates());
            double[] normalized = dp.getNormalizedInput(d.getNecessaryNumberOfStates());
            System.out.println("Non-normalized input:");
            for (int i = 0; i < input.length; i++) {
                System.out.print(input[i] + " ");
            }
            System.out.print("\n");

            System.out.println("\nNormalized input:");
            for (int i = 0; i < normalized.length; i++) {
                System.out.print(normalized[i] + " ");
            }
            System.out.print("\n");
        }
    }

    @Test
    public void testGetTargetValue() throws Exception {
        Dataset d = new Dataset("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/justOne.arff");
        for(DataPoint dp : d.getData()) {
            dp.printSelf();
            System.out.println("Target: " + dp.getTargetValue());
        }
    }
}