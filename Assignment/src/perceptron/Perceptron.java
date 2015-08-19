package perceptron;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSeparatorUI;

import primitives.Dataset;
import linear.MathFunctions;
import visualization.StepByStepWeightedUnit;

public class Perceptron implements StepByStepWeightedUnit{
	
	private List<Example> data;
	private double learningRate;
	private double[] weights;
	private Example example;
	private double weightedSum;
	private int output;
	private double[] futureWeights;
	private int index;
	private int count;
	private int steps;
	private long timeElapsed;
	
	public Perceptron(List<Example> data) {
		this.data = data;
		for(Example ex : data) ex.addAttribute(1);
		weights = new double[data.get(0).length()];
		futureWeights = new double[weights.length];
		learningRate = 0.1;
		Arrays.fill(weights, 0);
		Arrays.fill(futureWeights, 0);
		timeElapsed = steps = count = index = 0;
	}
	
	public Perceptron(String pathToFile) {
		Dataset ds = new Dataset(pathToFile);
		data = DataBuilder.build(ds.getAllTrainingExamples(), ds.getAllTargetValues());
		for(Example ex : data) ex.addAttribute(1);
		weights = new double[data.get(0).length()];
		futureWeights = new double[weights.length];
		learningRate = 0.1;
		Arrays.fill(weights, 0);
		Arrays.fill(futureWeights, 0);
		steps = count = index = 0;
	}
	
	public void setExample(Example example) {this.example = example;}
	public void setOutput(int o) {output = o;}
	public void setWeightedSum(double sum) {weightedSum = sum;}
	public void setWeights(double[] w) {for(int i = 0; i < weights.length; i++) weights[i] = w[i];}
	@Override public double[] getAttributes() {return example.toArrayOfDoubles();}
	@Override public double getOutput() {return output;}
	@Override public double getWeightedSum() {return weightedSum;}
	@Override public int getSize() {return weights.length;}
	@Override public double[] getWeights() {return weights;}
	@Override public double getLearningRate() {return learningRate;}
	
	@Override
	public boolean runNextStep() {
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		if(index == data.size()) index = 0;
		if(count != data.size()) {
			Example example = data.get(index);
			setWeights(futureWeights);
			setExample(example);			
			int sign = sign(example);
			if(!correctlyClassifies(example, sign)) {
				count = 0;
				updateWeights(example, -sign);
			} else count++;
			index++;
			steps++;
			endTime = System.currentTimeMillis();
			timeElapsed += endTime-startTime;
			return true;
		}
		System.out.println("Training has finished");
		printWeights();
		System.out.println("Number of steps necessary: "+steps);
		System.out.println("Time for training: "+timeElapsed+" ms");
		return false;
	}
	
	private int sign(Example example) {
		double sum = MathFunctions.dotProduct(example.toArrayOfDoubles(), weights);
		setWeightedSum(sum);
		if(sum > 0) {
			setOutput(1);
			return 1;
		} else {
			setOutput(0);
			return -1;
		}
	}
	
	private void updateWeights(Example example, int sign) {
		for(int i = 0; i < futureWeights.length; i++)
			futureWeights[i] += sign*learningRate*example.getAttribute(i);
	}
	
	private boolean correctlyClassifies(Example example, int sign) {
		if((example.getClassValue() == 1 && sign == 1) || 
				(example.getClassValue() == 0 && sign == -1))
			return true;
		
		return false;
	}
	
	private void printWeights() {
		System.out.println();
		for(int i = 0; i < weights.length; i++) 
			System.out.print("w"+i+": "+weights[i]+" ");
		System.out.println();
	}
}