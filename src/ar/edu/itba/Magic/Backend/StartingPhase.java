package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.Phase;

/** 
 * The starting phase is responsible for asking which player goes first.
 */
public class StartingPhase {
	
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
		
		Match.getMatch().startingPhaseYesOrNoPrompt("Would you like to play first?");
	}
	
	public void finish() {
		Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
		Match.getMatch().setCurrentPhase(Phase.BEGINNING_PHASE);
		Match.getMatch().beginningPhase();
	}

	public void playerSelectedYes() {
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.finish();
	}
	
	public void playerSelectedNo() {
			Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
			Match.getMatch().setTurnOwner(Match.getMatch().getActivePlayer());
			this.finish();
	}
}
