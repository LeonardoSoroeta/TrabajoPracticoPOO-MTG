package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction {
	
	private Card sourceCard;
	
	public Card getSourceCard() {
		return sourceCard;
	}

	public void setSourceCard(Card sourceCard) {
		this.sourceCard = sourceCard;
	}
	
	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
}
