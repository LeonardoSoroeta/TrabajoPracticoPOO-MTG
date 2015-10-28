package ar.edu.itba.Magic.Backend;

public abstract class LastingEffect implements ExecutesOnEvent {

    Object target;
    Ability sourceAbility;

    public LastingEffect(Object target) {
    	this.target = target;
    }
    
    public Object getTarget() {
    	return target;
    }
    
    public Ability getSource() {
    	return sourceAbility;
    }
    
    public void setSource(Ability sourceAbility) {
    	this.sourceAbility = sourceAbility;
    }
    
    public abstract void executeOnEvent(GameEvent gameEvent);
    
    public abstract void applyEffect();
    
    public abstract void undoEffect();

}
