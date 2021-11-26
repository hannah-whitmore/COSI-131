package cs131.pa2.filter.concurrent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs131.pa2.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop). It reads
 * commands from the user, parses them, executes them and displays the result.
 * 
 * @author cs131a
 *
 */
public class ConcurrentREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;

	/**
	 * pipe string
	 */
	static final String PIPE = "|";

	/**
	 * redirect string
	 */
	static final String REDIRECT = ">";
	
	/**
	 * “repl_jobs” should assign a number to each command executed
	 * this hashmap keeps track of the assignments
	 */
	static HashMap<Integer,String> commandIDs;
	
	/**
	 * this hashmap links each list of threads to their respective command number 
	 * easy retrieval when for instance the user wants to kill a specified command 
	 */
	static HashMap<Integer,LinkedList<Thread>> threadsIds;
	
	/**
	 * integer to keep track of the number associated with each new job 
	 */
	static int taskId;

	/**
	 * The main method that will execute the REPL loop
	 * implementing boolean flagging
	 * @param args not used
	 */
	public static void main(String[] args) {
		taskId = 1;

		// set cwd here so that it can be reset by tests that run main() function
		currentWorkingDirectory = System.getProperty("user.dir");
		
		commandIDs = new HashMap<>();
		threadsIds = new HashMap<>();

		Scanner consoleReader = new Scanner(System.in);
		System.out.print(Message.WELCOME);


		// whether or not shell is running
		boolean running = true;

		while(running) {
			System.out.print(Message.NEWCOMMAND);

			// read user command, if its just whitespace, skip to next command
			String cmd = consoleReader.nextLine();
			
				// if we have only an ExitFilter, that means user typed "exit" or "exit"
				// surrounded by ws, stop the shell
				if (cmd.equals("exit")) {
					running = false;
					break;
				} 
				
					
				// logic for repl_jobs
				// assign a number id to the background commands
				// prints alive processes
				
				if (cmd.equals("repl_jobs")) {
					// to list all of the alive processes, need to retrieve the keys in commandIDs
					
					for (int key : commandIDs.keySet() ) {
						LinkedList<Thread> threads = threadsIds.get(key);
						
						for (Thread currThread : threads) {
							
							if (currThread.isAlive()) {
								System.out.println(commandIDs.get(key));
							} else {
								threadsIds.remove(key);
							}
						}
					}
				
				}
				
				// logic for kill command 
				
				else if(cmd.startsWith("kill")) {
					if(cmd.length()==4){
						System.out.print(Message.REQUIRES_PARAMETER.with_parameter("kill"));
					}
					int kill = 0;
					
					Boolean isNumber = Character.isDigit(cmd.charAt(5));
					if (isNumber) {
						kill = Integer.parseInt(cmd.substring(5));
									
						// extract any threads to kill
						LinkedList<Thread> threads = threadsIds.get(kill);
						if (threads != null) {
							for (Thread cur : threads) {
								cur.interrupt();
							}
							threadsIds.remove(kill);
							commandIDs.remove(kill);
						}
					}
					
				}
			
				// regular commands and background commands 
				
				else if (!(cmd.isBlank())) {
					
					ConcurrentFilter filters = ConcurrentCommandBuilder.createFiltersFromCommand(cmd);
					
					Thread last = null;
					
					LinkedList<Thread> commands = new LinkedList<>();
					
					while(filters != null) {
						Thread t = new Thread(filters, filters.toString());
						commands.add(t);
						last = t;
						t.start();
			
						filters = (ConcurrentFilter) filters.getNextFilter();
					}
					// determine if command is background command
					if (cmd.endsWith("&")){
						// remove & for processing 
						cmd = cmd.substring(0, cmd.length() - 1);	
							// update mappings
							commandIDs.put(taskId, ""+ taskId + ". " + cmd + "&");
							threadsIds.put(taskId, commands);
							taskId++;				
					} else if (last !=null){
						try {
							last.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
					
			}		

		System.out.print(Message.GOODBYE);

		consoleReader.close();

	}

}
