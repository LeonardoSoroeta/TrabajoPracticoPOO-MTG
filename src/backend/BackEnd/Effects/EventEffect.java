package backend.BackEnd.Effects;

import backend.BackEnd.EventHandler.EventHandler;
import backend.BackEnd.Interfaces.Effect;

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
