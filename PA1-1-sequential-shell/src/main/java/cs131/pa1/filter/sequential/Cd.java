package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import cs131.pa1.filter.Filter;
import java.io.File;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * Change to another directory relative to the current directory
 * Cannot have input and does not have output
 *
 */

public class Cd extends SequentialFilter{
	
	String directoryKey;
	String newDirectory;
	
	/**
	 * @param d the key to direct the current working directory to
	 * 
	 */
	
	public Cd(String d) {

		super();
		this.directoryKey = d;
		this.newDirectory = SequentialREPL.currentWorkingDirectory;
		
	}
	
	/**
	 * Adjusts the current working directory based on the command given
	 * Moves back, stays the same, or moves into a folder specified if that folder exists
	 * 
	 * @param line 
	 * @return null because cd should have no output
	 */
	protected String processLine(String line) {
				
		switch(this.directoryKey) {
		 
		case ".": // stay in the same directory 
			break;
			
		case "..":
			
			this.newDirectory = SequentialREPL.currentWorkingDirectory.substring(0, SequentialREPL.currentWorkingDirectory.lastIndexOf(Filter.FILE_SEPARATOR));
			break;
			
		 default: // folder to go into 
			 
			 String dir =  this.newDirectory + Filter.FILE_SEPARATOR + this.directoryKey;
			 
			 File validDirectory = new File(dir);
			 
			 if (validDirectory.isDirectory()) {
				 this.newDirectory += Filter.FILE_SEPARATOR + this.directoryKey;
			 } else {
				 System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter("cd " + this.directoryKey));
			 }
			 
		}
		
		SequentialREPL.currentWorkingDirectory = this.newDirectory;
		
		return null;
	}
	
	public void process() {
		processLine("");
	}
	
	/**
	 * @return search word key for printing out error messages 
	 */
	public String getDirectorKey() {
		return this.directoryKey;
	}

		

}
