package ar.edu.itba.Magic.Backend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

public class CombatPhase {
	
	private static CombatPhase self = new CombatPhase();
	
	private LinkedList<Creature> attackers = new LinkedList<Creature>();
	private LinkedList<Creature> blockers = new LinkedList<Creature>();
	private HashMap<Creature, Creature> creaturePairs = new HashMap<Creature, Creature>();
	
	private CombatState combatState;
	private BlockingPhase blockingPhase;
	private Creature blocker;
	private Creature attackerToBlock;

	private CombatPhase() {
		
	}
	
	public static CombatPhase getCombatPhase() {
		return self;
	}
	
	public void start() {
		this.combatState = CombatState.INITIAL_STATE;
		this.resumeExecution();
	}
	
	public void resumeExecution() {
		if(combatState.equals(CombatState.INITIAL_STATE)) {
			this.combatState = CombatState.DECLARING_ATTACKERS;
			Match.getMatch().awaitAttackerSelection("Select an attacker:");
		} else if(combatState.equals(CombatState.DECLARING_ATTACKERS)) {
			this.declareAttackers();
		} else if(combatState.equals(CombatState.DECLARING_BLOCKERS)) {
			this.declareBlockers();
		} else if(combatState.equals(CombatState.DEALING_DAMAGE)) {
			this.dealDamage();
		}
	}
	
	public void playerDoneClicking() {
		if(combatState.equals(CombatState.DECLARING_ATTACKERS) && attackers.isEmpty()) {
			this.resetData();
			Match.getMatch().executeNextPhase();
		} else if(combatState.equals(CombatState.DECLARING_ATTACKERS)) {
			combatState = CombatState.DECLARING_BLOCKERS;
			Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
			Match.getMatch().awaitBlockerSelection("Select a blocker: ");
		} else if(combatState.equals(CombatState.DECLARING_BLOCKERS)) {
			combatState = CombatState.DEALING_DAMAGE;
		} 
	}
	
	private void declareAttackers() {
		Permanent permanent;
		Creature attacker;
		permanent = (Permanent)Match.getMatch().getSelectedTarget();
		
		if(!(permanent instanceof Creature)) {
			Match.getMatch().awaitAttackerSelection("Select a creature attacker:");
		} else {
			attacker = (Creature)permanent;
			if(attackers.contains(attacker)) {
				Match.getMatch().awaitAttackerSelection("You already selected this attacker. Select again: ");
			} else if(attacker.containsAttribute(Attribute.SUMMONING_SICKNESS)) {
				Match.getMatch().awaitAttackerSelection("Selected creature contains summoning sickness. Select again: ");
			} else if(!attacker.containsAttribute(Attribute.CAN_ATTACK)) {
				Match.getMatch().awaitAttackerSelection("Selected creature cannot attack. Select again: ");
			} else if(attacker.isTapped()) {
				Match.getMatch().awaitAttackerSelection("Selected creature is tapped and cannot attack. Select again: ");
			} else {
				if(attacker.containsAttribute(Attribute.TAPS_ON_ATTACK)) {
					attacker.tap();
				}
				attackers.add(attacker);
				Match.getMatch().awaitAttackerSelection("Select another attacker:");
			}
		}
	}
	
	private void declareBlockers() {
		
		if(blockingPhase.equals(BlockingPhase.SELECTING_BLOCKERS)) {
			blocker = (Creature)Match.getMatch().getSelectedTarget();
			if(blockers.contains(blocker)) {
				Match.getMatch().awaitBlockerSelection("You already selected this blocker. Select again: ");
			} else if(!blocker.containsAttribute(Attribute.CAN_BLOCK)) {
				Match.getMatch().awaitBlockerSelection("Selected creature cannot block. Select again: ");
			} else if(blocker.isTapped()) {
				Match.getMatch().awaitBlockerSelection("Selected creature is tapped and cannot block. Select again: ");
			} else {
				blockingPhase = BlockingPhase.SELECTING_ATTACKERS_TO_BLOCK;
				Match.getMatch().awaitAttackerToBlockSelection("Select an attacker to block with this creature: ");
			}
			
		} else if(blockingPhase.equals(BlockingPhase.SELECTING_ATTACKERS_TO_BLOCK)) {
			attackerToBlock = (Creature)Match.getMatch().getSelectedTarget();
			if(this.attackerLandwalks(attackerToBlock)) {
				Match.getMatch().awaitAttackerToBlockSelection("Selected attacker landwalks. Select another: ");
			} else if(creaturePairs.containsKey(attackerToBlock)) {
				Match.getMatch().awaitAttackerToBlockSelection("Selected attacker is already being blocked. Select another: ");
			} else if(attackerToBlock.containsAttribute(Attribute.FLYING)) {
				if(!blocker.containsAttribute(Attribute.FLYING)) {
					Match.getMatch().awaitAttackerToBlockSelection("Selected attacker flies. Select another: ");
				}
			} else {
				blockers.add(blocker);
				creaturePairs.put(attackerToBlock, blocker);
				blockingPhase = BlockingPhase.SELECTING_BLOCKERS;
				Match.getMatch().awaitBlockerSelection("Select a blocker: ");
			}
		}

	}
	
	public void cancelAttackerToBlockSelection() {
		blockingPhase = BlockingPhase.SELECTING_BLOCKERS;
		Match.getMatch().awaitBlockerSelection("Select blockers: ");
	}
	
	private void dealDamage() {
		for(Map.Entry<Creature, Creature> entry : creaturePairs.entrySet()) {
			if(entry.getKey().containsAttribute(Attribute.FIRST_STRIKE)) {
				if(entry.getValue().containsAttribute(Attribute.FIRST_STRIKE)) {
					entry.getKey().dealDamageTo(entry.getValue());
					entry.getValue().dealDamageTo(entry.getKey());
					trample(entry.getKey(), entry.getValue());
				} else {	//osea key first strike pero value no
					entry.getKey().dealDamageTo(entry.getValue());
					trample(entry.getKey(), entry.getValue());
					if(entry.getValue().isStillALegalTarget()) {
						entry.getValue().dealDamageTo(entry.getKey());
					}
				}
			} else {	//aca key no tiene arrollar
				if(entry.getValue().containsAttribute(Attribute.FIRST_STRIKE)) {
					entry.getValue().dealDamageTo(entry.getKey());
					if(entry.getKey().isStillALegalTarget()) {
						entry.getKey().dealDamageTo(entry.getValue());
						trample(entry.getKey(), entry.getValue());
					}
				} else {
					entry.getKey().dealDamageTo(entry.getValue());
					entry.getValue().dealDamageTo(entry.getKey());
					trample(entry.getKey(), entry.getValue());
				}		
			}
		}
		
		for(Creature attacker : attackers) {
			if(!creaturePairs.containsKey(attacker)) {
				attacker.dealDamageTo(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getTurnOwner()));
			}
		}
		
		this.resetData();
		Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
		Match.getMatch().executeNextPhase();
	}
	
	public List<Creature> getAttackers() {
		List<Creature> attackers = new LinkedList<Creature>();
		
		attackers.addAll(this.attackers);
		
		return attackers;
	}
	
	public List<Creature> getBlockers() {
		List<Creature> blockers = new LinkedList<Creature>();
		
		blockers.addAll(this.blockers);
		
		return blockers;
	}
	
	public Map<Creature, Creature> getAttackerBlockerPairs() {
		return creaturePairs;
	}
	
	public void trample(Creature attacker, Creature blocker) {
		if(attacker.containsAttribute(Attribute.TRAMPLE)) {
			if(attacker.getAttack() > blocker.getDefense())	{
				blocker.getController().takeDamage(attacker.getAttack() - blocker.getDefense());
			}
		}
	}
	
	private boolean attackerLandwalks(Creature attacker) {
		LinkedList<Land> lands = Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getTurnOwner()).getLands();
		
		if(attacker.containsAttribute(Attribute.SWAMPWALK)) {
			for(Land each : lands) {
				if(each.getCardType().equals(CardType.SWAMP)) {
					return true;
				}
			}
		}
		if(attacker.containsAttribute(Attribute.FORESTWALK)) {
			for(Land each : lands) {
				if(each.getCardType().equals(CardType.FOREST)) {
					return true;
				}
			}
		}
		if(attacker.containsAttribute(Attribute.PLAINSWALK)) {
			for(Land each : lands) {
				if(each.getCardType().equals(CardType.PLAINS)) {
					return true;
				}
			}
		}
		if(attacker.containsAttribute(Attribute.MOUNTAINWALK)) {
			for(Land each : lands) {
				if(each.getCardType().equals(CardType.MOUNTAINS)) {
					return true;
				}
			}
		}
		if(attacker.containsAttribute(Attribute.ISLANDWALK)) {
			for(Land each : lands) {
				if(each.getCardType().equals(CardType.ISLAND)) {
					return true;
				}
			}
		}
		
		return false;		
	}
	
	public void resetData() {
		attackers.clear();
		blockers.clear();
		creaturePairs.clear();
		combatState = CombatState.INITIAL_STATE;
		blockingPhase = BlockingPhase.SELECTING_BLOCKERS;
	}
	
	private enum CombatState {
		INITIAL_STATE,
		DECLARING_ATTACKERS,
		DECLARING_BLOCKERS,
		DEALING_DAMAGE
	}
	
	private enum BlockingPhase {
		SELECTING_BLOCKERS,
		SELECTING_ATTACKERS_TO_BLOCK
	}

}
