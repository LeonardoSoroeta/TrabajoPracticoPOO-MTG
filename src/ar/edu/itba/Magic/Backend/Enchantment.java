package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;


public class Enchantment extends Permanent {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Enchantment(Card sourceCard, String name, Color color, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, color, getDefaultEnchantmentAttributes(), coloredManaCost, colorlessManaCost, ability);
	}
	
	private static List<Attribute> getDefaultEnchantmentAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
			//agregar
		
		return attributes;
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
		
		gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, this));
		
		((PermanentAbility)this.getAbility()).executeOnEntering();
	}

}
