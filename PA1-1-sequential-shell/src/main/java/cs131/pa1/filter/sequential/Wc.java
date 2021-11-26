package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;

/**
 * Reads lines from piped input and output the number of lines, words, and characters, separated with space
 */

public class Wc extends SequentialFilter{
	int lines;
	int chars; 
	int words;
		
	public Wc() {
		super();
		
		this.lines = 0;
		this.words = 0;
		this.chars = 0;
	}

	/**
	 * Returns the number of lines, words, and characters of input 
	 * 
	 * @param string input to loop through and determine counts
	 * @return number of lines, words, and characters of input separated by space
	 */
	protected String processLine(String line) {
		
		if(line == null) {
			return this.lines + " " + this.words + " " + this.chars;
		}
		if (line.length()>0) {
		
			String [] lines =  line.split("\\r?\\n");
			this.lines = lines.length;
			
		    line = line.trim();
		    
		    String [] words = line.split("\\s+");
		    this.words = words.length;
		    
		    for (String w : words) {
		    	this.chars += w.length();
		    	
		    }
		}
		return this.lines + " " + this.words + " " + this.chars;
	
	}

}
