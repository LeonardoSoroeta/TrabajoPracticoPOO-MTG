package backend.BackEnd.EventHandler;

import java.util.LinkedList;
import java.util.List;

import backend.BackEnd.Effects.EventEffect;
import backend.BackEnd.Effects.LastingEffect;

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