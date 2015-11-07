package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;

public class Land extends Permanent {
	
	public Land(Card sourceCard, PermanentAbility ability) {
		super(sourceCard, getDefaultLandAttributes(), ability);
		
	}
	
	private static List<Attribute> getDefaultLandAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			// TODO agregar
		
		return attributes;
	}

}
