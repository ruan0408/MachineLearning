package perceptron;

import org.junit.Test;

public class PerceptronTest {

	Perceptron p;

	@Test
	public void eightInputANDTest() throws Exception {
		p = new Perceptron("inputs/8inputAND.arff");
		while(p.runNextStep());
	}

	
	@Test
	public void eightInputORTest() throws Exception {
		p = new Perceptron("inputs/8inputOR.arff");
		while(p.runNextStep());
	}
		
//	@Test
//	public void eightInputNonLinearlySeparableTest() throws Exception {
//		p = new Perceptron("inputs/8inputNonLinearlySeparable.arff");
//		while(p.runNextStep());
//	}

}
