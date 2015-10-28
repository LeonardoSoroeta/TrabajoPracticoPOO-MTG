package ar.edu.itba.Magic.Backend;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction, Activation {

	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
	public abstract void executeOnActivation();

}
