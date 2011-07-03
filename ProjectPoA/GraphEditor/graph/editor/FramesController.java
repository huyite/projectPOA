package graph.editor;

import java.io.File;

import javax.swing.JFrame;

public interface FramesController {

	public File Open(GraphFrame graphFrame);
	
	public File Save(JFrame frame );
	
	public void quit();

	public JFrame createFrame();

	public void deleteFrame(JFrame frame);
}