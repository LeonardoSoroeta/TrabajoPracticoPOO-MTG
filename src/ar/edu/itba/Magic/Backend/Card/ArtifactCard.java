package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Artifact;
import ar.edu.itba.Magic.Backend.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;

public class ArtifactCard extends Card {

	public ArtifactCard(CardName cardName, Integer colorlessManaCost, Ability ability) {
		super(cardName, Color.COLORLESS, 0, colorlessManaCost, ability);
	}

	public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Artifact artifact = new Artifact(this, this.getCardName(), this.getColoredManaCost(), (PermanentAbility)this.getAbility());
            artifact.getAbility().setSourcePermanent(artifact);
            artifact.setController(this.getController());
            artifact.sendToStack();
            this.getController().discardCard(this);
        }
    }
}
