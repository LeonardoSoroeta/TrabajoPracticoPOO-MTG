package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Event;

public class CardDiscardPhase {
	
	private static CardDiscardPhase self = null;
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	Object selectedTarget;
	
	private CardDiscardPhase() {
		
	}
	
	public static CardDiscardPhase getCardDiscardPhase() {
		if(self == null) {
			self = new CardDiscardPhase();
		}
		return self;
	}
	
	public void start() {
		if(match.getTurnOwner().getHand().size() > 7) {
			match.requestCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, match.getTurnOwner()));
			match.executeNextPhase();
		}
	}
	
	public void resumeExecution() {
		this.selectedTarget = match.getSelectedTarget();
		match.getTurnOwner().discardCard((Card)selectedTarget);
		
		if(match.getTurnOwner().getHand().size() > 7) {
			match.requestCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, match.getTurnOwner()));
			match.executeNextPhase();
		}
	}

}
