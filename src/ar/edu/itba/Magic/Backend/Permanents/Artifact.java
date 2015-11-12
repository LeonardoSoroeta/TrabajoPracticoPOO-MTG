package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;

public class Artifact extends Permanent implements GameStackObject {
	 
	public Artifact(Card sourceCard, PermanentAbility ability) {
		super(sourceCard, getDefaultArtifactAttributes(), ability);
	}
	
	private static List<Attribute> getDefaultArtifactAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		attributes.add(Attribute.UNTAPS_DURING_UPKEEP);
		
		return attributes;
	}
	
}
