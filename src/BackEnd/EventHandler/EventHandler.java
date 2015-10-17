package BackEnd.EventHandler;

import BackEnd.Effects.LastingEffect;
import BackEnd.Event;
import BackEnd.Interfaces.Ability;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 17/10/2015.
 */

public class EventHandler {
    /**
     * This class is implemented based in Singletone Pattern because we will need only one instance
     * each game.
     */

    private List<LastingEffect> events;
    private static EventHandler instance = new EventHandler();

    private EventHandler(){
        events = new ArrayList<LastingEffect>();
    }

    public static EventHandler getEventHandler(){
        return instance;
    }

    public void addEvent(LastingEffect event){
        if(event != null)
        events.add(event);
    }







}
