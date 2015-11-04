package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.EventListener;

/**
 * AutomaticLastingEffect is a LastingEffect that takes on the responsiblity of removing itself 
 * on a specific GameEvent.
 */
public abstract class AutomaticLastingEffect extends LastingEffect implements EventListener {
	
	public AutomaticLastingEffect(Ability sourceAbility) {
		super(sourceAbility);
	}
	
    public abstract void executeOnEvent(GameEvent gameEvent);
    
    public abstract void applyEffect();
    
    public abstract void undoEffect();

}
