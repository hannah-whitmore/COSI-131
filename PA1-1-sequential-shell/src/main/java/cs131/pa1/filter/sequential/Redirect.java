package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.io.PrintWriter;

import cs131.pa1.filter.Message;

import java.io.File;
import java.io.FileWriter;

import cs131.pa1.filter.Filter;

public class Redirect extends SequentialFilter {
	
	/**
	 * Reads piped input, and writes it to a file specified after the > symbol
	 * no output
	 */
	
	FileWriter writer;
	
	public Redirect(String fileName) throws Exception {
		super();
		try {
			writer = new FileWriter(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + fileName);
		} catch (IOException e) {
			throw new Exception();
		}
	}
	
	/**
	 * Writes piped input to a specified file 
	 * 
	 * @param string line, the content to write to the file
	 * @return nothing to the console, but writes to the file specified in the constructor parameter 
	 */
	public String processLine(String line) {
		try {
			writer.append(line + "\n");
			if(isDone()) {
				writer.close();
			}
		} catch (IOException e) {
			System.out.println("error");
		}
		return null;
	}
}
