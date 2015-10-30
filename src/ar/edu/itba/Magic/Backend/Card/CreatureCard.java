package ar.edu.itba.Magic.Backend.Card;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Attribute;
import ar.edu.itba.Magic.Backend.Creature;
import ar.edu.itba.Magic.Backend.PermanentAbility;

/**
 * When played, this card creates a Creature Permanent and places it on the game stack. This card may only be played
 * during a player's main phase. A CreatureCard and it's generated Creature may or may not contain an Ability.
 */
public class CreatureCard extends Card {

	private int attackPoints;
	private int defencePoints;
	private List<Attribute> attributes;
	
	public CreatureCard(String cardName, String cardType, ColorCard color, int coloredManaCost, int colorlessManaCost, Ability ability, int attackPoints, int defencePoints, List<Attribute> attributes) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
		attributes = new LinkedList<Attribute>();
	}

	public CreatureCard(String cardName, String cardType, ColorCard color, int coloredManaCost, int colorlessManaCost, int attackPoints, int defencePoints, List<Attribute> attributes) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, null);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
		attributes = new LinkedList<Attribute>();
	}
	
	/**
	 * Plays the card if currently in player's hand. Must request the owner to pay the card's mana cost first. Must also
	 * execute the ability's satisfyCastingRequireMents method. If the player fails to satisfy the card's casting requirements,
	 * the card fails to cast.
	 */
	
	public List<Attribute> getAttributes(){
		
		if(attributes.size() == 0){
			return null;
		}
		else{
			List<Attribute> aux = new LinkedList<Attribute>(attributes);
		
			for(Attribute each: attributes){
				aux.add(each);
			}
			return aux;
		}
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
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