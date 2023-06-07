package Prototype_003;

/*
 * Program given by ChatGPT.
 */

import java.util.*;

public class BackEnd {

	private double[] priceData;
	private int periods;
	private double[] fibonacciRetracementLevels = new double[7];
	
	public BackEnd(
			double[] priceData, int periods,
			double[] movingAverages, double[] standardDeviations,
			double[] fibonacciRetracementLevels
			) {
		this.priceData = priceData;
		this.periods = periods;
		this.fibonacciRetracementLevels = fibonacciRetracementLevels;
	}
	
	public BackEnd(double[] priceData, int periods) {
		this(priceData, periods, new double[999], new double[999], new double[7]);
	}
	
	public BackEnd(double[] priceData) {
		this(priceData, priceData.length, new double[999], new double[999], new double[7]);
	}
	
	public BackEnd() {
		this(new double[999], 999, new double[999], new double[999], new double[7]);
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
  		if(
			(Math.abs(close - open) / (high - low) < 0.1) &&
			((high - Math.max(close, open)) > (3 * Math.abs(close - open))) &&
			((Math.min(close, open) - low) > (3 * Math.abs(close - open)))
			) {
  			return date + " Doji Detected";
  		}
  		
  		return null;
  	}

	//5 Main Types of Doji Candlesticks are detected instead of just regular Dojis
	  public String detectDojiVariations(String date, float open, float high, float low, float close) {
	    float bodyLength = Math.abs(close - open);
	    float totalLength = high - low;
	    float upperShadow = Math.abs(high - Math.max(open, close));
	    float lowerShadow = Math.abs(Math.min(open, close) - low);

	    if (bodyLength <= totalLength * 0.1 && upperShadow >= totalLength * 0.4 && lowerShadow >= totalLength * 0.4) {
	      return date + " Long-Legged Doji Detected";
	    } else if ((bodyLength < totalLength * 0.1) && (lowerShadow > (3 * bodyLength)) &&
	      (upperShadow < bodyLength)) {
	      return date + " Dragon Fly Doji Detected";
	    } else if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * bodyLength)) &&
	      (lowerShadow <= bodyLength)) {
	      return date + " Gravestone Doji Detected";
	    } else if (bodyLength <= totalLength * 0.1 && upperShadow == 0 && lowerShadow == 0) {
	      return date + " Four-Price Doji Detected";
	    } else if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * Math.abs(close - open))) &&
	      (lowerShadow > (3 * bodyLength))) {
	      return date + " Regular Doji Detetced";
	    }
	    return null;
	  }

  	public String detectEngulf(
		String date, float open, float high, float low, float close,
		float prevOpen, float prevHigh, float prevLow, float prevClose
		) {
		if(
				open >= prevClose &&
				prevClose > prevOpen &&
				open > close &&
				prevOpen >= close &&
				(open - close) > (prevClose - prevOpen)
				){
  			return date + " Bearish Engulfment Detected";
		} else if(
				close >= prevOpen &&
				prevOpen > prevClose &&
				close > open &&
				prevClose >= open &&
				(close - open) > (prevOpen - prevClose)
				){
  			return date + " Bullish Engulfment Detected";
		}
		return null;
  	}
	
	public String detectMorningStar(
			String date, float open, float high, float low, float close, float prevOpen,
			float prevHigh, float prevLow, float prevClose, float prevOpen2, float prevHigh2, float prevLow2,
			float prevClose2
			) {
		
		if (Math.max(prevOpen, prevClose) < prevClose2 && prevClose2 < prevOpen2 && close > open &&
				open > Math.max(prevOpen, prevClose)) {
			return date + " Morning Star Detected";
		}
		
		return null;
	}

	public String detectEveningStar(
			String date, float open, float high, float low, float close, float prevOpen,
			float prevHigh, float prevLow, float prevClose, float prevOpen2, float prevHigh2, float prevLow2,
			float prevClose2
	      ) {
		
		if (Math.min(prevOpen, prevClose) > prevClose2 && prevClose2 > prevOpen2 && close < open &&
				open < Math.min(prevOpen, prevClose)) {
			return date + " Evening Star Detected";
		}
		
		return null;
	}
	
	// RSI CALCULATION ALGORITHM
  	public double[] calculateRSI(double[] closingPrices, int periodLength) {
      		double[] rsiValues = new double[closingPrices.length];

      		double prevAvgGain = 0;
      		double prevAvgLoss = 0;
      		double relativeStrength = 0;
      		double rsi = 0;

      		for (int i = periodLength; i < closingPrices.length; i++) {
          		if (i == periodLength) {
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
              			prevAvgGain = averageGain;
              			prevAvgLoss = averageLoss;

              			relativeStrength = averageGain / averageLoss;
              			rsi = 100 - (100 / (1 + relativeStrength));

              			rsiValues[i] = rsi;
          		} else if (i > periodLength) {
              			double diff = closingPrices[i] - closingPrices[i - 1];
              			double currGain = diff > 0 ? diff : 0;
              			double currLoss = diff < 0 ? Math.abs(diff) : 0;
              			prevAvgGain = (prevAvgGain * (periodLength - 1) + currGain) / periodLength;
              			prevAvgLoss = (prevAvgLoss * (periodLength - 1) + currLoss) / periodLength;
              			relativeStrength = prevAvgGain / prevAvgLoss;

              			rsi = 100 - (100 / (1 + relativeStrength));

              			rsiValues[i] = rsi;
          		}
      		}

      		return rsiValues;
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
	
	private double calculateMACD(double[] closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
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
	
	/*
	private List<Double> calcMACD(double[] closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
		List<Double> macdList = new ArrayList<>();
		
		for(int i = 0; i < closingPrices.length; i++) {
			macdList.add(calculateMACD(closingPrices, shortPeriod, longPeriod, signalPeriod));
		}
		
		return macdList;
	}
	*/
	
	//Front End EMA method  Link: https://is.gd/m2Q5WI
	public List<Double> calculateEMALine(double[] closingPrices, int periods) {
		
		List<Double> ema = new ArrayList<>();
		
		for(int i = 0; i < periods; i++) {
			ema.add(null);
		}
		
		ema.add(periods, closingPrices[0]);
		
		for(int j = periods + 1; j < closingPrices.length; j++) {
			double multiplier = 2.0 / (periods + 1.0);
			
			ema.add( (closingPrices[j] * multiplier) + (ema.get(j - 1) * (1 - multiplier)));
		}
		
		return ema;
  	}
	
	//Front end MACD method
	public List<Double> calculateMACDLine(
			double[] closingPrices, int shortPeriod, int longPeriod, int signalPeriod, int periods
			) {
		double[] shortEMA = calculateEMA(closingPrices, shortPeriod);
		double[] longEMA = calculateEMA(closingPrices, longPeriod);

		int minPeriod = Math.min(shortEMA.length, longEMA.length);
		
		//Signal period is the 9 day EMA. When MACD line crosses signal line, it indicates a buy or sell.
		
		List<Double> macdLine = new ArrayList<Double>();
		
		for(int i = 0; i < periods; i++) {
			macdLine.add(null);
		}
		
		for (int j = periods; j < minPeriod; j++) {
			macdLine.add( (shortEMA[j] - longEMA[j]));
		}
		
		return macdLine;
	}
	
	//THE RSI LINE TO BE PLOTTED IN FRONT END	
  	public List<Double> calculateRSILine(double[] closingPrices, int periodLength) {
  		List<Double> rsiValues = new ArrayList<Double>();

  		double prevAvgGain = 0.0;
  		double prevAvgLoss = 0.0;
  		double relativeStrength = 0.0;
  		double rsi = 0.0;
  		
  		for(int i = 0; i < periodLength; i++) {
  			rsiValues.add(null);
  		}

  		for (int i = periodLength; i < closingPrices.length; i++) {
  			if(i == periodLength){
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
  				prevAvgGain = averageGain;
  				prevAvgLoss = averageLoss;
    
  				relativeStrength = averageGain / averageLoss;
  				rsi = 100 - (100 / (1 + relativeStrength));
    
  				rsiValues.add(i, rsi);
  			} else if(i > periodLength){
  				double diff = closingPrices[i] - closingPrices[i - 1];
  				double currGain = diff > 0 ? diff : 0;
  				double currLoss = diff < 0 ? Math.abs(diff) : 0;
  				prevAvgGain = (prevAvgGain * (periodLength - 1) + currGain) / periodLength;
  				prevAvgLoss = (prevAvgLoss * (periodLength - 1) + currLoss) / periodLength;
  				relativeStrength = prevAvgGain / prevAvgLoss;
          
  				rsi = 100 - (100 / (1 + relativeStrength));

  				rsiValues.add(i, rsi);
  			}
  		}
    
  		return rsiValues;
  	}


	//Coding short term predictions via Technical Analysis.
	//Then Fundamental Analysis.
	//Lastly combining them to predict stocks accurately.
	
	//Calculate a simple moving average of a stock. 
	//Link: https://is.gd/O5XYS9
	
	//@param periods is the total number of times the stock prices goes into the sum before being divided by periods.
	//Gets closing price data
	private List<Double> simpleMovingAverage(double[] closeData, int periods) {
		
		List<Double> movAvgs = new ArrayList<>();
		
		for(int i = 0; i < closeData.length; i++) {
			
			//Adds null elements until period.
			if (i < periods) {
		        movAvgs.add(null);
		        continue;
		      }
			
			//Then adds to the sum the elements from index i - j to the array.
			
			double sum = 0.0;
			
			for(int j = 0; j < periods; j++) {
				sum += closeData[i - j];
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
	
	private List<Double> standardDeviation(double[] closeData, int periods) {
		
		List<Double> stdDevs = new ArrayList<>();
		List<Double> movAvgs = simpleMovingAverage(closeData, periods);
		
		for(int i = 0; i < closeData.length; i++) {
			
			if(i < periods) {
				stdDevs.add(null);
				continue;
			}
			
			double sumOfSquares = 0.0, variance = 0.0, standardDeviation = 0.0;
			
			for(int j = 0; j < periods; j++) {
				double sum = 0.0;
				
				sum += movAvgs.get(i) - closeData[i - j];
				sumOfSquares += Math.pow( sum, 2);
			}
			
			variance = sumOfSquares / periods;
			
			standardDeviation = Math.sqrt(variance);
			
			stdDevs.add(standardDeviation);
		}
		
		return stdDevs;
	}
	
	private List<Double> upperBollingerBand(double[] closeData, int periods) {
		//The middle bollinger band is the moving average
		List<Double> movAvg = simpleMovingAverage(priceData, periods);
		
		List<Double> stdDev = standardDeviation(priceData, periods);
		
		List<Double> upBollBand = new ArrayList<>();
		
		for(int i = 0; i < closeData.length; i++) {
			if(i < periods) {
				upBollBand.add(null);
				continue;
			}
			
			upBollBand.add(movAvg.get(i) + (stdDev.get(i) * 2));
		}
		
		return upBollBand;
	}
	
	private List<Double> lowerBollingerBand(double[] closeData, int periods) {
		List<Double> movAvg = simpleMovingAverage(priceData, periods);
		
		List<Double> stdDev = standardDeviation(priceData, periods);
		
		List<Double> lowBollBand = new ArrayList<>();
		
		for(int i = 0; i < closeData.length; i++) {
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
	
	//Calculate Fibonacci Retracement levels of a stock at 2 points. Updates only when a new high or low is detected.
	//Links: https://is.gd/aRjSTM && https://is.gd/D0sR5w
	//Discontinued until xChart updates with AnnotationLine features that allow for specific coordinate positions.
	
	/*
	private List<Double> calculateFibonacciRetracementLevels(double[] highPoints, double[] lowPoints, int fibLevel) {
		
		List<Double> highPointsList = Arrays.stream(highPoints).boxed().collect(Collectors.toList());
		double highPoint = highPointsList.stream().mapToDouble(d -> d).max().getAsDouble();
		
		List<Double> lowPointsList = Arrays.stream(lowPoints).boxed().collect(Collectors.toList());
		double lowPoint = lowPointsList.stream().mapToDouble(d -> d).min().getAsDouble();
		
		double range = highPoint - lowPoint;
		
		//Common levels from a high of 100% to a low of 0%;
		fibonacciRetracementLevels[0] = highPoint;
		fibonacciRetracementLevels[1] = highPoint - (0.236 * range);
		fibonacciRetracementLevels[2] = highPoint - (0.382 * range);
		fibonacciRetracementLevels[3] = highPoint - (0.5 * range);
		fibonacciRetracementLevels[4] = highPoint - (0.618 * range);
		fibonacciRetracementLevels[5] = highPoint - (0.786 * range);
		fibonacciRetracementLevels[6] = lowPoint;
		
		return Arrays.stream(fibonacciRetracementLevels).boxed().collect(Collectors.toList());
	}
	*/
	
	/*
	private double calculateFibonacciRetracementLevels(double highPoint, double lowPoint, int fibLevel) {
		
		double range = highPoint - lowPoint;
		
		//Common levels from a high of 100% to a low of 0%;
		fibonacciRetracementLevels[0] = highPoint;
		fibonacciRetracementLevels[1] = highPoint - (0.236 * range);
		fibonacciRetracementLevels[2] = highPoint - (0.382 * range);
		fibonacciRetracementLevels[3] = highPoint - (0.5 * range);
		fibonacciRetracementLevels[4] = highPoint - (0.618 * range);
		fibonacciRetracementLevels[5] = highPoint - (0.786 * range);
		fibonacciRetracementLevels[6] = lowPoint;
		
		return fibonacciRetracementLevels[fibLevel];
	}
	*/
	
	/*
	 * Public methods for user access:
	 * 
	 * Should return List<Double> for .addSeries() in `Main.java`
	*/
	
	public List<Double> getSimpleMovingAverage(double[] closeData, int periods) {
		return simpleMovingAverage(closeData, periods);
	}
	
	public List<Double> getStandardDeviation(double[] closeData, int periods) {
		return standardDeviation(closeData, periods);
	}
	
	public List<Double> getUpperBand(double[] closeData, int periods) {
		return upperBollingerBand(closeData, periods);
	}
	
	public List<Double> getLowerBand(double[] closeData, int periods) {
		return lowerBollingerBand(closeData, periods);
	}
	
	public List<Double> getMACD(
			double[] closingPrices, int shortPeriod, int longPeriod, int signalPeriod, int periods
			) {
		return calculateMACDLine(closingPrices, shortPeriod, longPeriod, signalPeriod, periods);
	}
	
	public List<Double> getEMA(double[] closeData, int periods) {
		return calculateEMALine(closeData, periods);
		//return Arrays.stream(calculateEMA(closeData, periods)).boxed().collect(Collectors.toList());
	}
	
	public List<Double> getRSI(double[] closeData, int periods) {
		return calculateRSILine(closeData, periods);
		//return Arrays.stream(calculateRSI(closeData, periods)).boxed().collect(Collectors.toList());
	}
	
	/*
	public List<Double> getFibonnaciLevel(double[] highPrice, double[] lowPrice, int level) {
		return calculateFibonacciRetracementLevels(highPrice, lowPrice, level);
	}
	*/
	
	/*
	public double getMovingAverage(List<Double> priceData, int periods) {
		return movingAverage(priceData, periods);
	}
	*/
	
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
	
	/*
	public double getFibonnaciLevel(double highPrice, double lowPrice, int level) {
		return calculateFibonacciRetracementLevels(highPrice, lowPrice, level);
	}
	*/
	
	public double[] getPriceData() {
		return priceData;
	}
	
	public int getPeriods() {
		return periods;
	}
	
	public void setPeriods(int numPeriods) {
		periods = numPeriods;
	}
	
	public int priceDataSize() {
		return priceData.length;
	}
	
	public double lastPrice() {
		return priceData[priceDataSize()-1];
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
