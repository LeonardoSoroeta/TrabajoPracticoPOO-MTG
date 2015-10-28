package ar.edu.itba.Magic.Backend;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements ExecutesOnEvent {

	public abstract void executeOnIntroduction();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
