package perceptron.tests;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import perceptron.DataBuilder;
import perceptron.Example;


public class DataBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		List<Example> list = DataBuilder.build("boolean_functions/and.txt");
		for(Example ex : list)
			assertEquals(2, ex.length());
		assertEquals(4, list.size());
	}

}
