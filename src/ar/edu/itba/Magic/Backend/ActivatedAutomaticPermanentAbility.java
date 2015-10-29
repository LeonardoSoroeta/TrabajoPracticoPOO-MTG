package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activation;
import ar.edu.itba.Magic.Backend.Interfaces.ExecutesOnEvent;

public abstract class ActivatedAutomaticPermanentAbility extends PermanentAbility implements Activation, ExecutesOnEvent {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();

}
