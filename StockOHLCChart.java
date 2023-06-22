package Prototype_003;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class StockOHLCChart {

	public OHLCChart OHLCGraph(StockInput si, BackEnd tradeAlgo) {
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendLayout(LegendLayout.Vertical);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		//addSeries() only allows List<?> and List<Date> for second parameter. Not Date[].
		
		chart.addSeries(
				"Candlestick", si.getXDateList(), si.getOpenPriceList(), si.getHighPriceList(), si.getLowPriceList(),
				si.getClosePriceList()
				);
		
		if(si.getXDateList().size() < 30) {
			chart.addSeries("SMA5", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 5)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA10", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 10));
			chart.addSeries("SMA15", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 15));
		} else {
			chart.addSeries("SMA5", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 5)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA10", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 10)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA15", si.getXDateList(), tradeAlgo.getSimpleMovingAverage(si.getClosePrices(), 15)).setMarker(
					SeriesMarkers.NONE
					);
		}
		
		chart.addSeries("Upper Bollinger Band", si.getXDateList(), tradeAlgo.getUpperBand(si.getClosePrices(), 5)).setMarker(
				SeriesMarkers.NONE
				);
		chart.addSeries("Lower Bollinger Band", si.getXDateList(), tradeAlgo.getLowerBand(si.getClosePrices(), 5)).setMarker(
				SeriesMarkers.NONE
				);
		
		if(si.getXDateList().size() < 30) {
			chart.addSeries("EMA5", si.getXDateList(), tradeAlgo.getEMA(si.getClosePrices(), 5));
		} else {
			chart.addSeries("EMA5", si.getXDateList(), tradeAlgo.getEMA(si.getClosePrices(), 5)).setMarker(
					SeriesMarkers.NONE
					);
		}
		
		return chart;
	}
}
