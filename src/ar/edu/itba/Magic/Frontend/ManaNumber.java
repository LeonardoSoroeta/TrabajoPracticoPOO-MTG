package ar.edu.itba.Magic.Frontend;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Enums.Color;

public class ManaNumber {

	Map< Color, ExtendedImage> mana;
	
	
	
	public ManaNumber() throws SlickException{
		
		mana = new HashMap< Color,  ExtendedImage>();
		
			for(Color color: Color.values()){
			
					mana.put(color, new ExtendedImage("res/Match/Mana/TransparentButton.png"));	

				
			}	
	}
	
	public Map< Color, ExtendedImage> getMana(){
		
		return mana;
	}
	
	
}
