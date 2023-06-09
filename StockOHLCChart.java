package Prototype_003;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class StockOHLCChart {

	public OHLCChart OHLCGraph(double[] yPrices, BackEnd tradeAlgo) {
		
		OHLCChart chart = new OHLCChartBuilder().width(800).height(600).title("Project GoldenTrades").build();
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setLegendLayout(LegendLayout.Vertical);
		chart.getStyler().setYAxisDecimalPattern("##.00");
		chart.getStyler().setToolTipsEnabled(true);
		
		Date[] xTime = new Date[yPrices.length-1];
		double[] openData = new double[yPrices.length-1];
		double[] highData = new double[yPrices.length-1];
		double[] lowData = new double[yPrices.length-1];
		double[] closeData = new double[yPrices.length-1];
		
		populateData(tradeAlgo, yPrices, xTime, openData, highData, lowData, closeData);
		
		List<Date> xTimeList = arrToList(xTime);
		List<Double> openDataList = arrToList(openData);
		List<Double> highDataList = arrToList(highData);
		List<Double> lowDataList = arrToList(lowData);
		List<Double> closeDataList = arrToList(closeData);
		
		//addSeries() only allows List<?> and List<Date> for second parameter. Not Date[].
		chart.addSeries("Candlestick", xTimeList, openDataList, highDataList, lowDataList, closeDataList);
		chart.addSeries("SMA5", xTimeList, tradeAlgo.getSimpleMovingAverage(closeData, 5));
		//chart.addSeries("SMA10", xTimeList, tradeAlgo.getSimpleMovingAverage(closeData, 10));
		//chart.addSeries("SMA15", xTimeList, tradeAlgo.getSimpleMovingAverage(closeData, 15));
		
		/*
		chart.addSeries("stdDev", xTimeList, tradeAlgo.getStandardDeviation(closeData, 5)).setMarker(
				SeriesMarkers.NONE
				);
		chart.addSeries("Upper Bollinger Band", xTimeList, tradeAlgo.getUpperBand(closeData, 5)).setMarker(
				SeriesMarkers.NONE
				);
		chart.addSeries("Lower Bollinger Band", xTimeList, tradeAlgo.getLowerBand(closeData, 5)).setMarker(
				SeriesMarkers.NONE
				);
		*/
		
		//Common MACD: 12, 26, 9
		
		/*
		chart.addSeries("MACD", xTimeList, tradeAlgo.getMACD(closeData, 12, 26, 9, 5)).setMarker(SeriesMarkers.NONE);
		chart.addSeries("EMA9 (Signal Line)", xTimeList, tradeAlgo.getEMA(closeData, 9));
		chart.addSeries("EMA5", xTimeList, tradeAlgo.getEMA(closeData, 5));
		chart.addSeries("RSI", xTimeList, tradeAlgo.getRSI(closeData, 5)).setMarker(SeriesMarkers.NONE);
		*/
		
		CandleStickDetectionAlgo cda = new CandleStickDetectionAlgo();
		
		CandleStickWindow csw = new CandleStickWindow(cda, xTime, openData, highData, lowData, closeData);
		csw.createWindow();
		
		
		return chart;
	}
	
	private static void populateData(
			BackEnd tradeAlgo, double[] yPrices, Date[] xTime,
			double[] openData, double[] highData, double[] lowData,
			double[] closeData
			) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(cal.getTime());
		
		double data = yPrices[0];
		
		for(int i = 0; i < yPrices.length-1; i++) {
			
			cal.add(Calendar.DATE, 1);
			
			xTime[i] = cal.getTime();
			
			double previous = yPrices[i];
			
			data = tradeAlgo.getNewClose(yPrices[i+1]);
			
			openData[i] = previous;
			
			highData[i] = tradeAlgo.getHigh(Math.max(previous, data), yPrices[i+1]);
			
			lowData[i] = tradeAlgo.getLow(Math.min(previous, data), yPrices[i+1]);
			
			closeData[i] = data;
		}
		
	}
	
	private List<Date> arrToList(Date[] arr) {
		return Arrays.stream(arr).collect(Collectors.toList());
	}
	
	private List<Double> arrToList(double[] arr) {
		return Arrays.stream(arr).boxed().collect(Collectors.toList());
	}

}
