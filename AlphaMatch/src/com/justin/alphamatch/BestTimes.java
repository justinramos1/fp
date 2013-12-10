/************************************************************************
 *  Justin Ramos
 *  BestTimes.java
 *  Reads from sharedpreferences and displays the top 5 times
 **************************************************************************/

package com.justin.alphamatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BestTimes extends Activity 
{
	Button quitBtn, startGame;
	private TextView label1, label2, label3, label4, label5;
	SharedPreferences prefs;
	final String DEFAULT = "0";
	String time1,time2,time3,time4,time5;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.besttimes);
		
		prefs = this.getSharedPreferences("com.justin.alphamatch", Context.MODE_PRIVATE);
		time1 = prefs.getString("one", DEFAULT);
		
		label1 = (TextView) findViewById(R.id.time1);
		label1.setText(time1);
		
		time2 = prefs.getString("two", DEFAULT);
		
		label2 = (TextView) findViewById(R.id.time2);
		label2.setText(time2);
		
		time3 = prefs.getString("three", DEFAULT);
		
		label3 = (TextView) findViewById(R.id.time3);
		label3.setText(time3);
		
		time4 = prefs.getString("four", DEFAULT);
		
		label4 = (TextView) findViewById(R.id.time4);
		label4.setText(time4);
		
		time5 = prefs.getString("five", DEFAULT);
		
		label5 = (TextView) findViewById(R.id.time5);
		label5.setText(time5);
		
		quitBtn = (Button) findViewById(R.id.quit);
		quitBtn.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				finish();
				
			}
		});
		
		startGame = (Button) findViewById(R.id.start);
		startGame.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				start();
				
			}
		});
	}
	
	@Override
	protected void onStop() 
	{
		finish();
		super.onStop();
	}
	
	private void start() 
	{
		Intent launchGame = new Intent(this,GameScreen.class);
		startActivity(launchGame);
	}
}
