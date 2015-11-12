package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Event;

public class CardDiscardPhase {
	
	private static CardDiscardPhase self = new CardDiscardPhase();
	
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	Object selectedTarget;
	
	private CardDiscardPhase() {
		
	}
	
	public static CardDiscardPhase getCardDiscardPhase() {
		return self;
	}
	
	public void start() {
		if(Match.getMatch().getTurnOwner().getHand().size() > 7) {
			Match.getMatch().awaitCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, Match.getMatch().getTurnOwner()));
			Match.getMatch().executeNextPhase();
		}
	}
	
	public void resumeExecution() {
		this.selectedTarget = Match.getMatch().getSelectedTarget();
		Match.getMatch().getTurnOwner().discardCard((Card)selectedTarget);
		
		if(Match.getMatch().getTurnOwner().getHand().size() > 7) {
			Match.getMatch().awaitCardToDiscardSelection("You have more than 7 cards in your hand. Select a card to discard: ");
		} else {
			this.finishCardDiscardPhase();
		}
	}
	
	public void finishCardDiscardPhase() {
		eventHandler.triggerGameEvent(new GameEvent(Event.END_OF_TURN, Match.getMatch().getTurnOwner()));
		Match.getMatch().getPlayer1().getManaPool().resetMana();
		Match.getMatch().getPlayer2().getManaPool().resetMana();
		Match.getMatch().executeNextPhase();
	}
	
}
