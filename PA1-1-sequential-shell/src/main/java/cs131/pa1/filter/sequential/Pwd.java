package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

public class Pwd extends SequentialFilter{
	
	/**
	 * Pipes the working directory to the output message queue 
	 * Cannot have input
	 */
	
	public Pwd() {
		super();
	}
	
	
	public void process() {
		if (input != null){
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
			
		} else {
			output.add(processLine(""));
		}
	}
	
	/**
	 * Returns the current working directory string
	 * 
	 * @param the string directory
	 * @return the current working directory  
	 */
	public String processLine(String directory) {
		return SequentialREPL.currentWorkingDirectory;
	}

}
