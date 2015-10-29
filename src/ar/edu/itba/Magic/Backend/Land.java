package ar.edu.itba.Magic.Backend;

import java.util.List;

public class Land extends Permanent {
	
	public Land(Card sourceCard, String name, String color, List<String> attributes, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, 0, 0, ability);
	}

}
