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
import java.util.stream.Stream;
import java.awt.Color;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoField;

import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.internal.*;

public class Main {

	public static void main(String[] args) {
		
		List<Double> yPrices = new ArrayList<>(
				List.of(
						9.23, 10.50, 8.75, 8.96, 7.70, 8.80, 9.02, 11.01, 10.97, 11.05, 8.08, 8.87, 7.46, 8.03,
						9.00, 8.64, 8.45, 8.55, 8.99, 8.79, 9.02, 9.55, 10.01, 10.5, 10.25
						)
				);
		
		BackEnd tradeAlgo = new BackEnd(yPrices);
		
		Main mainChart = new Main();
		
		OHLCChart chart = mainChart.OHLCGraph(yPrices, tradeAlgo);
		
		new SwingWrapper<>(chart).displayChart();
		
	}
	
	private OHLCChart OHLCGraph(List<Double> yPrices, BackEnd tradeAlgo) {
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.InsideS);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		List<Date> xTime = new ArrayList<>();
		List<Double> openData = new ArrayList<>();
		List<Double> highData = new ArrayList<>();
		List<Double> lowData = new ArrayList<>();
		List<Double> closeData = new ArrayList<>();
		
		populateData(yPrices, xTime, openData, highData, lowData, closeData);
		
		chart.addSeries("Candlestick", xTime, openData, highData, lowData, closeData);
		chart.addSeries("SMA5", xTime, tradeAlgo.getMovingAverage(closeData, 5));
		chart.addSeries("SMA10", xTime, tradeAlgo.getMovingAverage(closeData, 10));
		chart.addSeries("SMA15", xTime, tradeAlgo.getMovingAverage(closeData, 15));
		chart.addSeries("stdDev", xTime, tradeAlgo.getStandardDeviation(closeData, 5));
		
		return chart;
	}
	
	private static void populateData(
			List<Double> yPrices, List<Date> xTime, List<Double> openData,
			List<Double> highData, List<Double> lowData, List<Double> closeData
			) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date todaysDate = sdf.parse("2023-06-01");
			
			populateData(todaysDate, yPrices, xTime, openData, highData, lowData, closeData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void populateData(
			Date startDate, List<Double> yPrices, List<Date> xTime, List<Double> openData,
			List<Double> highData, List<Double> lowData, List<Double> closeData
			) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(startDate);
		
		double data = yPrices.get(0);
		
		for(int i = 0; i < yPrices.size()-1; i++) {
			
			cal.add(Calendar.DATE, 1);
			
			xTime.add(cal.getTime());
			
			double previous = yPrices.get(i);
			
			data = getNewClose(yPrices.get(i+1));
			
			openData.add(previous);
			
			highData.add( getHigh(Math.max(previous, data), yPrices.get(i+1)) );
			
			lowData.add( getLow(Math.min(previous, data), yPrices.get(i+1)) );
			
			closeData.add(data);
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
		*/
		
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
	
}
