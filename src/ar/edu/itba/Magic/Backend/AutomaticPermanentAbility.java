package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.ExecutesOnEvent;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements ExecutesOnEvent {

	public abstract void executeOnEvent(GameEvent gameEvent);

}
