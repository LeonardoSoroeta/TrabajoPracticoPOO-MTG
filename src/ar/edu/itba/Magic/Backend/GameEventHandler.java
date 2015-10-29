package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Interfaces.ExecutesOnEvent;

import java.util.*;

/**
 * Created by Martin on 18/10/2015.
 */
public class GameEventHandler {

    private static GameEventHandler instance = new GameEventHandler();
    
    /* lasting effects y abilities que implementan ExecutesOnEvent */
    private List<ExecutesOnEvent> observers = new LinkedList<ExecutesOnEvent>();

    private GameEventHandler(){
    	
    }

    public static GameEventHandler getGameEventHandler() {
        return instance;
    }

    public void notifyGameEvent(GameEvent gameEvent) {		
		for(ExecutesOnEvent observer : observers)
			observer.executeOnEvent(gameEvent);
	}
	
	public void add(ExecutesOnEvent observer) {
		observers.add(observer);
	}
	
	public void remove(ExecutesOnEvent observer) {
		observers.remove(observer);
	}
	
}