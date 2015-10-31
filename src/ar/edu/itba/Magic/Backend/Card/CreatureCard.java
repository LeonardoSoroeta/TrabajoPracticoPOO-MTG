package ar.edu.itba.Magic.Backend.Card;
import java.util.List;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;
import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Creature;
import ar.edu.itba.Magic.Backend.PermanentAbility;

/**
 * When played, this card creates a Creature Permanent and places it on the game stack. This card may only be played
 * during a player's main phase. A CreatureCard and it's generated Creature may or may not contain an Ability.
 */
public class CreatureCard extends Card {

	private Integer attackPoints;
	private Integer defencePoints;
	private List<Attribute> attributes;
	
	public CreatureCard(String cardName, String cardType, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attackPoints, Integer defencePoints, Ability ability) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
		this.attributes = attributes;
	}

	public CreatureCard(String cardName, String cardType, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attackPoints, Integer defencePoints) {
		super(cardName, cardType, color, coloredManaCost, colorlessManaCost, null);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
		this.attributes = attributes;
	}
	
/*	
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
 */
	
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
				creature = new Creature(this, this.getCardName(), this.getColor(), attributes, this.getColoredManaCost(), this.getColorlessManaCost(), attackPoints, defencePoints, (PermanentAbility)this.getAbility());
				creature.getAbility().setSource(creature);
				creature.setController(this.getController());
				creature.sendToStack();	
				this.getController().getHand().remove(this);
			}
		}
		else {
			creature = new Creature(this, this.getCardName(), this.getColor(), attributes, this.getColoredManaCost(), attackPoints, defencePoints, this.getColorlessManaCost());
			creature.getAbility().setSource(creature);
			creature.setController(this.getController());
			creature.sendToStack();	
			this.getController().getHand().remove(this);
		}

	}
	
}