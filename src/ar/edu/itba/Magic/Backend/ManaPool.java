package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.Color;

//import ar.edu.itba.Magic.Backend.Exceptions.NoAvailableManaException;

import java.util.HashMap;
import java.util.Map;

public class ManaPool {
	private Map<Color, Integer> manapool;

	public ManaPool() {
		manapool = new HashMap<Color, Integer>();
		manapool.put(Color.RED, 0);
		manapool.put(Color.GREEN, 0);
		manapool.put(Color.BLACK, 0);
		manapool.put(Color.BLUE, 0);
		manapool.put(Color.WHITE, 0);
		manapool.put(Color.COLORLESS, 0);
	}
	
	public Integer getAvailableManaOfThisColor(Color color) {
		return manapool.get(color);
	}
	
	public void setManaOfThisColor(Color color, Integer quantity) {
		manapool.put(color, quantity);
	}
	
	public void addManaOfThisColor(Color color, Integer ammount) {
		manapool.put(color, manapool.get(color) +1);
	}
	
	public void addOneManaOfThisColor(Color color) {
		manapool.put(color, manapool.get(color)+1);
	}
	
	public void removeOneManaOfThisColor(Color color) /*throws NoAvailableManaException*/ {
		if(containsOneManaOfThisColor(color))
			manapool.put(color, manapool.get(color)-1);
		//else throw new NoAvailableManaException();
	}

	public boolean containsOneManaOfThisColor(Color color) {
		return manapool.get(color) > 0;
	}
	
	public Integer getConvertedManaCost() {
		Integer total = 0;
		for(Color each : manapool.keySet()) {
				total+=manapool.get(each);
		}
		return total;
	}
	
	public void resetMana(){
		for(Color each : manapool.keySet()) {
			this.setManaOfThisColor(each, 0);
		}
	}
	
	public void manaBurn(Player player) {
		for(Color each : manapool.keySet()) {
			player.takeDamage(this.getAvailableManaOfThisColor(each));
		}
		this.resetMana();
	}
	
	public boolean containsEnoughManaToPay(Color color, Integer coloredManaCost, Integer colorlessManaCost) {
		Integer convertedManaCost = this.getConvertedManaCost();
		if(this.getAvailableManaOfThisColor(color) < coloredManaCost) {
			return false;
		} else if(convertedManaCost - coloredManaCost < colorlessManaCost) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
