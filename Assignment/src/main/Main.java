package main;

import linear.LinearUnit;
import perceptron.Perceptron;
import visualization.StepByStepWeightedUnit;
import visualization.UnitVisualizer;

public class Main {

    public static void main(String[] args) {
    	System.out.println(args[0]);
        StepByStepWeightedUnit unit;
        UnitVisualizer viz;
        String path;
        int delay = 2000;
        if(args[0].equals("-l")) {
        	int epochs = Integer.parseInt(args[1]);
        	if(args[2].equals("-d")) {
        		delay = Integer.parseInt(args[3]);
        		path = args[4];
        	} else path = args[2];
        	
        	unit = new LinearUnit(path, 1E-3, 0.8, epochs);
        	viz = new UnitVisualizer(unit, delay);
            viz.initialize();
        } else if(args[0].equals("-p")) {
        	if(args[1].equals("-d")) {
        		delay = Integer.parseInt(args[2]);
        		path = args[3];
        	} else path = args[1];
        	unit = new Perceptron(path);
        	viz = new UnitVisualizer(unit, delay);
            viz.initialize();
        } else {
        	System.out.println("Usage: [-l epochs | -p] [-d delay] <pathToFile.arff>");
        }
    }
}
