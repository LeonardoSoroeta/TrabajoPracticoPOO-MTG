package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Land;
import ar.edu.itba.Magic.Backend.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Event;

/**
 * When played, this card creates a Land Permanent and places it in play. This card may only be played during a player's
 * main phase, and only one per turn.
 */
public class LandCard extends Card{
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();

	public LandCard(String cardName, String cardType, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
	}
	
	/**
	 * Plays the card if currently in player's hand. Must execute the ability's satisfyCastingRequireMents method. If the 
	 * player fails to satisfy the card's casting requirements, the card fails to cast.
	 */
	public void playCard() {
		Land land;
		
		//pagar costo
		
		if(this.getAbility().satisfyCastingRequirements() == true) {
			land = new Land(this, this.getCardName(), this.getColor(), (PermanentAbility)this.getAbility());	
			land.getAbility().setSourcePermanent(land);
			land.setController(this.getController());			
			land.getController().getHand().remove(this);			
			land.getController().getPermanentsInPlay().add(land);			
			gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, land));			
			((PermanentAbility)land.getAbility()).executeOnEntering();
		}
	}
	
}
