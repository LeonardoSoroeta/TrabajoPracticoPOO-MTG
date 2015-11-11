package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Event;

public class CardDiscardPhase {
	
	private static CardDiscardPhase self = new CardDiscardPhase();
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	Object selectedTarget;
	
	private CardDiscardPhase() {
		
	}
	
	public static CardDiscardPhase getCardDiscardPhase() {
		return self;
	}
	
	public void start() {
		if(match.getTurnOwner().getHand().size() > 7) {
			match.awaitCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, match.getTurnOwner()));
			match.executeNextPhase();
		}
	}
	
	public void resumeExecution() {
		this.selectedTarget = match.getSelectedTarget();
		match.getTurnOwner().discardCard((Card)selectedTarget);
		
		if(match.getTurnOwner().getHand().size() > 7) {
			match.awaitCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			this.manaBurnStep();
		}
	}
	
	public void manaBurnStep() {
		if( match.getPlayer1().manaBurn() || match.getPlayer2().manaBurn()) {
			match.giveManaBurnNotice();
		}
	}

	public void finishCardDiscardPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, match.getTurnOwner()));
		match.executeNextPhase();
	}
	
}
