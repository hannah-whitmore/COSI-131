package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import java.util.ArrayList;


public class Tail extends SequentialFilter{
	/**
	 * Output the last 10 lines from the input to the output message queue.
	 */
	
	int count;
	
	public Tail() {
		this.count = 0;
		
	}

	
	/**
	 * Returns the last 10 lines of input to the output message queue 
	 * 
	 * @param input to loop through
	 * @return null but adds last 10 lines to output 
	 */
	protected String processLine(String line) {
		
		if (line.length() > 0) {
		
			String [] lines = line.split("\n");
			
			ArrayList <String> list = new ArrayList<String>();
			
			for (int i=(lines.length-1); i>=0; i--) {
				
				if (this.count==10) {
					break;
				}
	
				list.add(lines[i]);
				this.count ++;
			}
			
			for (int i=(list.size()-1); i>=0; i--) {
				output.add(list.get(i));
			}
		}
		
		return null;
	}
}
