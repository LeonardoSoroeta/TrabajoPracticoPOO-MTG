package ar.edu.itba.Magic.Backend;

public abstract class TriggeredAbility extends PermanentAbility implements ExecutesOnEvent, GameStackAction {

	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void executeOnAppearance();
	
	public abstract void sendToStack();
	
	public abstract void resolve();
	
	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract String getEventDescriptor();

}
