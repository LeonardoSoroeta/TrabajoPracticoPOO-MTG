package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Exceptions.NoAvailableManaException;

//import ar.edu.itba.Magic.Backend.Exceptions.NoAvailableManaException;

import java.util.HashMap;
import java.util.Map;


/**
 * Each player contains one ManaPool. A Manapool is responsible for keeping all mana belonging to one player.
 */
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
	
	/**
	 * Gets the ammount of available mana of a given color.
	 */
	public Integer getAvailableManaOfThisColor(Color color) {
		return manapool.get(color);
	}
	
	/**
	 * Sets the ammount of mana of a given color contained by this mana pool.
	 */
	public void setManaOfThisColor(Color color, Integer quantity) {
		manapool.put(color, quantity);
	}
	
	/**
	 * Adds a determined ammount of mana of a given color.
	 */
	public void addManaOfThisColor(Color color, Integer ammount) {
		manapool.put(color, manapool.get(color) + ammount);
	}
	
	/**
	 * Adds one mana of a given color.
	 */
	public void addOneManaOfThisColor(Color color) {
		manapool.put(color, manapool.get(color)+1);
	}
	
	/**
	 * Removes one mana of a given color.
	 */
	public void removeOneManaOfThisColor(Color color) {
		if(containsOneManaOfThisColor(color))
			manapool.put(color, manapool.get(color)-1);
		else throw new NoAvailableManaException();
	}

	/**
	 * Returns true if mana pool contains one mana of a given color.
	 */
	public boolean containsOneManaOfThisColor(Color color) {
		return manapool.get(color) > 0;
	}
	
	/**
	 * Gets the total ammount of mana, independent of color.
	 */
	public Integer getConvertedManaCost() {
		Integer total = 0;
		for(Color each : manapool.keySet()) {
				total+=manapool.get(each);
		}
		return total;
	}
	
	/**
	 * Empties this mana pool.
	 */
	public void resetMana(){
		for(Color each : manapool.keySet()) {
			this.setManaOfThisColor(each, 0);
		}
	}
	
	/** 
	 * Returns true if the ManaPool's owner contains enough mana to pay a specified mana cost.
	 */
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
