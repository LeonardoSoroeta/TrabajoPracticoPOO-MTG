package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;

public abstract class AutomaticStackingPermanentAbility extends PermanentAbility implements GameEventListener, GameStackObject {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
