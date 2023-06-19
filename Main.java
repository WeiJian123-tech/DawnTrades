package Prototype_003;

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

import org.knowm.xchart.*;
import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		
		/*
		9.23, 10.50, 8.75, 8.96, 7.70, 8.80, 9.02, 11.01, 10.97, 11.05, 8.08, 8.87, 7.46, 8.03,
		9.00, 8.64, 8.45, 8.55, 8.99, 8.79, 9.02, 9.55, 10.01, 10.5, 10.25, 10.32, 10.28, 10.29,
		10.26, 10.30
		*/
		
		/*
		 * 10.0, 12.0, 23.0, 22.0, 16.0, 23.0, 21.0, 16.0, 17.0, 19.0
		 */
		
		
		StockInput si = new StockInput();
		
		/*
		si.insertStockData(
				
				new File(
						"C:/Users/ZhenF/eclipse-workspace/"
						+ "AutomaticTradingProgram/src/Prototype_003/SingularStockPriceTest.csv"
						)
				
				
				new File(
						"C:/Users/ZhenF/eclipse-workspace/AutomaticTradingProgram/src/Prototype_003/MSFT.csv"
						)
				
				);
				
		double[] yPrices = new double[] {
				9.23, 10.50, 8.75, 8.96, 7.70, 8.80, 9.02, 11.01, 10.97, 11.05, 8.08, 8.87, 7.46, 8.03,
				9.00, 8.64, 8.45, 8.55, 8.99, 8.79, 9.02, 9.55, 10.01, 10.5, 10.25, 10.32, 10.28, 10.29,
				10.26, 10.30
		};
		
		*/
		
		BackEnd tradeAlgo = new BackEnd();
		
		StockOHLCChart candlestickChart = new StockOHLCChart();
		
		OHLCChart csChart = candlestickChart.OHLCGraph(si, tradeAlgo);
		
		//new SwingWrapper<>(csChart).displayChart();
	}
}
