package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;

import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

public class CombatPhase {
	
	private static CombatPhase self = null;
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	LinkedList<Creature> attackers = new LinkedList<Creature>();
	LinkedList<Creature> blockers = new LinkedList<Creature>();

	
	private CombatPhase() {
		
	}
	
	public static CombatPhase getCombatPhase() {
		if(self == null) {
			self = new CombatPhase();
		}
		return self;
	}
	
	public void start() {
		eventHandler.triggerGameEvent(new GameEvent(Event.COMBAT_PHASE, match.getTurnOwner()));	

		eventHandler.triggerGameEvent(new GameEvent(Event.DECLARE_ATTACKERS_STEP,  match.getTurnOwner()));
		
		
		eventHandler.triggerGameEvent(new GameEvent(Event.DECLARE_BLOCKERS_STEP,  match.getTurnOwner()));

		eventHandler.triggerGameEvent(new GameEvent(Event.COMBAT_DAMAGE_STEP,  match.getTurnOwner()));

		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_COMBAT_PHASE,  match.getTurnOwner()));

	}
	
	public void playerDoneClicking() {
		
	}
	
	public void resumeExecution() {
		
	}

}
