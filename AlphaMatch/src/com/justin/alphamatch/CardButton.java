package com.justin.alphamatch;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CardButton extends GameScreen
{
	public int id; //id is the random number passed in
	public Button button;
	public Boolean flipped;

	public CardButton(int id, Button button)
	{
		this.id = id;
		this.button = button;
		this.flipped = false;
	}
	
	//getters/setters
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Button getButton() 
	{
		return button;
	}

	public void setButton(Button button) 
	{
		this.button = button;
	}
	
	public Boolean getFlipped() 
	{
		return flipped;
	}

	public void setFlipped(Boolean flipped) 
	{
		this.flipped = flipped;
	}
	
}
