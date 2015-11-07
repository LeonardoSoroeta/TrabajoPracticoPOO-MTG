package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardType;
import ar.edu.itba.Magic.Backend.Land;
import ar.edu.itba.Magic.Backend.PermanentAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class LandCard extends Card {
	
    public LandCard(CardType cardType, Ability ability) {
        super(cardType, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Land land = new Land(this, (PermanentAbility)this.getAbility());
            land.getAbility().setSourcePermanent(land);
            land.setController(this.getController());
            land.setSpellState(false);
            this.getController().discardCard(this);
            this.getController().placePermanentInPlay(land);
        }
    }
}