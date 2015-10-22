package ar.edu.itba.Magic.Backend;

/**
 * Created by Martin on 18/10/2015.
 */
public class LastingEffect {

    Object target;
   	GameEvent finalizingEvent;

    public LastingEffect(Object target, GameEvent finalizingEvent){
        this.target = target;
        this.finalizingEvent = finalizingEvent;	
    }
    
    public void applyEffect() {
    	
    }
    
    public void removeEffect() {
    	
    }
    
    public GameEvent getFinalizingEvent() {
    	return finalizingEvent;
    }
    
    public Object getTarget() {
    	return target;
    }

}
