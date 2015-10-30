package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Interfaces.ExecutesOnEvent;

import java.util.*;

/**
 * Contains a list of all automatic abilities and lasting effects currently in play. These objects implement ExecutesOnEvent.
 * They receive an instance of a game event descriptor (GameEvent) through their excuteOnEvent method every time a game event 
 * is notified.
 */
public class GameEventHandler {

    private static GameEventHandler instance = new GameEventHandler();
      
    private List<ExecutesOnEvent> observers = new LinkedList<ExecutesOnEvent>();

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
    public void notifyGameEvent(GameEvent gameEvent) {		
		for(ExecutesOnEvent observer : observers)
			observer.executeOnEvent(gameEvent);
	}
	
    /**
     * Adds an automatic ability or lasting effect to the observer list.
     */
	public void add(ExecutesOnEvent observer) {
		observers.add(observer);
	}
	
	/**
	 * Removes an automatic ability or lasting effect from the observer list.
	 * 
	 * @param observer Any automatic ability or lasting effect.
	 */
	public void remove(ExecutesOnEvent observer) {
		observers.remove(observer);
	}
	
}