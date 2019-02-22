package edu.smith.cs.csc212.p4;

/*
 * A timekeeper that tracks in game time and increments it every step. 
 * Also tracks total time spent ingame and whether it is night or day.
 */
public class GameTime {
	
	/* 
	 * Keeps track of current 24hr time
	 */
	public int hour;
	
	/*
	 *  Whether or not it is currently daytime (8am to 8pm)
	 */
	public boolean day;
	
	/*
	 *  Total time spent in the game
	 */
	public int totalTime;
	
	
	/*
	 * Internal constructor for the timer, sets time to midnight day to night. 
	 */
	public GameTime() {
		
		this.hour = 0;
		this.day = false;
		this.totalTime = 0;
		
	}
	
	
	/*
	 * Increments the hour up one, and loops back to zero as a clock would
	 */
	public void increaseHour() {
		this.hour += 1;
		if(this.hour > 23) {
			this.hour -= 24;
		}
		if(this.hour > 20 || this.hour < 8) {
			this.day = false;
		} else {
			this.day = true;
		}
		this.totalTime += 1;
	}
	
	/*
	 * Returns the current hour 
	 */
	public int getHour() {
		return this.hour;
	}
	
	/*
	 * Returns whether it is currently night or day 
	 */
	public boolean getDay() {
		return this.day;
	}
	
	/*
	 * Returns the 12 hour time nicely formatted, day/night included
	 */
	public String printHour() {
		String pm;
		int twelveHour;
		String outside;
		
		if(hour > 12) {
			pm = "PM";
			twelveHour = hour - 12;
		} else {
			twelveHour = hour;
			pm  = "AM";
		}
		if(this.day) {
			outside = "Daytime";
		} else {
			outside = "Nighttime";
		}
		
		String time = "It is " + twelveHour + ":00 " + pm + ", " + outside + "." ; 
		return time;
	}
	
	
	
}
