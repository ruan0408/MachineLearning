package visualization.tests;
import static org.junit.Assert.*;
import linear.LinearUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import perceptron.Perceptron;
import visualization.UnitVisualizer;


public class UnitVisualizerTest {

	private UnitVisualizer v;
	
	@Before public void setUp() throws Exception {}
	@After public void tearDown() throws Exception {}


	@Test
	public void eightInputAndPerceptronTest() throws Exception {
		v = new UnitVisualizer(new Perceptron("inputs/8inputAND.arff"), 1);
		v.initialize();
	}

	
//	@Test
//	public void eightInputOrPerceptronTest() throws Exception {
//		v = new UnitVisualizer(new Perceptron("inputs/8inputOR.arff"), 1);
//		v.initialize();
//	}
	
//	@Test
//	public void eightInputNonLinearlySeparablePerceptronTest() throws Exception {
//		v = new UnitVisualizer(new Perceptron("inputs/8inputNonLinearlySeparable.arff"), 1);
//		v.initialize();
//	}
	

	@Test
	public void initializeLinearUnitTest() throws Exception {
		v = new UnitVisualizer(new LinearUnit("inputs/autoMpg.arff", 1E-3, 0.8, 2000), 0);
		v.initialize();
	}
}
