package Prototype_003;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class OHLCXtraChart extends StockOHLCChart {

	public XYChart xtraGraph(StockInput si, BackEnd tradeAlgo) {
		//Create new Class.
		XYChart xtraChart = new XYChartBuilder().width(800).height(500).title("Extra Stock Indicators").build();
		
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
		xtraChart.addSeries("RSI", si.getXDateList(), tradeAlgo.getRSI(si.getClosePrices(), 5)).setMarker(SeriesMarkers.NONE);
		
		return xtraChart;
	}
}
