package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

public abstract class AutomaticPermanentAbility extends PermanentAbility implements GameEventListener {

	public abstract void executeOnEvent(GameEvent gameEvent);

}
