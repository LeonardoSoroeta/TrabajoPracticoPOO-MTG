package ar.edu.itba.Magic.Backend;

public abstract class AutomaticStackingPermanentAbility extends PermanentAbility implements ExecutesOnEvent, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
