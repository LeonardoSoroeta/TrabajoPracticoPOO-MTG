package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction {
	
	private Card source;
	
	public Card getSource() {
		return source;
	}

	public void setSource(Card source) {
		this.source = source;
	}
	
	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
}
