package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Interfaces.Activator;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;

public abstract class ActivatedAutomaticStackingPermanentAbility extends PermanentAbility implements Activator, GameEventListener, GameStackObject {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 

}
