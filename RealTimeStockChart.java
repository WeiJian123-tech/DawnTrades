package Prototype_003;

import java.util.*;
import javax.swing.SwingWorker;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class RealTimeStockChart {
	
	stockSwingWorker ssw;
	SwingWrapper<XYChart> sw;
	XYChart chart;
	
	public SwingWorkerRealTime realTimeChart(double[] yPrices, BackEnd tradeAlgo) {
		
		SwingWorkerRealTime swrt = new SwingWorkerRealTime();
		
		chart = QuickChart.getChart(
				"Real Time Stock Chart", "Time", " Stock Prices", "Stock Value", yPrices, yPrices
				);
		
		sw = new SwingWrapper<XYChart>(chart);
		
		sw.displayChart();
		
		ssw = new stockSwingWorker();
		ssw.execute();
		
		return swrt;
	}
	
	private class stockSwingWorker extends SwingWorker<Boolean, double[]> {

		LinkedList<Double> stockList = new LinkedList<>(); 
		
		public stockSwingWorker() {
			stockList.add(0.0);
		}
		
		@Override
		protected Boolean doInBackground() throws Exception {
			
			do {
				stockList.add(stockList.get(stockList.size()-1) + Math.random() - .5);
				
				if(stockList.size() > 500) {
					stockList.removeFirst();
				}
				
				double[] arr = new double[stockList.size()];
				
				for(int i = 0; i < stockList.size(); i++) {
					arr[i] = stockList.get(i);
				}
				
				publish(arr);
				
				try {
					Thread.sleep(5);
		        	} catch (InterruptedException e) {
		        		// eat it. caught when interrupt is called
		        		System.out.println("SwingWorker shut down.");
		        	}
				 
			} while(!isCancelled());
			
			return true;
		}
		
		@Override
	    protected void process(List<double[]> chunks) {
	 
	      System.out.println("number of chunks: " + chunks.size());
	 
	      double[] mostRecentDataSet = chunks.get(chunks.size() - 1);
	 
	      chart.updateXYSeries("Stock Value", null, mostRecentDataSet, null);
	      sw.repaintChart();
	 
	      long start = System.currentTimeMillis();
	      long duration = System.currentTimeMillis() - start;
	      try {
	        Thread.sleep(40 - duration); // 40 ms ==> 25fps
	        // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
	      } catch (InterruptedException e) {
	      }
	 
	    }
		
	}
}
