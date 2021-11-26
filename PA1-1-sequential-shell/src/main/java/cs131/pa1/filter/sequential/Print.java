package cs131.pa1.filter.sequential;

public class Print extends SequentialFilter {

	public Print() {
		super();
		
	}
	
	public void process() {
		while(!isDone()) {
			processLine(input.poll());
		}
	}
	
	/**
	 * Prints outputs of commands, if there are outputs to be printed  
	 * 
	 * @return null, but print the output to the console
	 */
	public String processLine(String line) {
		System.out.println(line);
		return null;
		
	}
}
