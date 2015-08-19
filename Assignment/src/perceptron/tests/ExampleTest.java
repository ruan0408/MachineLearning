package perceptron.tests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import perceptron.DataBuilder;
import perceptron.Example;


public class ExampleTest {

	private List<Example> list;
	@Before
	public void setUp() throws Exception {
		list = DataBuilder.build("boolean_functions/and.txt");
		
	}
	
		

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getClassValueTest() {
		Example ex;
		ex = this.list.get(0);
		assertEquals("0 0 0", ex.toString());
		ex = this.list.get(1);
		assertEquals("0 1 0", ex.toString());
		ex = this.list.get(2);
		assertEquals("1 0 0", ex.toString());
		ex = this.list.get(3);
		assertEquals("1 1 1", ex.toString());
	}

}
