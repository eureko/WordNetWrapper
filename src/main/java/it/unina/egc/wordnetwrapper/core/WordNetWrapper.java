package it.unina.egc.wordnetwrapper.core;

import it.unina.egc.wordnetwrapper.JWIUtils.JWIWrapper;
import it.unina.egc.wordnetwrapper.gui.MainPanel;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


public class WordNetWrapper 
{
	public static JWIWrapper JWIwrapper;
	
	public static void main(String[] args)
	{
		
		try 
		{
			JWIwrapper = new JWIWrapper();
			JWIwrapper.exploreTerm("entity");
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
				
		SwingUtilities.invokeLater(new Runnable() 
		{
		      public void run() {
		        createGUI();
		      }
		});
	}	
		
	protected static void createGUI() {
		
		JFrame frame = new JFrame();
		
		JMenu fileMenu = new JMenu("File");
		JMenu infoMenu = new JMenu("?");
		
		
		JMenuItem openItem = new JMenuItem("Open...");
		JMenuItem compareItem = new JMenuItem("Match...");
		fileMenu.add(openItem);
		fileMenu.add(compareItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		 
		frame.setJMenuBar(menuBar);
		
		frame.setContentPane(new MainPanel());
		frame.setTitle("WordNet Framework for APIs testing");
		
		
		frame.setMinimumSize(new Dimension(640, 480));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.repaint();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
}
