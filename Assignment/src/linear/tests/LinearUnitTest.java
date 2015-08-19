package linear.tests;

import linear.LinearUnit;
import linear.MathFunctions;
import org.junit.Test;

public class LinearUnitTest {

    /*@Test
    public void testWeightUpdate() throws Exception{
        LinearUnit l = new LinearUnit("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/justOne.arff");

        System.out.println("Original (random) weights:");

        double[] w = l.weights;
        for (int i = 0; i < w.length; i++) {
            System.out.print(w[i] + " ");
        }
        System.out.print("\n");

        double input[] = l.getExamples().get(0);
        System.out.println("Input:");
        for (int i = 0; i < input.length; i++) {
            System.out.print(input[i] + " ");
        }
        System.out.print("\n");

        double normal[] = l.getNormalizedInputs().get(0);

        System.out.println("Normalized Input:");
        for (int i = 0; i < normal.length; i++) {
            System.out.print(normal[i] + " ");
        }
        System.out.print("\n");

        System.out.println("Target: "+l.getTargetValues().get(0));
        System.out.print("Output: " + MathFunctions.dotProduct(w,normal));
        System.out.print("\n");

        l.trainGradientDescent(1);

        w = l.weights;

        System.out.println("New (trained) weights:");
        for (int i = 0; i < w.length; i++) {
            System.out.print(w[i] + " ");
        }
        System.out.print("\n");
        System.out.println("New Output: " + MathFunctions.dotProduct(w,input));
        System.out.print("\n");
    }
*/

    @Test
    public void testPerformance() throws Exception{
        LinearUnit l = new LinearUnit("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/autoMpg.arff",1E-3,0.8,2000);
        System.out.print("\n\n\n");
        l.evaluatePerformance();
        double[] w = l.weights;

        System.out.println("NUM EXAMPLES = " + l.examplesForTraining.size());

        System.out.println("New (trained) weights:");
        for (int i = 0; i < w.length; i++) {
            System.out.print(w[i] + " ");
        }
        System.out.print("\n");

        int dp = 0;

        double[] notNormal = l.examplesForValidation.get(dp);
        System.out.println("Non-normalized Input validation:");
        for (int i = 0; i < notNormal.length; i++) {
            System.out.print(notNormal[i] + " ");
        }
        System.out.print("\n");

        double [] normal = l.getNormalizedInputs().get(dp);
        System.out.println("Normalized Input validation:");
        for (int i = 0; i < normal.length; i++) {
            System.out.print(normal[i] + " ");
        }
        System.out.print("\n");
        System.out.println("Target: "+l.getTargetValues().get(dp));
        System.out.print("Output: " + MathFunctions.dotProduct(w,normal));

        System.out.print("\n\n");


        double[] notNormal2 = l.examplesForTraining.get(dp);
        System.out.println("Non-normalized Input training:");
        for (int i = 0; i < notNormal.length; i++) {
            System.out.print(notNormal2[i] + " ");
        }
        System.out.print("\n");
        double [] normal2 = l.testExamples.get(dp);
        System.out.println("Normalized Input training:");
        for (int i = 0; i < normal.length; i++) {
            System.out.print(normal2[i] + " ");
        }
        System.out.print("\n");
        System.out.println("Target: "+l.targetValuesForTraining.get(dp));
        System.out.print("Output: " + MathFunctions.dotProduct(w,normal2));


        System.out.print("\n\n\n\n");
    }

}
