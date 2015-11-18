package ar.edu.itba.Magic.Backend.Permanents;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.PermanentMechanics;

/** Artifacts are colorless permanents */
public class Artifact extends Permanent implements Spell {
	 
	public Artifact(Card sourceCard, PermanentMechanics ability) {
		super(sourceCard, getDefaultArtifactAttributes(), ability);
	}
	
	private static List<Attribute> getDefaultArtifactAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		attributes.add(Attribute.UNTAPS_DURING_UPKEEP);
		
		return attributes;
	}
	
}
