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

import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.internal.*;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Double> stockPrices = new ArrayList<>(
				List.of(162.39, 162.68, 163.28, 164.56, 164.60, 164.46, 165.0, 164.67, 164.66)
				);
		
		BackEnd tradeAlgo = new BackEnd(stockPrices);
	}

}
