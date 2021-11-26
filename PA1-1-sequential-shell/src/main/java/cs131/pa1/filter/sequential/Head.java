package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

public class Head extends SequentialFilter {
	
	/*
	 * Output the first 10 lines from the input to the output message queue 
	 */
	
	int count;
	
	public Head()  {
		super();
		this.count = 0;	
	}
	
	
	/**
	 * Returns only the first 10 lines of the input are added to the output queue,
	 * if there are less than 10 lines of input, output whole file
	 * 
	 * @param the input for head to read 
	 * @return null but added to the output queue
	 */
	protected String processLine(String line) {
		if (line.length()>0) {

			this.count = 0;
			String [] lines = line.split("\n");
			
			for (int i=0; i<lines.length; i++) {
				
				if (this.count==10) {
					break;
				}
	
				output.add(lines[i]);
				this.count ++;		
			}
		}
		return null;
	}
 
 

}
