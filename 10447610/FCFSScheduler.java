
/**
 * First comes first served scheduler.
 */

import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FCFSScheduler implements Scheduler {

  @Override
  public void schedule(String inputFile, String outputFile) {
    // implement your scheduler here.
    // write the scheduler output to {@code outputFile}
	  
	try {

		ArrayList<Process> processes = new ArrayList<Process>();
		
		//reads all lines from the input file
		List<String> lines = Files.readAllLines(Paths.get(inputFile), StandardCharsets.UTF_8);
		
		//parses all the lines and puts data into an array of processes
	  	String delimiters = "[ ]+";
	  	for( int i = 0; i < lines.size(); i++ ) {
	  		String[] tokens = lines.get(i).split( delimiters );
	  		processes.add( new Process( Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])) );
	  	}
	  	
	  	//organize the array so that it is in FCFS order
	  	processes = organize_processes( processes );
	  	
	  	int ticker = 0;
	  	int index_of_current = 0;
	  	while( !all_processes_finished( processes ) ) {
	  		
	  		ticker++;

	  		//check all the other processes. if they are waiting, increment their waiting time
	  		for( int i = index_of_current+1; i < processes.size(); i++ ) {
	  			if( ticker > processes.get(i).getArrivalTime() ) {
	  				processes.get(i).incrementWaitingTime();
	  			}
	  		}

	  		//check if the process has arrived. if yes, start executing
	  		if( ticker > processes.get(index_of_current).getArrivalTime() ) {
	  			processes.get(index_of_current).incrementExecutingTime();

	  			//check if the process is done executing. if yes, set the finishtime and go to the next process
		  		if( processes.get(index_of_current).getExecutingTime() >= processes.get(index_of_current).getBurstTime() ) {
			  			processes.get(index_of_current).set_finished();
			  			processes.get(index_of_current).setFinishTime(ticker);
			  			index_of_current++;
			  	}
	  		}
	  		
	  	}
	  	
	  	ArrayList<Process> PID_organized_processes = orgProcessesPID(processes);
		write_to_file(PID_organized_processes, outputFile);
	
	} catch (IOException e1) {
		e1.printStackTrace();
	}

    //read in process data 
  	//parse the data into fragments to create processes.
    //make new process with the data
    //store the processes into an array
    //sort the processes according to arrival time (FCFS scheduler)
    
    //run the algorithm
    	//use a while loop to simulate "time". every iteration is a "tick". have an unsigned int variable to keep track of the current amount of ticks

  		//run the process at the front of the queue only if "tick" >= arrival time
  		//otherwise, continue "ticking"
  			//while running a process:
  				//count its running time until running time >= processBurstTime
  				//take note of the finish time of the process ("tick" is the finish time)
  				//increment the other process' waiting time if "tick" >= arrivaltime

  	//use each individual process to calculate its own waiting time and turnaround time.

    //output the new data into an output file.
  }

  
  
  private void write_to_file( ArrayList<Process> processes, String outputFile) {	  
	  
	  double avg_wait_time = 0;
	  double avg_turn_time = 0;

	  
	  File f = null;
	  
	  try {
		  
		  f = new File( outputFile );
		  
		  FileWriter fw = new FileWriter( f );
		  PrintWriter output = new PrintWriter( fw );
		  
		  for( int i = 0; i < processes.size(); i++ ) {

			  
			  output.println( Long.toString( processes.get(i).getPID() ) + " "
					  		  + processes.get(i).getFinishTime() + " "
					  		  + processes.get(i).getWaitingTime() + " "
					  		  + processes.get(i).getTurnaroundTime() );
			  
			  avg_wait_time += processes.get(i).getWaitingTime();
			  avg_turn_time += processes.get(i).getTurnaroundTime();
		  }
		  
		  avg_wait_time = avg_wait_time/processes.size();
		  avg_turn_time = avg_turn_time/processes.size();
		  
		  
		  output.print( Long.toString( Math.round(avg_wait_time) ) + " " + Long.toString( Math.round(avg_turn_time) ) );
		  
		  output.close();
		  fw.close();

	  } catch (IOException e) {
			e.printStackTrace();
	  }
	  
  }
  
  private boolean all_processes_finished( ArrayList<Process> processes) {
	  
	  for( int i = 0; i < processes.size(); i++ ) {
		  if( !processes.get(i).is_finished() ) {
			  return false;
		  }
	  }
	  
	  return true;
  }
  
  
  private ArrayList<Process> organize_processes( ArrayList<Process> group) {
	  int i = 0;
	  int j = 0;
	  int arrival_time = 0;
	  int index_of_earliest;
	  
	  for( i = 0; i < group.size(); i++ ) {
		  
		  arrival_time = group.get(i).getArrivalTime();
		  index_of_earliest = i;
		  
		  for( j = i+1; j < group.size(); j++ ) {
		  	  if( arrival_time == group.get(j).getArrivalTime() ) {
		  	  		if( group.get(index_of_earliest).getPID() > group.get(j).getPID() ) {
		  	  			index_of_earliest = j;
		  	  		}
		  	  } else if( arrival_time > group.get(j).getArrivalTime() ) {
				  arrival_time = group.get(j).getArrivalTime();
				  index_of_earliest = j;
			  }
		  }
		  
		  if( index_of_earliest != i ) {
			  Process tmp = group.get(i);
			  group.set( i, group.get(index_of_earliest) );
			  group.set(index_of_earliest, tmp);
		  }
	  }
	  
	  return group;
  }


  private ArrayList<Process> orgProcessesPID(ArrayList<Process> processes) {
	  int i = 0;
	  int j = 0;
	  long PID = 0;
	  int index_of_least;
	  
	  for( i = 0; i < processes.size(); i++ ) {
		  
		  PID = processes.get(i).getPID();
		  index_of_least = i;
		  
		  for( j = i+1; j < processes.size(); j++ ) {
			  if( PID > processes.get(j).getPID() ) {
				  PID = processes.get(j).getPID();
				  index_of_least = j;
			  }
		  }
		  
		  if( index_of_least != i ) {
			  Process tmp = processes.get(i);
			  processes.set( i, processes.get(index_of_least) );
			  processes.set(index_of_least, tmp);
		  }
	  }
	  
	  return processes;
  }

}


