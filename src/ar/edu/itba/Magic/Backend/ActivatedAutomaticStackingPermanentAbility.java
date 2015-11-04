package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;
import ar.edu.itba.Magic.Backend.Interfaces.EventListener;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

public abstract class ActivatedAutomaticStackingPermanentAbility extends PermanentAbility implements Activator, EventListener, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
