package ar.edu.itba.Magic.Backend;


import java.util.LinkedList;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Stack.GameStack;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Enums.Phase;


public class Match {
	
	private static Match self = new Match();
	
	GameStack gameStack = GameStack.getGameStackInstance();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	CombatPhase combatPhase = CombatPhase.getCombatPhase();
	CardDiscardPhase cardDiscardPhase = CardDiscardPhase.getCardDiscardPhase();
	
	private Player player1;
	private Player player2;
	private Player turnOwner;
	private Player activePlayer;
	private Phase currentPhase;
	private MatchState matchState = MatchState.INITIAL_STATE;
	private MatchState previousMatchState;
	private boolean isFirstTurn = true;
	private boolean landPlayedThisTurn;
	private Ability targetRequestingAbility;
	private Ability manaRequestingAbility;
	private boolean playerDoneClicking;
	private boolean targetSelectionCancelled;
	private boolean manaPaymentCancelled;
	private String messageToPlayer;
	private Object selectedTarget;
	
    private Match() {
    	
    }
	
	public static Match getMatch() {
		return self;
	}

	public void update() {
		if(matchState.equals(MatchState.INITIAL_STATE)) {
			this.start();
			
		} else if(matchState.equals(MatchState.AWAITING_MAIN_PHASE_ACTIONS)) {
			if(playerDoneClicking == true) {
				this.executeNextPhase();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_CASTING_MANA_PAYMENT)) {
			if(manaPaymentCancelled == true) {
				manaRequestingAbility.cancelCastingManaRequest();
			} else if(selectedTarget != null) {
				manaRequestingAbility.resumeCastingManaRequest();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ABILITY_MANA_PAYMENT)) {
			if(manaPaymentCancelled == true) {
				((PermanentAbility)manaRequestingAbility).cancelAbilityManaRequest();
			} else if(selectedTarget != null) {
				((PermanentAbility)manaRequestingAbility).resumeAbilityManaRequest();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_CASTING_TARGET_SELECTION)) {
			if(targetSelectionCancelled == true) {
				targetRequestingAbility.cancelCastingTargetSelection();
			} else if(selectedTarget != null) {
				targetRequestingAbility.resumeCastingTargetSelection();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ABILITY_TARGET_SELECTION)) {
			if(targetSelectionCancelled == true) {
				((PermanentAbility)manaRequestingAbility).cancelAbilityTargetSelection();
			} else if(selectedTarget != null) {
				((PermanentAbility)manaRequestingAbility).resumeAbilityTargetSelection();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_STACK_ACTIONS)) {
			if(playerDoneClicking == true) {
				gameStack.continueExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ATTACKER_SELECTION)) {
			if(playerDoneClicking == true) {
				combatPhase.playerDoneClicking();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_BLOCKER_SELECTION)) {
			if(playerDoneClicking == true) {
				combatPhase.playerDoneClicking();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ATTACKER_TO_BLOCK_SELECTION)) {
			if(playerDoneClicking == true) {
				combatPhase.playerDoneClicking();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}	
			
		} else if(matchState.equals(MatchState.AWAITING_CARD_TO_DISCARD_SELECTION)) {
			if(selectedTarget != null) {
				cardDiscardPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.GAME_OVER)) {
			if(playerDoneClicking == true) {
				// TODO salir del match
			}
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
		this.awaitMainPhaseActions("Main Phase: Cast spells, activate abilities.");
	}
	
	public void combatPhase() {	
		combatPhase.start();
	}
	
	public void endingPhase() {
		
		eventHandler.triggerGameEvent(new GameEvent(Event.ENDING_PHASE, activePlayer));
		
		eventHandler.triggerGameEvent(new GameEvent(Event.CLEANUP_STEP, activePlayer));
		
		this.removeAllDamageCounters();
		
		cardDiscardPhase.start();
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
		return this.landPlayedThisTurn;
	}
		
	public void setLandPlayThisTurnTrue() {
		this.landPlayedThisTurn = true;
	}
	
	public void setLandPlayThisTurnFalse() {
		this.landPlayedThisTurn = false;
	}
	
	public void removeAllDamageCounters() {
		LinkedList<Creature> creatures = new LinkedList<Creature>();
		creatures.addAll(this.getPlayer1().getCreatures());
		creatures.addAll(this.getPlayer2().getCreatures());
		for(Creature creature : creatures) {
			creature.resetDamageMarkers();
		}
	}
	
	public void awaitMainPhaseActions(String messageToPlayer) {
		this.matchState = MatchState.AWAITING_MAIN_PHASE_ACTIONS;
		this.playerDoneClicking = false;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitStackActions(String messageToPlayer) {
		this.matchState = MatchState.AWAITING_STACK_ACTIONS;
		this.playerDoneClicking = false;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitCastingTargetSelection(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.targetSelectionCancelled = false;
		this.targetRequestingAbility = requestingAbility;
		this.matchState = MatchState.AWAITING_CASTING_TARGET_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitCastingManaPayment(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.manaPaymentCancelled = false;
		this.manaRequestingAbility = requestingAbility;
		this.matchState = MatchState.AWAITING_CASTING_MANA_PAYMENT;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAbilityTargetSelection(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.targetSelectionCancelled = false;
		this.targetRequestingAbility = requestingAbility;
		this.matchState = MatchState.AWAITING_ABILITY_TARGET_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAbilityManaPayment(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.manaPaymentCancelled = false;
		this.manaRequestingAbility = requestingAbility;
		this.matchState = MatchState.AWAITING_ABILITY_MANA_PAYMENT;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAttackerSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.matchState = MatchState.AWAITING_ATTACKER_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitBlockerSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.matchState = MatchState.AWAITING_BLOCKER_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAttackerToBlockSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.matchState = MatchState.AWAITING_ATTACKER_TO_BLOCK_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitCardToDiscardSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.matchState = MatchState.AWAITING_CARD_TO_DISCARD_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public Object getSelectedTarget() {
		Object selectedTarget = this.selectedTarget;
		
		if(selectedTarget != null) {
			selectedTarget = this.selectedTarget;
			this.selectedTarget = null;
			return selectedTarget;
		} else {
			return null;
		}
	}
	
	public void executeNextPhase() {
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
	
	public void cancelManaRequest() {
		this.manaPaymentCancelled = true;
	}
	
	public String getMessageToPlayer() {
		return this.messageToPlayer;
	}
	
	public void returnSelectedTarget(Object selectedTarget) {
		this.selectedTarget = selectedTarget;
	}
	
	public int getPlayerPlaying(){
		if( this.getActivePlayer().equals(player1))
			return 1;
		else
			return 2;
	}
	
}
