package ar.edu.itba.Magic.Backend;

/**
 * When played, this card places a SpellAbility on the game stack. This card may be played during a player's main phase or
 * as a fast action.
 */
public class InstantCard extends Card {
	
	public InstantCard(String cardName, String cardType, String color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, null, coloredManaCost, colorlessManaCost, ability);
	}

	/**
	 * Plays the card if currently in player's hand. Must request the owner to pay the card's mana cost first. Must also
	 * execute the ability's satisfyCastingRequireMents method. If the player fails to satisfy the card's casting requirements,
	 * the card fails to cast.
	 */
	public void playCard() {
		// pagar costo
		
		this.getAbility().setSource(this);
		
		if(this.getAbility().satisfyCastingRequirements() == true)
			((SpellAbility)this.getAbility()).sendToStack();
	}
	
	
}
