package ar.edu.itba.Magic.Backend;
import java.util.*;

/**
 * Created by Martin on 18/10/2015.
 */
public class GameEventHandler {
    /**
     * This class is implemented based in Singletone Pattern because it will be needed only one instance
     * each game.
     */

    private static GameEventHandler instance = new GameEventHandler();
    
    /* lasting effects y abilities que implementan ExecutesOnEvent */
    private List<ExecutesOnEvent> observers = new LinkedList<ExecutesOnEvent>();

    private GameEventHandler(){
       
    }

    public static GameEventHandler getGameEventHandler() {
        return instance;
    }

    public void signalGameEvent(GameEvent gameEvent) {		
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