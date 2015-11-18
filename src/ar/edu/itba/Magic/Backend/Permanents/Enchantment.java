package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Mechanics.PermanentMechanics;

/** Enchantments are colored permanents that are not a creature */
public class Enchantment extends Permanent {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Enchantment(Card sourceCard, PermanentMechanics ability) {
		super(sourceCard, getDefaultEnchantmentAttributes(), ability);
	}
	
	/**
	 * Creates a list of default attributes contained by enchantments. 
	 * @return Returns a list of default attributes contained by enchantments.
	 */
	private static List<Attribute> getDefaultEnchantmentAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		attributes.add(Attribute.UNTAPS_DURING_UPKEEP);
		
		return attributes;
	}

}
