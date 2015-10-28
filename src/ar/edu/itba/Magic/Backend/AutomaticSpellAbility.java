package ar.edu.itba.Magic.Backend;

public abstract class AutomaticSpellAbility extends SpellAbility implements ExecutesOnEvent {

	public abstract void sendToStack();
	
	public abstract void resolveInStack();
	
	public abstract void executeOnEvent(GameEvent gameEvent);

}
