package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.util.*;
/*
 * Filters out duplicate lines from the input
 */

public class Uniq extends SequentialFilter{
	
	Set<String> uniqueWords; 
	
	public Uniq() {
		uniqueWords = new HashSet<String>();

	}
	
	/**
	 * Returns only unique lines of the input
	 * 
	 * @param string input to loop through and find unique lines
	 * @return null but adds unique lines to output queue 
	 */
	protected String processLine(String line) {
		
		String [] lines = line.split("\n");
		
		for (String l : lines) {
			if(!(uniqueWords.contains(l))){
				uniqueWords.add(l);
				output.add(l);
			}
			
		}
		return null;	
	
	}

}
