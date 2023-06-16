package Prototype_003;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class StockOHLCChart {

	public OHLCChart OHLCGraph(StockInput si, BackEnd tradeAlgo) {
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendLayout(LegendLayout.Vertical);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		si.insertStockData(
				new File(
						"C:/Users/ZhenF/eclipse-workspace/AutomaticTradingProgram/src/Prototype_003/MSFT.csv"
						)
				);
		
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
		
		//addSeries() only allows List<?> and List<Date> for second parameter. Not Date[].
		
		chart.addSeries(
				"Candlestick", xTimeList, si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(),
				si.getClosePrices()
				);
		
		if(xTime.length < 30) {
			chart.addSeries("SMA5", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);;
			//chart.addSeries("SMA10", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 10));
			//chart.addSeries("SMA15", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 15));
		} else {
			chart.addSeries("SMA5", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);
			/*
			chart.addSeries("SMA10", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 10)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA15", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 15)).setMarker(
					SeriesMarkers.NONE
					);
			*/
		}
		
		/*
		chart.addSeries("Upper Bollinger Band", xTimeList, tradeAlgo.getUpperBand(closePrices, 5)).setMarker(
				SeriesMarkers.NONE
				);
		chart.addSeries("Lower Bollinger Band", xTimeList, tradeAlgo.getLowerBand(closePrices, 5)).setMarker(
				SeriesMarkers.NONE
				);
		*/
		
		/*
		if(xTime.length < 30) {
			chart.addSeries("EMA5", xTimeList, tradeAlgo.getEMA(closePrices, 5));
		} else {
			chart.addSeries("EMA5", xTimeList, tradeAlgo.getEMA(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);
		}
		*/
		
		/*
		XYChart xtraChart = new XYChartBuilder().width(800).height(500).title("Extra Stock Indicators").build();
		
		xtraChart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		xtraChart.getStyler().setLegendLayout(LegendLayout.Vertical);
		xtraChart.getStyler().setYAxisDecimalPattern("##.00");
		xtraChart.getStyler().setToolTipsEnabled(true);
		xtraChart.getStyler().setZoomEnabled(true);
		xtraChart.getStyler().setZoomResetByButton(true);
		xtraChart.getStyler().setZoomResetByDoubleClick(true);
		
		//Common MACD: 12, 26, 9
		xtraChart.addSeries("stdDev", xTimeList, tradeAlgo.getStandardDeviation(closePrices, 5)).setMarker(
				SeriesMarkers.NONE
				);
		xtraChart.addSeries("MACD", xTimeList, tradeAlgo.getMACD(closePrices, 12, 26, 5)).setMarker(
				SeriesMarkers.NONE
				);
		xtraChart.addSeries("EMA9 (Signal Line)", xTimeList, tradeAlgo.getEMA(closePrices, 9));
		xtraChart.addSeries("RSI", xTimeList, tradeAlgo.getRSI(closePrices, 5)).setMarker(SeriesMarkers.NONE);
		
		new SwingWrapper<XYChart>(xtraChart).displayChart();
		*/
		
		CandleStickDetectionAlgo cda = new CandleStickDetectionAlgo();
		
		CandleStickWindow csw = new CandleStickWindow(cda, xTime, openPrices, highPrices, lowPrices, closePrices);
		csw.createWindow();
		
		return chart;
	}
	
	private void populateData(
			Date[] xTime, double[] openPrices, double[] highPrices, double[] lowPrices, double[] closePrices,
			double[] adjClosePrices, BigInteger[] volumes, List<Date> xTimeList, List<String> datesList,
			List<Double> openPricesList, List<Double> highPricesList, List<Double> lowPricesList,
			List<Double> closePricesList, List<Double> adjClosePricesList, List<BigInteger> volumesList
			) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		
		for(int i = 0; i < datesList.size(); i++) {
			
			Date date = sdf.parse(datesList.get(i));
			xTimeList.add(date);
			
			xTime[i] = date;
			openPrices[i] = openPricesList.get(i);
			highPrices[i] = highPricesList.get(i);
			lowPrices[i] = lowPricesList.get(i);
			closePrices[i] = closePricesList.get(i);
			adjClosePrices[i] = adjClosePricesList.get(i);
			volumes[i] = volumesList.get(i);
		}
		
	}
}
