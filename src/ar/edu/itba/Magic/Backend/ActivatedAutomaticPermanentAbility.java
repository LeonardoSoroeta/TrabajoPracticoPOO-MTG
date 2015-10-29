package ar.edu.itba.Magic.Backend;

public abstract class ActivatedAutomaticPermanentAbility extends PermanentAbility implements Activation, ExecutesOnEvent {

	public abstract void executeOnEvent(GameEvent gameEvent);
	
	public abstract void executeOnActivation();

}
