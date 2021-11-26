package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 * Output the entirety of one file to the output message queue
 */


public class Cat extends SequentialFilter{
	Scanner scan;
	String fileName;
	
	
	/**
	 * constructor for cat class that outputs the contents of a specified file 
	 * 
	 * @param fileName : the name of the file cat reads
	 * @throws FileNotFoundException if file is not found
	 */
	public Cat(String fileName) throws FileNotFoundException{
		
		super();
		this.fileName = fileName;
		
		scan = new Scanner(new File(fileName));
	}

	
	
	/**
	 * reads each line of the file, converts it to a string and adds it to the output queue
	 * first element of the output queue will have all of the contents of the cat file
	 */
	public void process() {		
			String text = "";
			try {
				text = new String(Files.readAllBytes(Paths.get(this.fileName)));

			} catch (IOException e ) {
				e.printStackTrace();
			}
			output.add(text.replaceFirst("\\s++$", ""));
	}
	
	public String processLine(String line) {
		return null;
	}
	
	/**
	 * @return the name of the input file to read, used for error reporting 
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	
	
}
