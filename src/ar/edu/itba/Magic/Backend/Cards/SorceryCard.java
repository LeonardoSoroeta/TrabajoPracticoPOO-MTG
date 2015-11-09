package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.CardType;


public class SorceryCard extends Card {
    public SorceryCard(CardType cardType, Ability ability) {
        super(cardType, ability);
    }

    public void playCard() {
        this.getAbility().executeOnCasting(this);
     }
}
