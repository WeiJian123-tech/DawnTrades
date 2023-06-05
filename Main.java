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

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.Color;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoField;

import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.Styler.LegendPosition;

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
		
		double[] yPrices = new double[] {
				10.0, 12.0, 23.0, 22.0, 16.0, 23.0, 21.0, 16.0, 17.0, 19.0
		};
		
		BackEnd tradeAlgo = new BackEnd(yPrices);
		
		Main mainChart = new Main();
		
		OHLCChart chart = mainChart.OHLCGraph(yPrices, tradeAlgo);
		
		new SwingWrapper<>(chart).displayChart();
		
	}
	
	private OHLCChart OHLCGraph(double[] yPrices, BackEnd tradeAlgo) {
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.InsideS);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		Date[] xTime = new Date[yPrices.length-1];
		double[] openData = new double[yPrices.length-1];
		double[] highData = new double[yPrices.length-1];
		double[] lowData = new double[yPrices.length-1];
		double[] closeData = new double[yPrices.length-1];
		
		populateData(yPrices, xTime, openData, highData, lowData, closeData);
		
		List<Date> xTimeList = Arrays.stream(xTime).collect(Collectors.toList());
		List<Double> openDataList = Arrays.stream(openData).boxed().collect(Collectors.toList());
		List<Double> highDataList = Arrays.stream(highData).boxed().collect(Collectors.toList());
		List<Double> lowDataList = Arrays.stream(lowData).boxed().collect(Collectors.toList());
		List<Double> closeDataList = Arrays.stream(closeData).boxed().collect(Collectors.toList());
		
		//addSeries() only allows List<?> and List<Date> for second parameter. Not Date[].
		chart.addSeries("Candlestick", xTimeList, openDataList, highDataList, lowDataList, closeDataList);
		chart.addSeries("SMA5", xTimeList, tradeAlgo.getMovingAverage(closeData, 5));
		//chart.addSeries("SMA10", xTimeList, tradeAlgo.getMovingAverage(closeData, 10));
		//chart.addSeries("SMA15", xTimeList, tradeAlgo.getMovingAverage(closeData, 15));
		
		//chart.addSeries("stdDev", xTimeList, tradeAlgo.getStandardDeviation(closeData, 5));
		//chart.addSeries("Upper Bollinger Band", xTimeList, tradeAlgo.getUpperBand(closeData, 5));
		//chart.addSeries("Lower Bollinger Band", xTimeList, tradeAlgo.getLowerBand(closeData, 5));
		
		/*
		System.out.println(tradeAlgo.getFibonnaciLevel(highData, lowData, 0).size());
		chart.addSeries("Fibonnacci Levels", xTimeList, tradeAlgo.getFibonnaciLevel(highData, lowData, 0));
		*/
		
		//chart.addSeries("MACD", xTime, tradeAlgo.calcMACD(closeData, 12, 26, 9));
		
		return chart;
	}
	
	public static void populateData(
			double[] yPrices, Date[] xTime, double[] openData,
			double[] highData, double[] lowData, double[] closeData
			) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(cal.getTime());
		
		double data = yPrices[0];
		
		for(int i = 0; i < yPrices.length-1; i++) {
			
			cal.add(Calendar.DATE, 1);
			
			xTime[i] = cal.getTime();
			
			double previous = yPrices[i];
			
			data = getNewClose(yPrices[i+1]);
			
			openData[i] = previous;
			
			highData[i] = getHigh(Math.max(previous, data), yPrices[i+1]);
			
			lowData[i] = getLow(Math.min(previous, data), yPrices[i+1]);
			
			closeData[i] = data;
		}
		
	}
	
	//Math.random() * 0.02 creates random variations of the chart each run.
	private static double getHigh(double close, double originalPrice) {
		return close + (originalPrice * 0.02);
	}
	
	private static double getLow(double close, double originalPrice) {
		return close - (originalPrice * 0.02);
	}
	
	private static double getNewClose(double nextPrice) {
		return nextPrice;
	}
	
	/*
	private static void lineGraph(List<Double> yPrices, List<Date> xTime) {
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//sdf.setTimeZone(TimeZone.getDefault());
		
		/*
		Date d = new Date();
		
		long second = 1000;
		
		long minute = 60000;
		
		long halfHour = 3600000 / 2;
		
		long hour = 3600000;
		
		System.out.println(sdf.format(d) + " " + sdf.format(d.getTime() - hour));
		*/ /*
		
		//Get current date and time.
		Calendar cal = Calendar.getInstance();
		
		//Set today's time to 9:00 A.M. GMT
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		//Start from 9:00 A.M. Increment every 30 minutes for each price index. Add each 30 minute interval to xTime.
		for(int i = 0; i < yPrices.size(); i++) {
			cal.add(Calendar.MINUTE, 30);
			xTime.add(cal.getTime());
		}
		
		//Create the line chart
		XYChart chart = new XYChartBuilder().
				width(800).height(600).title("Project GoldenTrades").
				xAxisTitle("Time").yAxisTitle("Stock Price").build();
		
		//Customize line chart
		chart.getStyler().setChartTitleVisible(true);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		
		//Format Y-axis to include a "$" sign and set Double value to two decimal places
		Function<Double, String> yTickFormatter = prices -> "$" + String.format("%.2f", prices);
		
		chart.getStyler().setyAxisTickLabelsFormattingFunction(yTickFormatter);
		
		//Create Title, X-Axis, and Y-Axis
		chart.addSeries("Test", xTime, yPrices);
		
		//Enables tooltips to hover over with mouse to view plotted information.
		chart.getStyler().setToolTipsEnabled(true);
		
		//Show the line chart
		new SwingWrapper<XYChart>(chart).displayChart();
	}
	*/
	
}
