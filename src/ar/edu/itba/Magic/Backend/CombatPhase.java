package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.Event;

public class CombatPhase {
	
	private static CombatPhase self = null;
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
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
