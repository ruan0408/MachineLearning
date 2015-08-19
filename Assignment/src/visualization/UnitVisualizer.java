package visualization;

import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.*;

@SuppressWarnings("serial")
public class UnitVisualizer extends JPanel {

	private final int 		ARR_SIZE = 4;
	private final String	MAX_LABEL = "#######";
	
	private StepByStepWeightedUnit unit;
	private int delay;
	private JLabel[] inputLabels;
	private JLabel[] weightLabels;
	private JLabel outputLabel;
	private JLabel sumLabel;
	private JLabel endLabel;
	private Ellipse2D[] inputCircles;
	private Ellipse2D sumCircle;
	private Ellipse2D thresholdCircle;
	private Ellipse2D outputCircle;
	private JFrame window;
	
	public UnitVisualizer(StepByStepWeightedUnit unit, int delay) {
		//super("Unit Visualization");
		//super.setResizable(true);
		this.unit = unit;
		this.delay = delay;
		window = new JFrame("Unit Visualization");
		window.setResizable(true);
		window.setSize(1000, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
	}
	
	public void initialize() {
		createMainPanel();
		createCircles();
		createLabels();
		
		window.pack();
		setVisible(true);
		window.setVisible(true);
		
		try {
			run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void run() throws InterruptedException {
		while(unit.runNextStep()) {
			double[] weights	= 	unit.getWeights();
			double[] example 	= 	unit.getAttributes();
			double output 		= 	unit.getOutput();
			double weightedSum 	=	unit.getWeightedSum();
			
			//NumberFormat f = new DecimalFormat("0.######E0");
//			System.out.println("e:"+Arrays.toString(example));
//			System.out.println("w:"+Arrays.toString(weights));
//			System.out.println("s:"+weightedSum);
//			System.out.println("o:"+output);
			NumberFormat f = new DecimalFormat("#0.00000");
			for(int i = 0; i < inputLabels.length; i++) {
				weightLabels[i].setText(f.format(weights[i])+"");
				inputLabels[i].setText(f.format(example[i])+"");
			}
			
			outputLabel.setText(f.format(output)+"");
			sumLabel.setText(f.format(weightedSum)+"");
			Thread.sleep(delay);
		}
		endLabel.setText("Training has finished");
		System.out.println("Training has finished");
		
		Thread.sleep(10000);
	}
		
	private void createMainPanel() {
		//mainPanel = new JPanel();
		setLayout(null);
		setSize(new Dimension(1000, unit.getSize()*100));
		setBackground(Color.WHITE);
		setPreferredSize(getSize());
		window.add(new JScrollPane(this));
	}
	
	private void createCircles() {
		int n = unit.getSize();
		inputCircles = new Ellipse2D[n];
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int smallRadio = 15;
		int bigRadio = 25;
		
		for(int i = 0; i < n; i++) {
			int yCoord = (int)((i)*1.0*panelHeight/n+3*smallRadio);
			inputCircles[i] = new Ellipse2D.Double(30, yCoord, 2*smallRadio, 2*smallRadio);
		}
		
		sumCircle = new Ellipse2D.Double(panelWidth/2-bigRadio, panelHeight/2-bigRadio, 2*bigRadio, 2*bigRadio);
		thresholdCircle = new Ellipse2D.Double(3*panelWidth/4-bigRadio, panelHeight/2-bigRadio, 2*bigRadio, 2*bigRadio);
		outputCircle = new Ellipse2D.Double(panelWidth-2*smallRadio-30, panelHeight/2-smallRadio, 2*smallRadio, 2*smallRadio);
	}
	
	private void createLabels() {
		int n = unit.getSize();
		weightLabels = new JLabel[n];
		inputLabels = new JLabel[n];
		
		outputLabel = new JLabel(MAX_LABEL);
		outputLabel.setSize(outputLabel.getPreferredSize());
		outputLabel.setLocation((int)outputCircle.getCenterX(), (int)outputCircle.getCenterY());
		add(outputLabel);

		sumLabel = new JLabel(MAX_LABEL);
		sumLabel.setSize(sumLabel.getPreferredSize());
		int xCoord = ((int)sumCircle.getMinX()+(int)thresholdCircle.getMinX())/2 - 10;
		sumLabel.setLocation(xCoord, (int)sumCircle.getCenterY());
		add(sumLabel);
		
		endLabel = new JLabel();
		endLabel.setSize(200, 20);;
		xCoord = ((int)sumCircle.getMinX()+(int)thresholdCircle.getMinX())/2 - 30;
		endLabel.setLocation(xCoord , (int)sumCircle.getMinY()-50);
		add(endLabel);
		
		JLabel inputLabel, weightLabel;
		Ellipse2D cir;
		for(int i = 0; i < weightLabels.length; i++) {
			weightLabels[i] = new JLabel(MAX_LABEL);
			inputLabels[i] = new JLabel(MAX_LABEL);
			inputLabel = inputLabels[i];
			weightLabel = weightLabels[i];
			cir = inputCircles[i];
			
			weightLabel.setSize(weightLabel.getPreferredSize());
			weightLabel.setLocation((int)cir.getMaxX()+10, (int)cir.getMinY());
			add(weightLabel);
			
			inputLabel.setSize(inputLabel.getPreferredSize());
			inputLabel.setLocation((int)cir.getMinX(), (int)cir.getMinY()-40);
			add(inputLabel);
		}
	}
	
	@Override
    public Dimension getPreferredSize()	{
        int size = unit.getSize()*100;
        return new Dimension(1000, size);
    }
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//super.paintComponent(g2d);
		g2d.setPaint( Color.black );
		g2d.draw(sumCircle);
		g2d.draw(thresholdCircle);
		g2d.draw(outputCircle);
		
		int destX, destY, origX, origY, midX, midY, i;
		destX = (int) sumCircle.getX();
		destY = (int) sumCircle.getCenterY();
		i = 0;
		for(Ellipse2D cir : inputCircles) {
			origX = (int) cir.getMaxX();
			origY = (int) cir.getCenterY();
			midX = (int) (getWidth()/4.0);
			midY = origY;
			g2d.draw(cir);
			g2d.draw(new Line2D.Double(origX, origY, midX, midY));
			drawArrow(g2d, midX, midY, destX, destY);
			g2d.drawString("x"+i, (int)cir.getCenterX()-5, (int)cir.getCenterY()+5);
			i++;
		}
		
		drawArrow(g2d, (int)sumCircle.getMaxX(), (int)sumCircle.getCenterY(), 
				(int)thresholdCircle.getMinX(), (int)thresholdCircle.getCenterY());
		
		drawArrow(g2d, (int)thresholdCircle.getMaxX(), (int)thresholdCircle.getCenterY(), 
				(int)outputCircle.getMinX(), (int)outputCircle.getCenterY());
		
	}
	
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
}