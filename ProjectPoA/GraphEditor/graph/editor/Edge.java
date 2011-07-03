package graph.editor;


import graph.util.Shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

class Edge {
	static int number=0;
	private int id=0;
	Vertex v1;
	Vertex v2;
	List<RectangularShape> jointPoints = new ArrayList<RectangularShape>();
	private static final int JOINT_POINT_SIZE = 4;
	private static final int ARROW_SIZE = 10;
	private static final double ARROW_ANGLE = Math.PI / 6;
	JTextField textField = null;
    private boolean state=false;
	Edge(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
		id=number++;
		
	}
   int getID()
   {
	   return this.id;
   }
   
   void setState(boolean s)
   {
	   this.state=s;
   }
   boolean getState()
   {
	   return this.state;
   }
	void addJointPoint() {
		jointPoints
				.add(new Ellipse2D.Double((int) v2.shape.getCenterX(),
						(int) v2.shape.getCenterY(), JOINT_POINT_SIZE,
						JOINT_POINT_SIZE));
	}

	void removeJointPoint(RectangularShape jp) {
		jointPoints.remove(jp);
	}

	RectangularShape getJointPoint(int x, int y) {
		return Shapes.getShape(jointPoints, x, y);
	}

	boolean contains(int x, int y, double precision) {
		double x1 = v1.shape.getCenterX();
		double y1 = v1.shape.getCenterY();
		for (RectangularShape jp : jointPoints) {
			double x2 = jp.getCenterX();
			double y2 = jp.getCenterY();
			if (Line2D.ptLineDist(x1, y1, x2, y2, x, y) < precision)
				return true;
			x1 = x2;
			y1 = y2;
		}
		double x2 = v2.shape.getCenterX();
		double y2 = v2.shape.getCenterY();
		return Line2D.ptLineDist(x1, y1, x2, y2, x, y) < precision;
	}

	private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
		double alpha = x1 == x2 ? Math.PI / 2. : Math
				.atan(((y1 - y2) / ((double) (x1 - x2))));
		if (x2 - x1 < 0 || (x1 == x2 && y1 > y2))
			alpha = Math.PI + alpha;

		int x3 = (int) (x2 - ARROW_SIZE * Math.cos(alpha + ARROW_ANGLE));
		int y3 = (int) (y2 - ARROW_SIZE * Math.sin(alpha + ARROW_ANGLE));
		int x4 = (int) (x2 - ARROW_SIZE * Math.cos(alpha - ARROW_ANGLE));
		int y4 = (int) (y2 - ARROW_SIZE * Math.sin(alpha - ARROW_ANGLE));

		g2.drawLine(x2, y2, x3, y3);
		g2.drawLine(x2, y2, x4, y4);
	}
	
	Point labelPosition() {
		int x1, x2, y1, y2;
		int jps = jointPoints.size();
		if (jps <= 1) {
			x1 = (int) v1.shape.getCenterX();
			y1 = (int) v1.shape.getCenterY();
			if (jps == 0) {
				x2 = (int) v2.shape.getCenterX();
				y2 = (int) v2.shape.getCenterY();
			} else {
				RectangularShape rs = jointPoints.get(0);
				x2 = (int) rs.getCenterX();
				y2 = (int) rs.getCenterY();
			}
		} else {
			int index = jointPoints.size() / 2;
			RectangularShape rs1 = jointPoints.get(index - 1);
			RectangularShape rs2 = jointPoints.get(index);
			x1 = (int) rs1.getCenterX();
			y1 = (int) rs1.getCenterY();
			x2 = (int) rs2.getCenterX();
			y2 = (int) rs2.getCenterY();
		}
		return new Point((x1 + x2) / 2, (y1 + y2) / 2);
	}

	void draw(Graphics2D g2) {
		if(this.state==true)
		{
			g2.setColor(Color.YELLOW);
		}else 
		{ g2.setColor(Color.BLACK);}
		int x1 = (int) v1.shape.getCenterX();
		int y1 = (int) v1.shape.getCenterY();
		for (RectangularShape jp : jointPoints) {
			int x2 = (int) jp.getCenterX();
			int y2 = (int) jp.getCenterY();
			g2.drawLine(x1, y1, x2, y2);
			drawArrow(g2, x1, y1, (3 * x1 + x2) / 4, (3 * y1 + y2) / 4);
			x1 = x2;
			y1 = y2;
		}
		Paint bg = g2.getPaint();
		g2.setPaint(Color.RED);
		for (RectangularShape jp : jointPoints)
			g2.fill(jp);
		g2.setPaint(bg);
		int x2 = (int) v2.shape.getCenterX();
		int y2 = (int) v2.shape.getCenterY();
		g2.drawLine(x1, y1, x2, y2);
		drawArrow(g2, x1, y1, (3 * x1 + x2) / 4, (3 * y1 + y2) / 4);
		Point lp = labelPosition();
		if (textField != null) {
			textField.setLocation(lp);
		}
	}

}
