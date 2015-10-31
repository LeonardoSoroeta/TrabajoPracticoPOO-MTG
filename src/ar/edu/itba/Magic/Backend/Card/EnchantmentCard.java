package ar.edu.itba.Magic.Backend.Card;


import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Enchantment;
import ar.edu.itba.Magic.Backend.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;

/**
 * When played, this card creates an Enchantment Permanent and places it on the game stack. This card may only be played
 * during a player's main phase.
 */
public class EnchantmentCard extends Card {

	public EnchantmentCard(String cardName, String cardType, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
	}
	
	/**
	 * Plays the card if currently in player's hand. Must request the owner to pay the card's mana cost first. Must also
	 * execute the ability's satisfyCastingRequireMents method. If the player fails to satisfy the card's casting requirements,
	 * the card fails to cast.
	 */
	public void playCard() {
		Enchantment enchantment;
		
		//pagar costo
		
		if(this.getAbility().satisfyCastingRequirements() == true) {
			enchantment = new Enchantment(this, this.getCardName(), this.getColor(), this.getColoredManaCost(), this.getColorlessManaCost(), (PermanentAbility)this.getAbility());			
			enchantment.setController(this.getController());		
			enchantment.sendToStack();			
			this.getController().getHand().remove(this);
		}
	}
}
