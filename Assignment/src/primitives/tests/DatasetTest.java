package primitives.tests;

import junit.framework.TestCase;
import org.junit.Test;
import primitives.DataPoint;
import primitives.Dataset;
import primitives.Attribute;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatasetTest extends TestCase {

    @Test
    public void testRealRegexMatches() throws Exception{
        Pattern realPattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+real");
        String line = "@attribute acceleration real";
        assertTrue(realPattern.matcher(line).matches());
    }

    @Test
    public void testDiscreteRegexMatches() throws Exception{
        Pattern discretePattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+(\\{\\s*\\w+\\s*(,\\s*\\w+)*\\})");
        String line = "@attribute cylinders { 8, 4, 6, 3, 5}";
        assertTrue(discretePattern.matcher(line).matches());
    }

    @Test
    public void testThatCaptureGroupsWork() throws Exception{
        Pattern realPattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+real");
        Pattern discretePattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+(\\{\\s*\\w+\\s*(,\\s*\\w+)*\\})");
        String realLine = "@attribute acceleration real";
        String discreteLine = "@attribute cylinders { 8, 4, 6, 3, 5}";

        assertTrue(realPattern.matcher(realLine).groupCount() == 1);
        assertTrue(discretePattern.matcher(discreteLine).groupCount() == 3 );
    }

    @Test
    public void testInformationCapturedInRealIsCorrect() throws Exception{
        Pattern realPattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+real");
        String realLine = "@attribute acceleration real";
        Matcher realMatcher = realPattern.matcher(realLine);

        realMatcher.find();
        assertTrue(realMatcher.group(1).equals("acceleration"));

    }

    @Test
    public void testInformationCapturedInDiscreteIsCorrect() throws Exception{
        Pattern discretePattern = Pattern.compile("@attribute[\\s]+([\\w]+)[\\s]+(\\{\\s*\\w+\\s*(,\\s*\\w+)*\\})");
        String discreteLine = "@attribute cylinders { 8, 4, 6, 3, 5}";
        Matcher discreteMatcher = discretePattern.matcher(discreteLine);

        discreteMatcher.find();
        assertTrue(discreteMatcher.group(1).equals("cylinders"));
        assertTrue(discreteMatcher.group(2).equals("{ 8, 4, 6, 3, 5}"));
    }

    @Test
    public void testAnalyzeLineForReals() throws Exception {
        Dataset set = new Dataset("foo.txt");
        String line = "@attribute acceleration real";
        set.analyzeLine(line);
        Attribute attribute = set.getAttributes().get(0);
        assertTrue(attribute.getName().equals("acceleration"));
        assertTrue(attribute.getLinePosition() == 0);
        assertTrue(attribute.isReal());
    }

    @Test
    public void testAnalyzeLineForDiscrete() throws Exception {
        Dataset set = new Dataset("foo.txt");
        String line = "@attribute cylinders { 8, 4, 6, 3, 5}";
        set.analyzeLine(line);
        Attribute attribute = set.getAttributes().get(0);
        assertTrue(attribute.getName().equals("cylinders"));
        assertTrue(attribute.getLinePosition() == 0);
        assertTrue(attribute.isDiscrete());
    }

    @Test
    public void testAnalyzeLineForBoolean() throws Exception{
        Dataset dataset = new Dataset("foo.arff");
        String line = "@attribute  x01  {0,1}";
        dataset.analyzeLine(line);
        Attribute attribute = dataset.getAttributes().get(0);
        assertTrue(attribute.getName().equals("x01"));
        assertTrue(attribute.getLinePosition() == 0);
        assertTrue(attribute.isBoolean());
    }

    @Test
    public void testParseBooleanData() throws Exception{
        Dataset dataset = new Dataset("foo.arff");
        String line1 = "@attribute  x01  {0,1}";
        String line2 = "@attribute  x02  {0,1}";
        String line3 = "@attribute  target  {0,1}";
        String dataLine = "1,0,0";

        dataset.analyzeLine(line1);
        dataset.analyzeLine(line2);
        dataset.analyzeLine(line3);
        dataset.analyzeLine(dataLine);

        Attribute attribute = dataset.getAttributes().get(0);
        assertTrue(attribute.getName().equals("x01"));
        assertTrue(attribute.getLinePosition() == 0);
        assertTrue(attribute.isBoolean());

        attribute = dataset.getAttributes().get(1);
        assertTrue(attribute.getName().equals("x02"));
        assertTrue(attribute.getLinePosition() == 1);
        assertTrue(attribute.isBoolean());

        attribute = dataset.getAttributes().get(2);
        assertTrue(attribute.getName().equals("target"));
        assertTrue(attribute.getLinePosition() == 2);
        assertTrue(attribute.isBoolean());

        DataPoint dataPoint = dataset.getData().get(0);
        dataPoint.printSelf();

    }

    @Test
    public void testAnalyzeLine() throws Exception{
        Dataset set = new Dataset("foo.txt");

        String lineReal = "@attribute acceleration real";
        String lineDiscrete = "@attribute cylinders { 8, 4, 6, 3, 5}";

        set.analyzeLine(lineReal);
        set.analyzeLine(lineDiscrete);

        Attribute real = set.getAttributes().get(0);
        Attribute discrete = set.getAttributes().get(1);

        assertTrue(real.getName().equals("acceleration"));
        assertTrue(real.getLinePosition() == 0);
        assertTrue(real.isReal());

        assertTrue(discrete.getName().equals("cylinders"));
        assertTrue(discrete.getLinePosition() == 1);
        assertTrue(discrete.isDiscrete());
        assertTrue(set.getNumAttributes() == 2);
    }

    @Test
    public void testDiscardingIncompleteDataPoints() throws Exception{
        Dataset set = new Dataset("foo.txt");

        String lineReal = "@attribute acceleration real";
        String lineDiscrete = "@attribute cylinders { 8, 4, 6, 3, 5}";
        String faultyDataLine = "12.8,?";
        String dataLine = "12,8";
        String faultyLine2 = "1,2,3";

        set.analyzeLine(lineReal);
        set.analyzeLine(lineDiscrete);
        set.analyzeLine(faultyDataLine);
        set.analyzeLine(dataLine);
        set.analyzeLine(faultyLine2);

        Attribute real = set.getAttributes().get(0);
        Attribute discrete = set.getAttributes().get(1);

        assertTrue(real.getName().equals("acceleration"));
        assertTrue(real.getLinePosition() == 0);
        assertTrue(real.isReal());

        assertTrue(discrete.getName().equals("cylinders"));
        assertTrue(discrete.getLinePosition() == 1);
        assertTrue(discrete.isDiscrete());
        assertTrue(set.getNumAttributes() == 2);

        assertTrue(set.getData().size() == 1);
    }

    @Test
    public void testsDifferentDataSet() throws Exception{
        Dataset dataset = new Dataset("/Users/gervasiosantos/Exchange/Machine Learning/Project/inputs/autoMpg.arff");
    }

}