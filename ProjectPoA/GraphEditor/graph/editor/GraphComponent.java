package graph.editor;


import graph.util.Shapes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import observable.ObservableAutomaton;

import automaton.NotDeterministiTransitionException;
import automaton.NotDeterministicInitialStateException;
import automaton.State;
import automaton.StateImpl;
import automaton.Transition;
import automaton.TransitionImpl;
import automaton.UnknownInitialStateException;

public class GraphComponent extends JComponent implements Observer,MouseInputListener,
		KeyListener {
	private static final long serialVersionUID = 1L;

	private static int n = 0;

	List<Vertex> vertices = new ArrayList<Vertex>();
	List<Edge> edges = new ArrayList<Edge>();
	Vertex currentVertex = null;
	private RectangularShape vertexSample = new Ellipse2D.Double(0, 0, 30, 30);
    State stateVertex=new StateImpl(true,false);
	private RectangularShape currentJointPoint = null;
	Edge currentEdge = null;
	
	//private List<Transition<String>> transitions=new ArrayList<Transition<String>>();
	//private String[] inputauto;
	
    /*
	
	*/
	@SuppressWarnings("unchecked")
	private Transition[] CreateTransition()
	{  
		Transition[] transitions=new Transition[edges.size()];
		for(int i=0;i<edges.size();i++)
		{
			edges.get(i).setState(false);
		Vertex s1=edges.get(i).v1;
		Vertex s2=edges.get(i).v2;
		String s=edges.get(i).textField.getText();
	   Transition<String> T=new TransitionImpl<String>(s1,s2,s);
	   T.setID(edges.get(i).getID());
	    	    transitions[i]=T;
		}
	    
		return transitions;
	}
	
	@SuppressWarnings("unchecked")
	public boolean DeterAuto(String[] s) throws NotDeterministiTransitionException, NotDeterministicInitialStateException, UnknownInitialStateException
	{
		ObservableAutomaton<String> Deterministicautomaton=new ObservableAutomaton<String>(CreateTransition());
		Deterministicautomaton.addObserver(this);
		return Deterministicautomaton.recognize(s);
			
	}
	
	
	
	public GraphComponent() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		Graphics2D g2 = (Graphics2D) g;
		for (Vertex v : vertices)
		{
			v.draw(g2);
		}
		for (Edge e : edges)
		{
			e.draw(g2);					   		
		}
		
	}

	private Vertex getVertex(int x, int y) {
		for (Vertex v : vertices) {
			if (v.shape.contains(x, y))
				return v;
		}
		return null;
	}

	private RectangularShape getJointPoint(int x, int y) {
		for (Edge e : edges) {
			RectangularShape jp = e.getJointPoint(x, y);
			if (jp != null)
				return jp;
		}
		return null;
	}

	private static final double EDGE_EPSILON = 2.0;

	private Edge getEdge(int x, int y) {
		for (Edge e : edges)
			if (e.contains(x, y, EDGE_EPSILON))
				return e;
		return null;
	}

	public void setVertexSample(RectangularShape sample) {
		vertexSample = sample;
		
	}

	private RectangularShape newVertexShape(int x, int y) {
		RectangularShape rs = (RectangularShape) vertexSample.clone();
		Shapes.moveShape(rs, x, y);
		return rs;
	}

	private Vertex createVertex(int x, int y) {
		RectangularShape rs = newVertexShape(x, y);
		Vertex v = new Vertex(rs, Integer.toString(n++),this.stateVertex);
		v.id=n;
		vertices.add(v);
		return v;
	}

	private void removeVertex(Vertex v) {
		List<Edge> toRemove = new ArrayList<Edge>();
		for (Edge e : edges) {
			if (e.v1 == v || e.v2 == v)
				toRemove.add(e);
		}
		for (Edge e : toRemove)
			removeEdge(e);
		vertices.remove(v);
	}

	private void removeEdge(Edge e) {
		remove(e.textField);
		edges.remove(e);
	}

	private Edge startEdge(Vertex v) {
		RectangularShape rs2 = newVertexShape(0, 0);
		RectangularShape rs = v.shape;
		State s=v.state;
		rs2.setFrameFromCenter((int) rs.getCenterX(), (int) rs.getCenterY(),
				(int) rs.getCenterX(), (int) rs.getCenterY());
		Edge l = new Edge(v, new Vertex(rs2, null,s));
		edges.add(l);
		return l;
	}
   
	private void addJointPoint(Edge e) {
		e.addJointPoint();
	}

	private void endLine(Edge e, int x, int y) {
		Vertex v = getVertex(x, y);
		if (v == null) {
			e.v2.shape.setFrameFromCenter(x, y, x + vertexSample.getHeight()
					/ 2, y + vertexSample.getWidth() / 2);
			e.v2.label = Integer.toString(n++);
			vertices.add(e.v2);
		} else
			e.v2 = v;
	}

	public void mousePressed(MouseEvent e) {
		requestFocusInWindow();
		if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK)
			return;
		int x = e.getX();
		int y = e.getY();
		Vertex v = getVertex(x, y);
	
		if (v == null) {
			currentJointPoint = getJointPoint(x, y);
		}
		if (v == null && currentJointPoint == null)
			v = createVertex(x, y);
		if (v != null && e.isAltDown())
			currentEdge = startEdge(v);
		else
			currentVertex = v;
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		if (currentVertex != null) {
			Shapes.moveShape(currentVertex.shape, e.getX(), e.getY());
			repaint();
		} else if (currentEdge != null) {
			Shapes.moveShape(currentEdge.v2.shape, e.getX(), e.getY());
			repaint();
		} else if (currentJointPoint != null) {
			Shapes.moveShape(currentJointPoint, e.getX(), e.getY());
			repaint();
		}
	}

	public void addEdgeLabel(final Edge e) {
		final JTextField textField = new JTextField();
		textField.setSize(50,30);
		textField.setFont(new Font(textField.getFont().getName(),textField.getFont().getStyle(),30));
		textField.setLocation(e.labelPosition());
		e.textField = textField;
		this.add(textField);
	}

	public void mouseReleased(MouseEvent e) {
		if (currentEdge != null) {
			endLine(currentEdge, e.getX(), e.getY());
			addEdgeLabel(currentEdge);
			currentEdge = null;
			repaint();
		}
		currentVertex = null;
		currentJointPoint = null;
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			int x = e.getX();
			int y = e.getY();
			Vertex v = getVertex(x, y);
			
			if (v != null) {
				removeVertex(v);
				repaint();
				return;
			}
			for (Edge edge : edges) {
				RectangularShape jp = edge.getJointPoint(x, y);
				if (jp != null) {
					edge.removeJointPoint(jp);
					repaint();
					return;
				}
			}
			Edge edge = getEdge(x, y);
			if (edge != null) {
				removeEdge(edge);
				repaint();
				return;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == ' ' && currentEdge != null) {
			addJointPoint(currentEdge);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	@SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		int t=((Transition<String>) arg1).getID();
		for(Edge e:edges)
		{
			if (e.getID()==t)
				e.setState(true);
		}
		repaint();
		
	}
	public void Loadfile(File file) throws IOException
	{
		DataInputStream in = new DataInputStream(new
	            BufferedInputStream(new FileInputStream(file)));
		
		while  (!this.vertices.isEmpty()) // remove all vertices
		this.vertices.remove(0);
		
		while  (!this.edges.isEmpty()) // remove all edges
			this.edges.remove(0);
          
		int num=in.readInt();          // number of vertex size
          int  x=0;
          int  y=0;
           int state=0;
           State s=new StateImpl(false,false);
            for(int i=0;i<num;i++)
            {
            	x=in.readInt();
            	y=in.readInt();
            	state=in.readInt();
            	
            	switch (state)
            	{
            		case 1:
            			   s.setInitial(true);
            			   s.setTerminal(false);
            			   break;
            		case 2:
            			   s.setInitial(false);
            			   s.setTerminal(true);
            			   break;
            		case 3:
            			 s.setInitial(true);
            			 s.setTerminal(true);
            			   break;
            		case 4:
            			 s.setInitial(false);
            			 s.setTerminal(false);
            			   break;
            	}
            	Vertex v=new Vertex(newVertexShape(x,y),Integer.toString(i),s);
            	this.vertices.add(v);
            }
            int numEdges=0;
            int numJointpoint=0;
            numEdges=in.readInt();
            Edge e;
            Vertex v1;
            Vertex v2;
            RectangularShape joinpoint;
            for(int i=0;i<numEdges;i++)
            {
            	
            	v1=this.vertices.get(in.readInt()-1);
            	v2=this.vertices.get(in.readInt()-1);
            	e=new Edge(v1,v2);
            	
            	numJointpoint=in.readInt();
            	for(int j=0;j<numJointpoint;j++)
            	{
            		x=in.readInt();
                	y=in.readInt();
            	 joinpoint=new Ellipse2D.Double(x,y,4,4);
            	 e.jointPoints.add(joinpoint);
                }
            	this.addEdgeLabel(e);
            	e.textField.setText(in.readUTF());
            	this.edges.add(e);
            }
            in.close();
            this.repaint();
            
	}
	public void SaveFile(File file) throws IOException 
	{
		DataOutputStream out = new DataOutputStream(new
	            BufferedOutputStream(new FileOutputStream(file)));
		out.writeInt(this.vertices.size());
        for(Vertex v:this.vertices)
        {
        	out.writeInt((int)(v.shape.getCenterX()));
        	out.writeInt((int)(v.shape.getCenterY()));
        	if(v.state.initial()&&!v.state.terminal())
        	out.writeInt(1);
        	if(!v.state.initial()&&v.state.terminal())
            	out.writeInt(2);
        	if(v.state.initial()&&v.state.terminal())
            	out.writeInt(3);
        	if(!v.state.initial()&&!v.state.terminal())
            	out.writeInt(4);
        }
           out.writeInt(this.edges.size());
        for(Edge e:this.edges)
        {
        	out.writeInt((int)(e.v1.id));
        	out.writeInt((int)(e.v2.id));
        	
        	out.writeInt(e.jointPoints.size());
        	for(RectangularShape joinpoint:e.jointPoints )
        	{
        		out.writeInt((int)(joinpoint.getCenterX()));
        		out.writeInt((int)(joinpoint.getCenterY()));
        	}
        	out.writeUTF(e.textField.getText());
        }
        out.close();
       
            
	}
}
