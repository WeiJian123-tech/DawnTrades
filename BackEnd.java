package Prototype_003;

/*
 * Program given by ChatGPT.
 */

import java.util.*;

public class BackEnd {

	private List<Double> priceData;
	private int periods;
	private double[] movingAverages;
	private double[] standardDeviations;
	private double[] fibonacciRetracementLevels = new double[7];
	
	public BackEnd(List<Double> priceData, int periods, double[] movingAverages, double[] standardDeviations, double[] fibonacciRetracementLevels) {
		this.priceData = priceData;
		this.periods = periods;
		this.movingAverages = movingAverages;
		this.standardDeviations = standardDeviations;
		this.fibonacciRetracementLevels = fibonacciRetracementLevels;
	}
	
	public BackEnd(List<Double> priceData, int periods) {
		this(priceData, periods, new double[999], new double[999], new double[7]);
	}
	
	public BackEnd(List<Double> priceData) {
		this(priceData, priceData.size(), new double[999], new double[999], new double[7]);
	}
	
	public BackEnd() {
		this(new ArrayList<Double>(), 0, new double[999], new double[999], new double[7]);
	}
	
	/*
	CANDLESTICK PATTERN DETECTION ALGORITHMS

	Things to do:
	  - Get better calculations/detection results
	  - Make functions work with data in Backend Class instance fields
	  - Improve return types
	  - Possible change date format
  
  	*/
  	public String detectMarubozu(String date, float open, float high, float low, float close){
    		if(low == open || high == close) {
        		return date + "Bull Marubozu Detected";
      		} else if(high == open || low == close){
        		return date + " Bear Marubozu Detected";
      		}
    		return null;
  	}

  	public String detectDoji(String date, float open, float high, float low, float close){
    		if((Math.abs(close - open) / (high - low) < 0.1) && ((high - Math.max(close, open)) > (3 * Math.abs(close - open))) && ((Math.min(close, open) - low) > (3 * Math.abs(close - open)))){
      			return date + " Doji Detected";
    		}
    		return null;
  	}

  	public String detectEngulf(String date, float open, float high, float low, float close, float prevOpen, float prevHigh, float prevLow, float prevClose){
    		if(open >= prevClose && prevClose > prevOpen && open > close && prevOpen >= close && (open - close) > (prevClose - prevOpen)){
      			return date + " Bearish Engulfment Detected";
    		} else if(close >= prevOpen && prevOpen > prevClose && close > open && prevClose >= open && (close - open) > (prevOpen - prevClose)){
      			return date + " Bullish Engulfment Detected";
    		}
    		return null;
  	}
	
	//Coding short term predictions via Technical Analysis.
	//Then Fundamental Analysis.
	//Lastly combining them to predict stocks accurately.
	
	//Set Doubles to BigDecimal to adjust rounding errors. Set to 4 decimal places.
	
	//Calculate a simple moving average of a stock. 
	//Link: https://is.gd/O5XYS9
	private double movingAverage(List<Double> priceData2, int periods) {
		
		double movingAverage = 0.0;
		
		for(Double prices: priceData2) {
			movingAverage += prices;
		}
		
		movingAverage /= periods;
		
		return movingAverage;
	}
	
	//Calculate a standard deviation of a stock.
	//Links: https://is.gd/NErz9N & https://is.gd/AkfTaX
	private double standardDeviation(List<Double> closingPrices, int periods) {
		
		double sum = 0.0, standardDeviation = 0.0;
		
		for(Double prices: closingPrices) {
			sum += prices;
		}
		
		double mean = sum/periods;
		
		for(Double prices: closingPrices) {
			standardDeviation += Math.pow(prices - mean, 2);
		}
		
		return standardDeviation;
	}
	
	/**
	 * Links for Bollinger Bands:
	 * Fidelity summary: https://is.gd/mof0KQ
	 * Investopedia explanation: https://is.gd/cQeFkF
	 * Stock Charts Formula: https://is.gd/oFRc9L
	**/
	
	//Calculate upper bollinger band
	private double upperBollingerBand(List<Double> closingPrices, int periods) {
		
		//The middle bollinger band is the moving average
		double movAvg = movingAverage(closingPrices, periods);
		
		double stdDev = standardDeviation(closingPrices, periods);
		
		return movAvg + (stdDev * 2);
	}
	
	//Calculate lower bollinger band
	private double lowerBollingerBand(List<Double> priceData2, int periods) {
		
		//The middle bollinger band is the moving average
		double movAvg = movingAverage(priceData2, periods);
		
		double stdDev = standardDeviation(priceData2, periods);
		
		return movAvg - (stdDev * 2);
	}
	
	//Calculate Fibonacci Retracement levels of a stock at 2 points.
	//Link: https://is.gd/aRjSTM
	private double calculateFibonacciRetracementLevels(double highPoint, double lowPoint, int level) {
		
		double range = highPoint - lowPoint;
		
		//Common levels from a high of 100% to a low of 0%;
		fibonacciRetracementLevels[0] = highPoint;
		fibonacciRetracementLevels[1] = highPoint - (0.236 * range);
		fibonacciRetracementLevels[2] = highPoint - (0.382 * range);
		fibonacciRetracementLevels[3] = highPoint - (0.5 * range);
		fibonacciRetracementLevels[4] = highPoint - (0.618 * range);
		fibonacciRetracementLevels[5] = highPoint - (0.786 * range);
		fibonacciRetracementLevels[6] = lowPoint;
		
		return fibonacciRetracementLevels[level];
	}
	
	/**
	 * Public methods for user access;
	**/
	
	public double getMovingAverage(List<Double> priceData, int periods) {
		return movingAverage(priceData, periods);
	}
	
	public double getStandardDeviation(List<Double> priceData, int periods) {
		return standardDeviation(priceData, periods);
	}
	
	public double getUpperBand(List<Double> priceData, int periods) {
		return upperBollingerBand(priceData, periods);
	}
	
	public double getLowerBand(List<Double> priceData, int periods) {
		return lowerBollingerBand(priceData, periods);
	}
	
	public double getFibonnaciLevel(double highPrice, double lowPrice, int level) {
		return calculateFibonacciRetracementLevels(highPrice, lowPrice, level);
	}
	
	public List<Double> getPriceData() {
		return priceData;
	}
	
	public int getPeriods() {
		return periods;
	}
	
	public void setPeriods(int numPeriods) {
		periods = numPeriods;
	}
	
	public int priceDataSize() {
		return priceData.size();
	}
	
	public boolean isDataEmpty() {
		return priceData.isEmpty();
	}
	
	public double lastPrice() {
		return priceData.get(priceData.size()-1);
	}
	
	/***
		From ChatGPT's explanation of its shouldBuy() and shouldSell() methods:
		
		The shouldBuy() method first checks if the current price is below the lower band.
		If it is, then it returns true, indicating that a buy order should be placed.
		If the current price is between the lower band and the middle band, it returns false,
		indicating that no action should be taken.
		Finally, if the current price is above the middle band,
		it checks if the price has increased by a certain percentage since the last buy order.
		If it has, then it returns true, indicating that a new buy order should be placed.
		Otherwise, it returns false.

		The shouldSell() method first checks if the current price is above the upper band. 
		If it is, then it returns true, indicating that a sell order should be placed. 
		If the current price is between the middle band and the upper band, it returns false, 
		indicating that no action should be taken. 
		Finally, if the current price is below the middle band, 
		it checks if the price has decreased by a certain percentage since the last sell order. 
		If it has, then it returns true, indicating that a new sell order should be placed. 
		Otherwise, it returns false.
	***/
	
	//Links: https://youtu.be/5FHrvwmKFis & https://is.gd/nblok6
	
	//Method that indicates when to buy a stock at a particular period.
	public boolean shouldBuy(int currentIndex) {
		
		double currentPrice = priceData.get(currentIndex);
		double lowerBand = lowerBollingerBand(priceData, currentIndex);
		double retraceLevelMiddle = fibonacciRetracementLevels[3];
		
		return (currentPrice > retraceLevelMiddle) && (currentPrice < lowerBand);
	}
	
	//Method that indicates when to sell a stock at a particular period.
	public boolean shouldSell(int currentIndex) {
		
		double currentPrice = priceData.get(currentIndex);
		double upperBand = upperBollingerBand(priceData, currentIndex);
		double retraceLevelMiddle = fibonacciRetracementLevels[3];
		
		return (currentPrice < retraceLevelMiddle) && (currentPrice > upperBand);
	}
}
