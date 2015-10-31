package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.ExecutesOnEvent;

public abstract class LastingEffect implements ExecutesOnEvent {

    Permanent target;
    Ability sourceAbility;

    public LastingEffect() {

    }
    
    public void setTarget(Permanent target) {
    	this.target = target;
    }
    
    public Permanent getTarget() {
    	return target;
    }
    
    public Ability getSourceAbility() {
    	return sourceAbility;
    }
    
    public void setSourceAbility(Ability sourceAbility) {
    	this.sourceAbility = sourceAbility;
    }

    public void executeOnEvent(GameEvent gameEvent) {
    	
    }
    
    public abstract void applyEffect();
    
    public abstract void undoEffect();

}
