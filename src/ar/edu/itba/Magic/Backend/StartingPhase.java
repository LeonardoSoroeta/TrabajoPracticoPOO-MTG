package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.LandCard;
import ar.edu.itba.Magic.Backend.Enums.Phase;

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
		if(innerPhase.equals(InnerPhase.ASKING_PLAYER_ONE_MULLIGAN)) {
			if(!this.containsLandCards(Match.getMatch().getActivePlayer().getHand())) {
				Match.getMatch().startingPhaseYesOrNoPrompt("Would you like draw again?");
			} else {
				Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
				Match.getMatch().startingPhaseYesOrNoPrompt("Would you like draw again?");
				innerPhase = InnerPhase.ASKING_PLAYER_TWO_MULLIGAN;
			}
		}
	}
	
	public void finish() {
		Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
		Match.getMatch().setCurrentPhase(Phase.BEGINNING_PHASE);
		Match.getMatch().beginningPhase();
	}

	public void playerSelectedYes() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		
		} else if(innerPhase.equals(InnerPhase.ASKING_PLAYER_ONE_MULLIGAN)) {
			Match.getMatch().getActivePlayer().get
		
		} else if(innerPhase.equals(InnerPhase.ASKING_PLAYER_TWO_MULLIGAN)) {
			
		}
	}
	
	public void playerSelectedNo() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		
		} else if(innerPhase.equals(InnerPhase.ASKING_PLAYER_ONE_MULLIGAN)) {
			
		
		} else if(innerPhase.equals(InnerPhase.ASKING_PLAYER_TWO_MULLIGAN)) {
			
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
