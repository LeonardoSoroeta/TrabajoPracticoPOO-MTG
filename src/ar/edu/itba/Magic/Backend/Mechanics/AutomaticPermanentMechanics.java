package ar.edu.itba.Magic.Backend.Mechanics;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

public abstract class AutomaticPermanentMechanics extends PermanentMechanics implements GameEventListener {

	public abstract void executeOnEvent(GameEvent gameEvent);

}
