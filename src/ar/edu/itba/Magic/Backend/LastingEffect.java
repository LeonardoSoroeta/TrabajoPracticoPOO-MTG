package ar.edu.itba.Magic.Backend;

/**
 * Created by Martin on 18/10/2015.
 */
public abstract class LastingEffect {

    Object target;
   	String finalizerEventDescriptor;

    public LastingEffect(Object target, String finalizerEventDescriptor) {
        this.target = target;
        this.finalizerEventDescriptor = finalizerEventDescriptor;	
    }
    
    public abstract void applyEffect();
    
    public abstract void undoEffect();
    
    public String getEventDescriptor() {
    	return finalizerEventDescriptor;
    }
    
    public Object getTarget() {
    	return target;
    }

}
