package Prototype_003;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.SwingWrapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;

public class StockInputWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File stockData;
	private Desktop desktop;
	private JPanel inputPanel;
	
	public StockInputWindow() {
		inputPanel = new JPanel();
		stockData = new File("");
		desktop = Desktop.getDesktop();
	}

	public void createWindow() {
		
		setTitle("Stock CSV File Uploader");
		setBackground(Color.LIGHT_GRAY);
		setMinimumSize(new Dimension(540, 190));
		setPreferredSize(new Dimension(550, 200));
		
		windowContents();
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void windowContents() {
		
		inputPanel = new JPanel();
		
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setMinimumSize(new Dimension(500, 500));
		inputPanel.setPreferredSize(new Dimension(550, 500));
		
		textPanel();
		
		uploadPanel();
		
		add(inputPanel);
	}
	
	private void textPanel() {
		
		JPanel txtPanel = new JPanel();
		
		txtPanel.setMinimumSize(new Dimension(500, 50));
		txtPanel.setPreferredSize(new Dimension(500, 75));
		
		JLabel inputLabel = new JLabel(
				"Please Upload .csv File that includes these categories: "
		);
		
		JLabel inputLabel2 = new JLabel(
				"\n" +
				"Date,Open,High,Low,Close,Adj Close,Volume"
				);
		
		txtPanel.add(inputLabel);
		txtPanel.add(inputLabel2);
		
		inputPanel.add(txtPanel, BorderLayout.PAGE_START);
	}
	
	//File explorer link: https://is.gd/fL9fPu
	//Open window once previous window has opened: https://stackoverflow.com/a/9573069/11628809
	//https://stackoverflow.com/a/9573032/11628809
	//With Tabs: https://is.gd/5dvcsC
	private void uploadPanel() {
		
		JPanel uplPanel = new JPanel();
		
		uplPanel.setMinimumSize(new Dimension(475, 50));
		uplPanel.setPreferredSize(new Dimension(500, 75));
		uplPanel.setLayout(new GridBagLayout());
		
		JTextField fileTxtBox = new JTextField();
		
		fileTxtBox.setMinimumSize(new Dimension(350, 25));
		fileTxtBox.setPreferredSize(new Dimension(400, 30));
		fileTxtBox.setEditable(true);
		
		Action txtBoxAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		};
		
		fileTxtBox.addActionListener(txtBoxAction);
		
		
		JButton confirmBtn = new JButton("Enter");
		
		confirmBtn.setMinimumSize(new Dimension(75, 25));
		confirmBtn.setPreferredSize(new Dimension(100, 30));
		confirmBtn.setActionCommand(confirmBtn.getText());
		
		Action confirmAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				stockData = new File(fileTxtBox.getText());
				
				//si.insertStockData(stockData);
				
				/*
				File directory = new File("C://Program Files//");
				
				desktop = Desktop.getDesktop();
				
				try {
					desktop.open(directory);
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				*/
				
			}
			
		};
		
		confirmBtn.addActionListener(confirmAction);
		
		uplPanel.add(confirmBtn);
		uplPanel.add(fileTxtBox);
		
		inputPanel.add(uplPanel, BorderLayout.CENTER);
	}
}
