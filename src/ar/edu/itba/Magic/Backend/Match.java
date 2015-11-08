package ar.edu.itba.Magic.Backend;


import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.ActivatedPermanentAbility;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.InstantCard;
import ar.edu.itba.Magic.Backend.Cards.LandCard;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Interfaces.Activator;

public class Match {
	
	private static Match self = null;
	
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	private final int END_OF_TURN_MAX_CARD = 7;
	
	private Player player1;
	private Player player2;
	private Player activePlayer;
	private boolean targetRequired = false;
	private Card cardTaget;
	private boolean doneClicking;
	private Card selectedCard;
	private Permanent selectedPermanent;
	private boolean waitCard;
	private boolean waitPermanent;
	private boolean landPlayThisTurn;
	private CreaturesFight fight;
	
    private Match() {
    	
    }
	
	
	public static Match getMatch() {
		if(self == null) {
			self = new Match();
		}
		return self;
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
	
	public void changeActivePlayer(){
		this.activePlayer = this.getOpposingPlayerFrom(this.activePlayer);
	}
	
	public void start() {
		
		activePlayer = this.randomPlayer();
		boolean isFirstTurn = true;
		
		/*while(){
			this.playTurn(activePlayer, isFirstTurn);
			if(isFirstTurn){
				isFirstTurn = false;
			}
			this.changeActivePlayer();
		}*/
		
		//TODO
		//roll dice (see who chooses who goes first)
		//shuffle decks
		//draw cards
		//players can mulligan	
		//while(no winner)
			//playTurn()	(whoever goes first doesnt draw a card)
	}
	
	
	//public void priority() {}
	
	//public void switchPriority() {}
	
	public Player randomPlayer(){
		int randomNum = 1 + (int)(Math.random()*2);
		if(randomNum == 1){
			return this.player1;
		} else {
			return this.player2;
		}
	}
	
	
	public void playTurn(Player player, boolean isFirstTurn) {
		
		if(isFirstTurn) {
			beginningPhase();
		}
		
		mainPhase();
		combatPhase();
		mainPhase();
		endingPhase();
	}
	
	public void beginningPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.UNTAP_STEP, activePlayer));
		//for all cards in play that contain attribute can_untap -> untap
		this.activePlayer.untapDuringUnkeep();
		eventHandler.triggerGameEvent(new GameEvent(Event.UPKEEP_STEP, activePlayer));
		//players play instants and activated abilities...
		
		eventHandler.triggerGameEvent(new GameEvent(Event.DRAW_CARD_STEP, activePlayer));
		//draw card(s)...
		//players play instants and activated abilities...
		this.activePlayer.drawCard();
	}
	
	public void discardCard(Card card, Player player){
		player.removeCardFromHand(card);
		player.placeCardInGraveyard(card);
	}
	
	public void mainPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.MAIN_PHASE, activePlayer));
		//active player casts spells & activated abilities / other players casts instants & activated abilities
		//active player can play 1 land if not already casted this turn
		while(!this.isDoneClicking()) {
			if(this.isWaitingForCard()) {
				this.cardWasSelected();
				if(selectedCard.getClass().equals(LandCard.class)) {
					if(this.isLandPlayThisTurn()) {
						this.setLandPlayThisTurnTrue();
						this.selectedCard.playCard();
					}//TODO despues se ve si devuelve algo si no se pudo jugar
				}else {
					this.selectedCard.playCard();
				}
			}
			if(this.isWaitingForPermanent()){
				if(this.selectedPermanent.getAbility() instanceof Activator){
				((ActivatedPermanentAbility)this.selectedPermanent.getAbility()).executeOnActivation();
				}	
			}
		}
		this.setDoneClickingFalse();
		
		this.changeActivePlayer();
		while(!this.isDoneClicking()){
			if(this.isWaitingForCard()) {
				this.cardWasSelected();
				if(selectedCard.getClass().equals(InstantCard.class)){
					this.selectedCard.playCard();
				}
			}
			if(this.isWaitingForPermanent()){
				if(this.selectedPermanent.getAbility() instanceof Activator){
					((ActivatedPermanentAbility)this.selectedPermanent.getAbility()).executeOnActivation();
				}
			}
		}
		this.setDoneClickingFalse();
		this.changeActivePlayer();
	}
	
	public void combatPhase() {
		List<Creature> attackers = new LinkedList<Creature>();
		List<Creature> deffenders = new LinkedList<Creature>();
		
		eventHandler.triggerGameEvent(new GameEvent(Event.COMBAT_PHASE, activePlayer));
		//players can play instants and activated abilities
		while(!this.isDoneClicking()) {
			if(this.isWaitingForPermanent()){
				if(this.selectedPermanent instanceof Creature){
					this.fight.addAttacker((Creature)this.selectedPermanent, attackers);
				}
			}
		}
		this.setDoneClickingFalse();
		
		eventHandler.triggerGameEvent(new GameEvent(Event.DECLARE_ATTACKERS_STEP, activePlayer));
		//active player declares attackers (tap creatures)
			//solo criaturas que no estan tapeadas, se las agrega a la lista de attackers
			//si creature.containsAttribute("taps_on_attack") entonces se la tapea
		
		//then players can play instants and activated abilities again
		this.changeActivePlayer();
		while(!this.isDoneClicking()) {
			if(this.isWaitingForPermanent()){
				if(this.selectedPermanent instanceof Creature){
					this.fight.addDefender((Creature)this.selectedPermanent, deffenders);
				}
			}
		}
		this.setDoneClickingFalse();
		this.changeActivePlayer();
		eventHandler.triggerGameEvent(new GameEvent(Event.DECLARE_BLOCKERS_STEP, activePlayer));
		//opponent declares blockers
			//solo criaturas que no estan tapeadas (y no se las tapea). se las mapea a un atacante cada una
			//no se le permite al jugador mapear bloqueadores no voladores a atacantes voladores
			//no se le permite al jugador mapear un bloqueador de X color si la criatura atacante contiene protection from ese color
			//no se le permite al jugador mapear ningun bloqueador a una criatura con "swampwalk" si el defensor tiene un swamp
				//lo mismo para plainswalk islandwalk etc...
			
		//then players can play instants and activated abilities again
		
		eventHandler.triggerGameEvent(new GameEvent(Event.COMBAT_DAMAGE_STEP, activePlayer));
			// - unblocked attackers deal damage equal to their power to the defending player
			// - blocked attackers deal their damage to the creatures blocking them. if more than one creature blocks
			//   one of your attackers, you decide how to divide the attackers damage among the blockers
			// - blockers deal their damage to the creatures they're blocking. if a creature becomes tapped since
			//   it was declared a blocker, it still deals damage normally
			// - If an attacking creature was blocked at the declare blockers step, it doesn't
		 	//   deal any damage to the defending player. This is true even if all the blockers
		 	//   have left play.
			// - Once you decide how combat damage will be dealt, the damage goes on the
		 	//   stack. After that, the damage is locked in. It will be dealt even if some of
			//   the creatures leave play.
			// - Players may then play instants and activated abilities. Once these have all
		 	//   resolved, combat damage is actually dealt. If a creature tries to deal damage
			//   to a creature no longer in play, it can't and the damage isn't dealt.
			this.fight.realizeFight(attackers, deffenders, this.activePlayer, this.getOpposingPlayerFrom(this.activePlayer));
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_COMBAT_PHASE, activePlayer));
		//players can play instants and activated abilities again
		
	}
	
	public void endingPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.ENDING_PHASE, activePlayer));
		//players can play instants and activated abilities
		
		eventHandler.triggerGameEvent(new GameEvent(Event.CLEANUP_STEP, activePlayer));
		//if you have more than 7 cards in your hand -> discard cards
		
		while(this.activePlayer.getHand().size() <= this.END_OF_TURN_MAX_CARD) {
			if(this.isWaitingForCard()) {
				this.cardWasSelected();
				this.activePlayer.discardCard(this.selectedCard);
			}
		}
		
		//damage on creatures is removed
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, activePlayer));
		activePlayer.manaBurn();
	}
	
	//este ejemplo es bastante feo pero es para darse una idea
	public void sendTarget(Object obj) {
		if(obj instanceof Card) {
			this.cardTaget = (Card)obj;
		}
	}
	
	public void setTargetRequired() {
		this.targetRequired = true;
	}
	
	
	public boolean isTargetRequired() {
		return this.targetRequired;
	}

	public boolean isDoneClicking() {
		return this.doneClicking;
	}
	
	public void setDoneClickingTrue() {
		this.doneClicking = true;
	}
	
	public void setDoneClickingFalse() {
		this.doneClicking = false;
	}
	
	public void setSelectedCard(Card card) {
		this.selectedCard = card;
	}
	
	public void awaitCardSelection() {
		this.waitCard = true;
	}
	
	public void cardWasSelected() {
		this.waitCard = false;
	}
	
	public boolean isWaitingForCard() {
		return this.waitCard;
	}
	
	public boolean isLandPlayThisTurn() {
		return this.landPlayThisTurn;
	}
	
	public void setSelectedPermanent(Permanent permanent) {
		this.selectedPermanent = permanent;
	}
	
	public void awaitPermanentSelection() {
		this.waitCard = true;
	}
	
	public void PermanentWasSelected() {
		this.waitCard = false;
	}
	
	public boolean isWaitingForPermanent() {
		return this.waitCard;
	}
		
	public void setLandPlayThisTurnTrue() {
		this.landPlayThisTurn = true;
	}
	
	public void setLandPlayThisTurnFalse() {
		this.landPlayThisTurn = false;
	}
	
}

