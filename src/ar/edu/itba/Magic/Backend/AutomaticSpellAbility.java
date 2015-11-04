package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

public abstract class AutomaticSpellAbility extends SpellAbility implements GameEventListener {

	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
