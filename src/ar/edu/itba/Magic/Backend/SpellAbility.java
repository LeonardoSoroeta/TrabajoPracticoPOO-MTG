package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction {

	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
}
