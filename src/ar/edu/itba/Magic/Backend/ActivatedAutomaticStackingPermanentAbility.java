package ar.edu.itba.Magic.Backend;

public abstract class ActivatedAutomaticStackingPermanentAbility extends PermanentAbility implements Activation, ExecutesOnEvent, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
