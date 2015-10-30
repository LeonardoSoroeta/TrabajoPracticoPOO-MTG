package ar.edu.itba.Magic.Backend;

import java.util.List;

/**
 * When played, this card creates a Creature Permanent and places it on the game stack. This card may only be played
 * during a player's main phase. A CreatureCard and it's generated Creature may or may not contain an Ability.
 */
public class CreatureCard extends Card {

	private int attackPoints;
	private int defencePoints;
	
	public CreatureCard(String cardName, String cardType, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints, Ability ability) {
		super(cardName, cardType, color, attributes, colorMana, colorlessMana, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}

	public CreatureCard(String cardName, String cardType, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints) {
		super(cardName, cardType, color, attributes, colorMana, colorlessMana, null);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}
	
	/**
	 * Plays the card if currently in player's hand. Must request the owner to pay the card's mana cost first. Must also
	 * execute the ability's satisfyCastingRequireMents method. If the player fails to satisfy the card's casting requirements,
	 * the card fails to cast.
	 */
	public void playCard() {
		Creature creature;
		
		//pagar costo
		
		if(this.containsAbility()) {
			if(this.getAbility().satisfyCastingRequirements() == true) {
				creature = new Creature(this, this.getCardName(), attackPoints, defencePoints, this.getColor(), this.getAttributes(), this.getColoredManaCost(), this.getColorlessManaCost(), (PermanentAbility)this.getAbility());
				creature.setController(this.getController());
				creature.sendToStack();	
				this.getController().getHand().remove(this);
			}
		}
		else {
			creature = new Creature(this, this.getCardName(), attackPoints, defencePoints, this.getColor(), this.getAttributes(), this.getColoredManaCost(), this.getColorlessManaCost());
			creature.setController(this.getController());
			creature.sendToStack();	
			this.getController().getHand().remove(this);
		}

	}
	
}