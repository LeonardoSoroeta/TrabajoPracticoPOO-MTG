package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Mechanics.PermanentMechanics;

/** A Land is a special permanent responsible for generating mana */
public class Land extends Permanent {
	
	public Land(Card sourceCard, PermanentMechanics ability) {
		super(sourceCard, getDefaultLandAttributes(), ability);
		
	}
	
	private static List<Attribute> getDefaultLandAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		attributes.add(Attribute.UNTAPS_DURING_UPKEEP);
		
		return attributes;
	}

}
