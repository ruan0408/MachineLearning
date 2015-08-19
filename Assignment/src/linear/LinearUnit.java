package linear;

import primitives.*;
import visualization.StepByStepWeightedUnit;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by gervasio on 08/05/15.
 */
public class LinearUnit implements StepByStepWeightedUnit{
    public double[] weights;
    int size;
    double learningRate = 1E-3;
    boolean trained;
    double trainingSplit = 0.9;
    int epochs = 2000;

    private List<double[]> examples;
    public List<double[]> examplesForTraining;
    public List<double[]> examplesForValidation;

    private List<double[]> normalizedInputs;
    public List<double[]> testExamples;
    public List<double[]> validationExamples;

    private List<Double> targetValues;
    public List<Double> targetValuesForTraining;
    public List<Double> targetValuesForValidation;

    double[] currentInput;
    double currentOutput;
    double currentWeightedSum;
    int currentIteration;

    public List<double[]> getExamples(){return examples;}
    public List<double[]> getNormalizedInputs(){return normalizedInputs;}
    public List<Double> getTargetValues(){return targetValues;}

    public double[] getWeights(){return weights;}
    public int getSize(){return size;}
    public double getLearningRate(){return learningRate;}
    public double[] getAttributes(){return currentInput;}
    public double getOutput(){return currentOutput;}
    public double getWeightedSum(){return currentWeightedSum;}

    private double delta_w[];
    private int currentIndex = 0;


    public LinearUnit(String pathToDataset, double learningRate, double trainingSplit, int epochs){
        Dataset dataset = new Dataset(pathToDataset);

        size = dataset.getNecessaryNumberOfStates();
        weights = new double[size];
        examples = dataset.getAllTrainingExamples();
        normalizedInputs = dataset.getAllTrainingExamplesNormalized();
        targetValues = dataset.getAllTargetValues();
        trained = false;
        this.epochs = epochs;

        for(int i = 0; i < size; i++){
            weights[i] = Math.random()/100; //initializes the weights to a small random number between 0 and 0.01
        }

        delta_w = new double[size];

        this.trainingSplit = trainingSplit;
        this.learningRate = learningRate;
        currentIteration = 0;
        setTestAndValidationExamples(); //divides the input data between training examples (90%) and validation
                                                    //examples (10%)
    }

    public void setTestAndValidationExamples() {
        int sizeTrainingSet = (int) (normalizedInputs.size()*trainingSplit);
        testExamples = new LinkedList<double[]>();
        validationExamples = new LinkedList<double[]>();
        examplesForTraining =  new LinkedList<double[]>();
        targetValuesForTraining = new LinkedList<Double>();
        targetValuesForValidation = new LinkedList<Double>();
        Random random = new Random();

        for (int i = 0; i < sizeTrainingSet;i++){
            int randomPosition = Math.abs(random.nextInt())%normalizedInputs.size();
            testExamples.add(normalizedInputs.remove(randomPosition));
            targetValuesForTraining.add(targetValues.remove(randomPosition));
            examplesForTraining.add(examples.remove(randomPosition));
        }
        validationExamples = normalizedInputs;
        targetValuesForValidation = targetValues;
        examplesForValidation = examples;
    }

    public boolean runNextStep(){
        if(currentIteration > epochs) {
            trained = true;
            return false;
        }

        if(currentIndex < testExamples.size()) {
            currentInput = testExamples.get(currentIndex);
            double currentTarget = targetValuesForTraining.get(currentIndex);
            currentOutput = currentWeightedSum = MathFunctions.dotProduct(currentInput, weights);
            for (int i = 0; i < size; i++) {
                try {
                    delta_w[i] += learningRate * (currentTarget - currentOutput) * currentInput[i];
                }catch (Exception e) {
                    System.out.println("CURRENT INDEX = " + currentIndex);
                }
            }
            currentIndex++;
        }

        else{
            weights = MathFunctions.add(weights, delta_w);
            currentIteration++;
            for(int i = 0; i < size; i++)
                delta_w[i] = 0;
            currentIndex = 0;
        }

        return true;
    }

    /*public boolean runNextStep(){
         if(currentIteration > epochs) {
             trained = true;
             return false;
         }

        double delta_w[] = new double[size];
        for (int d = 0; d < testExamples.size(); d++){
            currentInput = testExamples.get(d);
            currentOutput = currentWeightedSum = MathFunctions.dotProduct(currentInput,weights);
            for (int i = 0; i < size; i++){
                delta_w[i] += learningRate*(targetValuesForTraining.get(d) - currentOutput)*currentInput[i];
            }
        }
        weights = MathFunctions.add(weights,delta_w);
        currentIteration++;

        return true;
    }*/


    public void evaluatePerformance(){
        int n = validationExamples.size();
        double meanAbsoluteError = 0;
        double relativeAbsoluteError = 0;
        double meanSquaredError = 0;
        double rootMeanSquaredError = 0;

        if(!trained)
            while (runNextStep()){}

        double averageTargetValue = 0;

        for(Double target : this.targetValues){
            averageTargetValue += target/n;
        }

        double sumOfDifferenceFromAverage = 0;
        double sumOfAbsoluteDifferences = 0;
        double sumOfSquaredDifferences = 0;

        int j = 0;
        for(double[] input : this.validationExamples){
            double target = targetValuesForValidation.get(j++);
            double output = MathFunctions.dotProduct(weights,input);
            sumOfDifferenceFromAverage += Math.abs(target-averageTargetValue);
            sumOfAbsoluteDifferences += Math.abs(output-target);
            sumOfSquaredDifferences += (output-target)*(output-target);
        }

        meanAbsoluteError = sumOfAbsoluteDifferences/n;
        relativeAbsoluteError = sumOfAbsoluteDifferences/sumOfDifferenceFromAverage;
        meanSquaredError = sumOfSquaredDifferences/n;
        rootMeanSquaredError = Math.sqrt(meanSquaredError);

        System.out.println("Mean Absolute Error = " + meanAbsoluteError);
        System.out.println("Relative Absolute Error = " + relativeAbsoluteError);
        System.out.println("Mean Squared Error = " + meanSquaredError);
        System.out.println("Root Mean Squared Error = " + rootMeanSquaredError);
        System.out.print("\n");
    }

}
