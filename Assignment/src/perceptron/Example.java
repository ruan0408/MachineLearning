package perceptron;
import java.util.Arrays;

public class Example {

	private int[] attributes;
	private int classValue;
	
	public Example(){;}
	public Example(String attributes) {
		String[] aux = attributes.trim().split("\\s+");
		this.attributes = new int[aux.length-1];// have to desconsider the class value
		
		for(int i = 0 ; i < this.attributes.length; i++)
			this.attributes[i] = Integer.parseInt(aux[i]);
		
		this.classValue = Integer.parseInt(aux[aux.length-1]);
	}
	
	public void addAttribute(int value) { // inserts an attribute at the front
		int[] newAtt = new int[attributes.length+1];
		newAtt[0] = value;
		System.arraycopy(attributes, 0, newAtt, 1, attributes.length);
		attributes = newAtt;
	}
	
	public int length() {return attributes.length;}
	public int getClassValue() {return classValue;}
	public void setClassValue(int value) {classValue = value;}
	public int getAttribute(int i) {return attributes[i];}
	public void setAttributes(int[] attributes) {this.attributes = attributes;}
	
	public double[] toArrayOfDoubles() {
		double[] aux = new double[attributes.length];
		int i = 0;
		for(int x : attributes) aux[i++] = x;
		return aux;
	}
	
	public String toString() {
		return Arrays.toString(attributes).replace("[", "").replace("]", "")
				.replace(",", "").trim()+" "+classValue;
	}
}