package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;

/** This card creates a non-permanent Spell on casting. */
public class SorceryCard extends Card {
    public SorceryCard(CardType cardType, Mechanics ability) {
        super(cardType, ability);
    }

    public void playCard() {
        this.getAbility().executeOnCasting(this);
     }
}
