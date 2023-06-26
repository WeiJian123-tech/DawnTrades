package Prototype_003;

import org.knowm.xchart.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/*
 * This class is the front end of the program where tabs are shown to the end user.
 * 
 * @Authors: Wei Jian Zhen, Jawad Rahman
 * 
 * @License: Apache 2.0 Open-Source License && Attribution-ShareAlike 4.0 International (CC BY-SA 4.0)
 */

public class TabbedFrontEnd extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	protected static JScrollPane scrollPane;
	private JPanel inputPanel, inputTxtPanel, searchPanel, stockCont, extraStockCont;
	protected static JPanel csPanel;
	private JPanel settingsPanel;
	private JLabel inputPrompt, categoryTxt, cautionTxt;
	protected static JLabel detectLabel;
	private Dimension minSize, prefSize;
	private GridBagConstraints gbc;
	private Font regularFont, bigFont;
	private boolean isDay, isSmall;
	
	public TabbedFrontEnd() {
		tabbedPane = new JTabbedPane();
		scrollPane = new JScrollPane();
		inputPanel = new JPanel();
		stockCont = new JPanel();
		extraStockCont = new JPanel();
		csPanel = new JPanel();
		settingsPanel = new JPanel();
		minSize = new Dimension(500, 500);
		prefSize = new Dimension(650, 600);
		inputPrompt = new JLabel();
		categoryTxt = new JLabel();
		cautionTxt = new JLabel();
		detectLabel = new JLabel();
		gbc = new GridBagConstraints();
		regularFont = new Font("Arial Black", Font.PLAIN, 12);
		bigFont = new Font("Arial Black", Font.PLAIN, 18);
		isDay = false;
		isSmall = false;
	}
	
	/*
	 * Sets the size of the JFrame, background color of the JFrame,
	 * sets the image icon for the window, calls tPanes() method,
	 * and organizes tPanes() method panels.
	 */
	/**
	 * Creates a window that displays all features for DawnTrades
	 */
	public void createAndDisplayGUI() {
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(600, 600));
		setBackground(new Color(255, 255, 255));
		
		setTitle("DawnTrades");
		
		/*
		 * Thanks to Anuj Patel (https://stackoverflow.com/users/876142/anuj-patel)
		 * Edited by Anish B. (https://stackoverflow.com/users/8340997/anish-b)
		 * For how to get System Directory: https://stackoverflow.com/a/7603444/11628809
		 * 
		 * Question from Qazi (https://stackoverflow.com/users/541929/qazi) 
		 * Edited by auspicious99 (https://stackoverflow.com/users/1578867/auspicious99):
		 * https://stackoverflow.com/q/4871051/11628809
		 * 
		 * Modified by storing System.getProperty("user.dir"); to a String called currentDir 
		 * inside createAndDisplayGUI() method.
		 */
		String currentDir = System.getProperty("user.dir");
		
		//System.out.println("Working Directory = " + currentDir);
		
		//Gets the file path for the image icons from Eclipse via image file "properties" (Alt + Enter) tab.
		File image16x16 = new File("/AutomaticTradingProgram/src/Prototype_003/DawnTradesIcon16x16.png");
		File image32x32 = new File("/AutomaticTradingProgram/src/Prototype_003/DawnTradesIcon32x32.png");
		File image64x64 = new File("/AutomaticTradingProgram/src/Prototype_003/DawnTradesIcon64x64.png");
		File image128x128 = new File("/AutomaticTradingProgram/src/Prototype_003/DawnTradesIcon128x128.png");
		
		//Gets the absolute path of above files and parses the string to start at /src/.
		String image16x16AbsPath = image16x16.getAbsolutePath();
		String sourcePath16 = image16x16AbsPath.substring(image16x16AbsPath.indexOf("src") - 1);
		
		String image32x32AbsPath = image32x32.getAbsolutePath();
		String sourcePath32 = image32x32AbsPath.substring(image32x32AbsPath.indexOf("src") - 1);
		
		String image64x64AbsPath = image64x64.getAbsolutePath();
		String sourcePath64 = image64x64AbsPath.substring(image64x64AbsPath.indexOf("src") - 1);
		
		String image128x128AbsPath = image128x128.getAbsolutePath();
		String sourcePath128 = image128x128AbsPath.substring(image128x128AbsPath.indexOf("src") - 1);
		
		//System.out.println(sourcePath16);
		
		//Adds System Directory and parsed absolute path of image files together to create full image location path. 
		String fullPath16 = currentDir + sourcePath16;
		String fullPath32 = currentDir + sourcePath32;
		String fullPath64 = currentDir + sourcePath64;
		String fullPath128 = currentDir + sourcePath128;
		
		//File location from Eclipse:
		
		//currentDir == \Users\ZhenF\eclipse-workspace\AutomaticTradingProgram
		//sourcePath16 == \src\Prototype_003\DawnTradesIcon16x16.png
		
		//C:\Users\ZhenF\eclipse-workspace\AutomaticTradingProgram\src\Prototype_003\DawnTradesIcon16x16.png
		
		//System.out.println(fullImgPath16);
		
		//Creates new ImageIcons by getting the full image location path.
		ImageIcon img16 = new ImageIcon(fullPath16);
		ImageIcon img32 = new ImageIcon(fullPath32);
		ImageIcon img64 = new ImageIcon(fullPath64);
		ImageIcon img128 = new ImageIcon(fullPath128);
		
		//Creates a new ArrayList to store each ImageIcon and enters them into the window to be displayed.
		ArrayList<Image> iconImages = new ArrayList<>();
		
		iconImages.add(img16.getImage());
		iconImages.add(img32.getImage());
		iconImages.add(img64.getImage());
		iconImages.add(img128.getImage());
		
		setIconImages(iconImages);
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		
		/*
		 * Gets the back end files for each tab. 
		 * Creates the U.I. for the tabs.
		 * Initializes the first tab, the input tab.
		 * Contains each tab in its container
		 */
		tPanes();
		
		pack();
		setVisible(true);
	}
	
	private void tPanes() {
		
		StockInput si = new StockInput();
		BackEnd tradeAlgo = new BackEnd();
		CandleStickDetectionAlgo csda = new CandleStickDetectionAlgo();
		
		tabbedPane = new JTabbedPane();
		
		tabbedPane.setMinimumSize(new Dimension(500, 500));
		tabbedPane.setPreferredSize(new Dimension(600, 600));
		tabbedPane.setBackground(new Color(107, 223, 255));
		tabbedPane.setFont(regularFont);
		tabbedPane.setBackground(getBackground());
		
		inputTab(si, tradeAlgo, csda);
		
		tabbedPane.add("Stock CSV File Uploader", inputPanel);
		
		add(tabbedPane);
	}
	
	/*
	 * The inputTab method is the first tab that the end user will see when he or she starts the program.
	 * Comprises of an explanation text that welcomes the end user and advises them on how to use DawnTrades,
	 * A JButton that opens their file explorer,
	 * And a JButton and a JTextField that inserts a file from the end user that initiates the rest of the tabs
	 * such as stock charts and candlestick detections.
	 */
	private void inputTab(StockInput si, BackEnd tradeAlgo, CandleStickDetectionAlgo csda) {
		inputPanel = new JPanel();
		
		inputPanel.setMinimumSize(minSize);
		inputPanel.setPreferredSize(prefSize);
		inputPanel.setBackground(new Color(184, 228, 255));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS)); //Arranges panels in a vetical layout.
		
		//Panel filled with explanation text
		inputTxtPanel = new JPanel();
		
		inputTxtPanel.setMinimumSize(new Dimension(500, 175));
		inputTxtPanel.setPreferredSize(new Dimension(600, 200));
		inputTxtPanel.setBackground(new Color(184, 228, 255));
		
		inputPrompt.setFont(regularFont);
		categoryTxt.setFont(regularFont);
		cautionTxt.setFont(regularFont);
		
		inputPrompt = new JLabel(
				"Please Upload .csv File that includes these categories: "
				);
		
		categoryTxt = new JLabel(
				"\n" +
				"Date,Open,High,Low,Close,Adj Close,Volume" +
				"\n"
				);
		
		cautionTxt = new JLabel(
				"\n" +
				"Please also make sure that you have permissions to be able to access your file" +
				"\n"
				);
		
		inputTxtPanel.add(inputPrompt);
		inputTxtPanel.add(categoryTxt);
		inputTxtPanel.add(cautionTxt);
		
		inputPanel.add(inputTxtPanel);
		
		//Panel that contains the two JButtons and one JTextField for the end user to interact.
		searchPanel = new JPanel();
		
		searchPanel.setMinimumSize(new Dimension(500, 175));
		searchPanel.setPreferredSize(new Dimension(600, 200));
		searchPanel.setBackground(new Color(184, 228, 255));
		searchPanel.setLayout(new GridBagLayout());
		
		//With Tabs: https://is.gd/5dvcsC
		//Enter Key Registration: https://stackoverflow.com/a/16069707/11628809
		JTextField fileTxtBox = new JTextField();
		
		fileTxtBox.setMinimumSize(new Dimension(350, 25));
		fileTxtBox.setPreferredSize(new Dimension(400, 30));
		fileTxtBox.setEditable(true);
		
		JButton openDirBtn = new JButton("Open Directory");
		
		AbstractAction openDirAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*
				 * Thanks to Genuine Coder
				 * with how to open file explorer
				 * Genuine Coder's website:
				 * https://is.gd/fL9fPu
				 */
				File directory = new File("C://Program Files//");
				
				Desktop desktop = Desktop.getDesktop();
				
				try {
					desktop.open(directory);
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
			}
			
		};
		
		openDirBtn.addActionListener(openDirAction);
		
		/*
		 * Thanks to Eng.Fouad (https://stackoverflow.com/users/597657/eng-fouad) and 
		 * nIcE cOw (https://stackoverflow.com/users/1057230/nice-cow)
		 * for thier answers to Jim_CS's (https://stackoverflow.com/users/1088617/jim-cs) question: 
		 * https://stackoverflow.com/q/9572931/11628809
		 * 
		 * Eng.Fouad's answer: https://stackoverflow.com/a/9573069/11628809
		 * nIcE cOw's answer: https://stackoverflow.com/a/9573032/11628809
		 * 
		 * Alongside Oracle's "Java Tutorials Code Sample – TabDemo.java" program 
		 * That aided Wei Jian Zhen in designing the tabs for DawnTrades: 
		 * 
		 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
		 * https://is.gd/5dvcsC
		 * 
		 * Very little to none code was taken from the above answers.
		 * The lines of code such as `tabbedPane.add(BUTTONPANEL, card1);`
		 * from Oracle's "Java Tutorials Code Sample – TabDemo.java" program 
		 * were taken and transformed to get the tab name as a string and the panel.
		 */
		
		JButton enterBtn = new JButton("Enter");
		
		/*
		 * This action gets the file text typed by the end user,
		 * stores the .csv file information into the program,
		 * and inserts it into the panels for implementation.
		 * 
		 * Displays all tab panels once this action is activated.
		 */
		AbstractAction enterAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				File stockFile = new File(fileTxtBox.getText());
				
				si.insertStockData(stockFile);
				
				stockTab(si, tradeAlgo);
				extraStockTab(si, tradeAlgo);
				csTab(si, csda);
				settingsTab();
				
				tabbedPane.add("OHLC Chart", stockCont);
				tabbedPane.add("OHLC Extra Chart", extraStockCont);
				tabbedPane.add("Candlestick Detections", scrollPane);
				tabbedPane.add("Settings", settingsPanel);
				
				csPanel.revalidate();
  				scrollPane.revalidate();
  				tabbedPane.revalidate();
				
			}
			
		};
		
		//Allows users to be able to hit the enter key to activate the above action enterAction.
		enterBtn.getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter"
						);
		enterBtn.getActionMap().put("enter", enterAction);
		
		enterBtn.addActionListener(enterAction);
		
		searchPanel.add(openDirBtn);
		searchPanel.add(enterBtn);
		searchPanel.add(fileTxtBox);
		
		inputPanel.add(searchPanel);
	}
	
	/*
	 * Displays the main stock chart that is built with xChart Library.
	 */
	private void stockTab(StockInput si, BackEnd tradeAlgo) {
		stockCont = new XChartPanel<>(new StockOHLCChart().OHLCGraph(si, tradeAlgo));
		
		stockCont.setMinimumSize(minSize);
		stockCont.setPreferredSize(prefSize);
	}
	
	/*
	 * Displays an extra stock chart built with the xChart Library that
	 * shows extra stock information that are too big or too small to be viewed 
	 * concisely on the main stock chart.
	 */
	private void extraStockTab(StockInput si, BackEnd tradeAlgo) {
		extraStockCont = new XChartPanel<>(new OHLCXtraChart().xtraGraph(si, tradeAlgo));
		
		extraStockCont.setMinimumSize(minSize);
		extraStockCont.setPreferredSize(prefSize);
	}
	
	/*
	 * Panel that displays text regarding candlestick detections.
	 */
	private void csTab(StockInput si, CandleStickDetectionAlgo csda) {
		/*
		 * Thanks to Paul Samsotha (https://stackoverflow.com/users/2587435/paul-samsotha) 
		 * for their answer to puprog's (https://stackoverflow.com/users/3234614/puprog) question
		 * edited by Paul Samsotha:
		 * https://stackoverflow.com/q/21636895/11628809
		 * 
		 * Paul Samsotha's answer for implementing a JScrollPane into a JTabbedPane:
		 * https://stackoverflow.com/a/21643625/11628809
		 * 
		 * Removed adding JScrollPane to JTabbedPane since this is a void method.
		 * Transferred from main method to csTab void method.
		 * Removed most code and modified initialization of JPanel and JScrollPane.
		 * Added methods from CandleStickDetectionAlgo class.
		 * Set scroll policy and size of JscrollPane.
		 */
		csPanel = new JPanel();
		
		csPanel.setBackground(Color.WHITE);
		csPanel.setLayout(new BoxLayout(csPanel, BoxLayout.PAGE_AXIS));
		
		scrollPane = new JScrollPane(csPanel);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setMinimumSize(new Dimension(500, 500));
		scrollPane.getViewport().setPreferredSize(new Dimension(600, 600));
		
		detectLabel.setFont(regularFont);
		
		csPanel.add(new JLabel("Detect Marubozu: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayMarubozu(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Marubozu Flexible: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayMarubozuFlexible(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Doji: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayDoji(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Doji Variations: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayDojiVariation(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Engulf: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayEngulf(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Morning Star: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayMorningStar(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csPanel.add(new JLabel("\n"));
		csPanel.add(new JLabel("Detect Evening Star: " + "\n"));
		csPanel.add(new JLabel("\n"));
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
		
		csda.displayEveningStar(
				si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices()
				);
		
		csPanel.revalidate();
		scrollPane.revalidate();
		tabbedPane.revalidate();
	}
	
	/*
	 * Panel that displays setting options for the end user.
	 * Limited to toggling night mode and increasing font size 
	 * in this current working prototype.
	 */
	private void settingsTab() {
		settingsPanel = new JPanel();
		
		settingsPanel.setMinimumSize(minSize);
		settingsPanel.setPreferredSize(prefSize);
		settingsPanel.setBackground(new Color(255, 252, 240));
		settingsPanel.setLayout(new GridBagLayout());
		
		JRadioButton nightModeBtn = new JRadioButton("Night Mode");
		
		nightModeBtn.setFont(regularFont);
		nightModeBtn.setBackground(Color.WHITE);
		nightModeBtn.setForeground(Color.BLACK);
		
		JRadioButton biggerTxtBtn = new JRadioButton("Increase Text Size");
		
		biggerTxtBtn.setFont(regularFont);
		biggerTxtBtn.setBackground(Color.WHITE);
		biggerTxtBtn.setForeground(Color.BLACK);
		
		/*
		 * Thanks to aioobe's (https://stackoverflow.com/users/276052/aioobe)
		 * answer to Stefanos Kargas' (https://stackoverflow.com/users/350061/stefanos-kargas)
		 * question edited by aioobe: https://stackoverflow.com/q/2966334/11628809
		 * 
		 * aioobe's answer about how to set foreground JLabel color: https://stackoverflow.com/a/2966363/11628809
		 * 
		 * Turned nightModeBtn and biggerTxtBtn JButton foreground text color to many colors beside red.
		 */
		
		/*
		 * Toggles night and day mode for DawnTrades program.
		 */
		AbstractAction nightModeAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isDay) {
					//Since isDay is false first, if block activates light mode second.
					tabbedPane.setBackground(new Color(107, 223, 255));
					
					inputPanel.setBackground(new Color(184, 228, 255));
					inputTxtPanel.setBackground(new Color(184, 228, 255));
					searchPanel.setBackground(new Color(184, 228, 255));
					
					csPanel.setBackground(Color.WHITE);
					
					settingsPanel.setBackground(new Color(255, 252, 240));
					nightModeBtn.setBackground(Color.WHITE);
					nightModeBtn.setForeground(Color.BLACK);
					biggerTxtBtn.setBackground(Color.WHITE);
					biggerTxtBtn.setForeground(Color.BLACK);
					
				} else {
					//Since isDay is false first, else block activates night mode.
					tabbedPane.setBackground(new Color(172, 178, 230));
					
					inputPanel.setBackground(new Color(167, 196, 214));
					inputTxtPanel.setBackground(new Color(167, 196, 214));
					searchPanel.setBackground(new Color(167, 196, 214));
					
					csPanel.setBackground(new Color(210, 210, 210));
					
					settingsPanel.setBackground(new Color(150, 150, 150));
					nightModeBtn.setBackground(new Color(100, 100, 100));
					nightModeBtn.setForeground(new Color(200, 213, 250));
					biggerTxtBtn.setBackground(new Color(100, 100, 100));
					biggerTxtBtn.setForeground(new Color(230, 196, 133));
					
				}
				
				isDay = !isDay; //Switch from false to true. Then true to false.
			}
			
		};
		
		nightModeBtn.addActionListener(nightModeAction);
		
		/*
		 * Toggles increased text size for end user.
		 */
		AbstractAction bigTxtAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isSmall) {
					//Since isSmall is false first, if block activates second.
					biggerTxtBtn.setFont(regularFont);
					nightModeBtn.setFont(regularFont);
					inputPrompt.setFont(regularFont);
					categoryTxt.setFont(regularFont);
					cautionTxt.setFont(regularFont);
					tabbedPane.setFont(regularFont);
				} else {
					//Since isSmall is false first, else block activates first.
					biggerTxtBtn.setFont(bigFont);
					nightModeBtn.setFont(bigFont);
					inputPrompt.setFont(bigFont);
					categoryTxt.setFont(bigFont);
					cautionTxt.setFont(bigFont);
					tabbedPane.setFont(bigFont);
				}
				
				isSmall = !isSmall; //Switch from false to true. Then true to false.
			}
			
		};
		
		biggerTxtBtn.addActionListener(bigTxtAction);
		
		settingsPanel.add(biggerTxtBtn);
		settingsPanel.add(nightModeBtn);
	}
	
	/*
	 * Sets gridBagConstraints for JComponents that have GridBagLayout as their layout.
	 * Currently unused because of tricky implementation of GridBagLayout and 
	 * lack of time before release date of working prototype of DawnTrades.
	 */
	private void setGridBagConstraints(
			int fill, int anchor,
			int gridx, int gridy,
			int gridheight, int gridwidth,
			int weightx, int weighty,
			int ipadx, int ipady
			) {
		gbc.fill = fill;
		gbc.anchor = anchor;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridheight = gridheight;
		gbc.gridwidth = gridwidth;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.ipadx = ipadx;
		gbc.ipady = ipady;
	}

}
