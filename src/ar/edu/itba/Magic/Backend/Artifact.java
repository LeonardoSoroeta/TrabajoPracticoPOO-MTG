package ar.edu.itba.Magic.Backend;

import java.util.List;

public class Artifact extends Permanent {
 
	public Artifact(Card sourceCard, String name, List<String> attributes, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, "colorless", attributes, 0, colorlessManaCost, ability);
	}
	
}
