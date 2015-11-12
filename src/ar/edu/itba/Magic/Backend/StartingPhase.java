package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.LandCard;

public class StartingPhase {
	
	InnerPhase innerPhase = InnerPhase.ASKING_WHO_GOES_FIRST;

	private static StartingPhase self = new StartingPhase();
	
	private StartingPhase() {
		
	}
	
	public static StartingPhase getStartingPhase() {
		return self;
	}
	
	public void start() {
		Match.getMatch().getPlayer1().getDeck().shuffleDeck();
		Match.getMatch().getPlayer2().getDeck().shuffleDeck();
		Match.getMatch().getPlayer1().drawCards(7);
		Match.getMatch().getPlayer2().drawCards(7);
		
		Match.getMatch().startingPhaseYesOrNoPrompt("Would you like to go play first?");
	}
	
	public void mulliganStep() {
		Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
		if(!this.containsLandCards(Match.getMatch().getActivePlayer().getHand())) {
			
		}
	}
	
	public void finish() {
		
	}

	public void playerSelectedYes() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		}
	}
	
	public void playerSelectedNo() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		}
	}
	
	public void resetData() {
		innerPhase = InnerPhase.ASKING_WHO_GOES_FIRST;
	}
	
	public boolean containsLandCards(List<Card> cards) {
		for(Card card : cards) {
			if(card instanceof LandCard) {
				return true;
			}
		}
		
		return false;
	}
	
	private enum InnerPhase{
		ASKING_WHO_GOES_FIRST,
		ASKING_PLAYER_ONE_MULLIGAN,
		ASKING_PLAYER_TWO_MULLIGAN,
	}
	
}
