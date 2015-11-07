package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;

public class Artifact extends Permanent implements GameStackAction {
	 
	public Artifact(Card sourceCard, PermanentAbility ability) {
		super(sourceCard, getDefaultArtifactAttributes(), ability);
	}
	
	private static List<Attribute> getDefaultArtifactAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			// TODO agregar
		
		return attributes;
	}
	
}
