package com.justin.alphamatch;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class GameScreen extends Activity
{
	Timer t1;
	Button quitBtn, viewBestTimesBtn;
	SharedPreferences prefs;
	public Button buttons[] = new Button[12]; //array of buttons
	public CardButton cardButtons[] = new CardButton[12];
	final int MAX = 6;
	final int MIN = 1;
	int row = 0;
	int col = 0;
	private int randNum; //serves as id for each button
	int matches = 0; //6 matches wins the game
	int firstId = 0; //first and second tile flipped
	int secondId = 0;
	private int[] valuesNotUsed = { 0, 2, 2, 2, 2, 2, 2 }; //numbers are decremented as they are picked by random number generator
	//MediaPlayer vars for sounds                          //index represents number val
	private MediaPlayer mpA; //Sound for letter A
	private MediaPlayer mpB; //Sound for letter B etc
	private MediaPlayer mpC;
	private MediaPlayer mpD;
	private MediaPlayer mpE;
	private MediaPlayer mpF;
	final String TAG = "TEST: ";
	int time;
	int timeSaved;
	String bestTime;
	private TextView bestTimeLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		prefs = this.getSharedPreferences("com.justin.alphamatch", Context.MODE_PRIVATE);
		
		bestTime = prefs.getString("one", "0");
		
		bestTimeLabel = (TextView) findViewById(R.id.best);
		bestTimeLabel.setText(bestTime
				);
		
		t1 = new Timer((TextView) findViewById(R.id.yourTime)); //create Timer object that starts Time
		
		//create buttons and setup listeners
		quitBtn = (Button) findViewById(R.id.quit);
		viewBestTimesBtn = (Button) findViewById(R.id.goToBestActivity);
		
		quitBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		viewBestTimesBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				viewBestTimes();
			}
		});
		
		//get buttons from board
		buttons[0] = (Button) findViewById(R.id.button_1);
		buttons[1] = (Button) findViewById(R.id.button_2);
		buttons[2] = (Button) findViewById(R.id.button_3);
		buttons[3] = (Button) findViewById(R.id.button_4);
		buttons[4] = (Button) findViewById(R.id.button_5);
		buttons[5] = (Button) findViewById(R.id.button_6);
		buttons[6] = (Button) findViewById(R.id.button_7);
		buttons[7] = (Button) findViewById(R.id.button_8);
		buttons[8] = (Button) findViewById(R.id.button_9);
		buttons[9] = (Button) findViewById(R.id.button_10);
		buttons[10] = (Button) findViewById(R.id.button_11);
		buttons[11] = (Button) findViewById(R.id.button_12);
		
		//b1.setBackgroundResource(R.drawable.a);
			
		for(int i = 0; i < 12; i++)
		{ //generate 12 random numbers; 1-6
			Random r = new Random();
			this.randNum = r.nextInt(this.MAX) + this.MIN; //rand between 1 and 6
			
			//check to see if we can use the random number, if not then loop until we get a usable number
			while (this.valuesNotUsed[this.randNum] == 0)
			{
				this.randNum = r.nextInt(this.MAX) + this.MIN; //generate rand number
			}
			
			this.addMyListener(this.buttons[i], this.randNum, i);
			cardButtons[i] = new CardButton(this.randNum, this.buttons[i]); //instantiate cardButton object
			
			this.valuesNotUsed[this.randNum]--;//decrement array
				
		}//end forloop
		
	}
	
	@Override
    protected void onStop() 
	{
        finish();
        super.onStop();
    }
	
	public void showWinnerMsg(){
		Context context = getApplicationContext();
		CharSequence text = "YOU WON!!!!!!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	public void addMyListener(final Button b, final int id, final int index)
	{
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				switch(id)
				{
					case 1:
							b.setBackgroundResource(R.drawable.a);
							break;
					case 2: 
							b.setBackgroundResource(R.drawable.b);
							break;
					case 3: 
							b.setBackgroundResource(R.drawable.c);
							break;
					case 4: 
							b.setBackgroundResource(R.drawable.d);
							break;
					case 5: 
							b.setBackgroundResource(R.drawable.e);
							break;
					case 6: 
							b.setBackgroundResource(R.drawable.f);
							break;
				
				}
				
				//see if tile is already flipped
				if(firstId == 0)
				{
					firstId = id;//set id to this one if no other tile is flipped
					flip(index);
				}
				else
				{
					secondId = id;
				
					//check for match
					if(firstId == secondId)
					{
						matches += 1;
					}
					else //wait so that card is shown, then reset cards
					{
						
						findCardById();
						
						b.setBackgroundResource(R.drawable.blank);
						
					}
					
					if(matches == 6) //game over
					{
						
						//get time
						time = t1.getStopTime();
						
						//set if time is beats any saved
						if(time < Integer.parseInt(prefs.getString("one", "9999")))
						{
							prefs.edit().putString("one", Integer.toString(time)).commit();
						}
						else if(time < Integer.parseInt(prefs.getString("two", "9999")))
						{
							prefs.edit().putString("two", Integer.toString(time)).commit();
						}
						else if(time < Integer.parseInt(prefs.getString("three", "9999")))
						{
							prefs.edit().putString("three", Integer.toString(time)).commit();
						}
						else if(time < Integer.parseInt(prefs.getString("four", "9999")))
						{
							prefs.edit().putString("four", Integer.toString(time)).commit();
						}
						else if(time < Integer.parseInt(prefs.getString("five", "9999")))
						{
							prefs.edit().putString("five", Integer.toString(time)).commit();
						}
						
						//Toast Winner
						Context context = getApplicationContext();
						CharSequence text = "YOU WON!!!!!!!!!";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					firstId = 0;
					secondId = 0;

				}
			}
			
		});
	}
	
	private void viewBestTimes() 
	{
		Intent launchBestTimes = new Intent(this,BestTimes.class);
		startActivity(launchBestTimes);
	}
	
	private void findCardById()
	{
		//find other card that was flipped and change background back to blank
		for(int i = 0; i < 12; i++)
		{
			if(cardButtons[i].id == firstId && cardButtons[i].flipped == true)
			{
				cardButtons[i].button.setBackgroundResource(R.drawable.blank);
				cardButtons[i].setFlipped(false);
				
			}
		}
	
	}
	
	private void flip(int index)
	{
		cardButtons[index].setFlipped(true);
		Log.d("LOOOOO","JL:KJ");
	}
	

}
