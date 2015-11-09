package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.CardType;


public class EnchantmentCard extends Card {
	
    public EnchantmentCard(CardType cardName, Ability ability) {
        super(cardName, ability);
    }

    public void playCard() {
        this.getAbility().executeOnCasting(this);
     }
}
