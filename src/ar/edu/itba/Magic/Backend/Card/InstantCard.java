package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Interfaces.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.SpellAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class InstantCard extends Card {
    public InstantCard(String cardName, String cardType, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
        super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            ((SpellAbility)this.getAbility()).setSourceCard(this);
            ((SpellAbility)this.getAbility()).sendToStack();
        }

    }
}
