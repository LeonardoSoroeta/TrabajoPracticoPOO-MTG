package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.SpellAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class SorceryCard extends Card {
    public SorceryCard(String cardName, String cardType, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
        super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            ((SpellAbility)this.getAbility()).setSourceCard(this);
            ((SpellAbility)this.getAbility()).sendToStack();
        }

    }
}
