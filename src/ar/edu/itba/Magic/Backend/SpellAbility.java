package ar.edu.itba.Magic.Backend;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction, Activation {

	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
	public abstract void executeOnActivation();

}
