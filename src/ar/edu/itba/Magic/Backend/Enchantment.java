package ar.edu.itba.Magic.Backend;

import java.util.List;

public class Enchantment extends Permanent {
	
	public Enchantment(Card sourceCard, String name, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, ability);
	}

}
