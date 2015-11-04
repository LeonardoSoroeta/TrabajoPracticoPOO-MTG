package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventObserver;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

public abstract class AutomaticStackingPermanentAbility extends PermanentAbility implements EventObserver, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
