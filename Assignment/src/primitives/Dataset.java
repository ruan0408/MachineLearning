package primitives;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gervasio on 25/04/15.
 */
public class Dataset {
    List<Attribute> attributes = new LinkedList<Attribute>();
    List<DataPoint> data = new LinkedList<DataPoint>();
    Integer numAttributes = new Integer(0);
    Integer numDiscreteAttributes = new Integer(0);
    Integer numBooleanAttributes = new Integer(0);
    Integer numRealAttributes = new Integer(0);
    Integer numDataPoints = new Integer(0);

    public List<DataPoint> getData(){return data;}
    public int getNumDataPoints(){return numDataPoints;}
    public int getNumAttributes(){return numAttributes;}
    public int getNumDiscreteAttributes(){return numDiscreteAttributes;}
    public int getNumRealAttributes(){return  numRealAttributes;}
    public List<Attribute> getAttributes(){return attributes;}
    public Iterator<DataPoint> dataPoints(){
        return data.iterator();
    }

    private static Pattern realPattern = Pattern.compile("@attribute[\\s]+(.+)[\\s]+real");
    private static Pattern discretePattern = Pattern.compile("@attribute[\\s]+(.+)[\\s]+(\\{\\s*\\w+\\s*(,\\s*\\w+)*\\})");
    private static Pattern booleanPattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+\\{[01],[01]\\}");

    public Dataset(String pathToFile){
        try {
            processInputFile(pathToFile);
            System.out.print("Processed file "+pathToFile+"\n");
        } catch (IOException e) {
            System.out.println("It was not possible to open file " + pathToFile);
        }
    }

    private void processInputFile(String pathToFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line;
        while ((line = reader.readLine()) != null){
            analyzeLine(line);
        }
    }

    public void analyzeLine(String line){
        Matcher realMatcher = realPattern.matcher(line);
        Matcher discreteMatcher = discretePattern.matcher(line);
        Matcher booleanMatcher = booleanPattern.matcher(line);
        if(line.matches("^\\s*$")) return; //ignores blank lines
        if(line.matches("^%.*")) return ; /*line is a comment and should be ignored.*/
        if(line.matches("@data")) return; //line signifies the begining of the data. Can be ingnored
        if(line.matches("@relation\\s+\\w+")) return; //indicates the name of the relation. Irrelevant.
        if(realMatcher.find()) { //line corresponds to the description of a real attribute
            attributes.add(new RealAttribute(realMatcher.group(1), numAttributes++, numRealAttributes++));
            RealAttribute.totalNumberOfRealAttributes++;
        }
        else  if(booleanMatcher.find()){
            attributes.add(new BooleanAttribute(booleanMatcher.group(1), numAttributes++));
            numBooleanAttributes++;
        }
        else if(discreteMatcher.find()) { //line corresponds to the description of a discrete attribute
            attributes.add(new DiscreteAttribute(discreteMatcher.group(1), discreteMatcher.group(2), numAttributes++));
            numDiscreteAttributes++;
        }
        else { //line corresponds to a data point
            processDataPoint(line);
            numDataPoints++;
        }
    }

    void processDataPoint(String line){
        DataPoint data = new DataPoint(numAttributes);
        String[] values = line.split(",");
        try {
            if(values.length != numAttributes){
                throw new Exception("Incorrect number of attributes detected. Data point discarded.");
            }
            for (Attribute attribute : attributes) {
                if (attribute.isReal()) {
                    double value = Double.parseDouble(values[attribute.getLinePosition()]);
                    RealAttribute realAttribute = (RealAttribute) attribute;
                    data.addRealAttribute(attribute.getName(), attribute.getLinePosition(), realAttribute.getNumReal(), value);
                } else if (attribute.isDiscrete()) {
                    String value = values[attribute.getLinePosition()];
                    if(value.contains("?")) {
                        throw new Exception("Unknown value for attribute. Data point discarded.");
                    }
                    DiscreteAttribute da = (DiscreteAttribute) attribute;
                    data.addDiscreteAttribute(da.getName(), da.getPossibleValues(), da.getLinePosition(), value);
                } else if (attribute.isBoolean()) {
                    int value = Integer.parseInt(values[attribute.getLinePosition()]);
                    data.addBooleanAttribute(attribute.getName(), attribute.getLinePosition(), value);
                }
            }
            this.data.add(data);
        } catch (Exception e){
            System.out.println("Problem while parsing data point. Data point discarded.");
            //e.printStackTrace();
        }
    }

    public int getNecessaryNumberOfStates(){
        List<Attribute> attributes = this.getAttributes();
        int totalStates = 0;
        for(int i = 0; i < attributes.size(); i++){
            Attribute attribute = attributes.get(i);
            if(attribute.isDiscrete()) {
                DiscreteAttribute da = (DiscreteAttribute) attribute;
                totalStates += da.getPossibleValues().size();
            }
            else{
                totalStates++;
            }
        }
        return totalStates;
    }

    public List<double[]> getAllTrainingExamples(){
        List<double[]> examples = new LinkedList<double[]>();
        int states = this.getNecessaryNumberOfStates();
        for(DataPoint dataPoint : this.getData()){
            double[] example = dataPoint.getInput(states);
            examples.add(example);
        }
        return examples;
    }

    public List<double[]> getAllTrainingExamplesNormalized(){
        List<double[]> examples = new LinkedList<double[]>();
        int states = this.getNecessaryNumberOfStates();
        for(DataPoint dataPoint : this.getData()){
            double[] example = dataPoint.getNormalizedInput(states);
            examples.add(example);
        }
        return examples;
    }

    public List<Double> getAllTargetValues(){
        List<Double> targets = new LinkedList<Double>();
        for (DataPoint dataPoint : this.getData()){
            Double target = dataPoint.getTargetValue();
            targets.add(target);
        }
        return targets;
    }

}

//@attribute[\s]+[\w]+[\s]+(real|{\s*\w+\s*(,\s*\w+)*})