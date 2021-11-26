package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;

/** 
 * Reads lines from piped input, and only output lines with a given search string
 */

public class Grep extends SequentialFilter{
	
	String searchWord;
	String outputLine;
	
	/**
	 * @param searchWord : the keyword grep searches for in the given input
	 */
	public Grep(String searchWord) {
		super();
		this.searchWord = searchWord;
	}
	
	
	/**
	 * Returns the lines of input that contain a specified search word 
	 * 
	 * @param the string line of input passed that grep searches through 
	 * @return the lines from the input that contain the search word parameter 
	 */
	protected String processLine(String line) {
		
		String lines[] = line.split("\\r?\\n");
		String res = "";
		
		for (String l : lines) {
			if (l.contains(this.searchWord)) {
				
				res += l + "\n";
			}
		}
		
		if (res.equals("")){
			return null;
		} else {
			return res.trim();
		}
	}
	
	/**
	 * @return search word key for printing out error messages 
	 */
	public String getSearchWord() {
		return this.searchWord;
	}

}
