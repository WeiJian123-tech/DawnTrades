package Prototype_003;

import java.util.Date;
import java.util.List;

import javax.swing.JLabel;

public class CandleStickDetectionAlgo extends TabbedFrontEnd {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private double open;
	private double high;
	private double low;
	private double close;
	private List<String> dateList;
	private double[] openData;
	private double[] highData;
	private double[] lowData;
	private double[] closeData;
	private StringBuilder sb;
	
	public CandleStickDetectionAlgo(
			String date, double open, double high, double low, double close,
			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
			) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.dateList = dateList;
		this.openData = openData;
		this.highData = highData;
		this.lowData = lowData;
		this.closeData = closeData;
		this.sb = new StringBuilder();
	}

	public CandleStickDetectionAlgo(String date, double open, double high, double low, double close) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.sb = new StringBuilder();
	}
	
	public CandleStickDetectionAlgo() {
		this("", 0.0, 0.0, 0.0, 0.0);
	}
	
	/*
	CANDLESTICK PATTERN DETECTION ALGORITHMS

	Things to do:
	  - Get better calculations/detection results
	  - Make functions work with data in Backend Class instance fields
	  - Improve return types
	  - Possible change date format
  
  	*/
	
  	//Uses Ideal definition of Marubozu Candles
  	public String detectMarubozu(String date, double open, double high, double low, double close) {
  	    double bodyLength = Math.abs(close - open);
  	    double totalLength = Math.abs(high - low); //Potential future use
  	    double upperShadow = Math.abs(high - Math.max(open, close));
  	    double lowerShadow = Math.abs(Math.min(open, close) - low);
  	    //double variance = 3 * (Math.abs(open - close) + 15 / 2) / 2;  //Potential future use
  	    String s = null;

  	    boolean isBull = (low == open || high == close);
  	    boolean isFull = (bodyLength == totalLength);
  	    boolean topWick = (upperShadow <= 0.1 * bodyLength && lowerShadow == 0);
  	    boolean bottomWick = (lowerShadow <= 0.1 * bodyLength && upperShadow == 0);

  	    if (isBull) {
  	        s = isFull ? 
  	        		date + " Bull Marubozu Full Detected" : topWick ?
  	        				date + " Bull Marubozu Open Detected" : bottomWick ?
  	        						date + " Bull Marubozu Close Detected" : null;
  	    } else {
  	        s = isFull ?
  	        		date + " Bear Marubozu Full Detected" : topWick ?
  	        				date + " Bear Marubozu Close Detected" : bottomWick ?
  	        						date + " Bear Marubozu Open Detected" : null;
  	    }
  	    return s;
  	}
  	
  	//To display to the end user for all candlesticks presented on the OHLCChart
  	//Concatenate all non null strings: https://is.gd/WJi63O
  	public void displayMarubozu(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length; i++) {
  			if(detectMarubozu(dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]) != null) {
  				String detectMarb = detectMarubozu(
						dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]
						);
  				
  				JLabel marbLabel = new JLabel(detectMarb);
  				
  				TabbedFrontEnd.csPanel.add(marbLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}

  	//Detects Candles that are very close to being Marubozu even if they aren't by strict definition
  	public String detectMarubozuFlexible(String date, double open, double high, double low, double close) {
  		double bodyLength = Math.abs(close - open);
  		//double totalLength = high - low;  //Potential future use
  		double upperShadow = Math.abs(high - Math.max(open, close));
  		double lowerShadow = Math.abs(Math.min(open, close) - low);
  		//double variance = 3 * (Math.abs(open - close) + 15 / 2) / 2;  //Potential future use

  		if ((close - open > 0) && (upperShadow <= 0.1 * bodyLength && lowerShadow <= 0.1 * bodyLength)) {
  			return date + " Bull Marubozu Detected";
  		}
  		
  		if ((close - open < 0) && (upperShadow <= 0.1 * bodyLength && lowerShadow <= 0.1 * bodyLength)) {
  			return date + " Bear Marubozu Detected";
  		}
  		
  		return null;
  	}
  	
  	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayMarubozuFlexible(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length; i++) {
  			if(detectMarubozuFlexible(dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]) != null) {
  				String detectMarbFlex = detectMarubozuFlexible(
							dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]
							);
  				
  				JLabel marbFlexLabel = new JLabel(detectMarbFlex);
  				
  				TabbedFrontEnd.csPanel.add(marbFlexLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  			
  		}
  	}

  	public String detectDoji(String date, double open, double high, double low, double close){
  		if(
			(Math.abs(close - open) / (high - low) < 0.1) &&
			((high - Math.max(close, open)) > (3 * Math.abs(close - open))) &&
			((Math.min(close, open) - low) > (3 * Math.abs(close - open)))
			) {
  			return date + " Doji Detected";
  		}
  		
  		return null;
  	}
  	
  	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayDoji(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length; i++) {
  			if(detectDoji(dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]) != null) {
  				String detectDoji = detectDoji(
						dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]
						);
  				
  				JLabel dojiLabel = new JLabel(detectDoji);
  				
  				TabbedFrontEnd.csPanel.add(dojiLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}

	//5 Main Types of Doji Candlesticks are detected instead of just regular Dojis
  	public String detectDojiVariations(String date, double open, double high, double low, double close) {
  		double bodyLength = Math.abs(close - open);
  		double totalLength = high - low;
  		double upperShadow = Math.abs(high - Math.max(open, close));
  		double lowerShadow = Math.abs(Math.min(open, close) - low);

  		if (
  				bodyLength <= totalLength * 0.1 &&
  				upperShadow >= totalLength * 0.4 &&
  				lowerShadow >= totalLength * 0.4
  				) {
  			return date + " Long-Legged Doji Detected";
  		}

  		if ((bodyLength < totalLength * 0.1) && (lowerShadow > (3 * bodyLength)) &&
  				(upperShadow < bodyLength)) {
  			return date + " Dragon Fly Doji Detected";
  		}

  		if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * bodyLength)) &&
  				(lowerShadow <= bodyLength)) {
  			return date + " Gravestone Doji Detected";
  		}

  		if (bodyLength <= totalLength * 0.1 && upperShadow == 0 && lowerShadow == 0) {
  			return date + " Four-Price Doji Detected";
  		}

  		if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * Math.abs(close - open))) &&
  				(lowerShadow > (3 * bodyLength))) {
  			return date + " Regular Doji Detetced";
  		}
    
  		return null;
  	}
  	
  	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayDojiVariation(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length; i++) {
  			if(detectDojiVariations(dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]) != null) {
  				String detectDojVar = detectDojiVariations(
	  					dateList.get(i), openData[i], highData[i], lowData[i], closeData[i]
							);
  				
  				JLabel dojVarLabel = new JLabel(detectDojVar);
  				
  				TabbedFrontEnd.csPanel.add(dojVarLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}

  	public String detectEngulf(
		String date, double open, double high, double low, double close,
		double prevOpen, double prevHigh, double prevLow, double prevClose
		) {
		if(
				open >= prevClose &&
				prevClose > prevOpen &&
				open > close &&
				prevOpen >= close &&
				(open - close) > (prevClose - prevOpen)
				){
  			return date + " Bearish Engulfment Detected";
		}
		
		if(
				close >= prevOpen &&
				prevOpen > prevClose &&
				close > open &&
				prevClose >= open &&
				(close - open) > (prevOpen - prevClose)
				){
  			return date + " Bullish Engulfment Detected";
		}
		
		return null;
  	}
  	
  	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayEngulf(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length-1; i++) {
  			if(
  					detectEngulf(dateList.get(i), openData[i + 1],
					highData[i + 1], lowData[i + 1],
					closeData[i + 1], openData[i], highData[i], lowData[i], closeData[i]) != null
					) {
  				
  				String detectEngulf = detectEngulf(
	  					dateList.get(i), openData[i + 1],
						highData[i + 1], lowData[i + 1],
						closeData[i + 1], openData[i], highData[i], lowData[i], closeData[i]
								);
  				
  				JLabel engulfLabel = new JLabel(detectEngulf);
  				
  				TabbedFrontEnd.csPanel.add(engulfLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}
	
	public String detectMorningStar(
			String date, double open, double high, double low, double close, double prevOpen,
			double prevHigh, double prevLow, double prevClose, double prevOpen2, double prevHigh2, double prevLow2,
			double prevClose2
			) {
		
		if (Math.max(prevOpen, prevClose) < prevClose2 && prevClose2 < prevOpen2 && close > open &&
				open > Math.max(prevOpen, prevClose)) {
			return date + " Morning Star Detected";
		}
		
		return null;
	}
	
	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayMorningStar(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		this.sb = new StringBuilder();
  		
  		for(int i = 0; i < closeData.length-2; i++) {
  			if(
  					detectMorningStar(
  							dateList.get(i), openData[i + 2],
  							highData[i + 2], lowData[i + 2],
  							closeData[i + 2], openData[i + 1],
  							highData[i + 1], lowData[i + 1],
  							closeData[i + 1], openData[i],
  							highData[i], lowData[i],
  							closeData[i]
  									) != null
  							) {
  				
  				String detectMornStar = detectMorningStar(
	  					dateList.get(i), openData[i + 2],
						highData[i + 2], lowData[i + 2],
						closeData[i + 2], openData[i + 1],
						highData[i + 1], lowData[i + 1],
						closeData[i + 1], openData[i],
						highData[i], lowData[i],
						closeData[i]
								);
  				
  				JLabel mornStarLabel = new JLabel(detectMornStar);
  				
  				TabbedFrontEnd.csPanel.add(mornStarLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}

	public String detectEveningStar(
			String date, double open, double high, double low, double close, double prevOpen,
			double prevHigh, double prevLow, double prevClose, double prevOpen2, double prevHigh2, double prevLow2,
			double prevClose2
	      ) {
		
		if (Math.min(prevOpen, prevClose) > prevClose2 && prevClose2 > prevOpen2 && close < open &&
				open < Math.min(prevOpen, prevClose)) {
			return date + " Evening Star Detected";
		}
		
		return null;
	}
	
	//To display to the end user for all candlesticks presented on the OHLCChart
  	public void displayEveningStar(
  			List<String> dateList, double[] openData, double[] highData, double[] lowData, double[] closeData
  			) {
  		
  		for(int i = 0; i < closeData.length-2; i++) {
  			if(
  					detectEveningStar(
  							dateList.get(i), openData[i + 2],
  							highData[i + 2], lowData[i + 2],
  							closeData[i + 2], openData[i + 1],
  							highData[i + 1], lowData[i + 1],
  							closeData[i + 1], openData[i],
  							highData[i], lowData[i],
  							closeData[i]
							) != null
					) {
  				String detectEvnnStar = detectEveningStar(
	  					dateList.get(i), openData[i + 2],
						highData[i + 2], lowData[i + 2],
						closeData[i + 2], openData[i + 1],
						highData[i + 1], lowData[i + 1],
						closeData[i + 1], openData[i],
						highData[i], lowData[i],
						closeData[i]
								);
  				
  				JLabel evnnStarLabel = new JLabel(detectEvnnStar);
  				
  				TabbedFrontEnd.csPanel.add(evnnStarLabel);
  				
  				TabbedFrontEnd.csPanel.revalidate();
  				TabbedFrontEnd.scrollPane.revalidate();
  			}
  		}
  	}
}
