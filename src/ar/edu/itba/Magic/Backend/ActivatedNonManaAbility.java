package ar.edu.itba.Magic.Backend;

public abstract class ActivatedNonManaAbility extends ActivatedAbility implements GameStackAction {

	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void activate();

	public abstract void sendToStack();
	
	public abstract void resolve();
}
