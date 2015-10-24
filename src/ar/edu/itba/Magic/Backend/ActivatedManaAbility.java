package ar.edu.itba.Magic.Backend;

public abstract class ActivatedManaAbility extends ActivatedAbility {

	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void executeOnAppearance();
	
	public abstract void activate();
	
}
