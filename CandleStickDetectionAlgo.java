package Prototype_003;

public class CandleStickDetectionAlgo {
	
	private String date;
	private double open;
	private double high;
	private double low;
	private double close;

	public CandleStickDetectionAlgo(String date, double open, double high, double low, double close) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
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
  		//double totalLength = high - low;  //Potential future use
  		double upperShadow = Math.abs(high - Math.max(open, close));
  		double lowerShadow = Math.abs(Math.min(open, close) - low);
  		//double variance = 3 * (Math.abs(open - close) + 15 / 2) / 2;  //Potential future use

  		if (low == open && high == close) {
  			return date + "Bull Marubozu Full Detected";
  		} else if (high == open && low == close) {
  			return date + " Bear Marubozu Full Detected";
  		} else if (low == open || high == close) {
  			if (upperShadow <= 0.1 * bodyLength && lowerShadow == 0) {
  				return date + " Bull Marubozu Open Detected";
  			} else if (lowerShadow <= 0.1 * bodyLength && upperShadow == 0) {
  				return date + " Bull Marubozu Close Detected";
  			}
  		} else if (high == open || low == close) {
  			if (upperShadow <= 0.1 * bodyLength && lowerShadow == 0) {
  				return date + " Bear Marubozu Close Detected";
  			} else if (lowerShadow <= 0.1 * bodyLength && upperShadow == 0) {
  				return date + " Bear Marubozu Open Detected";
  			}
  		}
  		return null;
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
  		} else if ((close - open < 0) && (upperShadow <= 0.1 * bodyLength && lowerShadow <= 0.1 * bodyLength)) {
  			return date + " Bear Marubozu Detected";
  		}
  		
  		return null;
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
	    } else if ((bodyLength < totalLength * 0.1) && (lowerShadow > (3 * bodyLength)) &&
	      (upperShadow < bodyLength)) {
	      return date + " Dragon Fly Doji Detected";
	    } else if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * bodyLength)) &&
	      (lowerShadow <= bodyLength)) {
	      return date + " Gravestone Doji Detected";
	    } else if (bodyLength <= totalLength * 0.1 && upperShadow == 0 && lowerShadow == 0) {
	      return date + " Four-Price Doji Detected";
	    } else if ((bodyLength < totalLength * 0.1) && (upperShadow > (3 * Math.abs(close - open))) &&
	      (lowerShadow > (3 * bodyLength))) {
	      return date + " Regular Doji Detetced";
	    }
	    return null;
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
		} else if(
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
}
