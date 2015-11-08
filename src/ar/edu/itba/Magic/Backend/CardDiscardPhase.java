package ar.edu.itba.Magic.Backend;

public class CardDiscardPhase {
	
	private static CardDiscardPhase self = null;
	Match match = Match.getMatch();
	GameEventHandler eventHandler = GameEventHandler.getGameEventHandler();
	
	private CardDiscardPhase() {
		
	}
	
	public static CardDiscardPhase getCardDiscardPhase() {
		if(self == null) {
			self = new CardDiscardPhase();
		}
		return self;
	}
	
	public void start() {
		
	}
	
	public void resumeExecution() {
		
	}

}
