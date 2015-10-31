package ar.edu.itba.Magic.Backend;

public abstract class LastingEffect {

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
    
    public abstract void applyEffect();
    
    public abstract void undoEffect();

}
