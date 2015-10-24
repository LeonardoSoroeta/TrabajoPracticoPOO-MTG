package ar.edu.itba.Magic.Backend;

public abstract class StaticAbility extends PermanentAbility implements ExecutesOnEvent {

	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void executeOnAppearance();
	
	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract String getEventDescriptor();

}
