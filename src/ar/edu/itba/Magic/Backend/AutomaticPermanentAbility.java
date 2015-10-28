package ar.edu.itba.Magic.Backend;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements ExecutesOnEvent {
	
	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void executeOnIntroduction();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
