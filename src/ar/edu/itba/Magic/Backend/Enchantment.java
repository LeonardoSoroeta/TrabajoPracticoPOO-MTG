package ar.edu.itba.Magic.Backend;

import java.util.List;

public class Enchantment extends Permanent {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Enchantment(Card sourceCard, String name, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, ability);
	}
	
	/**
	 * Sends the enchantment to the game stack when it's Card is played.
	 */
	public void sendToStack(){
		//gameStack.add(this);
	}
	
	/**
	 * Places the enchantment in play. Notifies the event. Executes the ability's executeOnIntroduction method.
	 */
	public void resolveInStack() {
		this.getController().getPermanentsInPlay().add(this);
		
		gameEventHandler.notifyGameEvent(new GameEvent("new_permanent_in_play", this));
		
		((PermanentAbility)this.getAbility()).executeOnIntroduction();
	}

}
