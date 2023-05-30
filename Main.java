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
		
		ArrayList<Double> yPrices = new ArrayList<>(
				List.of(9.23, 10.50, 8.75, 8.96, 7.70, 8.80, 9.02, 11.01, 10.97, 11.05, 8.08, 8.87, 7.46, 8.03)
				);
		
		BackEnd tradeAlgo = new BackEnd(yPrices);
		
		List<Date> xTime = new ArrayList<>();
		
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
		
		//System.out.println(time.toString());
		
		//Create the chart
		XYChart chart = new XYChartBuilder().
				width(800).height(600).title("Project GoldenTrades").
				xAxisTitle("Time").yAxisTitle("Stock Price").build();
		
		//Customize Chart
		chart.getStyler().setChartTitleVisible(true);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		
		//Format Y-axis to include a "$" sign and set Double value to two decimal places
		Function<Double, String> yTickFormatter = prices -> "$" + String.format("%.2f", prices);
		
		chart.getStyler().setyAxisTickLabelsFormattingFunction(yTickFormatter);
		
		//Create Title, X-Axis, and Y-Axis
		chart.addSeries("Test", xTime, yPrices);
		
		//Enables tooltips to hover over with mouse to view plotted information.
		chart.getStyler().setToolTipsEnabled(true);
		
		//Show the chart
		new SwingWrapper<XYChart>(chart).displayChart();
	}
	
}
