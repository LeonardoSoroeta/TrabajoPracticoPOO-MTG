package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventListener;

public abstract class AutomaticSpellAbility extends SpellAbility implements EventListener {

	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
