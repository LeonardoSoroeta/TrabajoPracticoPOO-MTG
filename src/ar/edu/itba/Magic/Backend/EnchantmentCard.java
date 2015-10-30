package ar.edu.itba.Magic.Backend;

import java.util.List;

/**
 * When played, this card creates an Enchantment Permanent and places it on the game stack. This card may only be played
 * during a player's main phase.
 */
public class EnchantmentCard extends Card {

	public EnchantmentCard(String cardName, String cardType, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, attributes, coloredManaCost, colorlessManaCost, ability);
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
			enchantment = new Enchantment(this, this.getCardName(), this.getColor(), this.getAttributes(), this.getColoredManaCost(), this.getColorlessManaCost(), (PermanentAbility)this.getAbility());			
			enchantment.setController(this.getController());	
			enchantment.getAbility().setSource(enchantment);
			enchantment.sendToStack();			
			this.getController().getHand().remove(this);
		}
	}
}
