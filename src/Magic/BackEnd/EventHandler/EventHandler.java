package Magic.BackEnd.EventHandler;

import Magic.BackEnd.Effects.EventEffect;
import Magic.BackEnd.Effects.LastingEffect;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 18/10/2015.
 */
public class EventHandler {
    /**
     * This class is implemented based in Singletone Pattern because it will be needed only one instance
     * each game.
     */

    private List<LastingEffect> events;
    private static EventHandler instance = new EventHandler();

    private EventHandler(){
        events = new LinkedList<LastingEffect>();
    }

    public static EventHandler getEventHandler(){
        return instance;
    }

    public void addEvent(EventEffect event){
       //TODO
    }
}