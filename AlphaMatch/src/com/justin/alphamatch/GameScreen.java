/************************************************************************
 *  Justin Ramos
 *  GameScreen.java
 *  Allows user to play memory game and beat the best score, user can also navigate to view the best times or quit
 **************************************************************************/

package com.justin.alphamatch;


import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class GameScreen extends Activity
{
	//constants
	final int MAX = 6;
	final int MIN = 1;
	final String TAG = "TEST: ";
	
	//objects
	Timer t1;
	Button quitBtn, viewBestTimesBtn;
	SharedPreferences prefs;
	Button buttons[] = new Button[12]; //array of buttons
	CardButton cardButtons[] = new CardButton[12];
	
	//variables
	int time; //used for saving through sharedpreferences
	int timeSaved;
	String bestTime;
	private TextView bestTimeLabel;
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
	private MediaPlayer correct;
	private MediaPlayer incorrect;
	private MediaPlayer clapping;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		//create audio file objects
		correct = MediaPlayer.create(GameScreen.this, R.raw.correct);
		incorrect = MediaPlayer.create(GameScreen.this, R.raw.wrong);
		clapping = MediaPlayer.create(GameScreen.this, R.raw.appluase);
		
		prefs = this.getSharedPreferences("com.justin.alphamatch", Context.MODE_PRIVATE);
		
		bestTime = prefs.getString("one", "0");
		
		bestTimeLabel = (TextView) findViewById(R.id.best);
		bestTimeLabel.setText(bestTime);
		
		t1 = new Timer((TextView) findViewById(R.id.yourTime)); //create Timer object that starts Time
		
		//quit Button and listener
		quitBtn = (Button) findViewById(R.id.quit);
		quitBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		//button to view best time activity and listener
		viewBestTimesBtn = (Button) findViewById(R.id.goToBestActivity);
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
		
			
		for(int i = 0; i < 12; i++)
		{ //generate 12 random numbers; 1-6 are used for id's for cardButton class
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

	
	public void addMyListener(final Button b, final int id, final int index)
	{
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				switch(id) //set background on touch based on id
				{          // 1 is a ... 6 is f
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
					if(firstId == secondId) //if match then increment match count by 1 and play sound
					{  
						matches += 1;
						correct.stop();
						correct.start();
					}
					else //wait so that card is shown, then reset cards
					{
						incorrect.stop();
						incorrect.start();
						
						findCardById();
						
						b.postDelayed(new Runnable() {

						    public void run() {
						        // change image
						    	b.setBackgroundResource(R.drawable.blank);
						    }

						}, 1000); // 1000ms delay
						
					}
					
					if(matches == 6) //game over
					{
						
						//play applause
						clapping.start();
						
						//get time
						time = t1.getStopSeconds();
							
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
						
						t1.stopTimer(); 
						
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
	
	private void findCardById() //finds card other card that is flipped over and sets background to blank and flipped to false
	{
		
		//find other card that was flipped and change background back to blank
		for(int i = 0; i < 12; i++)
		{
			if(cardButtons[i].id == firstId && cardButtons[i].flipped == true)
			{
				final int index = i;
				cardButtons[i].button.postDelayed(new Runnable() {

				    public void run() {
				        // change image
				    	cardButtons[index].button.setBackgroundResource(R.drawable.blank);
				    }

				}, 1000); // 1000ms delay
				//cardButtons[i].button.setBackgroundResource(R.drawable.blank);
				cardButtons[i].setFlipped(false);
				
			}
		}
	
	}
	
	private void flip(int index) //flips cardButton to true based on index
	{
		cardButtons[index].setFlipped(true);
		
	}
	

}
