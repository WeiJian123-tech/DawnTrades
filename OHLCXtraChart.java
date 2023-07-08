package Prototype_003;

import java.awt.Color;

import org.knowm.xchart.*;
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
 * 	SwingDemo.java for being able to create xChart panels that connect to TabbedFrontEnd.java:
 * 	https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 	xchart-demo/src/main/java/org/knowm/xchart/standalone/SwingDemo.java#L14
 * 
 * 	XChartPanel.java for Zoom Functionality for Line Charts:
 * 	https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
 * 	xchart/src/main/java/org/knowm/xchart/XChartPanel.java#L55
 */

public class OHLCXtraChart extends StockOHLCChart {

	public XYChart xtraGraph(StockInput si, BackEnd tradeAlgo) {
		//Builds a new line chart.
		XYChart xtraChart = new XYChartBuilder().width(800).height(500).title("Extra Stock Indicators").build();
		
		//Styles the line chart with zoom enabled.
		xtraChart.getStyler().setPlotBackgroundColor(Color.WHITE);
		xtraChart.getStyler().setPlotGridLinesColor(Color.LIGHT_GRAY);
		xtraChart.getStyler().setAxisTickLabelsColor(Color.BLACK);
		xtraChart.getStyler().setLegendBackgroundColor(Color.WHITE);
		xtraChart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		xtraChart.getStyler().setLegendLayout(LegendLayout.Vertical);
		xtraChart.getStyler().setYAxisDecimalPattern("##.00");
		xtraChart.getStyler().setToolTipsEnabled(true);
		xtraChart.getStyler().setZoomEnabled(true);
		xtraChart.getStyler().setZoomResetByButton(true);
		xtraChart.getStyler().setZoomResetByDoubleClick(true);
		
		//Common MACD: 12, 26, 9
		xtraChart.addSeries(
				"stdDev", si.getXDateList(), tradeAlgo.getStandardDeviation(si.getClosePrices(), 5)
				).setMarker(SeriesMarkers.NONE);
		
		xtraChart.addSeries("MACD", si.getXDateList(), tradeAlgo.getMACD(si.getClosePrices(), 12, 26, 5)).setMarker(
				SeriesMarkers.NONE
				);
		xtraChart.addSeries("EMA9 (Signal Line)", si.getXDateList(), tradeAlgo.getEMA(si.getClosePrices(), 9));
		xtraChart.addSeries(
				"RSI", si.getXDateList(), tradeAlgo.getRSI(si.getClosePrices(), 5)
				).setMarker(SeriesMarkers.NONE);
		
		return xtraChart;
	}
}
