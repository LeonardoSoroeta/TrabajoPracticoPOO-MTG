package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;

public class Artifact extends Permanent implements GameStackAction {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
 
	public Artifact(Card sourceCard, CardName name, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, Color.COLORLESS, getDefaultArtifactAttributes(), 0, colorlessManaCost, ability);
	}
	
	private static List<Attribute> getDefaultArtifactAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			// TODO agregar
		
		return attributes;
	}
	
	/**
	 * Sends the artifact to the game stack when it's Card is played.
	 */
	public void sendToStack(){
		// TODO gameStack.add(this);
	}
	
	/**
	 * Places the artifact in play. Notifies the event. Executes the ability's executeOnIntroduction method.
	 */
	public void resolveInStack() {		
		this.getController().placePermanentInPlay(this);
	}
}
