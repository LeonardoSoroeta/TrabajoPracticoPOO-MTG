package ar.edu.itba.Magic.Backend;

public abstract class ActivatedAbility extends Ability{
	
	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void activate();

}
