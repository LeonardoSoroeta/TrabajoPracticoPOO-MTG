package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/**
 * A LastingEffect is generated by an Ability and affects a target permanent by modifying it in some manner.
 * A LastingEffect must be applied on a target permanent and removed from the target permanent by the Ability
 * that generated it. For a LastingEffect that removes itself, see AutomaticLastingEffect.
 */
public abstract class LastingEffect {

    Permanent target;
    Mechanics sourceAbility;

    public LastingEffect(Mechanics sourceAbility) {
    	this.sourceAbility = sourceAbility;
    }
    
    public void setTarget(Permanent target) {
    	this.target = target;
    }
    
    public Permanent getTarget() {
    	return target;
    }
    
    public Mechanics getSourceAbility() {
    	return sourceAbility;
    }

    public abstract void applyEffect();
    
    public abstract void undoEffect();

}
