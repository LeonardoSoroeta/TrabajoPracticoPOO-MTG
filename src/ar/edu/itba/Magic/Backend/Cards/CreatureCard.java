package ar.edu.itba.Magic.Backend.Cards;

import java.util.List;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

/**
 * Created by Martin on 31/10/2015.
 */
public class CreatureCard extends Card {
	private Integer attackPoints;
    private Integer defencePoints;
    private List<Attribute> attributes;

    public CreatureCard(CardType cardType, List<Attribute> attributes, Integer attackPoints, Integer defencePoints, Ability ability) {
        super(cardType, ability);
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.attributes = attributes;
    }

    public CreatureCard(CardType cardType, List<Attribute> attributes, Integer attackPoints, Integer defencePoints) {
        super(cardType, (Ability)null);
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.attributes = attributes;
    }

    public void playCard() {
        Creature creature;
        if(this.containsAbility()) {
            if(this.getAbility().satisfyCastingRequirements()) {
                creature = new Creature(this, this.attributes, this.attackPoints, this.defencePoints, (PermanentAbility)this.getAbility());
                creature.getAbility().setSourcePermanent(creature);
                creature.setController(this.getController());
                this.getController().discardCard(this);
                creature.sendToStack();
            }
        } else {
            creature = new Creature(this, this.attributes, this.attackPoints, this.defencePoints);
            creature.setController(this.getController());
            this.getController().discardCard(this);
            creature.sendToStack();
        }
    }
}
