package Prototype_003;

import java.util.*;
import java.io.*;
import java.math.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StockInput {
	
	private List<String> dateStringList;
	private List<Double> openPriceList;
	private List<Double> highPriceList;
	private List<Double> lowPriceList;
	private List<Double> closePriceList;
	private List<Double> adjClosePriceList;
	private List<BigInteger> volumeList;
	private List<Date> xDateList;
	private Date[] xDates;
	private double[] openPrices;
	private double[] highPrices;
	private double[] lowPrices;
	private double[] closePrices;
	private double[] adjClosePrices;
	private BigInteger[] volumes;
	private SimpleDateFormat sdf;
	
	public StockInput() {
		dateStringList = new ArrayList<>();
		openPriceList = new ArrayList<>();
		highPriceList = new ArrayList<>();
		lowPriceList = new ArrayList<>();
		closePriceList = new ArrayList<>();
		adjClosePriceList = new ArrayList<>();
		volumeList = new ArrayList<>();
		xDateList = new ArrayList<>();
		xDates = new Date[xDateList.size()];
		openPrices = new double[openPriceList.size()];
		highPrices = new double[highPriceList.size()];
		lowPrices = new double[lowPriceList.size()];
		closePrices = new double[closePriceList.size()];
		adjClosePrices = new double[adjClosePriceList.size()];
		volumes = new BigInteger[volumeList.size()];
		sdf = new SimpleDateFormat("yyy-MM-dd");
	}
	
	/**
	 * This method takes in a .csv file and parses the data into Lists and Arrays.
	 * Reads the data with a Scanner and adds the data to ArrayLists.
	 * Then copies the data from ArrayLists to Arrays for method/class use.
	 * @param stockData
	 */
	public void insertStockData(File stockData) {
		
		dateStringList = new ArrayList<>();
		openPriceList = new ArrayList<>();
		highPriceList = new ArrayList<>();
		lowPriceList = new ArrayList<>();
		closePriceList = new ArrayList<>();
		adjClosePriceList = new ArrayList<>();
		volumeList = new ArrayList<>();
		
		try {
			Scanner input = new Scanner(stockData);
			
			//Separates data from .csv file if there is a comma afterwards or a new line.
			input.useDelimiter(",|\n");
			input.useLocale(Locale.getDefault());
			
			//Skips the first row that describes the type of data
			input.skip("Date,Open,High,Low,Close,Adj Close,Volume");
			
			while(input.hasNext()) {
				//System.out.println(input.next());
				
				dateStringList.add(input.next());
				openPriceList.add(Double.valueOf(input.next()));
				highPriceList.add(Double.valueOf(input.next()));
				lowPriceList.add(Double.valueOf(input.next()));
				closePriceList.add(Double.valueOf(input.next()));
				adjClosePriceList.add(Double.valueOf(input.next()));
				volumeList.add(new BigInteger(input.next()));
				
			}
			
			//System.out.println(volumes.toString());
			
			populateData(
					dateStringList,
					openPriceList, highPriceList, lowPriceList,
					closePriceList, adjClosePriceList, volumeList
					);
			
			input.close();
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		} catch(ParseException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Gets stock data in the form of Lists. 
	 * Creates a new Lists for the x axis of xChart OHLC Charts called xDateList.
	 * Copies parameter list data to arrays via a regular for loop.
	 * @param dateStringList
	 * @param openPriceList
	 * @param highPriceList
	 * @param lowPriceList
	 * @param closePriceList
	 * @param adjClosePriceList
	 * @param volumeList
	 * @throws ParseException
	 */
	protected void populateData(
			List<String> dateStringList, List<Double> openPriceList, List<Double> highPriceList,
			List<Double> lowPriceList, List<Double> closePriceList, List<Double> adjClosePriceList,
			List<BigInteger> volumeList
			) throws ParseException {
		
		this.xDateList = new ArrayList<>();
		
		this.xDates = new Date[dateStringList.size()];
		this.openPrices = new double[openPriceList.size()];
		this.highPrices = new double[highPriceList.size()];
		this.lowPrices = new double[lowPriceList.size()];
		this.closePrices = new double[closePriceList.size()];
		this.adjClosePrices = new double[adjClosePriceList.size()];
		this.volumes = new BigInteger[volumeList.size()];
		
		/*
		 * Thanks to xChart and their developers/collaborators for their example code,
		 * particularly OHLCChart01's populateData() method for showing how to fill in
		 * array data for xChart OHLC Chart and line chart.
		 * 
		 * OHLCChart01.java:
		 * 	https://github.com/knowm/XChart/blob/9abbb92d37c69cd305f00fe10ff090cabda81ee5/
		 * 	xchart-demo/src/main/java/org/knowm/xchart/demo/charts/ohlc/OHLCChart01.java#L22
		 * 
		 * Modified method to get additional lists and list types from parameters.
		 * Replaced Calendar with just Date object type.
		 * Copies ArrayList elements to arrays instead of adding data to new Lists.
		 */
		
		this.sdf = new SimpleDateFormat("yyy-MM-dd");
		
		for(int i = 0; i < dateStringList.size(); i++) {
			
			Date date = sdf.parse(dateStringList.get(i));
			xDateList.add(date);
			
			xDates[i] = date;
			openPrices[i] = openPriceList.get(i);
			highPrices[i] = highPriceList.get(i);
			lowPrices[i] = lowPriceList.get(i);
			closePrices[i] = closePriceList.get(i);
			adjClosePrices[i] = adjClosePriceList.get(i);
			volumes[i] = volumeList.get(i);
		}
		
	}
	
	/*
	 * Getters for Lists and Arrays that contain stock data.
	 */
	
	/**
	 * Getter for List containing stock dates as String data types.
	 * @return dateStringList 
	 */
	public List<String> getDates() {
		return dateStringList;
	}
	
	/**
	 * Getter for List containing open prices of a stock as Double data types.
	 * @return openPriceList
	 */
	public List<Double> getOpenPriceList() {
		return openPriceList;
	}
	
	/**
	 * Getter for List containing high prices of a stock as Double data types.
	 * @return highPriceList
	 */
	public List<Double> getHighPriceList() {
		return highPriceList;
	}
	
	/**
	 * Getter for List containing low prices of a stock as Double data types.
	 * @return lowPriceList
	 */
	public List<Double> getLowPriceList() {
		return lowPriceList;
	}
	
	/**
	 * Getter for List containing close prices of a stock as Double data types.
	 * @return closePriceList
	 */
	public List<Double> getClosePriceList() {
		return closePriceList;
	}
	
	/**
	 * Getter for List containing adjusted close prices of a stock as Double data types.
	 * @return adjClosePriceList
	 */
	public List<Double> getAdjClosePriceList() {
		return adjClosePriceList;
	}
	
	/**
	 * Getter for List containing volumes of a stock as BigInteger data types.
	 * @return volumeList
	 */
	public List<BigInteger> getVolumeList() {
		return volumeList;
	}
	
	/**
	 * Getter for List containing stock dates as Date data types.
	 * Primarily for xChart OHLC Charts.
	 * @return xDateList
	 */
	public List<Date> getXDateList() {
		return xDateList;
	}
	
	/**
	 * Getter for array containing stock dates as Date data types.
	 * @return xDates.
	 */
	public Date[] getXDates() {
		return xDates;
	}
	
	/**
	 * Getter for an array of type double containing open prices.
	 * @return openPrices
	 */
	public double[] getOpenPrices() {
		return openPrices;
	}
	
	/**
	 * Getter for an array of type double containing high prices.
	 * @return highPries
	 */
	public double[] getHighPrices() {
		return highPrices;
	}
	
	/**
	 * Getter for an array of type double containing low prices.
	 * @return lowPrices
	 */
	public double[] getLowPrices() {
		return lowPrices;
	}
	
	/**
	 * Getter for an array of type double containing close prices.
	 * @return closePrices
	 */
	public double[] getClosePrices() {
		return closePrices;
	}
	
	/**
	 * Getter for an array of type double containing adjusted close prices.
	 * @return adjClosePrices
	 */
	public double[] getAdjClosePrices() {
		return adjClosePrices;
	}
	
	/**
	 * Getter for an array of type BigInteger containing volumes of a stock.
	 * @return volumes
	 */
	public BigInteger[] getVolumes() {
		return volumes;
	}
}
