package ar.edu.itba.Magic.Backend;

import java.util.List;

/**
 * When played, this card creates a Land Permanent and places it in play. This card may only be played during a player's
 * main phase, and only one per turn.
 */
public class LandCard extends Card{
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();

	public LandCard(String cardName, String cardType, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, attributes, coloredManaCost, colorlessManaCost, ability);
	}
	
	/**
	 * Plays the card if currently in player's hand. Must execute the ability's satisfyCastingRequireMents method. If the 
	 * player fails to satisfy the card's casting requirements, the card fails to cast.
	 */
	public void playCard() {
		Land land;
		
		//pagar costo
		
		if(this.getAbility().satisfyCastingRequirements() == true) {
			land = new Land(this, this.getCardName(), this.getColor(), this.getAttributes(), (PermanentAbility)this.getAbility());		
			land.setController(this.getController());		
			land.getAbility().setSource(land);
			land.getController().getHand().remove(this);			
			land.getController().getPermanentsInPlay().add(land);			
			gameEventHandler.notifyGameEvent(new GameEvent("new_permanent_in_play", land));			
			((PermanentAbility)land.getAbility()).executeOnIntroduction();
		}
	}
	
}
