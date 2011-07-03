package graph.editor;

import java.awt.Dimension;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class GraphEditor implements FramesController {
	
	public final static String MENU_FILE = "File";
	public final static String MENU_ITEM_NEW = "New";
	public final static String MENU_ITEM_OPEN="Open";
	public final static String MENU_ITEM_SAVE="Save";
	public final static String MENU_ITEM_CLOSE = "Close";
	public final static String MENU_ITEM_QUIT = "Quit";

	public final static String DIALOG_QUIT_MSG = "Do you really want to quit ?";
	public final static String DIALOG_QUIT_TITLE = "Quit ?";

	public final static String TITLE = "Graph";
	private static final List<JFrame> frames = new ArrayList<JFrame>();
    JFileChooser fc=new JFileChooser();
	public void quit() {
		int answer = JOptionPane.showConfirmDialog(null, DIALOG_QUIT_MSG,
				DIALOG_QUIT_TITLE, JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public JFrame createFrame() {
		JFrame frame = new GraphFrame(this);
		frame.setTitle(TITLE);
		int pos = 30 * (frames.size() % 5);
		frame.setLocation(pos, pos);
		frame.setPreferredSize(new Dimension(1300, 1000));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frames.add(frame);
		return frame;
	}

	public void deleteFrame(JFrame frame) {
		if (frames.size() > 1) {
			frames.remove(frame);
			frame.dispose();
		} else {
			quit();
		}
	}

	public File Open(GraphFrame graphFrame) {
		// TODO Auto-generated method stub
		int returnVal=fc.showOpenDialog(graphFrame);
		File file=fc.getSelectedFile();
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		  return file;
		  else return null;
	}

	public File Save(JFrame frame) {
		// TODO Auto-generated method stub
		int returnVal=fc.showSaveDialog(frame);
		File file=fc.getSelectedFile();
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		  return file;
		  else return null;
	}


}