package com.justin.alphamatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;

public class GameScreen extends Activity
{
	Timer t1;
	Button quitBtn;
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
	//MediaPlayer vars for sounds
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
		quitBtn.setOnClickListener(new View.OnClickListener(){
									
			@Override
			public void onClick(View v) {
				finish();
										
			}
		});
		
		b1 = (Button) findViewById(R.id.button1);
		b1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.a, 0, 0);
		
	}
	
	@Override
    protected void onStop() {
        finish();
        super.onStop();
    }
	

}
