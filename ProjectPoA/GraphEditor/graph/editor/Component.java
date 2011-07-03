package graph.editor;

import graph.util.Shapes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import javax.swing.JPanel;



public class Component extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void paintComponent(Graphics g) 
	{
        RectangularShape vertexSample = new Ellipse2D.Double(10,10, 30, 30);
        Ellipse2D boder= new Ellipse2D.Double(0, 0, 40, 40);
		Graphics2D dr=(Graphics2D)g;
		
		dr.setPaint(Color.BLACK);
		dr.fill(vertexSample);
		Shapes.moveShape(boder, (int)(vertexSample.getCenterX()), (int)vertexSample.getCenterY());
		dr.setStroke(new BasicStroke(5));
		dr.draw(boder);
		dr.drawString("Initial State", 50, 25);
		
		dr.setPaint(Color.RED);
		Shapes.moveShape(vertexSample,150,25);
		dr.fill(vertexSample);		
		Shapes.moveShape(boder, (int)(vertexSample.getCenterX()), (int)vertexSample.getCenterY());
		dr.setStroke(new BasicStroke(5));
		dr.draw(boder);
		dr.drawString("Terminal State", 180, 25);
		
		dr.setPaint(Color.YELLOW);
		Shapes.moveShape(vertexSample,300,25);
		dr.fill(vertexSample);		
		Shapes.moveShape(boder, (int)(vertexSample.getCenterX()), (int)vertexSample.getCenterY());
		dr.setStroke(new BasicStroke(5));
		dr.draw(boder);
		dr.drawString("Initial&Terminal", 325, 25);
		
		dr.setPaint(Color.BLUE);
		Shapes.moveShape(vertexSample,450,25);
		dr.fill(vertexSample);		
		Shapes.moveShape(boder, (int)(vertexSample.getCenterX()), (int)vertexSample.getCenterY());
		dr.setStroke(new BasicStroke(5));
		dr.draw(boder);
		dr.drawString("Normal State", 480, 25);
		
	}
	public Component()
	{
		this.setPreferredSize(new Dimension(600,50));
		this.repaint();
	}
}
