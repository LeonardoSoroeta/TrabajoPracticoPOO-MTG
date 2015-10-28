package ar.edu.itba.Magic.Backend;

public abstract class ActivatedAutomaticStackingPermanentAbility extends PermanentAbility implements Activation, ExecutesOnEvent, GameStackAction {
	
	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract void executeOnIntroduction();
	
	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
