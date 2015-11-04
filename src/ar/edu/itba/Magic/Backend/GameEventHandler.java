package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Interfaces.EventListener;

import java.util.*;

/**
 * Contains a list of all automatic abilities and lasting effects currently in play. These objects implement ExecutesOnEvent.
 * They receive an instance of a game event descriptor (GameEvent) through their excuteOnEvent method every time a game event 
 * is notified.
 */
public class GameEventHandler {

    private static GameEventHandler instance = new GameEventHandler();
      
    private List<EventListener> listeners = new LinkedList<EventListener>();

    private GameEventHandler(){
    	
    }

    public static GameEventHandler getGameEventHandler() {
        return instance;
    }
    
    /** 
     * Passes a GameEvent instance through the list of observers.
     * 
     * @param gameEvent Event descriptor.
     */
    public void triggerGameEvent(GameEvent gameEvent) {		
		for(EventListener listener : listeners)
			listener.executeOnEvent(gameEvent);
	}
	
    /**
     * Adds an automatic ability or lasting effect to the observer list.
     */
	public void addListener(EventListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes an automatic ability or lasting effect from the observer list.
	 * 
	 * @param observer Any automatic ability or lasting effect.
	 */
	public void removeListener(EventListener listener) {
		listeners.remove(listener);
	}
	
}