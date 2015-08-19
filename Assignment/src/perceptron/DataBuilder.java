package perceptron;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBuilder {

	public static List<Example> build(String pathToFile) throws IOException {
		String line;
		List<Example> list = new ArrayList<Example>();
		
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
		while((line = br.readLine()) != null)
			list.add(new Example(line.trim()));
		
		br.close();
		return list;
	}
	
	public static List<Example> build(List<double[]> examples, List<Double> targetValues) {
		List<Example> list = new ArrayList<Example>();
		Example aux;
		int[] ints;
		
		int i = 0;
		for(double[] ex : examples)	{
			aux = new Example();
			ints = new int[ex.length-1];
			for(int j = 1 ; j < ex.length; j++) ints[j-1] = (int) ex[j];
			
			aux.setAttributes(ints);
			aux.setClassValue((int)targetValues.get(i++).doubleValue());
			list.add(aux);
		}
		return list;
	}
}
