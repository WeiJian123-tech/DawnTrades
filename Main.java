package Prototype_003;

/*
 * Working Prototype.
 * 
 * Modified from a program given by ChatGPT.
 * 
 * Open source free trading program
 * 
 * Java Library: XChart
 * 
 * @Authors: Wei Jian Zhen, Jawad Rahman
 */

import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoField;

import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.internal.*;

public class Main {

	public static void main(String[] args) {
		
		
		ArrayList<Number> yPrices = new ArrayList<>(
				List.of(8.74, 8.78, 8.94, 8.92, 9.00, 9.00, 9.05, 9.11)
				);
		
		ArrayList<Number> xTime = new ArrayList<>(
				List.of(9.5, 10, 11, 12, 13, 14, 15, 16)
				);
		
		
		//System.out.println(Duration.ofDays(1).toHours() / 0.5);
		
		//LocalTime stockTime = LocalTime.now();
		
		//System.out.println(stockTime);
		
		/**
		ArrayList<Double> yPrices = new ArrayList<>(
				List.of(8.74, 8.78, 8.94, 8.92, 9.00, 9.00, 9.05, 9.11)
				);
		
		ArrayList<String> xTime = new ArrayList<>(
				List.of("12:00", "1", "2", "3", "4", "5", "6", "7")
				);
		**/
		
		ArrayList<Double> stockPrices = new ArrayList<Double>(
				List.of(8.74, 8.78, 8.94, 8.92, 9.00, 9.00, 9.05, 9.11)
				);
		
		BackEnd tradeAlgo = new BackEnd(stockPrices);
		
		//Create the chart
		XYChart chart = QuickChart.getChart(
				"Project GoldenTrades", "Time", "Prices", "Stock Price", xTime, yPrices
				);
		
		//Show the chart
		new SwingWrapper<XYChart>(chart).displayChart();
	}
	
}
