package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;


public class Enchantment extends Permanent {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Enchantment(Card sourceCard, CardName name, Color color, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, color, getDefaultEnchantmentAttributes(), coloredManaCost, colorlessManaCost, ability);
	}
	
	private static List<Attribute> getDefaultEnchantmentAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			//TODO agregar
		
		return attributes;
	}

}
