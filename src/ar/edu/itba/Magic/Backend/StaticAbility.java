package ar.edu.itba.Magic.Backend;

public abstract class StaticAbility extends GameEventRelatedAbility {

	public StaticAbility() {
		
	}

	public abstract GameEvent getRelatedGameEvent();
	
	public abstract void analyzeGameEvent(GameEvent gameEvent);

}
