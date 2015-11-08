package ar.edu.itba.Magic.Backend;


import java.util.LinkedList;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Stack.GameStack;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Enums.Phase;

public class Match {
	
	private static Match self = null;
	
	GameStack gameStack = GameStack.getGameStackInstance();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	CombatPhase combatPhase = CombatPhase.getCombatPhase();
	CardDiscardPhase cardDiscardPhase = CardDiscardPhase.getCardDiscardPhase();
	
	private Player player1;
	private Player player2;
	private Player turnOwner;
	private Player activePlayer;
	private Phase currentPhase;
	private MatchState matchState;
	private MatchState previousMatchState;
	private boolean matchStarted;
	private boolean noWinner = true;
	private boolean isFirstTurn = true;
	private boolean landPlayThisTurn;
	private Ability targetRequestingAbility;
	private Ability manaRequestingAbility;
	private boolean playerDoneClicking;
	private boolean targetSelectionCancelled;
	private String messageToPlayer;
	private Object selectedTarget;
	
    private Match() {
    	
    }
	
	public static Match getMatch() {
		if(self == null) {
			self = new Match();
		}
		return self;
	}
	
	public void update() {
		if(matchStarted == false) {
			this.matchStarted = true;
			this.start();
		} else if(noWinner == true) {
			if(matchState.equals(MatchState.REQUESTING_TARGET_SELECTION_BY_ABILITY)) {
				if(targetSelectionCancelled == true) {
					targetRequestingAbility.cancelTargetSelection();
				} else if(selectedTarget != null) {
					targetRequestingAbility.resumeExecution();
				}
			} else if(matchState.equals(MatchState.REQUESTING_MANA_PAYMENT_BY_ABILITY)) {
				if(selectedTarget != null) {
					manaRequestingAbility.resumeExecution();
				}
			} else if(matchState.equals(MatchState.REQUESTING_MAIN_PHASE_ACTIONS)) {
				if(playerDoneClicking == true) {
					this.executeNextPhase();
				}
			} else if(matchState.equals(MatchState.REQUESTING_STACK_ACTIONS)) {
				if(playerDoneClicking == true) {
					gameStack.continueExecution();
				}
			} else if(matchState.equals(MatchState.REQUESTING_ATTACKER_SELECTION)) {
				if(playerDoneClicking == true) {
					combatPhase.playerDoneClicking();
				} else if(selectedTarget != null) {
					combatPhase.resumeExecution();
				}
			} else if(matchState.equals(MatchState.REQUESTING_BLOCKER_SELECTION)) {
				if(playerDoneClicking == true) {
					combatPhase.playerDoneClicking();
				} else if(selectedTarget != null) {
					combatPhase.resumeExecution();
				}
			} else if(matchState.equals(MatchState.REQUESTING_ATTACKER_TO_BLOCK_SELECTION)) {
				if(playerDoneClicking == true) {
					combatPhase.playerDoneClicking();
				} else if(selectedTarget != null) {
					combatPhase.resumeExecution();
				}	
			} else if(matchState.equals(MatchState.REQUESTING_CARD_TO_DISCARD_SELECTION)) {
				if(selectedTarget != null) {
					cardDiscardPhase.resumeExecution();
				}
			}
		} else {
			// TODO hacer lo que haya que hacer cuando PLAYER LOST
		}
	}
	
	public void start() {		
		this.turnOwner = this.randomPlayer();
		this.activePlayer = this.turnOwner;
		
		//TODO roll dice (see who chooses who goes first), shuffle decks, draw cards, players can mulligan

		this.currentPhase = Phase.BEGINNING_PHASE;
		this.beginningPhase();
	}
	
	public void beginningPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.UNTAP_STEP, activePlayer));
		this.activePlayer.untapDuringUnkeep();
		
		eventHandler.triggerGameEvent(new GameEvent(Event.UPKEEP_STEP, activePlayer));

		eventHandler.triggerGameEvent(new GameEvent(Event.DRAW_CARD_STEP, activePlayer));
		if(this.isFirstTurn)  {
			this.isFirstTurn = false;
		} else {
			this.activePlayer.drawCard();
		}
		this.executeNextPhase();
	}
	
	public void mainPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.MAIN_PHASE, activePlayer));
		this.requestMainPhaseActions("Main Phase: Cast spells, activate abilities.");
	}
	
	public void combatPhase() {	
		combatPhase.start();
	}
	
	public void endingPhase() {
		
		eventHandler.triggerGameEvent(new GameEvent(Event.ENDING_PHASE, activePlayer));
		
		eventHandler.triggerGameEvent(new GameEvent(Event.CLEANUP_STEP, activePlayer));
		
		this.removeAllDamageCounters();
		
		cardDiscardPhase.start();
		
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, activePlayer));
	}
	
	public Player randomPlayer() {
		int randomNum = 1 + (int)(Math.random()*2);
		if(randomNum == 1){
			return this.player1;
		} else {
			return this.player2;
		}
	}
	
	public void setPlayer1(Player player) {
		this.player1 = player;
	}
	
	public void setPlayer2(Player player) {
		this.player2 = player;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public Player getOpposingPlayerFrom(Player player) {
		if(player == player1) {
			return player2;
		}
		else {
			return player1;
		}
	}
	
	public Player getActivePlayer() {
		return this.activePlayer;
	}
	
	public Player getTurnOwner() {
		return this.turnOwner;
	}
	
	public void changeActivePlayer() {
		this.activePlayer = this.getOpposingPlayerFrom(this.activePlayer);
	}
	
	public boolean isLandPlayThisTurn() {
		return this.landPlayThisTurn;
	}
		
	public void setLandPlayThisTurnTrue() {
		this.landPlayThisTurn = true;
	}
	
	public void setLandPlayThisTurnFalse() {
		this.landPlayThisTurn = false;
	}
	
	public void removeAllDamageCounters() {
		LinkedList<Creature> creatures = new LinkedList<Creature>();
		creatures.addAll(this.getPlayer1().getCreatures());
		creatures.addAll(this.getPlayer2().getCreatures());
		for(Creature creature : creatures) {
			creature.resetDamageMarkers();
		}
	}
	
	public void requestMainPhaseActions(String messageToPlayer) {
		this.matchState = MatchState.REQUESTING_MAIN_PHASE_ACTIONS;
		this.playerDoneClicking = false;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestStackActions(String messageToPlayer) {
		this.matchState = MatchState.REQUESTING_STACK_ACTIONS;
		this.playerDoneClicking = false;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestTargetSelectionFromAbility(Ability requestingAbility, String messageToPlayer) {
		this.targetSelectionCancelled = false;
		this.targetRequestingAbility = requestingAbility;
		this.matchState = MatchState.REQUESTING_TARGET_SELECTION_BY_ABILITY;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestManaPaymentFromAbility(Ability requestingAbility, String messageToPlayer) {
		this.targetSelectionCancelled = false;
		this.manaRequestingAbility = requestingAbility;
		this.matchState = MatchState.REQUESTING_MANA_PAYMENT_BY_ABILITY;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestAttackerSelection(String messageToPlayer) {
		this.matchState = MatchState.REQUESTING_ATTACKER_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestBlockerSelection(String messageToPlayer) {
		this.matchState = MatchState.REQUESTING_BLOCKER_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void requestAttackerToBlockSelection(String messageToPlayer) {
		this.matchState = MatchState.REQUESTING_ATTACKER_TO_BLOCK_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public Object getSelectedTarget() {
		Object selectedTarget = this.selectedTarget;
		this.selectedTarget = null;
		return selectedTarget;
	}
	
	private void executeNextPhase() {
		if(currentPhase == Phase.BEGINNING_PHASE) {
			this.currentPhase = Phase.PRE_COMBAT_MAIN_PHASE;
			this.mainPhase();
		}
		if(currentPhase == Phase.PRE_COMBAT_MAIN_PHASE) {
			this.currentPhase = Phase.COMBAT_PHASE;
			this.combatPhase();
		}
		if(currentPhase == Phase.COMBAT_PHASE) {
			this.currentPhase = Phase.POST_COMBAT_MAIN_PHASE;
			this.mainPhase();
		}
		if(currentPhase == Phase.POST_COMBAT_MAIN_PHASE) {
			this.currentPhase = Phase.ENDING_PHASE;
			this.endingPhase();
		}
		if(currentPhase == Phase.ENDING_PHASE) {
			this.turnOwner = this.getOpposingPlayerFrom(this.turnOwner);
			this.activePlayer = this.turnOwner;
			this.currentPhase = Phase.BEGINNING_PHASE;
			this.beginningPhase();
		}
	}

	/* ******************************************************************************************************* */
	/*						       DE ACA PARA ABAJO METODOS QUE SOLO USA EL FRONT							   */
	/* ******************************************************************************************************* */
	
	public MatchState getMatchState() {
		return this.matchState;
	}
	
	public void playerDoneClicking() {
		this.playerDoneClicking = true;
	}
	
	public void cancelTargetSelection() {
		this.targetSelectionCancelled = true;
	}
	
	public String getMessageToPlayer() {
		return this.messageToPlayer;
	}
	
	public void returnSelectedTarget(Object selectedTarget) {
		this.selectedTarget = selectedTarget;
	}
	
	
}
