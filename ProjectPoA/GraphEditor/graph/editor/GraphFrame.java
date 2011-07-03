package graph.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import automaton.NotDeterministiTransitionException;
import automaton.NotDeterministicInitialStateException;
import automaton.UnknownInitialStateException;

public class GraphFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GraphComponent component;
	private FramesController controller;
	JTextField text;
	JCheckBox initialButton;
	JCheckBox finalButton ;
	public GraphFrame(FramesController controller) {
		this.controller = controller;

		component = new GraphComponent();
		component.setForeground(Color.BLACK);
		component.setBackground(Color.WHITE);
		component.setOpaque(true);
		component.setPreferredSize(new Dimension(1000, 1000));
		
		JScrollPane scrollPane = new JScrollPane(component);
        JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(GraphEditor.MENU_FILE);
		menuBar.add(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_NEW, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.createFrame();
			}
		});
		createMenuItem(menu, GraphEditor.MENU_ITEM_CLOSE, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_OPEN, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				File file=GraphFrame.this.controller.Open(GraphFrame.this);
				try {
					component.Loadfile(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		createMenuItem(menu, GraphEditor.MENU_ITEM_SAVE, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				File file=GraphFrame.this.controller.Save(GraphFrame.this);
				try {
					component.SaveFile(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		createMenuItem(menu, GraphEditor.MENU_ITEM_QUIT, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.quit();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});
		
		Component c=new Component();
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolbar.add(c);
		JButton start=new JButton("Start");
        start.setPreferredSize(new Dimension(60,50));
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			char[] c=text.getText().toCharArray();
			String[] s=new String[text.getText().length()];
			for(int i=0;i<c.length;i++)
			{
				s[i]=(Character.toString(c[i]));
			}
					
			 try {
				if(component.DeterAuto(s))
					JOptionPane.showMessageDialog(null,"Recognize");
				else
					JOptionPane.showMessageDialog(null,"Not Recognize");
				
			} catch (NotDeterministiTransitionException e1) {
				// TODO Auto-generated catch block
				 JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (NotDeterministicInitialStateException e1) {
				// TODO Auto-generated catch block
				 JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (UnknownInitialStateException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
			}
		});
	      
		 text=new JTextField();
		 text.setPreferredSize(new Dimension(200,50));
		 Font font=new Font(text.getFont().getName(),text.getFont().getStyle(),30);  
		 text.setFont(font);
		 toolbar.add(text);
		 toolbar.add(start);
		initialButton = new JCheckBox("Initial State"  , true);		
		initialButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				component.stateVertex.setInitial(GraphFrame.this.initialButton.isSelected());
			}
		}
		);
		finalButton= new JCheckBox("Final State"   , false);
		finalButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				component.stateVertex.setTerminal(GraphFrame.this.finalButton.isSelected());
				
			}
		}
		);
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(2, 1));
		radioPanel.add(initialButton);
		radioPanel.add(finalButton);
		toolbar.add(radioPanel);
		
		
		Container contentPane = getContentPane();
		contentPane.add(toolbar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

	}
	private void createMenuItem(JMenu menu, String name, ActionListener action) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(action);
		menu.add(menuItem);
	}

	private void createMenuSeparator(JMenu menu) {
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.lightGray);
		menu.add(separator);
	}
}
