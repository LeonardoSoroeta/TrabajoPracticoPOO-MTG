package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardType;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Abilities.SpellAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class InstantCard extends Card {
    public InstantCard(CardType cardType, Ability ability) {
        super(cardType, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            ((SpellAbility)this.getAbility()).setSourceCard(this);
            this.getController().discardCard(this);
            ((SpellAbility)this.getAbility()).sendToStack();
        }
    }
}
