package Prototype_003;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class StockOHLCChart {

	public OHLCChart OHLCGraph(StockInput si, BackEnd tradeAlgo) {
		
		/*
		si.insertStockData(
				new File(
						"C:/Users/ZhenF/eclipse-workspace/AutomaticTradingProgram/src/Prototype_003/MSFT.csv"
						)
				);
		*/
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendLayout(LegendLayout.Vertical);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		/*
		try {
			si.populateData(
					si.getXDates(), si.getOpenPrices(), si.getHighPrices(), si.getLowPrices(),
					si.getClosePrices(), si.getAdjClosePrices(), si.getVolumes(), si.getXDateList(),
					si.getDates(), si.getOpenPriceList(), si.getHighPriceList(), si.getLowPriceList(),
					si.getClosePriceList(), si.getAdjClosePriceList(), si.getVolumeList()
					);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		*/
		
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
		
		//addSeries() only allows List<?> and List<Date> for second parameter. Not Date[].
		
		chart.addSeries(
				"Candlestick", si.getXDateList(), si.getOpenPriceList(), si.getHighPriceList(), si.getLowPriceList(),
				si.getClosePriceList()
				);
		/*
		if(xTime.length < 30) {
			chart.addSeries("SMA5", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA10", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 10));
			chart.addSeries("SMA15", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 15));
		} else {
			chart.addSeries("SMA5", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA10", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 10)).setMarker(
					SeriesMarkers.NONE
					);
			chart.addSeries("SMA15", xTimeList, tradeAlgo.getSimpleMovingAverage(closePrices, 15)).setMarker(
					SeriesMarkers.NONE
					);
		}
		
		chart.addSeries("Upper Bollinger Band", xTimeList, tradeAlgo.getUpperBand(closePrices, 5)).setMarker(
				SeriesMarkers.NONE
				);
		chart.addSeries("Lower Bollinger Band", xTimeList, tradeAlgo.getLowerBand(closePrices, 5)).setMarker(
				SeriesMarkers.NONE
				);
		
		if(xTime.length < 30) {
			chart.addSeries("EMA5", xTimeList, tradeAlgo.getEMA(closePrices, 5));
		} else {
			chart.addSeries("EMA5", xTimeList, tradeAlgo.getEMA(closePrices, 5)).setMarker(
					SeriesMarkers.NONE
					);
		}
		
		CandleStickDetectionAlgo cda = new CandleStickDetectionAlgo();
		
		CandleStickWindow csw = new CandleStickWindow(cda, xTime, openPrices, highPrices, lowPrices, closePrices);
		csw.createWindow();
		
		*/
		
		return chart;
	}
	
	protected void populateData(
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
