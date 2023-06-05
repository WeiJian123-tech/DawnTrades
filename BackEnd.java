package Prototype_003;

/*
 * Program given by ChatGPT.
 */

import java.util.*;
import java.math.*;

public class BackEnd {

	private List<Double> priceData;
	private int periods;
	private double[] fibonacciRetracementLevels = new double[7];
	
	public BackEnd(List<Double> priceData, int periods, double[] movingAverages, double[] standardDeviations, double[] fibonacciRetracementLevels) {
		this.priceData = priceData;
		this.periods = periods;
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
  	//Uses Ideal definition of Marubozu Candles
  	public String detectMarubozu(String date, float open, float high, float low, float close) {
	      float bodyLength = Math.abs(close - open);
	      //float totalLength = high - low;  //Potential future use
	      float upperShadow = Math.abs(high - Math.max(open, close));
	      float lowerShadow = Math.abs(Math.min(open, close) - low);
	      //float variance = 3 * (Math.abs(open - close) + 15 / 2) / 2;  //Potential future use

	      if (low == open && high == close) {
		  return date + "Bull Marubozu Full Detected";
	      } else if (high == open && low == close) {
		  return date + " Bear Marubozu Full Detected";
	      } else if (low == open || high == close) {
		  if (upperShadow <= 0.1 * bodyLength && lowerShadow == 0) {
		      return date + " Bull Marubozu Open Detected";
		  } else if (lowerShadow <= 0.1 * bodyLength && upperShadow == 0) {
		      return date + " Bull Marubozu Close Detected";
		  }
	      } else if (high == open || low == close) {
		  if (upperShadow <= 0.1 * bodyLength && lowerShadow == 0) {
		      return date + " Bear Marubozu Close Detected";
		  } else if (lowerShadow <= 0.1 * bodyLength && upperShadow == 0) {
		      return date + " Bear Marubozu Open Detected";
		  }
	      }
	      return null;
  	}

  	//Detects Candles that are very close to being Marubozu even if they aren't by strict definition
  	public String detectMarubozuFlexible(String date, float open, float high, float low, float close) {
	      float bodyLength = Math.abs(close - open);
	      //float totalLength = high - low;  //Potential future use
	      float upperShadow = Math.abs(high - Math.max(open, close));
	      float lowerShadow = Math.abs(Math.min(open, close) - low);
	      //float variance = 3 * (Math.abs(open - close) + 15 / 2) / 2;  //Potential future use

	      if ((close - open > 0) && (upperShadow <= 0.1 * bodyLength && lowerShadow <= 0.1 * bodyLength)) {
		  return date + " Bull Marubozu Detected";
	      } else if ((close - open < 0) && (upperShadow <= 0.1 * bodyLength && lowerShadow <= 0.1 * bodyLength)) {
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
	
	public String detectMorningStar(String date, float open, float high, float low, float close, float prevOpen,
	      float prevHigh, float prevLow, float prevClose, float prevOpen2, float prevHigh2, float prevLow2,
	      float prevClose2) {
	      if (Math.max(prevOpen, prevClose) < prevClose2 && prevClose2 < prevOpen2 && close > open &&
		  open > Math.max(prevOpen, prevClose)) {
		  return date + " Morning Star Detected";
	      }
	      return null;
	}

	public String detectEveningStar(String date, float open, float high, float low, float close, float prevOpen,
	      float prevHigh, float prevLow, float prevClose, float prevOpen2, float prevHigh2, float prevLow2,
	      float prevClose2) {
	      if (Math.min(prevOpen, prevClose) > prevClose2 && prevClose2 > prevOpen2 && close < open &&
		  open < Math.min(prevOpen, prevClose)) {
		  return date + " Evening Star Detected";
	      }
	      return null;
	}
	
	// RSI CALCULATION ALGORITHM
	public double[] calculateRSI(double[] closingPrices, int periodLength) {
	    double[] rsiValues = new double[closingPrices.length];

	    for (int i = periodLength; i < closingPrices.length; i++) {
		double gainSum = 0.0;
		double lossSum = 0.0;

		for (int j = i - periodLength; j < i; j++) {
		    double priceDiff = closingPrices[j + 1] - closingPrices[j];
		    if (priceDiff > 0) {
			gainSum += priceDiff;
		    } else {
			lossSum -= priceDiff;
		    }
		}

		double averageGain = gainSum / periodLength;
		double averageLoss = lossSum / periodLength;

		double relativeStrength = averageGain / averageLoss;
		double rsi = 100 - (100 / (1 + relativeStrength));

		rsiValues[i] = rsi;
	    }

	    return rsiValues;
	    /*
	     * double prevClose = 0;
	     * double currClose = 0;
	     * double[] posChanges = new double[closingPrices.length];
	     * double[] negChanges = new double[closingPrices.length];
	     * double[] avgGains = new double[closingPrices.length];
	     * double[] avgLoss = new double[closingPrices.length];
	     * posChanges[0] = 0;
	     * negChanges[0] = 0;
	     * double diff = 0;
	     * double[] rsiPoints = new double[closingPrices.length];
	     * 
	     * for(int i = 1; i < closingPrices.length; i++){
	     * currClose = closingPrices[i];
	     * prevClose = closingPrices[i-1];
	     * diff = currClose - prevClose;
	     * 
	     * if(diff > 0){
	     * posChanges[i] = diff;
	     * negChanges[i] = 0;
	     * } else if(diff < 0){
	     * posChanges[i] = 0;
	     * negChanges[i] = Math.abs(diff);
	     * } else{
	     * posChanges[i] = 0;
	     * negChanges[i] = 0;
	     * }
	     * 
	     * if(i == Math.max(i, numPeriods)){
	     * double gainSum = 0;
	     * double lossSum = 0;
	     * 
	     * for(int j = Math.max(1, numPeriods); j > 0; j--){
	     * gainSum += posChanges[j];
	     * lossSum += negChanges[j];
	     * }
	     * } else if(i > Math.max(1, numPeriods)){
	     * avgGains[i] = (avgGains[i-1] * (numPeriods - 1) + posChanges[i]) /
	     * Math.max(1, numPeriods);
	     * avgLoss[i] = (avgLoss[i-1] * (numPeriods - 1) + negChanges[i]) / Math.max(1,
	     * numPeriods);
	     * rsiPoints[i] = avgLoss[i] == 0 ? 100 : avgGains[i] == 0 ? 0 : Math.round(100
	     * - (100 / (1 + avgGains[i] / avgLoss[i])));
	     * }
	     * }
	     * for(int i = 0; i < rsiPoints.length; i++){
	     * System.out.println(rsiPoints[i]);
	     * }
	     * return rsiPoints;
	     */
	}
	
  //MACD CALCULATION ALGORITHM (AND SEPARATE EMA ALGORITHM)
	private static double[] calculateEMA(double[] closingPrices, int period) {
		double[] ema = new double[closingPrices.length];

		double multiplier = 2.0 / (period + 1);
		ema[0] = closingPrices[0];

		for (int i = 1; i < closingPrices.length; i++) {
			ema[i] = (closingPrices[i] - ema[i - 1]) * multiplier + ema[i - 1];
		}

		return ema;
  	}
	
	public static double calculateMACD(double[] closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
		double[] shortEMA = calculateEMA(closingPrices, shortPeriod);
		double[] longEMA = calculateEMA(closingPrices, longPeriod);

		int minPeriod = Math.min(shortEMA.length, longEMA.length);
		double[] macdLine = new double[minPeriod];
		for (int i = 0; i < minPeriod; i++) {
			macdLine[i] = shortEMA[i] - longEMA[i];
		}

		double[] signalLine = calculateEMA(macdLine, signalPeriod);

		int lastSignalIndex = signalLine.length - 1;
		return macdLine[lastSignalIndex] - signalLine[lastSignalIndex];
  	}	

	//Coding short term predictions via Technical Analysis.
	//Then Fundamental Analysis.
	//Lastly combining them to predict stocks accurately.
	
	//Calculate a simple moving average of a stock. 
	//Link: https://is.gd/O5XYS9
	
	//@param periods is the total number of times the stock prices goes into the sum before being divided by periods.
	//Gets closing price data
	private List<Double> movingAverage(List<Double> closeData, int periods) {
		
		List<Double> movAvgs = new ArrayList<>();
		
		for(int i = 0; i < closeData.size(); i++) {
			
			if (i < periods) {
		        movAvgs.add(null);
		        continue;
		      }
			
			double sum = 0.0;
			
			for(int j = 0; j < periods; j++) {
				sum += closeData.get(i - j);
			}
			
			movAvgs.add(sum / periods);
		}
		
		return movAvgs;
	}
	  
	/*
	private double movingAverage(List<Double> priceData2, int periods) {
		
		double movingAverage = 0.0;
		
		for(Double prices: priceData2) {
			movingAverage += prices;
		}
		
		movingAverage /= periods;
		
		return movingAverage;
	}
	*/
	
	//Calculate a standard deviation of a stock.
	//Links: https://is.gd/NErz9N & https://is.gd/AkfTaX
	
	private List<Double> standardDeviation(List<Double> closeData, int periods) {
		
		List<Double> stdDevs = new ArrayList<>();
		List<Double> movAvgs = movingAverage(closeData, periods);
		
		for(int i = 0; i < closeData.size(); i++) {
			
			if(i < periods) {
				stdDevs.add(null);
				continue;
			}
			
			double sumOfSquares = 0.0, variance = 0.0, standardDeviation = 0.0;
			
			for(int j = 0; j < periods; j++) {
				double sum = 0.0;
				
				sum += movAvgs.get(i) - closeData.get(i - j);
				sumOfSquares += Math.pow( sum, 2);
			}
			
			variance = sumOfSquares / periods;
			
			standardDeviation = Math.sqrt(variance);
			
			stdDevs.add(standardDeviation);
		}
		
		return stdDevs;
	}
	
	private List<Double> upperBollingerBand(List<Double> closeData, int periods) {
		//The middle bollinger band is the moving average
		List<Double> movAvg = movingAverage(priceData, periods);
		
		List<Double> stdDev = standardDeviation(priceData, periods);
		
		List<Double> upBollBand = new ArrayList<>();
		
		for(int i = 0; i < closeData.size(); i++) {
			if(i < periods) {
				upBollBand.add(null);
				continue;
			}
			
			upBollBand.add(movAvg.get(i) + (stdDev.get(i) * 2));
		}
		
		return upBollBand;
	}
	
	private List<Double> lowerBollingerBand(List<Double> closeData, int periods) {
		List<Double> movAvg = movingAverage(priceData, periods);
		
		List<Double> stdDev = standardDeviation(priceData, periods);
		
		List<Double> lowBollBand = new ArrayList<>();
		
		for(int i = 0; i < closeData.size(); i++) {
			if(i < periods) {
				lowBollBand.add(null);
				continue;
			}
			
			lowBollBand.add(movAvg.get(i) - (stdDev.get(i) * 2));
		}
		
		return lowBollBand;
	}
	
	/*
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
	*/
	
	/*
	 * Links for Bollinger Bands:
	 * Fidelity summary: https://is.gd/mof0KQ
	 * Investopedia explanation: https://is.gd/cQeFkF
	 * Stock Charts Formula: https://is.gd/oFRc9L
	*/
	
	/*
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
	*/
	
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
	
	/*
	 * Public methods for user access;
	*/
	
	public List<Double> getMovingAverage(List<Double> closeData, int periods) {
		return movingAverage(closeData, periods);
	}
	
	/*
	public double getMovingAverage(List<Double> priceData, int periods) {
		return movingAverage(priceData, periods);
	}
	*/
	
	public List<Double> getStandardDeviation(List<Double> closeData, int periods) {
		return standardDeviation(closeData, periods);
	}
	
	public List<Double> getUpperBand(List<Double> closeData, int periods) {
		return upperBollingerBand(closeData, periods);
	}
	
	public List<Double> getLowerBand(List<Double> closeData, int periods) {
		return lowerBollingerBand(closeData, periods);
	}
	
	/*
	public double getStandardDeviation(List<Double> priceData, int periods) {
		return standardDeviation(priceData, periods);
	}
	*/
	
	/*
	public double getUpperBand(List<Double> priceData, int periods) {
		return upperBollingerBand(priceData, periods);
	}
	
	public double getLowerBand(List<Double> priceData, int periods) {
		return lowerBollingerBand(priceData, periods);
	}
	*/
	
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
	
	/*
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
	*/
	
	//Links: https://youtu.be/5FHrvwmKFis & https://is.gd/nblok6
	
	/*
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
	*/
}
