package Prototype_003;

/*
 * Working Prototype.
 * 
 * Modified from a program given by ChatGPT.
 * 
 * Open source free trading program
 * 
 * @Authors: Wei Jian Zhen, Jawad Rahman
 */

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Double> stockPrices = new ArrayList<>(
				List.of(162.39, 162.68, 163.28, 164.56, 164.60, 164.46, 165.0, 164.67, 164.66)
				);
		
		BackEnd tradeAlgo = new BackEnd(stockPrices);
		
		FrontEnd tradeWindow = new FrontEnd(tradeAlgo);
		
		tradeWindow.defineGUI();
		
		/**
		for(int i = 1; i < stockPrices.size(); i++) {
			System.out.println(
					tradeAlgo.getMovingAverage(stockPrices, i)
					);
		}
		**/
		
		/**
		for(int i = 1; i < stockPrices.size(); i++) {
			System.out.println(
					tradeAlgo.getStandardDeviation(stockPrices, i)
					);
		}
		**/
	}

}
