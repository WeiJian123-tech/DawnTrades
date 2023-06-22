package Prototype_003;

import org.knowm.xchart.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TabbedFrontEnd extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	protected static JScrollPane scrollPane;
	private JPanel inputPanel,stockCont, extraStockCont;
	protected static JPanel csPanel;
	private JPanel settingsPanel;
	private Dimension minSize, prefSize;
	private GridBagConstraints gbc;
	
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
		gbc = new GridBagConstraints();
	}
	
	public void createAndDisplayGUI() {
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(600, 600));
		setTitle("Project GoldenTrades");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		
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
		tabbedPane.setBackground(getBackground());
		
		inputTab(si, tradeAlgo, csda);
		
		tabbedPane.add("Stock CSV File Uploader", inputPanel);
		
		add(tabbedPane);
	}
	
	private void inputTab(StockInput si, BackEnd tradeAlgo, CandleStickDetectionAlgo csda) {
		inputPanel = new JPanel();
		
		inputPanel.setMinimumSize(minSize);
		inputPanel.setPreferredSize(prefSize);
		inputPanel.setBackground(new Color(159, 196, 245));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
		
		JPanel inputTxtPanel = new JPanel();
		
		inputTxtPanel.setMinimumSize(new Dimension(500, 175));
		inputTxtPanel.setPreferredSize(new Dimension(600, 200));
		
		JLabel inputPrompt = new JLabel(
				"Please Upload .csv File that includes these categories: "
				);
		
		JLabel categoryTxt = new JLabel(
				"\n" +
				"Date,Open,High,Low,Close,Adj Close,Volume" +
				"\n"
				);
		
		JLabel cautionTxt = new JLabel(
				"\n" +
				"Please also make sure that you have permissions to be able to access your file" +
				"\n"
				);
		
		inputTxtPanel.add(inputPrompt);
		inputTxtPanel.add(categoryTxt);
		inputTxtPanel.add(cautionTxt);
		
		inputPanel.add(inputTxtPanel);
		
		
		JPanel searchPanel = new JPanel();
		
		searchPanel.setMinimumSize(new Dimension(500, 175));
		searchPanel.setPreferredSize(new Dimension(600, 200));
		searchPanel.setLayout(new GridBagLayout());
		
		//File explorer link: https://is.gd/fL9fPu
		//Open window once previous window has opened: https://stackoverflow.com/a/9573069/11628809
		//https://stackoverflow.com/a/9573032/11628809
		//With Tabs: https://is.gd/5dvcsC
		//Enter Key Registration: https://stackoverflow.com/a/16069707/11628809
		JTextField fileTxtBox = new JTextField();
		
		fileTxtBox.setMinimumSize(new Dimension(350, 25));
		fileTxtBox.setPreferredSize(new Dimension(400, 30));
		fileTxtBox.setEditable(true);
		fileTxtBox.setText("C:/Users/ZhenF/eclipse-workspace/AutomaticTradingProgram/src/Prototype_003/MSFT.csv");
		
		JButton openDirBtn = new JButton("Open Directory");
		
		AbstractAction openDirAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
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
		
		JButton enterBtn = new JButton("Enter");
		
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
		
		enterBtn.getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter"
						);
		enterBtn.getActionMap().put("enter", enterAction);
		
		enterBtn.addActionListener(enterAction);
		
		searchPanel.add(openDirBtn);
		searchPanel.add(enterBtn);
		searchPanel.add(fileTxtBox);
		
		inputPanel.add(searchPanel);
		
		/*
		inputPanel.add(inputPrompt);
		inputPanel.add(categoryTxt);
		inputPanel.add(cautionTxt);
		inputPanel.add(openDirBtn);
		inputPanel.add(enterBtn);
		inputPanel.add(fileTxtBox);
		*/
	}
	
	private void stockTab(StockInput si, BackEnd tradeAlgo) {
		stockCont = new XChartPanel<>(new StockOHLCChart().OHLCGraph(si, tradeAlgo));
		
		stockCont.setMinimumSize(minSize);
		stockCont.setPreferredSize(prefSize);
		stockCont.setBackground(Color.WHITE);
	}
	
	private void extraStockTab(StockInput si, BackEnd tradeAlgo) {
		extraStockCont = new XChartPanel<>(new OHLCXtraChart().xtraGraph(si, tradeAlgo));
		
		extraStockCont.setMinimumSize(minSize);
		extraStockCont.setPreferredSize(prefSize);
		extraStockCont.setBackground(Color.LIGHT_GRAY);
	}
	
	//Scroll pane in JTabbedPane: https://stackoverflow.com/a/21643625/11628809
	private void csTab(StockInput si, CandleStickDetectionAlgo csda) {
		csPanel = new JPanel();
		
		csPanel.setBackground(Color.WHITE);
		csPanel.setLayout(new BoxLayout(csPanel, BoxLayout.PAGE_AXIS));
		
		scrollPane = new JScrollPane(csPanel);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setMinimumSize(new Dimension(500, 500));
		scrollPane.getViewport().setPreferredSize(new Dimension(600, 600));
		
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
	
	private void settingsTab() {
		settingsPanel = new JPanel();
		
		settingsPanel.setMinimumSize(minSize);
		settingsPanel.setPreferredSize(prefSize);
		settingsPanel.setBackground(new Color(255, 252, 240));
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
		
		JCheckBox checkBox = new JCheckBox();
		
		AbstractAction action = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				settingsPanel.setBackground(new Color(120, 120, 120));
			}
			
		};
		
		checkBox.addActionListener(action);
		
		settingsPanel.add(checkBox);
	}
	
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
