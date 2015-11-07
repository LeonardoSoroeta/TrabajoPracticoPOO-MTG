package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Exceptions.NotAvailableManaException;

import java.util.HashMap;
import java.util.Map;

public class ManaPool {
	private Map<Color, Integer> manapool;

	public ManaPool(){
		manapool = new HashMap<Color, Integer>();
		manapool.put(Color.RED, 0);
		manapool.put(Color.GREEN, 0);
		manapool.put(Color.BLACK, 0);
		manapool.put(Color.BLUE, 0);
		manapool.put(Color.WHITE, 0);
		manapool.put(Color.COLORLESS, 0);
		
	}
	
	public int getMana(Color color){
		return manapool.get(color);
		
	}
	
	public void setMana(Color color, Integer quantity){
		manapool.put(color, quantity);
	}
	
	public void increaseMana(Color color){
		manapool.put(color, manapool.get(color)+1);
	}
	
	public void decreaseMana(Color color) throws NotAvailableManaException{
		if(isAvailable(color))
			manapool.put(color, manapool.get(color)-1);
		else throw new NotAvailableManaException();
	}

	private boolean isAvailable(Color color){
		return manapool.get(color) - 1 >= 0;
	}
	
	public int getColorlessMana(Color color){
		
		int aux = 0;
		
		for(Color each : manapool.keySet()){
			if(!each.equals(color)){
				aux+=manapool.get(each);
			}
		}
		
		return aux;
	}
}
