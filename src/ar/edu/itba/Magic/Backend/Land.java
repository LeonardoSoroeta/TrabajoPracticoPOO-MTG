package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;

public class Land extends Permanent {
	
	public Land(Card sourceCard, CardName name, Color color, PermanentAbility ability) {
		super(sourceCard, name, color, getDefaultLandAttributes() , 0, 0, ability);
		
	}
	
	private static List<Attribute> getDefaultLandAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			//agregar
		
		return attributes;
	}

}
