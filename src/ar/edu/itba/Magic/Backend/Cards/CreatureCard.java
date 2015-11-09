package ar.edu.itba.Magic.Backend.Cards;

import java.util.List;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.CardType;


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

    public void playCard() {
       this.getAbility().executeOnCasting(this);
    }
    
    public Integer getAttackPoints() {
    	return attackPoints;
    }
    
    public Integer getDefencePoints() {
    	return defencePoints;
    }
    
    public List<Attribute> getAttributes() {
    	return attributes;
    }
}

