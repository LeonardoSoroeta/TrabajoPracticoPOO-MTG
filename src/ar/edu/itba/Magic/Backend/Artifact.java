package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Event;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;

public class Artifact extends Permanent implements GameStackAction {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
 
	public Artifact(Card sourceCard, String name, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, Color.COLORLESS, getDefaultArtifactAttributes(), 0, colorlessManaCost, ability);
	}
	
	private static List<Attribute> getDefaultArtifactAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			//agregar
		
		return attributes;
	}
	
	/**
	 * Sends the artifact to the game stack when it's Card is played.
	 */
	public void sendToStack(){
		//gameStack.add(this);
	}
	
	/**
	 * Places the artifact in play. Notifies the event. Executes the ability's executeOnIntroduction method.
	 */
	public void resolveInStack() {		
		this.getController().getPermanentsInPlay().add(this);
		
		gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, this));
		
		((PermanentAbility)this.getAbility()).executeOnEntering();
	}
}
