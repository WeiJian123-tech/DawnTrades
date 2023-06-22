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
	
	public List<String> getDates() {
		return dateStringList;
	}
	
	public List<Double> getOpenPriceList() {
		return openPriceList;
	}
	
	public List<Double> getHighPriceList() {
		return highPriceList;
	}
	
	public List<Double> getLowPriceList() {
		return lowPriceList;
	}
	
	public List<Double> getClosePriceList() {
		return closePriceList;
	}
	
	public List<Double> getAdjClosePriceList() {
		return adjClosePriceList;
	}
	
	public List<BigInteger> getVolumeList() {
		return volumeList;
	}
	
	public List<Date> getXDateList() {
		return xDateList;
	}
	
	public Date[] getXDates() {
		return xDates;
	}
	
	public double[] getOpenPrices() {
		return openPrices;
	}
	
	public double[] getHighPrices() {
		return highPrices;
	}
	
	public double[] getLowPrices() {
		return lowPrices;
	}
	
	public double[] getClosePrices() {
		return closePrices;
	}
	
	public double[] getAdjClosePrices() {
		return adjClosePrices;
	}
	
	public BigInteger[] getVolumes() {
		return volumes;
	}
}
