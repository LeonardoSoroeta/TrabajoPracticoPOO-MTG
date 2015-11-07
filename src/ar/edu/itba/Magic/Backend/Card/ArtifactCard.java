package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardType;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;

public class ArtifactCard extends Card {

	public ArtifactCard(CardType cardType, Ability ability) {
		super(cardType, ability);
	}

	public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Artifact artifact = new Artifact(this, (PermanentAbility)this.getAbility());
            artifact.getAbility().setSourcePermanent(artifact);
            artifact.setController(this.getController());
            this.getController().discardCard(this);
            artifact.sendToStack();
        }
    }
}
