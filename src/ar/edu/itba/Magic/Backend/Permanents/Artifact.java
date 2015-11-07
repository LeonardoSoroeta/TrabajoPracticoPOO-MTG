package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

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
