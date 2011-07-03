package graph.editor;

import graph.util.Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

import automaton.State;
import automaton.StateImpl;

public class Vertex {
	int id;
	RectangularShape shape;
	String label;
	State state;
	
	private static final Point2D DELTA_LABEL = new Point(1, -1);
	
	private RectangularShape boder;
    public State getState()
    {
    	return this.state;
    }
	Vertex(RectangularShape rs, String label,State s) {
		this.shape = rs;
		this.label = label;
		this.state=new StateImpl(s.initial(),s.terminal());
		boder= new Ellipse2D.Double(0, 0, 40, 40);
	}
	
	
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}

	void draw(Graphics2D g2) {
		Shapes.moveShape(boder, (int)(shape.getCenterX()), (int)shape.getCenterY());
		g2.setStroke(new BasicStroke(5));
		
		if(this.state.initial()==true&&this.state.terminal()==false)
			{g2.setPaint(Color.BLACK);
		  
			} else if(this.state.initial()==true&&this.state.terminal()==true)
			{
				 g2.setPaint(Color.YELLOW);
			   
			}else if(this.state.initial()==false&&this.state.terminal()==true)
			{
				 g2.setPaint(Color.RED);
			     
			}else if(this.state.initial()==false&&this.state.terminal()==false)
			{
				 g2.setPaint(Color.BLUE);
			    
			}
	    
	     g2.fill(shape);
	     g2.draw(boder);
		
		if (label != null)
			g2.drawString(label, (int) (shape.getMaxX() + DELTA_LABEL.getX()),
					(int) (shape.getMinY() + DELTA_LABEL.getY()));
	}
	
}
