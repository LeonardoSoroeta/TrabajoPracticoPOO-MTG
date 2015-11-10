package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;

import java.util.LinkedList;

public class GameStack {
	
	 Match match = Match.getMatch();

    private static GameStack instance = new GameStack();
	private LinkedList<GameStackObject> gameStack = new LinkedList<>();

	private GameStack() {
		
	}

    public static GameStack getGameStackInstance(){
        return instance;
    }
    
    public void initiateSpellChain() {
    	match.setPreviousMatchState(match.getMatchState());
    	match.setMatchState(MatchState.AWAITING_STACK_ACTIONS);
    }
    
    public void playerDoneClicking() {
    	while(!gameStack.isEmpty()) {
    		gameStack.pop().resolveInStack();
    	}
    	match.setMatchState(match.getPreviousMatchState());
    }

	public void addStackObject(GameStackObject gameStackObject) {
		if(gameStack.isEmpty()) {
			gameStack.push(gameStackObject);
			this.initiateSpellChain();
		}
		gameStack.push(gameStackObject);
    }
	
	public void removeStackObject(GameStackObject gameStackObject) {
		gameStack.remove(gameStackObject);
	}
	
}