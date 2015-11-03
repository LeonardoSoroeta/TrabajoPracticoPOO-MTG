
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Event;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Card.LandCard;

public class Match {
	
	private static Match instance = getMatch();
	
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	private Player player1;
	private Player player2;
	private Player activePlayer;
	
	private boolean landCastedThisTurn = false;
	
	private Match() {
		
	}

    public Match getMatchInstance(){
        return this;
    }
	
	public static Match getMatch() {
		return instance;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public Player getOpponent(Player player) {
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
	
	public void start() {	
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
	
	public void playTurn() {
		
		
					
		beginningPhase();
		mainPhase();
		combatPhase();
		mainPhase();
		endingPhase();
	}
	
	public void beginningPhase() {
		eventHandler.notifyGameEvent(new GameEvent(Event.UNTAP_STEP, activePlayer));
		//for all cards in play that contain attribute can_untap -> untap
		untapPermanents();
		eventHandler.notifyGameEvent(new GameEvent(Event.UPKEEP_STEP, activePlayer));
		//players play instants and activated abilities...
		
		eventHandler.notifyGameEvent(new GameEvent(Event.DRAW_CARD_STEP, activePlayer));
		//draw card(s)...
		//players play instants and activated abilities...
		drawCard(activePlayer);
	}
	
	public void untapPermanents(){
		for(Permanent each : activePlayer.getPermanentsInPlay()){
			if(each.isTapped() && each.containsAttribute(Attribute.UNTAPS_DURING_UPKEEP)){
				each.untap();
			}
		}
	}
	 
	
	
	public void drawCard(Player player){
		Card aux = player.getDeck().getCard();
		player.getHand().add(aux);
	}
	
	public void mainPhasePlayCard(Card card, Player player){
		if(card.getClass().equals(LandCard.class)){
			if(!landCastedThisTurn){
				this.playCard(card, player);
				landCastedThisTurn = true;
			}
		}else{
			this.playCard(card, player);
		}
	}
	
	public void playCard(Card card, Player player){
		player.getHand().remove(card);
		card.playCard();
	}
	

	public void mainPhase() {
		eventHandler.notifyGameEvent(new GameEvent(Event.MAIN_PHASE, activePlayer));
		//active player casts spells & activated abilities / other players casts instants & activated abilities
		//active player can play 1 land if not already casted this turn

	}
	
	public void combatPhase() {
		List<Creature> attackers = new LinkedList<Creature>(); //linked list o lo q sea
		// Map<Creature, Creature> = new HashMap<Creature, Creature>(); // <blockers, attackers>
		
		eventHandler.notifyGameEvent(new GameEvent(Event.COMBAT_PHASE, activePlayer));
		//players can play instants and activated abilities
		
		eventHandler.notifyGameEvent(new GameEvent(Event.DECLARE_ATTACKERS_STEP, activePlayer));
		//active player declares attackers (tap creatures)
			//solo criaturas que no estan tapeadas, se las agrega a la lista de attackers
			//si creature.containsAttribute("taps_on_attack") entonces se la tapea
		
		//then players can play instants and activated abilities again
		
		eventHandler.notifyGameEvent(new GameEvent(Event.DECLARE_BLOCKERS_STEP, activePlayer));
		//opponent declares blockers
			//solo criaturas que no estan tapeadas (y no se las tapea). se las mapea a un atacante cada una
			//no se le permite al jugador mapear bloqueadores no voladores a atacantes voladores
			//no se le permite al jugador mapear un bloqueador de X color si la criatura atacante contiene protection from ese color
			//no se le permite al jugador mapear ningun bloqueador a una criatura con "swampwalk" si el defensor tiene un swamp
				//lo mismo para plainswalk islandwalk etc...
			
		//then players can play instants and activated abilities again
		
		eventHandler.notifyGameEvent(new GameEvent(Event.COMBAT_DAMAGE_STEP, activePlayer));
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
		
		eventHandler.notifyGameEvent(new GameEvent(Event.END_OF_COMBAT_PHASE, activePlayer));
		//players can play instants and activated abilities again
		
	}
	
	public void endingPhase() {
		eventHandler.notifyGameEvent(new GameEvent(Event.ENDING_PHASE, activePlayer));
		//players can play instants and activated abilities
		
		eventHandler.notifyGameEvent(new GameEvent(Event.CLEANUP_STEP, activePlayer));
		//if you have more than 7 cards in your hand -> discard cards
		
		//damage on creatures is removed
		eventHandler.notifyGameEvent(new GameEvent(Event.END_OF_TURN, activePlayer));		
	}
	

			
		/*
		public void playCard(Card card,Player player){
			if(player.getHand().contains(card)){
				player.getHand().remove(card);
			
				// ver como esta implementada Card
			if (card.isPermanent){
				player.objectsInPlay.add(card.playCard);
			}
			else	
				player.graveyard.add(card);
			}	
		}
		
		public void KillObjectInPlay(Permanent obj, Player player){
			if(player.objectsInPlay.contains(obj)){
				player.objectsInPlay.remove(obj);
				player.graveyard.add(obj.dead());
			}
		}
		
		public void reviveCard(Card card, Player player){
			if(player.graveyard.contains(card)){
				player.graveyard.remove(card);
				//ver como esta implementada Card
				player.objectsInPlay.add(card.playCard);
			}
		}
		*/

}
