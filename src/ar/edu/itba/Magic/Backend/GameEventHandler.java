package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Interfaces.GameEventListener;

import java.util.*;

/**
 * Contains a list of all automatic mechanics and lasting effects currently in play. These objects implement GameEventListener.
 * They receive an instance of a game event descriptor (GameEvent) through their excuteOnEvent method every time a game event 
 * is triggered.
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
     * Passes a GameEvent instance through the list of observers.
     */
    public void triggerGameEvent(GameEvent gameEvent) {	
    	LinkedList<GameEventListener> newList = new LinkedList<GameEventListener>();
    	newList.addAll(listeners);
		for(GameEventListener listener : newList)
			listener.executeOnEvent(gameEvent);
	}
    
    /**
     * Triggers an empty generic event.
     */
    public void refreshListeners() {
    	this.triggerGameEvent(new GameEvent(Event.GENERIC_EVENT));
    }
	
    /**
     * Adds an automatic mechanics or lasting effect to the observer list.
     */
	public void addListener(GameEventListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes an automatic mechanics or lasting effect from the observer list.
	 */
	public void removeListener(GameEventListener listener) {
		listeners.remove(listener);
	}
	
	public void resetData() {
		listeners.clear();
	}
	
}