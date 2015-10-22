package ar.edu.itba.Magic.Backend;

public abstract class TriggeredAbility extends GameEventRelatedAbility implements GameStackAction {

	public TriggeredAbility(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public abstract GameEvent getRelatedGameEvent();
	
	public abstract void analyzeGameEvent(GameEvent gameEvent);

}
