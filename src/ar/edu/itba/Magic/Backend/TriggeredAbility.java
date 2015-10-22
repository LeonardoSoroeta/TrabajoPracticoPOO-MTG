package ar.edu.itba.Magic.Backend;

public abstract class TriggeredAbility extends GameEventRelatedAbility implements GameStackAction {

	public TriggeredAbility() {
	
	}

	public abstract GameEvent getRelatedGameEvent();
	
	public abstract void analyzeGameEvent(GameEvent gameEvent);

}
