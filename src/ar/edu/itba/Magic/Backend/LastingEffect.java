package ar.edu.itba.Magic.Backend;

/**
 * Created by Martin on 18/10/2015.
 */
public class LastingEffect {

    Targetable target;			/*al que afecta, creo que "Targetable" se podria reemplazar por Permanent*/
   	Event finalizingEvent;		/*para que lo use el event handler*/

    public LastingEffect(Targetable target, Event finalizingEvent){
        this.target = target;
        this.finalizingEvent = finalizingEvent;	
    }
    
    public void apply() {
    	
    }
    
    public void remove() {
    	
    }
    
    public Event getFinalizingEvent() {
    	return finalizingEvent;
    }
    
    public Targetable getTarget() {
    	return target;
    }

}
