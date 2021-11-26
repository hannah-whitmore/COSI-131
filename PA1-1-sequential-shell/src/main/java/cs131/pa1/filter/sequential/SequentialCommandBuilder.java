package cs131.pa1.filter.sequential;

import java.util.LinkedList;

import java.util.List;
import cs131.pa1.filter.Message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileNotFoundException;

// get absolute method for file directory 


/**
 * This class manages the parsing and execution of a command. It splits the raw
 * input into separated subcommands, creates subcommand filters, and links them
 * into a list.
 * 
 * @author cs131a
 *
 */
public class SequentialCommandBuilder {
	
	
	/**
	 * Creates and returns a list of filters from the specified command
	 * 
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 */
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		
		List<SequentialFilter> filterList = new LinkedList<>();
		String [] subcommands = command.split("\\s\\|\\s", -1);
		
		for (int i = 0; i < subcommands.length - 1; i++){
			if (subcommands[i].contains(">")){
				System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subcommands[i].substring(subcommands[i].indexOf(">"))));
			}
			if (subcommands[i].contains("cd")){
				System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subcommands[i]));
			}
		}
		
		SequentialFilter filter;
		
		boolean redirecting = false;
		String rFilter = null;
		
		for(String s : subcommands) {
			
			if(s.contains(">")) {
				redirecting = true;
				rFilter = s.substring(s.indexOf("> "));
				s = s.substring(0, s.indexOf(" > "));
			}
			
			filter = constructFilterFromSubCommand(s);
			
			if(filter != null) {
				filterList.add(filter);
			} else {
				return null;
			}
		}

		if(redirecting) {
			filterList.add(constructFilterFromSubCommand(rFilter));
		}else {
			filterList.add(new Print());
		}	
			
		return linkFilters(filterList) ? filterList : null;
	}

	
	/**
	 * Returns the filter that appears last in the specified command
	 * 
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 */
	private static SequentialFilter determineFinalFilter(String command) {
		return null;
	}

	/**
	 * Returns a string that contains the specified command without the final filter
	 * 
	 * @param command the command to parse and remove the final filter from
	 * @return the adjusted command that does not contain the final filter
	 */
	private static String adjustCommandToRemoveFinalFilter(String command) {
		return null;
	}

	/**
	 * Creates a single filter from the specified subCommand
	 * 
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 */
	private static SequentialFilter constructFilterFromSubCommand(String command) {
		String [] cmdAndWord = command.split("\\s+");
			
		SequentialFilter filter = null;
		 // split on whitespace to get the command keyword

		switch (cmdAndWord[0]) {
		
		case "pwd":
			filter = new Pwd();
			break;
			
		case "ls":
			filter = new Ls();
			break;
			
		case "cd":
			if (cmdAndWord.length > 1) { // has an argument -- pass in the argument to the CD class 
				
				filter = new Cd(cmdAndWord[1]);
			
			} else {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
			
			}
			break;
			
		case "cat":
			if (cmdAndWord.length <= 1) {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cat"));
				break;
			} 
				try {
					filter = new Cat(cmdAndWord[1]);
				} catch (FileNotFoundException e) {
					System.out.print(Message.FILE_NOT_FOUND.with_parameter(command));
					return null;
			}
			
			break;
			
		case "grep":
			
			if (cmdAndWord.length < 2) { // no argument 
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter("grep"));
			} else {
				filter = new Grep(cmdAndWord[1]);
			}
			break;	
			
		case "wc":
			filter = new Wc();
			break;
		
		case "uniq":
			filter = new Uniq();
			break;
			
		case "head":
			filter = new Head();
			
			break;
			
		case "tail":
			filter = new Tail();
			break;
		
		case ">":
			
			if (cmdAndWord.length < 2) { // no argument 
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(">"));
				return null;
			}
			try {
				filter = new Redirect(cmdAndWord[1]);
			} catch (Exception e) {
				return null;
			}
			break;
	
		default: 
			
			if (cmdAndWord.length>1) {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(cmdAndWord[0] + " " + cmdAndWord[1]));
			} else {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(cmdAndWord[0]));
			}
			
			return null;
		} 
		
		return filter;
	}


	/**
	 * links the given filters with the order they appear in the list
	 * 
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors
	 *         encountered. Any error should be displayed by using the Message enum.
	 */

	private static boolean linkFilters(List<SequentialFilter> filters) {

		
		Iterator<SequentialFilter> i = filters.iterator(); 

		// grab first filter and do error checking for filters that require input
		SequentialFilter prev;
		SequentialFilter cur = i.next(); 

		
		if (cur instanceof Grep ){
			System.out.print(Message.REQUIRES_INPUT.with_parameter("grep " + ((Grep) cur).getSearchWord()));
			return false;
		}
		if (cur instanceof Wc) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("wc"));
			return false;
		}
		if (cur instanceof Head) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("head"));
			return false;
		}
		if (cur instanceof Tail) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("tail"));
			return false;
		}
		if (cur instanceof Uniq) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("uniq"));
			return false;
		}
		

		while(i.hasNext()) {
			
			// iterate through the rest of the filters and error check for filters that cannot have input
			prev = cur;
			cur = i.next();
			
			if (cur instanceof Cd ){
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cd " + ((Cd) cur).getDirectorKey()));
				return false;
			}
			if (cur instanceof Ls) {
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
				return false;
			}
			if (cur instanceof Cat) {
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cat " + ((Cat) cur).getFileName()));
				return false;
			}
			if (cur instanceof Pwd) {
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
				return false;
			}

			
			prev.setNextFilter(cur); 
		}
	
		return true;
	
		
		
	}
}
