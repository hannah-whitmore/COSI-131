package cs131.pa2.filter.concurrent;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa2.filter.Filter;

/**
 * An abstract class that extends the Filter and implements the basic functionality of all filters. Each filter should
 * extend this class and implement functionality that is specific for this filter. 
 * You should not modify this class.
 * @author cs131a
 *
 */
public abstract class ConcurrentFilter extends Filter implements Runnable{
	/**
	 * The input queue for this filter
	 */
	protected LinkedBlockingQueue<String> input;
	/**
	 * The output queue for this filter
	 */
	protected LinkedBlockingQueue<String> output;
	
	/**
	 * boolean indicating if job is complete 
	 */
	protected boolean processed = false;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter concurrentNext = (ConcurrentFilter) nextFilter;
			this.next = concurrentNext;
			concurrentNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			concurrentNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}

	/**
	 * processes the input queue and writes the result to the output queue
	 * @throws InterruptedException 
	 */
	public void process() throws InterruptedException{
		while (! (input.isEmpty()) ){
			String line = input.take();
			String processedLine = processLine(line);
			if (processedLine != null){
			//	System.out.println("generic - adding to output: " + processedLine);
				output.put(processedLine);
			}
		}
		processed = true;

	}
	
	/**
	 * check to see if filters are completed 
	 */
	public boolean isDone() {	
		return input.size() == 0;
	}
	
	protected abstract String processLine(String line);
	
	/**
	 * overriden method from Runnable interface to process
	 */
	public void run() {
		while(!this.isDone()) {
			try {
				process();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Thread.interrupted()) {
				return;
			}
		}
	}
	/**
	 * @return the next filter
	 */
	public Filter getNextFilter() {
		return this.next;
	}
}