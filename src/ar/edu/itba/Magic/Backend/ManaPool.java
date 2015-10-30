package ar.edu.itba.Magic.Backend;

import java.util.HashMap;
import java.util.Map;

public class ManaPool {
	Map<String, Integer> manapool;
	// cambiar por lo que se use para el color el String
	public ManaPool(){
		manapool = new HashMap<String, Integer>();
		manapool.put("red", 0);
		manapool.put("black", 0);
		manapool.put("green", 0);
		manapool.put("white", 0);
		manapool.put("blue", 0);
		manapool.put("colorless", 0);
		
	}
	
	public int getMana(String color){
		// hago con excepcion
		return manapool.get(color);
		
	}
	public void setMana(String color, int quantity){
		manapool.put(color, quantity);
	}
	
	public void increaseMana(String color){
		int aux = manapool.get(color);
		manapool.put("color", aux+1);
	}
	public void decreaseMana(String color){
		int aux = manapool.get(color);
		manapool.put("color", aux-1);
	}
	}