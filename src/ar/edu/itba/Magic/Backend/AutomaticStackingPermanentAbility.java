package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventListener;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

public abstract class AutomaticStackingPermanentAbility extends PermanentAbility implements EventListener, GameStackAction {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
