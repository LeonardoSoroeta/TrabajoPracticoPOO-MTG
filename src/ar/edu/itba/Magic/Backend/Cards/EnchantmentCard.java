package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;


public class EnchantmentCard extends Card {
	
    public EnchantmentCard(CardType cardName, Mechanics ability) {
        super(cardName, ability);
    }

    public void playCard() {
        this.getAbility().executeOnCasting(this);
     }
}
