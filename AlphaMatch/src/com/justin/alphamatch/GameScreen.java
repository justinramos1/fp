package com.justin.alphamatch;

import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.inputmethodservice.Keyboard.Key;
import android.media.MediaPlayer;

public class GameScreen extends Activity
{
	Timer t1;
	Button quitBtn;
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
	int MAX = 6;
	int MIN = 1;
	int row = 0;
	int col = 0;
	int randNum;
	int matches = 0; //6 matches wins the game
	int tries = 0; //if 2 tries but no match it gets reset to zero
	LinkedHashMap<Key, Value> memoryBoard = new LinkedHashMap<Key, Value>(); //button names are keys, values are 1 - 6
	private int[] valuesNotUsed = { 0, 2, 2, 2, 2, 2, 2 }; //numbers are decremented as they are picked by random number generator
	//MediaPlayer vars for sounds                          //index represents number val
	private MediaPlayer mpA; //Sound for letter A
	private MediaPlayer mpB; //Sound for letter B etc
	private MediaPlayer mpC;
	private MediaPlayer mpD;
	private MediaPlayer mpE;
	private MediaPlayer mpF;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		t1 = new Timer((TextView) findViewById(R.id.yourTime)); //create Timer object that starts Time
		//create start button and setup listener
		quitBtn = (Button) findViewById(R.id.quit);
		quitBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		//get buttons from board
		b1 = (Button) findViewById(R.id.button_1);
		b2 = (Button) findViewById(R.id.button_2);
		b3 = (Button) findViewById(R.id.button_3);
		b4 = (Button) findViewById(R.id.button_4);
		b5 = (Button) findViewById(R.id.button_5);
		b6 = (Button) findViewById(R.id.button_6);
		b7 = (Button) findViewById(R.id.button_7);
		b8 = (Button) findViewById(R.id.button_8);
		b9 = (Button) findViewById(R.id.button_9);
		b10 = (Button) findViewById(R.id.button10);
		b11 = (Button) findViewById(R.id.button_11);
		b12 = (Button) findViewById(R.id.button_12);
		
		b1.setBackgroundResource(R.drawable.a);
		
		for(int i = 0; i < 12; i++)
		{ //generate 12 random numbers; 1-6
			Random r = new Random();
			this.randNum = r.nextInt(this.MAX) + this.MIN; //rand between 1 and 6
			
			//check to see if we can use the random number, if not then loop until we get a usable number
			while (this.valuesNotUsed[this.randNum] == 0)
			{
				this.randNum = r.nextInt(this.MAX) + this.MIN; //generate rand number
			}
			
			//once we've arrived here we have a usable number
			
			
			this.valuesNotUsed[this.randNum]--;//decrement array
				
		}//end forloop
		
	}
	
	@Override
    protected void onStop() 
	{
        finish();
        super.onStop();
    }
	
	//Changes button background back to blank
	protected void setButtonBackground(Button b)
	{
		b.setBackgroundResource(R.drawable.blank);
	}
	

}
