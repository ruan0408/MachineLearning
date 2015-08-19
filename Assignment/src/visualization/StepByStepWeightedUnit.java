/** 
 * Generalization of the perceptron and the linear unit
 */
package visualization;
/**
 * @author ruan0408
 *
 */
public interface StepByStepWeightedUnit {
	
	/* 	Returns the weights that when combined with the current attributes, gives the
	 * 	current weighted sum.
	 */
	public double[] getWeights();
	
	/*	Returns the array of values of the current example being analysed*/
	public double[] getAttributes();
	
	/*	Returns the current weighted sum. Should be syncronized with the weights and attributes*/
	public double getWeightedSum();
	
	/*	Returns the current output of the unit*/
	public double getOutput();
	
	/*	Returns the number of inputs to the unit, counting any fake inputs necessary*/
	public int getSize();
	
	/*	Returns the current learning rate of the unit */
	public double getLearningRate();
	
	/* 	Runs the next step. If there is a next step, the visualizer will retrieve the data it need
	 * 	to print the screen. Returns false if there isn't a next step, that is, if the unit has
	 * 	finished it's training.
	 */
	public boolean runNextStep();
	
}
