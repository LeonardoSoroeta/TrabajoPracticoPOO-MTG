package ar.edu.itba.Magic.Backend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

public class CombatPhase {
	
	private static CombatPhase self = new CombatPhase();
	
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	private LinkedList<Creature> attackers = new LinkedList<Creature>();
	private LinkedList<Creature> blockers = new LinkedList<Creature>();
	private HashMap<Creature, Creature> creaturePairs = new HashMap<Creature, Creature>();
	
	private CombatState combatState;
	private Object selectedTarget;

	private CombatPhase() {
		
	}
	
	public static CombatPhase getCombatPhase() {
		return self;
	}
	
	public void start() {
		
		eventHandler.triggerGameEvent(new GameEvent(Event.COMBAT_PHASE, match.getTurnOwner()));	
		
		this.combatState = CombatState.DECLARING_ATTACKERS;
		this.resumeExecution();
	}
	
	public void resumeExecution() {
		if(combatState.equals(CombatState.DECLARING_ATTACKERS)) {
			this.declareAttackers();
		} else if(combatState.equals(CombatState.DECLARING_BLOCKERS)) {
			this.declareBlockers();
		} else if(combatState.equals(CombatState.DEALING_DAMAGE)) {
			this.dealDamage();
		}
	}
	
	public void playerDoneClicking() {
		if(combatState.equals(CombatState.DECLARING_ATTACKERS)) {
			combatState = CombatState.DECLARING_BLOCKERS;
		} else if(combatState.equals(CombatState.DECLARING_BLOCKERS)) {
			combatState = CombatState.DEALING_DAMAGE;
		} 
	}
	
	private void declareAttackers() {
		if(selectedTarget == null) {
			match.requestAttackerSelection("Select attacker:");
		} else {
			Creature target = (Creature)match.getSelectedTarget();
			
			if(attackers.contains(target)) {
				match.requestAttackerSelection("You already selected this attacker. Select again: ");
			} else if(target.containsAttribute(Attribute.SUMMONING_SICKNESS)) {
				match.requestAttackerSelection("Selected creature contains summoning sickness. Select again: ");
			} else if(!target.containsAttribute(Attribute.CAN_ATTACK)) {
					match.requestAttackerSelection("Selected creature cannot attack. Select again: ");
			} else if(target.isTapped()) {
				match.requestAttackerSelection("Selected creature is tapped and cannot attack. Select again: ");
			} else {
				if(target.containsAttribute(Attribute.TAPS_ON_ATTACK)) {
					target.tap();
				}
				attackers.add(target);
				match.requestAttackerSelection("Select another attacker:");
			}
		}
	}
	
	private void declareBlockers() {
		Creature blocker;
		Creature attackerToBlock;
	
		
		//creaturePairs.put(attackerToBlock, blocker);
	}
	
	private void dealDamage() {
		for(Map.Entry<Creature, Creature> entry : creaturePairs.entrySet()) {
			entry.getKey().dealDamageTo(entry.getValue());
			entry.getValue().dealDamageTo(entry.getKey());
		}
		for(Creature attacker : attackers) {
			if(!creaturePairs.containsKey(attacker)) {
				attacker.dealDamageTo(match.getOpposingPlayerFrom(match.getTurnOwner()));
			}
		}
		
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_COMBAT_PHASE,  match.getTurnOwner()));
		match.executeNextPhase();
	}
	
	private enum CombatState {
		DECLARING_ATTACKERS,
		DECLARING_BLOCKERS,
		DEALING_DAMAGE
	}

}
