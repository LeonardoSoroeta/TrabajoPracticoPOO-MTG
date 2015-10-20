package backend;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Match {
	
	Player player1;
	Player player2;
	Integer currentTurn;
	
	public Match(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		this.currentTurn = 0;
	}
	
	public void start() {
		
		//roll dice (see who chooses who goes first)
		//shuffle decks
		//draw cards
		//players can mulligan
		
		//while(no winner)
			//playTurn()	(whoever goes first doesnt draw a card)
	}
	
	public Integer getCurrentTurn() {
		return this.currentTurn;
	}
	
	//public void priority() {}
	
	//public void switchPriority() {}

	public void playTurn() {
		//boolean landCastedThisTurn = false;
		
		beginningPhase();
		mainPhase();
		combatPhase();
		mainPhase();
		endingPhase();
	
	}
	
	public void beginningPhase() {
		//signalEvent(untap_step)
		//for all cards in play that contain attribute can_untap -> untap
		
		//signalEvent(upkeep_step)
		//players play instants and activated abilities...
		
		//signalEvent(drawcard_step)
		//draw card(s)...
		//players play instants and activated abilities...
		
	}
	
	public void mainPhase() {
		//signalEvent(main_phase)
		//active player casts spells & activated abilities / other players casts instants & activated abilities
		//active player can play 1 land if not already casted this turn
		
	}
	
	public void combatPhase() {
		//signalEvent(combat_phase)
		//players can play instants and activated abilities
		
		//signalEvent(declare_attackers_step)
		//active player declares attackers (tap creatures)
		//then players can play instants and activated abilities again
		
		//signalEvent(declare_blockers_step)
		//opponent declares blockers
		//then players can play instants and activated abilities again
		
		//signalEvent(combat_damage_step)
			// - unblocked attackers deal damage equal to their power to the defending player
			// - blocked attackers deal their damage to the creatures blocking them. if more than one creature blocks
			//   one of your attackers, you decide how to divide the attackers damage among the blockers
			// - blockers deal their damage to the creatures they're blocking. if a creature becomes tapped since
			//   it was declared a blocker, it still deals damage normally
			// - If an attacking creature was blocked at the declare blockers step, it doesnâ€™t
		 	//   deal any damage to the defending player. This is true even if all the blockers
		 	//   have left play.
			// - Once you decide how combat damage will be dealt, the damage goes on the
		 	//   stack. After that, the damage is â€œlocked in.â€� It will be dealt even if some of
			//   the creatures leave play.
			// - Players may then play instants and activated abilities. Once these have all
		 	//   resolved, combat damage is actually dealt. If a creature tries to deal damage
			//   to a creature no longer in play, it canâ€™t and the damage isnâ€™t dealt.
		
		//signalEvent(end_of_combat_phase)
		//players can play instants and activated abilities again
		
	}
	
	public void endingPhase() {
		//signalEvent(ending_phase)
		//players can play instants and activated abilities
		
		//signalEvent(cleanup_step)
		//if you have more than 7 cards in your hand -> discard cards
		
		//damage on creatures is removed
		//signalEvent(end_of_turn)
		
	}
	
	
	//
	//
	
	//alguna excepcion le meto aca
	// tengo la duda si serian metodos de player
		public void drawCard(Card card, Player player){
			if (player.library.contains(card)){
				player.library.remove(card);
				player.hand.add(card);
			}	
		}
		
	
		public void playCard(Card card,Player player){
			if(player.hand.contains(card)){
				player.hand.remove(card);
			
				// ver como esta implementada Card
			if (card.isPermanent){
				player.objectsInPlay.add(card.playCard);
			}
			else	
				player.graveyard.add(card);
			}	
		}
		
		public void KillObjectInPlay(InPlayObject obj, Player player){
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

}
