package ar.edu.itba.Magic.Backend;

public abstract class ActivatedAbility extends Ability implements GameStackAction {

	public ActivatedAbility(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void activate();
	
	public abstract void resolve();

}
