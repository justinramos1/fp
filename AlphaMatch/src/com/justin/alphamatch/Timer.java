/************************************************************************
 *  Justin Ramos
 *  Timer.java
 *  Runs a thread that keeps track of time
 **************************************************************************/
package com.justin.alphamatch;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class Timer {
	
	private TextView timeLabel;
	private long startTime = 0L; //start time will always start at 0
	private Handler handler = new Handler();
	private int seconds;
	
	public Timer( TextView timeToUpdate) {	
		if(this.startTime == 0L){
			this.startTime = SystemClock.uptimeMillis();
			handler.removeCallbacks(UpdateTimeTask);
			handler.postDelayed(UpdateTimeTask, 100);
		}
		
		this.timeLabel = timeToUpdate; //set time label to timer on game screen
	}
	
	
	private Runnable UpdateTimeTask = new Runnable(){
		public void run(){
			final long start = startTime;
			long millis = SystemClock.uptimeMillis() - start;
			seconds = (int) (millis / 1000);
			
			timeLabel.setText(Integer.toString(seconds));
			handler.postDelayed(this, 200);
		}
	};
	
	public int getStopSeconds()
	{
		return seconds;
	}
	
	public void stopTimer()
	{
		handler.removeCallbacks(UpdateTimeTask);
	}
		
}
