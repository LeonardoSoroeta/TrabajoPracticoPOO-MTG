package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;
import ar.edu.itba.Magic.Backend.Interfaces.EventObserver;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

public abstract class ActivatedAutomaticStackingPermanentAbility extends PermanentAbility implements Activator, EventObserver, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
