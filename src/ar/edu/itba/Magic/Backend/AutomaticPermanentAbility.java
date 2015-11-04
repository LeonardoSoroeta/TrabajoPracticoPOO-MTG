package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventObserver;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements EventObserver {

	public abstract void executeOnEvent(GameEvent gameEvent);

}
