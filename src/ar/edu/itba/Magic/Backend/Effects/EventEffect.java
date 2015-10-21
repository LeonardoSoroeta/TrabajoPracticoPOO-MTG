package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.Effect;
import ar.edu.itba.Magic.Backend.EventHandler;


/**
 * Created by Martin on 18/10/2015.
 */
public class EventEffect  {

    Effect effect;

    public EventEffect(Effect e){
        effect = e;
    }

    /**
     * Resolve method only executes the effect
     */
    public void resolve(){
        effect.effect();
    }

    private void sendEvent(){
        EventHandler eventHandler = EventHandler.getEventHandler();
        eventHandler.addEvent(this);
    }

}
