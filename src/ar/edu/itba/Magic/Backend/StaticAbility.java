package ar.edu.itba.Magic.Backend;

public abstract class StaticAbility extends GameEventRelatedAbility {

	public StaticAbility(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public abstract GameEvent getRelatedGameEvent();
	
	public abstract void analyzeGameEvent(GameEvent gameEvent);

}
