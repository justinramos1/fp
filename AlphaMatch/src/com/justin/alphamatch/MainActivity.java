/************************************************************************
 *  Justin Ramos
 *  MainActivity.java
 *  Shows title and provides a way for the user to start the game, view best times, or quit
 **************************************************************************/

package com.justin.alphamatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity 
{
	
	Button startBtn, quitBtn, bestTimesBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//create start button and setup listener
		startBtn = (Button) findViewById(R.id.startButton);
		startBtn.setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v) 
			{
				startGame();
						
			}
		});
		
		quitBtn = (Button) findViewById(R.id.quit);
		quitBtn.setOnClickListener(new View.OnClickListener()
		{
							
			@Override
			public void onClick(View v) 
			{
				startGame();
								
			}
		});
		
		
		bestTimesBtn = (Button) findViewById(R.id.viewBestTimeBtn_Main);
		bestTimesBtn.setOnClickListener(new View.OnClickListener()
		{
									
			@Override
			public void onClick(View v) 
			{
				viewBestTimes();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    protected void onStop() 
	{
        finish();
        super.onStop();
    }
	
	
	private void startGame() 
	{
		Intent launchGame = new Intent(this,GameScreen.class);
		startActivity(launchGame);
	}
	
	private void viewBestTimes() 
	{
		Intent launchBestTimes = new Intent(this,BestTimes.class);
		startActivity(launchBestTimes);
	}

}	
