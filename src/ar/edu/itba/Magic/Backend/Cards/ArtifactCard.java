package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;

/** This card generates an Artifact Permanent on casting. */
public class ArtifactCard extends Card {

	public ArtifactCard(CardType cardType, Mechanics ability) {
		super(cardType, ability);
	}

    public void playCard() {
        this.getAbility().executeOnCasting(this);
     }
}
