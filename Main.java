package Prototype_003;

import javax.swing.SwingUtilities;

/*
 * DawnTrades is a free and open-source stock charting Java program
 * that is intended to assist with trading publicly available common stock.
 * 
 * Java and the Java Library xChart is utilized for the creation of DawnTrades
 * due to the relative ease and convenience of making fast programs with graphs/charts.
 * 
 * Currently, the development of this working prototype does not have all the features
 * that are going to be present in the final non-prototype build.
 * All features, User Interfaces (U.I.), and methodologies are subject to change
 * during the development and improvement of this and future working prototypes.
 * 
 * DawnTrades currently has:
 * 
 * 	A simple static graph that shows the stock price of a single stock ticker.
 * 	Inserting stock data via .csv file.
 * 	Stock indicators.
 * 	Technical Analysis integration.
 * 
 * For long time goals:
 * 
 * 	Using Funadamental Analysis combined with Technical Analysis
 *  to provide accurate predictions that can be displayed on charts.
 *  Be able to predict time to buy and sell for
 *  1 day, 5 days, 1 week, 1 month, 3 months, 6 months, 1 year, 2 years, 5 years, 10 years, and lifetime of stock.
 *  Clean, simple, easy to understand User Interface (U.I.)
 *  Fast - Trades today happen between milliseconds to nanoseconds.
 *  	Should speed be balanced by accuracy, or should speed be prioritized over accuracy?
 * 
 * Extra features that could be added:
 * 	Customizations of U.I.
 * 	Customization of trading strategies.
 * 
 * Derived and modified from a simple program given by ChatGPT.
 * 
 * Java Version: Java 8+
 * 
 * Java Library: XChart
 * 
 * @Authors: Wei Jian Zhen, Jawad Rahman
 * 
 * @License: Apache 2.0 Open-Source License && Attribution-ShareAlike 4.0 International (CC BY-SA 4.0)
 */

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new TabbedFrontEnd().createAndDisplayGUI();
			}
			
		});
	}
}
