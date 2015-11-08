package ar.edu.itba.Magic.Backend;


import java.util.LinkedList;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Enums.Phase;

public class Match {
	
	private static Match self = null;
	
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	private final int END_OF_TURN_MAX_CARD = 7;
	
	private Player player1;
	private Player player2;
	private Player activePlayer;
	private CreaturesFight fight;
	private Phase currentPhase;
	private boolean matchStarted;
	private boolean playerWon;
	private boolean isFirstTurn = true;
	private boolean landPlayThisTurn;
	private boolean targetRequested;
	private Ability targetRequestingAbility;
	private boolean awaitingActions;
	private boolean continueExecution;
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
		if(this.playerWon == false) {
			if(this.targetRequested == true) {
				if(selectedTarget != null) {
					targetRequestingAbility.getTargetAndContinueExecution();
					targetRequested = false;
					selectedTarget = null;
				}
			}
			else {
				if(this.matchStarted == false) {
					this.matchStarted = true;
					this.start();
				} else {
					if(continueExecution == true) {
						this.executeNextPhase();
					}
				}
			}
		} else {
			// TODO hacer lo que haya que hacer cuando termina la partida
		}
	}
	
	public void start() {		
		activePlayer = this.randomPlayer();
		
		//TODO roll dice (see who chooses who goes first), shuffle decks, draw cards, players can mulligan

		this.currentPhase = Phase.BEGINNING_PHASE;
		this.beginningPhase();
	}
	
	public void playTurn() {	
		this.beginningPhase();
		this.mainPhase();
		this.combatPhase();
		this.mainPhase();
		this.endingPhase();
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
	}
	
	public void mainPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.MAIN_PHASE, activePlayer));
		this.awaitActions();
	}
	
	public void combatPhase() {
		
	}
	
	public void endingPhase() {
		
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
		return activePlayer;
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
	
	public void receiveTarget(Object target) {
		this.selectedTarget = target;
	}
	
	public void requestTarget(Ability requestingAbility, String messageToPlayer) {
		this.targetRequestingAbility = requestingAbility;
		this.targetRequested = true;
		this.messageToPlayer = messageToPlayer;
	}
	
	public boolean isAwaitingTargetSelection() {
		return this.targetRequested;
	}
	
	public void awaitActions(String messageToPlayer) {
		this.awaitingActions = true;
		this.continueExecution = false;
		this.messageToPlayer = messageToPlayer;
	}
	
	public void noMoreActions() {
		this.awaitingActions = false;
		this.continueExecution = true;
	}
	
	public boolean isAwaitingAction() {
		return this.awaitingActions;
	}
	
	public String getMessageToPlayer() {
		return this.messageToPlayer;
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
			this.changeActivePlayer();
			this.currentPhase = Phase.BEGINNING_PHASE;
			this.beginningPhase();
		}
	}
	
}
