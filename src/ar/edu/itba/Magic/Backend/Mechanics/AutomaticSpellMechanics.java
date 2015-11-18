package ar.edu.itba.Magic.Backend.Mechanics;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

public abstract class AutomaticSpellMechanics extends SpellMechanics implements GameEventListener {
	
	public abstract void resolveInStack();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
