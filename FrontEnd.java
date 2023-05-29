package Prototype_003;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.*;
import java.io.*;
import java.text.NumberFormat.Style;

import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.internal.*;

public class FrontEnd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackEnd backEnd;
	//private JPanel graphPanel;
	
	public FrontEnd(BackEnd backEnd) {
		this.backEnd = backEnd;
		//this.graphPanel = new JPanel();
	}
	
	public void defineGUI() {
		setTitle("Trading Algorithm");
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(800, 800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		
		setLayout(new BorderLayout());
		
		//Method call to contents of graph
		graphContents();
		
		pack();
		setVisible(true);
	}
	
	private void graphContents() {
		
	}
	
}
