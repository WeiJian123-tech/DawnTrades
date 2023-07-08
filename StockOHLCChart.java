package Prototype_003;

import java.awt.Color;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

/*
 * Thanks to xChart and their developers/collaborators for their example code for aiding and guiding 
 * Wei Jian Zhen to be able to develop the main stock OHLC Chart for DawnTrades.
 * 
 * xChart Website:
 * https://knowm.org/open-source/xchart/
 * 
 * xChart Github Repository:
 * https://github.com/knowm/XChart
 * 
 * xChart example references:
 * 
 * 	LineChart05.java for Series Markers: 
 * 	https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 	xchart-demo/src/main/java/org/knowm/xchart/demo/charts/line/LineChart05.java#L4
 * 
 * 	OHLCChart01.java, OHLCChart02.java, and OHLCChart03.java for providing how to make OHLCCharts:
 * 	
 * 		OHLCChart01.java:
 * 		https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 		xchart-demo/src/main/java/org/knowm/xchart/demo/charts/ohlc/OHLCChart01.java#L22
 * 
 * 		OHLCChart02.java:
 * 		https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 		xchart-demo/src/main/java/org/knowm/xchart/demo/charts/ohlc/OHLCChart02.java#L50
 * 
 * 		OHLCChart03.java:
 * 		https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 		xchart-demo/src/main/java/org/knowm/xchart/demo/charts/ohlc/OHLCChart03.java#L51
 * 
 * 	SwingDemo.java for being able to create xChart panels that connect to TabbedFrontEnd.java:
 * 	https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 	xchart-demo/src/main/java/org/knowm/xchart/standalone/SwingDemo.java#L14
 */

public class StockOHLCChart {

	public OHLCChart OHLCGraph(StockInput si, BackEnd tradeAlgo) {
		
		//Build a new OHLC Chart
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("DawnTrades").build();
		
		//Style the OHLC Chart
		chart.getStyler().setPlotBackgroundColor(Color.WHITE);
		chart.getStyler().setPlotGridLinesColor(Color.LIGHT_GRAY);
		chart.getStyler().setAxisTickLabelsColor(Color.BLACK);
		chart.getStyler().setLegendBackgroundColor(Color.WHITE);
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendLayout(LegendLayout.Vertical);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		//addSeries() method only allows List<?> and List<Date> for second parameter. Not Date[].
		
		chart.addSeries(
				"Candlestick", si.getXDateList(), si.getOpenPriceList(), si.getHighPriceList(), si.getLowPriceList(),
				si.getClosePriceList()
				);
		
		chart.addSeries(
				"Upper Bollinger Band", si.getXDateList(), tradeAlgo.getUpperBand(si.getClosePrices(), 5)
				).setMarker(SeriesMarkers.NONE);
		chart.addSeries(
				"Lower Bollinger Band", si.getXDateList(), tradeAlgo.getLowerBand(si.getClosePrices(), 5)
				).setMarker(SeriesMarkers.NONE);
		
		/*
		If the number of stock data entries are greater than 30 days,
		the line to show the stock data will not have coordinates displayed.
		*/
		if(si.getXDateList().size() < 30) {
			chart.addSeries(
					"SMA5", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 5)
					);
			chart.addSeries("SMA10", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 10));
			chart.addSeries("SMA15", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 15));
			
			chart.addSeries("EMA5", si.getXDateList(), tradeAlgo.getEMA(si.getClosePrices(), 5));
		} else {
			chart.addSeries(
					"SMA5", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 5)
					).setMarker(SeriesMarkers.NONE);
			chart.addSeries(
					"SMA10", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 10)
					).setMarker(SeriesMarkers.NONE);
			chart.addSeries(
					"SMA15", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 15)
					).setMarker(SeriesMarkers.NONE);
			
			chart.addSeries("EMA5", si.getXDateList(), tradeAlgo.getEMA(si.getClosePrices(), 5)).setMarker(
					SeriesMarkers.NONE
					);
		}
		
		return chart;
	}
}
