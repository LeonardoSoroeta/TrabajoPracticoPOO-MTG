package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;

public class Enchantment extends Permanent {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Enchantment(Card sourceCard, PermanentAbility ability) {
		super(sourceCard, getDefaultEnchantmentAttributes(), ability);
	}
	
	/**
	 * Creates a list of default attributes contained by enchantments. 
	 * @return Returns a list of default attributes contained by enchantments.
	 */
	private static List<Attribute> getDefaultEnchantmentAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			//TODO agregar
		
		return attributes;
	}

}
