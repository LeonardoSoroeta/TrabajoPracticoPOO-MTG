package ar.edu.itba.Magic.Backend;

import java.util.List;

import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

public class Artifact extends Permanent implements GameStackAction {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
 
	public Artifact(Card sourceCard, String name, List<String> attributes, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, "colorless", attributes, 0, colorlessManaCost, ability);
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
		
		gameEventHandler.notifyGameEvent(new GameEvent("new_permanent_in_play", this));
		
		((PermanentAbility)this.getAbility()).executeOnIntroduction();
	}
}
