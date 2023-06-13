package Prototype_003;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.util.*;

public class CandleStickWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackEnd tradeAlgo;
	private CandleStickDetectionAlgo cda;
	private double[] yPrices;
	private Date[] xTime;
	private double[] openData;
	private double[] highData;
	private double[] lowData;
	private double[] closeData;
	
	public CandleStickWindow(
			double[] yPrices, Date[] xTime, double[] openData, double[] highData, double[] lowData,
			double[] closeData
			) {
		
		this.yPrices = yPrices;
		this.xTime = xTime;
		this.openData = openData;
		this.highData = highData;
		this.lowData = lowData;
		this.closeData = closeData;
	}

	public CandleStickWindow(Date[] xTime, double[] openData, double[] highData, double[] lowData,
			double[] closeData) {
		this.xTime = xTime;
		this.openData = openData;
		this.highData = highData;
		this.lowData = lowData;
		this.closeData = closeData;
	}

	public CandleStickWindow(BackEnd tradeAlgo, Date[] xTime, double[] openData, double[] highData,
			double[] lowData, double[] closeData) {
		this.tradeAlgo = tradeAlgo;
		this.xTime = xTime;
		this.openData = openData;
		this.highData = highData;
		this.lowData = lowData;
		this.closeData = closeData;
	}
	
	public CandleStickWindow(CandleStickDetectionAlgo cda, Date[] xTime, double[] openData, double[] highData,
			double[] lowData, double[] closeData) {
		this.cda = cda;
		this.xTime = xTime;
		this.openData = openData;
		this.highData = highData;
		this.lowData = lowData;
		this.closeData = closeData;
	}

	public void createWindow() {
		//Pop-up window could appear for real-time charts.
		
		setTitle("Candlestick Detections");
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(600, 600));
		
		//https://stackoverflow.com/a/10346673/11628809
		
		JPanel cSPanel = new JPanel();
		
		cSPanel.setMinimumSize(new Dimension(500, 500));
		cSPanel.setPreferredSize(new Dimension(600, 600));
		cSPanel.setLayout(new BoxLayout(cSPanel, BoxLayout.PAGE_AXIS));
		
		String detectMarb = "";
		String detectMarbFlex = "";
		String detectDoji = "";
		String detectDojVar = "";
		String detectEngulf = "";
		String detectMornStar = "";
		String detectEvnnStar = "";
		
		for(int i = 0; i < closeData.length; i++) {
			detectMarb = cda.detectMarubozu(
					xTime[i].toString(), openData[i], highData[i], lowData[i], closeData[i]
					);
			
			detectMarbFlex = cda.detectMarubozuFlexible(
					xTime[i].toString(), openData[i], highData[i], lowData[i], closeData[i]
							);
			
			detectDoji = cda.detectDoji(
					xTime[i].toString(), openData[i], highData[i], lowData[i], closeData[i]
							);
			
			detectDojVar = cda.detectDojiVariations(
					xTime[i].toString(), openData[i], highData[i], lowData[i], closeData[i]
					);
			
			JLabel marbLabel = new JLabel(detectMarb);
			JLabel marbFlexLabel = new JLabel(detectMarbFlex);
			JLabel dojiLabel = new JLabel(detectDoji);
			JLabel dojVarLabel = new JLabel(detectDojVar);
			
			cSPanel.add(marbLabel);
			cSPanel.add(marbFlexLabel);
			cSPanel.add(dojiLabel);
			cSPanel.add(dojVarLabel);
		}
		
		for(int i = 0; i < closeData.length - 1; i++) {
			detectEngulf = cda.detectEngulf(
					xTime[i].toString(), openData[i + 1],
					highData[i + 1], lowData[i + 1],
					closeData[i + 1], openData[i], highData[i], lowData[i], closeData[i]);
			
			JLabel engulfLabel = new JLabel(detectEngulf);
			
			cSPanel.add(engulfLabel);
		}
		
		for(int i = 0; i < closeData.length - 2; i++) {
			detectMornStar = cda.detectMorningStar(
					xTime[i].toString(), openData[i + 2],
					highData[i + 2], lowData[i + 2],
					closeData[i + 2], openData[i + 1],
					highData[i + 1], lowData[i + 1],
					closeData[i + 1], openData[i],
					highData[i], lowData[i],
					closeData[i]
					);
			
			detectEvnnStar = cda.detectEveningStar(
					xTime[i].toString(), openData[i + 2],
					highData[i + 2], lowData[i + 2],
					closeData[i + 2], openData[i + 1],
					highData[i + 1], lowData[i + 1],
					closeData[i + 1], openData[i],
					highData[i], lowData[i],
					closeData[i]
					);
			
			JLabel mornStarLabel = new JLabel(detectMornStar);
			JLabel evnnStarLabel = new JLabel(detectEvnnStar);
			
			cSPanel.add(mornStarLabel);
			cSPanel.add(evnnStarLabel);
		}
		
		add(cSPanel);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
}
