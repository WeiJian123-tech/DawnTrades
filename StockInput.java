package Prototype_003;

import java.util.*;
import java.io.*;
import java.math.*;

public class StockInput {

	public StockInput() {
		
	}
	
	public void insertStockData(File stockData) {
		
		/*
		String[] dates = new String[inputCatcher.size()];
		double[] openPrices = new double[inputCatcher.size()];
		double[] highPrices = new double[inputCatcher.size()];
		double[] lowPrices = new double[inputCatcher.size()];
		double[] closePrices = new double[inputCatcher.size()];
		double[] adjClosePrices = new double[inputCatcher.size()];
		double[] volumes = new double[inputCatcher.size()];
		*/
		
		List<String> dates = new ArrayList<>();
		List<Double> openPrices = new ArrayList<>();
		List<Double> highPrices = new ArrayList<>();
		List<Double> lowPrices = new ArrayList<>();
		List<Double> closePrices = new ArrayList<>();
		List<Double> adjClosePrices = new ArrayList<>();
		List<BigInteger> volumes = new ArrayList<>();
		
		try {
			Scanner input = new Scanner(stockData);
			
			input.useDelimiter(",|\n");
			input.useLocale(Locale.getDefault());
			
			//Skips the first row that describes the type of data
			input.skip("Date,Open,High,Low,Close,Adj Close,Volume");
			
			while(input.hasNext()) {
				//System.out.println(input.next());
				
				dates.add(input.next());
				openPrices.add(Double.valueOf(input.next()));
				highPrices.add(Double.valueOf(input.next()));
				lowPrices.add(Double.valueOf(input.next()));
				closePrices.add(Double.valueOf(input.next()));
				adjClosePrices.add(Double.valueOf(input.next()));
				volumes.add(new BigInteger(input.next()));
				
				//Reconfigure BackEnd.java to get Lists of stock data 
				//and then call BackEnd methods to StockOHLCChart.java for displaying.
			}
			
			//System.out.println(volumes.toString());
			
			input.close();
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
