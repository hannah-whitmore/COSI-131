package cs131.pa1.filter.sequential;
import java.util.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.io.File;

import cs131.pa1.filter.Message;


/**
 * The main implementation of the REPL loop (read-eval-print loop). It reads
 * commands from the user, parses them, executes them and displays the result.
 * 
 * @author cs131a
 *
 */
public class SequentialREPL {
	
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	static boolean endLoop;

	/**
	 * The main method that will execute the REPL loop
	 * 
	 * @param args not used
	 */
	
	public static void main(String[] args) {
		
		System.out.print(Message.WELCOME);
			
		List<SequentialFilter> filterList = new LinkedList<>();
		currentWorkingDirectory = System.getProperty("user.dir"); 
		
		System.out.print(Message.NEWCOMMAND);
		
	
		Scanner input = new Scanner(System.in);
		String command = input.nextLine();
		
		
		endLoop = false;
		
		while(!endLoop) {
			
				if (command.equals("exit".trim())) {
					endLoop = true;
					break;
				} else {
					
					filterList = SequentialCommandBuilder.createFiltersFromCommand(command);
					
					if (filterList != null){
						
						Iterator<SequentialFilter> i = filterList.iterator();
						while (i.hasNext()) {
							SequentialFilter filter = i.next();
							if (filter != null) {
								filter.process();	
							}
						}	
					}
			}
			
			System.out.print(Message.NEWCOMMAND);
			command = input.nextLine();
		}
			
		System.out.print(Message.GOODBYE);
		input.close();
		
	}

}
