package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.SpellAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class SorceryCard extends Card {
    public SorceryCard(CardName cardName, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
        super(cardName, color, coloredManaCost, colorlessManaCost, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            ((SpellAbility)this.getAbility()).setSourceCard(this);
            ((SpellAbility)this.getAbility()).sendToStack();
            this.getController().discardCard(this);
        }
    }
}
