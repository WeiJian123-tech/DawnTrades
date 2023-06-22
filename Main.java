package Prototype_003;

import javax.swing.SwingUtilities;

import org.knowm.xchart.*;

/*
 * Working Prototype.
 * 
 * Modified from a program given by ChatGPT.
 * 
 * Open source free trading program
 * 
 * Java Library: XChart
 * 
 * @Authors: Wei Jian Zhen, Jawad Rahman
 */

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new TabbedFrontEnd().createAndDisplayGUI();
			}
			
		});
		
		//StockInputWindow siw = new StockInputWindow();
		
		//siw.createWindow();
		
		//StockInput si = new StockInput();
		
		//BackEnd tradeAlgo = new BackEnd();
		
		//StockOHLCChart candlestickChart = new StockOHLCChart();
		
		//OHLCChart csChart = candlestickChart.OHLCGraph(si, tradeAlgo);
		
		//new SwingWrapper<>(csChart).displayChart();
		
	}
}
