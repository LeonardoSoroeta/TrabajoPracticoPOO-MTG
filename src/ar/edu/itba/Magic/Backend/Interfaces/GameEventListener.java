package ar.edu.itba.Magic.Backend.Interfaces;

import ar.edu.itba.Magic.Backend.GameEvent;

public interface GameEventListener {

	public void executeOnEvent(GameEvent gameEvent);
	
}
