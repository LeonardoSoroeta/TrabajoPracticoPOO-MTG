package ar.edu.itba.Magic.Backend;


import java.util.LinkedList;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Enums.Phase;
import ar.edu.itba.Magic.Backend.Exceptions.UninitializedPlayersException;

/**
 * This class is responsible for the match's game logic. It executes on every game update cycle, and always 
 * stops executing in a specific Match State, awaiting a player action.
 */
public class Match {
	
	private static Match self = new Match();
	
	GameStack gameStack = GameStack.getGameStackInstance();
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	CombatPhase combatPhase = CombatPhase.getCombatPhase();
	CardDiscardPhase cardDiscardPhase = CardDiscardPhase.getCardDiscardPhase();
	StartingPhase startingPhase = StartingPhase.getStartingPhase();
	
	private Player player1;
	private Player player2;
	private Player turnOwner;
	private Player activePlayer;
	private Phase currentPhase;
	private MatchState matchState = MatchState.GAME_OVER;
	private MatchState previousMatchState;
	private boolean isFirstTurn = true;
	private boolean landPlayedThisTurn;
	private Ability targetRequestingAbility;
	private Ability manaRequestingAbility;
	private boolean playerSelectedYes;
	private boolean playerSelectedNo;
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
	
	/** Must execute this method whenever the player does an action */
	public void update() {
		if(matchState.equals(MatchState.GAME_OVER)) {
			if(player1 == null || player2 == null) {
				throw new UninitializedPlayersException();
			} else {
				this.start();
			}

		} else if(matchState.equals(MatchState.AWAITING_MAIN_PHASE_ACTIONS)) {
			if(playerDoneClicking == true) {
				this.playerDoneClicking = false;
				this.executeNextPhase();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_CASTING_MANA_PAYMENT)) {
			if(manaPaymentCancelled == true) {
				matchState = previousMatchState;
				manaRequestingAbility.cancelCastingManaRequest();
			} else if(selectedTarget != null) {
				matchState = previousMatchState;
				manaRequestingAbility.resumeCastingManaRequest();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ABILITY_MANA_PAYMENT)) {
			if(manaPaymentCancelled == true) {
				matchState = previousMatchState;
				((PermanentAbility)manaRequestingAbility).cancelAbilityManaRequest();
			} else if(selectedTarget != null) {
				matchState = previousMatchState;
				((PermanentAbility)manaRequestingAbility).resumeAbilityManaRequest();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_CASTING_TARGET_SELECTION)) {
			if(targetSelectionCancelled == true) {
				matchState = previousMatchState;
				targetRequestingAbility.cancelCastingTargetSelection();
			} else if(selectedTarget != null) {
				matchState = previousMatchState;
				targetRequestingAbility.resumeCastingTargetSelection();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ABILITY_TARGET_SELECTION)) {
			if(targetSelectionCancelled == true) {
				matchState = previousMatchState;
				((PermanentAbility)manaRequestingAbility).cancelAbilityTargetSelection();
			} else if(selectedTarget != null) {
				matchState = previousMatchState;
				((PermanentAbility)manaRequestingAbility).resumeAbilityTargetSelection();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_STACK_ACTIONS)) {
			if(playerDoneClicking == true) {
				this.playerDoneClicking = false;
				gameStack.playerDoneClicking();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ATTACKER_SELECTION)) {
			if(playerDoneClicking == true) {
				this.playerDoneClicking = false;
				combatPhase.playerDoneClicking();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_BLOCKER_SELECTION)) {
			if(playerDoneClicking == true) {
				this.playerDoneClicking = false;
				combatPhase.playerDoneClicking();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_ATTACKER_TO_BLOCK_SELECTION)) {
			if(targetSelectionCancelled == true) {
				this.targetSelectionCancelled = false;
				combatPhase.cancelAttackerToBlockSelection();
			} else if(selectedTarget != null) {
				combatPhase.resumeExecution();
			}	
			
		} else if(matchState.equals(MatchState.AWAITING_CARD_TO_DISCARD_SELECTION)) {
			if(selectedTarget != null) {
				cardDiscardPhase.resumeExecution();
			}
			
		} else if(matchState.equals(MatchState.AWAITING_STARTING_PHASE_YES_OR_NO_CONFIRMATION)) {
			if(playerSelectedYes) {
				
			} else if(playerSelectedNo) {
				
			}
		
		}
	}
	
	/** Starts the match */
	private void start() {		
		this.activePlayer = this.randomPlayer();
		//this.turnOwner= this.activePlayer;
		//this.getPlayer1().getDeck().shuffleDeck();
		///this.getPlayer2().getDeck().shuffleDeck();
		//this.getPlayer1().drawCards(7);
		//this.getPlayer2().drawCards(7);
		
		//this.currentPhase = Phase.BEGINNING_PHASE;
		//this.beginningPhase();
		startingPhase.start();
	}
	
	/** Current player turn's beginning phase */
	public void beginningPhase() {
		gameEventHandler.triggerGameEvent(new GameEvent(Event.UNTAP_STEP, activePlayer));
		this.activePlayer.untapDuringUnkeep();
		this.activePlayer.removeAllSummoningSickness();
		this.landPlayedThisTurn = false;
		
		gameEventHandler.triggerGameEvent(new GameEvent(Event.UPKEEP_STEP, activePlayer));

		gameEventHandler.triggerGameEvent(new GameEvent(Event.DRAW_CARD_STEP, activePlayer));
		if(this.isFirstTurn)  {
			this.isFirstTurn = false;
		} else {
			this.activePlayer.drawCard();
		}
		this.executeNextPhase();
	}
	
	/** Executes on current player's pre and post combat main phase */
	public void mainPhase() {
		gameEventHandler.triggerGameEvent(new GameEvent(Event.MAIN_PHASE, activePlayer));
		this.awaitMainPhaseActions("Main Phase: Cast spells, activate abilities.");
	}
	
	/** Executes on current player's combat phase */
	public void combatPhase() {	
		combatPhase.start();
	}
	
	/** Removes damage counters from creatures and starts the card discard phase */
	public void endingPhase() {
		
		gameEventHandler.triggerGameEvent(new GameEvent(Event.ENDING_PHASE, activePlayer));
		
		gameEventHandler.triggerGameEvent(new GameEvent(Event.CLEANUP_STEP, activePlayer));
		
		this.removeAllDamageCounters();
		
		cardDiscardPhase.start();
	}
	
	/** Returns a random player */
	public Player randomPlayer() {
		int randomNum = 1 + (int)(Math.random()*2);
		if(randomNum == 1){
			return this.player1;
		} else {
			return this.player2;
		}
	}
	
	/** Sets player 1 */
	public void setPlayer1(Player player) {
		this.player1 = player;
	}
	
	/** Sets player 2 */
	public void setPlayer2(Player player) {
		this.player2 = player;
	}
	
	/** Gets player 1 */
	public Player getPlayer1() {
		return player1;
	}
	
	/** Gets player 2 */
	public Player getPlayer2() {
		return player2;
	}
	
	/** Given a specific player, returns the other one */
	public Player getOpposingPlayerFrom(Player player) {
		if(player == player1) {
			return player2;
		}
		else {
			return player1;
		}
	}
	
	/** Gets the player that is currently active */
	public Player getActivePlayer() {
		return this.activePlayer;
	}
	
	/** Sets the currently active player */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/** Sets the player to whom the turn belongs */
	public void setTurnOwner(Player player) {
		this.turnOwner = player;
	}
	
	/** Gets the player to whom the turn belongs */
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
		this.previousMatchState = this.matchState;
		this.matchState = MatchState.AWAITING_CASTING_TARGET_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitCastingManaPayment(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.manaPaymentCancelled = false;
		this.manaRequestingAbility = requestingAbility;
		this.previousMatchState = this.matchState;
		this.matchState = MatchState.AWAITING_CASTING_MANA_PAYMENT;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAbilityTargetSelection(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.targetSelectionCancelled = false;
		this.targetRequestingAbility = requestingAbility;
		this.previousMatchState = this.matchState;
		this.matchState = MatchState.AWAITING_ABILITY_TARGET_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAbilityManaPayment(Ability requestingAbility, String messageToPlayer) {
		this.selectedTarget = null;
		this.manaPaymentCancelled = false;
		this.manaRequestingAbility = requestingAbility;
		this.previousMatchState = this.matchState;
		this.matchState = MatchState.AWAITING_ABILITY_MANA_PAYMENT;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitAttackerSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.playerDoneClicking = false;
		this.matchState = MatchState.AWAITING_ATTACKER_SELECTION;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void awaitBlockerSelection(String messageToPlayer) {
		this.selectedTarget = null;
		this.playerDoneClicking = false;
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
	
	public void startingPhaseYesOrNoPrompt(String messageToPlayer) {
		this.playerSelectedNo = false;
		this.playerSelectedYes = false;
		this.matchState = MatchState.AWAITING_STARTING_PHASE_YES_OR_NO_CONFIRMATION;
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
		if(currentPhase.equals(Phase.BEGINNING_PHASE)) {
			this.currentPhase = Phase.PRE_COMBAT_MAIN_PHASE;
			this.mainPhase();
		} else if(currentPhase.equals(Phase.PRE_COMBAT_MAIN_PHASE)) {
			this.currentPhase = Phase.COMBAT_PHASE;
			this.combatPhase();
		} else if(currentPhase.equals(Phase.COMBAT_PHASE)) {
			this.currentPhase = Phase.POST_COMBAT_MAIN_PHASE;
			this.mainPhase();
		} else if(currentPhase.equals(Phase.POST_COMBAT_MAIN_PHASE)) {
			this.currentPhase = Phase.ENDING_PHASE;
			this.endingPhase();
		} else if(currentPhase.equals(Phase.ENDING_PHASE)) {
			this.turnOwner = this.getOpposingPlayerFrom(this.turnOwner);
			this.activePlayer = this.turnOwner;
			this.currentPhase = Phase.BEGINNING_PHASE;
			this.beginningPhase();
		}
	}
	
	public void endMatch() {
		this.resetData();
		gameStack.resetData();
		combatPhase.resetData();
		startingPhase.resetData();
		gameEventHandler.resetData();
	}
	
	public void resetData() {
		player1 = null;
		player2 = null;
		turnOwner = null;
		activePlayer = null;
		currentPhase = null;
		matchState = MatchState.GAME_OVER;
		previousMatchState = null;
		isFirstTurn = true;
		landPlayedThisTurn = false;
		targetRequestingAbility = null;
		manaRequestingAbility = null;
		playerDoneClicking = false;
		targetSelectionCancelled = false;
		manaPaymentCancelled = false;
		messageToPlayer = null;
		selectedTarget = null;
	}
	
	public void newMessageToPlayer(String message) {
		this.messageToPlayer = message;
	}
	
	public void setCurrentPhase(Phase phase) {
		this.currentPhase = phase;
	}
	
	public void setPreviousMatchState(MatchState matchState) {
		this.previousMatchState = matchState;
	}
	
	public MatchState getPreviousMatchState() {
		return this.previousMatchState;
	}
	
	public void setMatchState(MatchState matchState) {
		this.matchState = matchState;
	}
	
	public MatchState getMatchState() {
		return this.matchState;
	}
	
	public CombatPhase getCombatPhase() {
		return combatPhase;
	}

	/* ******************************************************************************************************** */
	/*								DE ACA PARA ABAJO METODOS QUE SOLO USA EL FRONT								*/
	/* ******************************************************************************************************** */
	
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
	
	public void playerSelectedYes() {
		this.playerSelectedYes = true;
	}
	
	public void playerSelectedNo() {
		this.playerSelectedNo = true;
	}
	
	public void returnSelectedTarget(Object selectedTarget) {
		this.selectedTarget = selectedTarget;
	}
	
	public int getPlayerPlaying(){
		if( this.activePlayer.equals(player1))
			return 1;
		else
			return 2;
	}
	
}
