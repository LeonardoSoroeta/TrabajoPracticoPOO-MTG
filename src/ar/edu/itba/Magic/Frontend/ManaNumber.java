package ar.edu.itba.Magic.Frontend;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Enums.Color;

public class ManaNumber {

	Map< Color, Map< Integer, ExtendedImage>> mana;
	
	
	
	public ManaNumber() throws SlickException{
		
		mana = new HashMap< Color, Map< Integer, ExtendedImage>>();
		
			for(Color color: Color.values()){
			
				Map< Integer, ExtendedImage> map = new HashMap< Integer, ExtendedImage>();
				
					for(int i=0; i<10; i++){
						
						
					map.put(i, new ExtendedImage("res/Match/Mana/" + color.toString() + "/" + i + ".png"));	
					
				
					}	
					
				mana.put(color , map);
				
			}	
	}
	
	public Map< Color, Map< Integer, ExtendedImage>> getNumber(){
		
		return mana;
	}
	
	
}
