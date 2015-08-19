Implementation of Perceptron and Linear Unit in Java for Machine Learning and Numerical Prediction.

To compile and create the jar file, do:
>ant jar
This will generate the file project.jar
Now, to actually run, do:
>java -jar project.jar [-l epochs | -p] [-d delay] <pathToFile.arff>

-l: run the linear unit
epochs: maximum number of iterations of the linear unit
-p: run the perceptron
-d: to set the delay
delay: time in millesecons to be used as delay in the visualization
pathToFile.arff: path to the data (needs to be in .arff format). 

e.g: 	java -jar project.jar -p -d 1000 inputs/8inputAND.arff
		java -jar project.jar -l 2000 inputs/autoMpg.arff

