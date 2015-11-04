package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventListener;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements EventListener {

	public abstract void executeOnEvent(GameEvent gameEvent);

}
