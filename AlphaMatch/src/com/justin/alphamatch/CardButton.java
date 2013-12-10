/************************************************************************
 *  Justin Ramos
 *  CardButton.java
 *  Class that is used to build objects for memory card game, each tile in GameScreen.java is an instance of this class
 **************************************************************************/
package com.justin.alphamatch;

import android.widget.Button;

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
