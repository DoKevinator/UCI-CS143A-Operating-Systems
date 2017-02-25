import java.util.ArrayList;


public class Process {

	//used to determine if the process id is unique or not. 
	//prevents a process with the same PID to execute.
	public static ArrayList<Long> unique_pid;

	//process attributes
	private long p_ID;
	private int arrival_time;
	private int burst_time;
	//private int share;

	//other process information
	private int waiting_time;
	private int executing_time;
	private int finish_time;
	
	private boolean finished = false;

	public Process() {
		p_ID = 0;
		arrival_time = 0;
		burst_time = 0;
		//share = 0;

		waiting_time = 0;
		finish_time = 0;
	}

	public Process( long proc_id, int arr_time, int burst) {
		p_ID = proc_id;
		arrival_time = arr_time;
		burst_time = burst;
		//share = shared;
	}

	public int timeRemaining() {
		return burst_time - executing_time;
	}
	
	/*public int getShare() {
		return share;
	}*/

	// public void set_ExecutingTime(int et) {
	// 	executing_time = et;
	// }
	
	public int getExecutingTime() {
		return executing_time;
	}
	
	public void incrementExecutingTime() {
		executing_time++;
	}
	
	public boolean is_finished() {
		return finished;
	}
	
	public void set_finished() {
		finished = true;
	}

	public void incrementWaitingTime() {
		waiting_time++;
	}

	// public void set_WaitingTime( int wt ) {
	// 	waiting_time = wt;
	// }
	
	public int getWaitingTime() {
		return waiting_time;
	}

	public void setFinishTime( int finish ) {
		finish_time = finish;
	}
	
	public int getFinishTime() {
		return finish_time;
	}

	public int getTurnaroundTime() {
		return waiting_time + burst_time;
	}

	public int getArrivalTime() {
		return arrival_time;
	}

	public int getBurstTime() {
		return burst_time;
	}
	
	public long getPID() {
		return p_ID;
	}



}
