package ar.edu.itba.Magic.Backend;


public class StartingPhase {
	
	Match match = Match.getMatch();
	
	InnerPhase innerPhase = InnerPhase.ASKING_WHO_GOES_FIRST;

	private static StartingPhase self = new StartingPhase();
	
	private StartingPhase() {
		
	}
	
	public static StartingPhase getStartingPhase() {
		return self;
	}
	
	public void start() {
		match.getPlayer1().getDeck().shuffleDeck();
		match.getPlayer2().getDeck().shuffleDeck();
		match.getPlayer1().drawCards(7);
		match.getPlayer2().drawCards(7);
		
		match.startingPhaseYesOrNoPrompt("Would you like to go play first?");
		
	}
	
	public void mulliganStep() {
		
	}
	
	public void finish() {
		
	}

	public void playerSelectedYes() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		}
	}
	
	public void playerSelectedNo() {
		if(innerPhase.equals(InnerPhase.ASKING_WHO_GOES_FIRST)) {
			match.setActivePlayer(match.getOpposingPlayerFrom(match.getActivePlayer()));
			this.innerPhase = InnerPhase.ASKING_PLAYER_ONE_MULLIGAN;
			this.mulliganStep();
		}
	}
	
	public void resetData() {
		innerPhase = InnerPhase.ASKING_WHO_GOES_FIRST;
	}
	
	private enum InnerPhase{
		ASKING_WHO_GOES_FIRST,
		ASKING_PLAYER_ONE_MULLIGAN,
		ASKING_PLAYER_TWO_MULLIGAN,
	}
	
}
