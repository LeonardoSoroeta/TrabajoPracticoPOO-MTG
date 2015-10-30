package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Artifact;
import ar.edu.itba.Magic.Backend.PermanentAbility;

/**
 * When played, this card creates an Artifact Permanent and places it on the game stack. This card may only be played
 * during a player's main phase.
 */
public class ArtifactCard extends Card {

	public ArtifactCard(String cardName, String cardType, int colorlessManaCost, Ability ability) {
		super(cardName, cardType, ColorCard.COLORLESS, 0, colorlessManaCost, ability);
	}
	
	/**
	 * Plays the card if currently in player's hand. Must request the owner to pay the card's mana cost first. Must also
	 * execute the ability's satisfyCastingRequireMents method. If the player fails to satisfy the card's casting requirements,
	 * the card fails to cast.
	 */
	public void playCard() {
		Artifact artifact;
		
		//pagar costo
		
		if(this.getAbility().satisfyCastingRequirements() == true) {
			artifact = new Artifact(this, this.getCardName(), this.getColorlessManaCost(), (PermanentAbility)this.getAbility());			
			artifact.setController(this.getController());	
			artifact.sendToStack();		
			this.getController().getHand().remove(this);
		}
	}
}
