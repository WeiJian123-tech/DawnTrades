package Prototype_003;

/*
 * Part of program provided, derived, and modified from ChatGPT.
 */

import java.util.*;

public class BackEnd {

	private CandleStickDetectionAlgo cda;
	private double[] priceData;
	private int periods;
	
	public BackEnd(CandleStickDetectionAlgo cda, double[] priceData, int periods) {
		this.cda = cda;
		this.priceData = priceData;
		this.periods = periods;
	}
	
	public BackEnd(double[] priceData, int periods) {
		this.priceData = priceData;
		this.periods = periods;
	}
	
	public BackEnd(double[] priceData) {
		this(priceData, priceData.length);
	}
	
	public BackEnd() {
		this(new double[999], 999);
	}
	
	// RSI CALCULATION ALGORITHM
  	private double[] calculateRSI(double[] closingPrices, int periods) {
      		double[] rsiValues = new double[closingPrices.length];

      		double prevAvgGain = 0;
      		double prevAvgLoss = 0;
      		double relativeStrength = 0;
      		double rsi = 0;

      		for (int i = periods; i < closingPrices.length; i++) {
      			
          		if (i == periods) {
              			double gainSum = 0.0;
              			double lossSum = 0.0;

              			for (int j = i - periods; j < i; j++) {
                  		double priceDiff = closingPrices[j + 1] - closingPrices[j];
                  			if (priceDiff > 0) {
                      				gainSum += priceDiff;
                  			} else {
                      				lossSum -= priceDiff;
                  			}
              			}

              			double averageGain = gainSum / periods;
              			double averageLoss = lossSum / periods;
              			prevAvgGain = averageGain;
              			prevAvgLoss = averageLoss;

              			relativeStrength = averageGain / averageLoss;
              			rsi = 100 - (100 / (1 + relativeStrength));

              			rsiValues[i] = rsi;
          		}
          		
          		if (i > periods) {
          			double diff = closingPrices[i] - closingPrices[i - 1];
          			double currGain = diff > 0 ? diff : 0;
          			double currLoss = diff < 0 ? Math.abs(diff) : 0;
          			prevAvgGain = (prevAvgGain * (periods - 1) + currGain) / periods;
          			prevAvgLoss = (prevAvgLoss * (periods - 1) + currLoss) / periods;
          			relativeStrength = prevAvgGain / prevAvgLoss;

          			rsi = 100 - (100 / (1 + relativeStrength));

          			rsiValues[i] = rsi;
          		}
          		
          		
      		}

      		return rsiValues;
  	}
	
  	//MACD CALCULATION ALGORITHM (AND SEPARATE EMA ALGORITHM)
  	private static double[] calculateEMA(double[] closingPrices, int periods) {
		double[] ema = new double[closingPrices.length];
		
		double multiplier = 2.0 / (periods + 1);
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
	 * Thanks to Investopedia for "What is EMA? How to Use Exponential Moving Average With Formula" article
	 * by James Chen (https://www.investopedia.com/contributors/101529/)
	 * reviewed by Charles Potters (https://www.investopedia.com/charles-potters-4942512)
	 * fact checked by Ryan Eichler (https://www.investopedia.com/ryan-eichler-5217138):
	 * https://is.gd/m2Q5WI
	 */
	
	//Front End EMA method
	public List<Double> calculateEMALine(double[] closingPrices, int periods) {
		
		//long startTime = System.nanoTime();
		
		List<Double> ema = new ArrayList<>();
		
		for(int i = 0; i < periods; i++) {
			ema.add(null);
		}
		
		ema.add(periods, closingPrices[0]);
		
		for(int j = periods + 1; j < closingPrices.length; j++) {
			double multiplier = 2.0 / (periods + 1.0);
			
			ema.add( (closingPrices[j] * multiplier) + (ema.get(j - 1) * (1 - multiplier)));
		}
		
		//long endTime = System.nanoTime();
		
		//long totalTime = endTime - startTime;
		
		//System.out.println(totalTime);
		
		return ema;
  	}
	
	//Front end MACD method
	public List<Double> calculateMACDLine(
			double[] closingPrices, int shortPeriod, int longPeriod, int periods
			) {
		//Replace calculateEMA() to further align with front end methods and maybe delete back end methods.
		//Also possibly improve efficiency.
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
	
	/*
		Thanks to Geeks for Geeks for the article about the 
		Sliding Door Approach/Window Sliding Technique:
		https://www.geeksforgeeks.org/window-sliding-technique/#
		
		Thanks to MKYong for the article about 
		Reverse for loop: https://is.gd/GTBAgX 
	 * 
	 */
	//THE RSI LINE TO BE PLOTTED IN FRONT END	
  	public List<Double> calculateRSILine(double[] closingPrices, int periods) {
  		List<Double> rsiValues = new ArrayList<Double>();

  		double prevAvgGain = 0.0;
  		double prevAvgLoss = 0.0;
  		double relativeStrength = 0.0;
  		double rsi = 0.0;
  		
  		for(int i = 0; i < periods; i++) {
  			rsiValues.add(null);
  		}
  		
  		double gainSum = 0.0;
		double lossSum = 0.0;
		
		for(int j = periods - 1; j >= 0; j--) {
			double priceDiff = closingPrices[j + 1] - closingPrices[j];
			if (priceDiff > 0) {
				gainSum += priceDiff;
			} else {
				lossSum -= priceDiff;
			}
		}

		double averageGain = gainSum / periods;
		double averageLoss = lossSum / periods;
		prevAvgGain = averageGain;
		prevAvgLoss = averageLoss;

		relativeStrength = averageGain / averageLoss;
		rsi = 100 - (100 / (1 + relativeStrength));

		rsiValues.add(1, rsi);

  		for (int i = periods + 1; i < closingPrices.length; i++) {
  			
  			double diff = closingPrices[i] - closingPrices[i - 1];
			double currGain = diff > 0 ? diff : 0;
			double currLoss = diff < 0 ? Math.abs(diff) : 0;
			prevAvgGain = (prevAvgGain * (periods - 1) + currGain) / periods;
			prevAvgLoss = (prevAvgLoss * (periods - 1) + currLoss) / periods;
			relativeStrength = prevAvgGain / prevAvgLoss;
  
			rsi = 100 - (100 / (1 + relativeStrength));

			rsiValues.add(i, rsi);
  		}
    
  		return rsiValues;
  	}

	//Coding short term predictions via Technical Analysis.
	//Then Fundamental Analysis.
	//Lastly combining them to predict stocks accurately.
	
	//Calculate a simple moving average of a stock. 
	/*
	 * Thanks to Investopedia for their article about Simple Moving Average
	 * by Adam Hayes (https://www.investopedia.com/contributors/53677/)
	 * reviewed by Charles Potters (https://www.investopedia.com/charles-potters-4942512)
	 * fact checked by Katrina Munichiello (https://www.investopedia.com/katrina-munichiello-5078531)
	 * 
	 * Link: https://is.gd/O5XYS9
	 */
	
	//@param periods is the total number of times the stock prices goes into the sum before being divided by periods.
	//Gets closing price data
  	//Thanks to ChatGPT for providing the body of code. Modified to be inserted into a List.
	private List<Double> simpleMovingAverage(double[] closingPrices, int periods) {
		
		//long startTime = System.nanoTime();
		
		List<Double> movAvgs = new ArrayList<>();
		
		for(int i = 0; i < periods; i++) {
			movAvgs.add(null);
		}
		
		double sum = 0.0;
		
		for(int i = 0; i < periods; i++) {
			sum += closingPrices[i];
		}
		
		for(int i = periods; i < closingPrices.length; i++) {
			sum += closingPrices[i] - closingPrices[i - periods];
			movAvgs.add(sum / periods);
		}
		
		//long endTime = System.nanoTime();
		//long totalTime = endTime - startTime;
		//System.out.println("duration: " + totalTime);
		
		return movAvgs;
	}
	
	//Calculate a standard deviation of a stock.
	/*
	 * Thanks to Programiz for their article about how to calculate standard deviation in Java:
	 * https://is.gd/NErz9N
	 * 
	 * Thanks to Zaner for thier article about how to calculate standard deviation of a stock:
	 * https://is.gd/AkfTaX
	 */
	
	private List<Double> standardDeviation(double[] closingPrices, int periods) {
		
		List<Double> stdDevs = new ArrayList<>();
		
		for(int i = 0; i < periods; i++) {
			stdDevs.add(null);
		}
		
		double sum = 0.0, sumOfSquares = 0.0, standardDeviation = 0.0;
		
		//Sliding Door Approach
		for(int i = 0; i < periods; i++) {
			sum += closingPrices[i];
		}
		
		double average = sum / periods;
		
		for(int i = 0; i < periods; i++) {
			sumOfSquares += Math.pow((closingPrices[i] - average), 2);
		}
		
		for(int i = periods; i < closingPrices.length; i++) {
			double prevCloPrice = closingPrices[i - periods];
			double currCloPrice = closingPrices[i];
			
			sum += currCloPrice - prevCloPrice;
			
			average = sum / periods;
			
			sumOfSquares -= Math.pow((prevCloPrice - average), 2);
			sumOfSquares += Math.pow((currCloPrice - average), 2);
			
			standardDeviation = Math.sqrt(Math.abs(sumOfSquares) / periods);
			stdDevs.add(standardDeviation);
		}
		
		return stdDevs;
	}
	
	/*
	 * Thanks to Fidelity, Investopedia, and StockCharts for their articles about Bollinger Bands.
	 * 
	 * Links for Bollinger Bands:
	 * Fidelity summary: https://is.gd/mof0KQ
	 * Investopedia explanation: https://is.gd/cQeFkF
	 * StockCharts Website and Bollinger Bands Formula: https://is.gd/oFRc9L
	*/
	private List<Double> upperBollingerBand(double[] closingPrices, int periods) {
		//The middle bollinger band is the moving average
		List<Double> movAvg = simpleMovingAverage(closingPrices, periods);
		
		List<Double> stdDev = standardDeviation(closingPrices, periods);
		
		List<Double> upBollBand = new ArrayList<>();
		
		for(int i = 0; i < closingPrices.length; i++) {
			if(i < periods) {
				upBollBand.add(null);
				continue;
			}
			
			upBollBand.add(movAvg.get(i) + (stdDev.get(i) * 2));
		}
		
		return upBollBand;
	}
	
	private List<Double> lowerBollingerBand(double[] closingPrices, int periods) {
		List<Double> movAvg = simpleMovingAverage(closingPrices, periods);
		
		List<Double> stdDev = standardDeviation(closingPrices, periods);
		
		List<Double> lowBollBand = new ArrayList<>();
		
		for(int i = 0; i < closingPrices.length; i++) {
			if(i < periods) {
				lowBollBand.add(null);
				continue;
			}
			
			lowBollBand.add(movAvg.get(i) - (stdDev.get(i) * 2));
		}
		
		return lowBollBand;
	}
	
	/*
	 * Calculate Fibonacci Retracement levels of a stock at 2 points. Updates only when a new high or low is detected.
	 * 
	 * Thanks to Investopedia for their article about Fibonnaci Retracements
	 * by Cory Mitchell (https://www.investopedia.com/contributors/335/)
	 * reviewed by Chip Stapleton (https://www.investopedia.com/chip-stapleton-5120345)
	 * fact checked by Kirsten Rohrs Schmitt (https://www.investopedia.com/kirsten-schmitt-5078524):
	 * https://is.gd/aRjSTM
	 * 
	 * Thanks to Centerpoint by Clear Street for their article about Fibonnaci Retracements:
	 * https://is.gd/D0sR5w
	 */
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
	public List<Double> testList(double[] arr) {
		long startTime = System.nanoTime();
		
		List<Double> newList = new ArrayList<>();
		
		for(int i = 0; i < arr.length; i++) {
			
			if(arr[i] % 5 == 0) {
				newList.add(arr[i]);
			}
			
			for(int j = 0; j < arr.length; j++) {
				if(arr[i] % 5 != 0) {
					newList.add(arr[i]);
				}
			}
		}
		
		long endTime = System.nanoTime();
		long totalTime1 = endTime - startTime;
		System.out.println(totalTime1);
		
		startTime = System.nanoTime();
		
		List<Double> parsedList = Arrays.stream(arr).boxed().collect(Collectors.toList());
		
		endTime = System.nanoTime();
		long totalTime2 = endTime - startTime;
		
		System.out.println(totalTime2);
		System.out.println(
				"Which is faster? " +
		(totalTime1 < totalTime2? "for loop: " + totalTime1 : "Arrays.stream(): " + totalTime2)
				);
		
		return null;
	}
	*/
	
	/*
	 * Public methods for user access:
	 * 
	 * Should return List<Double> for .addSeries() in `Main.java`
	*/
	
	public List<Double> getSimpleMovingAverage(double[] closingPrices, int periods) {
		return simpleMovingAverage(closingPrices, periods);
	}
	
	public List<Double> getStandardDeviation(double[] closingPrices, int periods) {
		return standardDeviation(closingPrices, periods);
	}
	
	public List<Double> getUpperBand(double[] closingPrices, int periods) {
		return upperBollingerBand(closingPrices, periods);
	}
	
	public List<Double> getLowerBand(double[] closingPrices, int periods) {
		return lowerBollingerBand(closingPrices, periods);
	}
	
	public List<Double> getMACD(
			double[] closingPrices, int shortPeriod, int longPeriod, int periods
			) {
		return calculateMACDLine(closingPrices, shortPeriod, longPeriod, periods);
	}
	
	public List<Double> getEMA(double[] closingPrices, int periods) {
		return calculateEMALine(closingPrices, periods);
	}
	
	public List<Double> getRSI(double[] closingPrices, int periods) {
		return calculateRSILine(closingPrices, periods);
	}
	
	/*
	public List<Double> getFibonnaciLevel(double[] highPrice, double[] lowPrice, int level) {
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
	
	/*
	TRADING STRATEGY ALGORITHMS
	
	Multiple smaller strategies get their own algorithm.
	They are then called by a higher level algorithm that combines their outputs and makes a decision.

 	IMPORTANT NOTE:
 	Strategy and decision NOT guaranteed to be profitable! Further revision required by developers and/or end user.
	
	*/
	
	  private double smAverage(double[] closingPrices, int periods) {

	    double movingAverage = 0.0;

	    for (double prices : closingPrices) {
	      movingAverage += prices;
	    }

	    movingAverage /= periods;

	    return movingAverage;
  	}


	  public int smaStrategy(double[] pData, int currentIndex) {
	    double sma20 = smAverage(pData, 20);
	    double sma50 = smAverage(pData, 50);
	    double sma200 = smAverage(pData, 200);
	    double currPrice = pData[currentIndex];

	    if(currPrice < sma20 && sma20 < sma50 && sma50 < sma200){
	      return 1;
	    } else if(currPrice > sma20 && sma20 > sma50 && sma50 > sma200){
	      return -1;
	    }
	    return 0;
	  }

	    public int macdStrategy(double[] pData, int currentIndex) {
	    double[] prevPData = Arrays.copyOfRange(pData, 0, currentIndex - 1);
	    double prevMACD = calculateMACD(prevPData, 12, 24, 9);
	    double currMACD = calculateMACD(pData, 12, 24, 9);
	      if(prevMACD < 0 && currMACD > 0){
		return 1;
	      } else if(prevMACD > 0 && currMACD < 0){
		return -1;
	      }
	      return 0;
	  }

	  public void finalDecision(
			  String date, double[] open, double[] high, double[] low, double[] close, int currentIndex
			  ){
	    double[] rsiVals = calculateRSI(close, 14);
	    rsiVals = Arrays.copyOfRange(rsiVals, currentIndex - 2, currentIndex);

	    int smaSignal = smaStrategy(close, currentIndex);
	    int macdSignal = macdStrategy(close, currentIndex);

	    int i = currentIndex;
	    int j = currentIndex - 1;
	    String engulf = cda.detectEngulf(
	    		date, (float) open[i],
	    		(float) high[i], (float) low[i],
	    		(float) close[i], (float) open[j],
	    		(float) high[j], (float) low[j],
	    		(float) close[j]
	    				);
	    int engulfSignal = (engulf == null) ? 0 : (engulf.contains("Bull")) ? 1 : -1;

	    int currIndicator = (macdSignal != 0) ?
	    		macdSignal : (smaSignal != 0) ?
	    				smaSignal : (engulfSignal != 0) ?
	    						engulfSignal : 0;

	    if(currIndicator > 0 && Arrays.stream(rsiVals).min().getAsDouble() < 40){
	      System.out.println(date + " BUY");
	    } else if(currIndicator < 0 && Arrays.stream(rsiVals).max().getAsDouble() > 60){
	      System.out.println(date + " SELL");
	    } else{
	      System.out.println(date + " HOLD");
	    }
	  }
}
