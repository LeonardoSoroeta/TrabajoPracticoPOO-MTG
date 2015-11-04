package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;
import ar.edu.itba.Magic.Backend.Interfaces.EventListener;

public abstract class ActivatedAutomaticPermanentAbility extends PermanentAbility implements Activator, EventListener {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();

}
