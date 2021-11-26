package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 
 * Pipes the contents of the current working directory to the output message queue
 * Cannot have input
 */

public class Ls extends SequentialFilter{
	public File directory;
	public File [] directoryList;
	
	public Ls() {
		super();
		
		this.directory =  new File(SequentialREPL.currentWorkingDirectory);
		this.directoryList = this.directory.listFiles();
		
	}
	
	/**
	 * No parameter, adds the contents of the current working directory to the output queue  
	 */
	public void process() {
		for (File f : directoryList) {
			output.add(f.getName());
			System.out.println(output.poll());
		}
	}
	
	/**
	 * @return the string containing the contents of the current working directory 
	 */
	protected String processLine(String line) {
		return output.poll();
	}

}
