package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

import java.util.*;

/**
 * Contains a list of all automatic abilities and lasting effects currently in play. These objects implement ExecutesOnEvent.
 * They receive an instance of a game event descriptor (GameEvent) through their excuteOnEvent method every time a game event 
 * is notified.
 */
public class GameEventHandler {

    private static GameEventHandler instance = new GameEventHandler();
      
    private List<GameEventListener> listeners = new LinkedList<GameEventListener>();

    private GameEventHandler(){
    	
    }

    public static GameEventHandler getGameEventHandler() {
        return instance;
    }
    
    /** 
     * Passes a GameEvent instance through the list of observers. Iterates over a copy of the listeners list
     * to avoid ConcurrentModificationException.
     * 
     * @param gameEvent Event descriptor.
     */
    public void triggerGameEvent(GameEvent gameEvent) {	
    	LinkedList<GameEventListener> newList = new LinkedList<GameEventListener>();
    	newList.addAll(listeners);
		for(GameEventListener listener : newList)
			listener.executeOnEvent(gameEvent);
	}
    
    public void refreshListeners() {
    	this.triggerGameEvent(new GameEvent(Event.GENERIC_EVENT));
    }
	
    /**
     * Adds an automatic ability or lasting effect to the observer list.
     */
	public void addListener(GameEventListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes an automatic ability or lasting effect from the observer list.
	 * 
	 * @param observer Any automatic ability or lasting effect.
	 */
	public void removeListener(GameEventListener listener) {
		listeners.remove(listener);
	}
	
	public void resetData() {
		listeners.clear();
	}
	
}