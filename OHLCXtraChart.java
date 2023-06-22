package Prototype_003;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		
		/*
		List<Date> xTimeList = new ArrayList<>();
		
		Date[] xTime = new Date[si.getDates().size()];
		double[] openPrices = new double[si.getOpenPrices().size()];
		double[] highPrices = new double[si.getHighPrices().size()];
		double[] lowPrices = new double[si.getLowPrices().size()];
		double[] closePrices = new double[si.getClosePrices().size()];
		double[] adjClosePrices = new double[si.getAdjClosePrices().size()];
		BigInteger[] volumes = new BigInteger[si.getVolumes().size()];
		
		try {
			populateData(
					xTime, openPrices, highPrices, lowPrices, closePrices, adjClosePrices, volumes, xTimeList,
					si.getDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(), si.getClosePrices(),
					si.getAdjClosePrices(), si.getVolumes()
					);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		*/
		
		//Common MACD: 12, 26, 9
		xtraChart.addSeries(
				"stdDev", si.getXDateList(), tradeAlgo.getStandardDeviation(si.getClosePrices(), 5)
				).setMarker(SeriesMarkers.NONE);
		
		/*
		xtraChart.addSeries("MACD", xTimeList, tradeAlgo.getMACD(closePrices, 12, 26, 5)).setMarker(
				SeriesMarkers.NONE
				);
		xtraChart.addSeries("EMA9 (Signal Line)", xTimeList, tradeAlgo.getEMA(closePrices, 9));
		xtraChart.addSeries("RSI", xTimeList, tradeAlgo.getRSI(closePrices, 5)).setMarker(SeriesMarkers.NONE);
		*/
		
		return xtraChart;
	}
}
