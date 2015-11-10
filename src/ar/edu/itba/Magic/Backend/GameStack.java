package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class GameStack {

    private static GameStack instance = new GameStack();
	private LinkedList<GameStackAction> gameStack = new LinkedList<>();

	private GameStack() {
		
	}

    public static GameStack getGameStackInstance(){
        return instance;
    }
    
    private void initiateSpellChain(GameStackAction gameStackAction) {
    	boolean actionAdded = true;
    			
    	gameStack.push(gameStackAction);
    	
    	while(actionAdded) {
    		// TODO request action to other player, if no action -> actionAdded = false;
    	}
    	
    	while(!gameStack.isEmpty()) {
    		gameStack.pop().resolveInStack();
    	}
    }
    
    public void continueExecution() {
    	
    }

	public void addStackAction(GameStackAction gameStackAction) {
		if(gameStack.isEmpty()) {
			this.initiateSpellChain(gameStackAction);
		}
		else {
			gameStack.push(gameStackAction);
		}
    }
	
	public void removeStackAction(GameStackAction gameStackAction) {
		gameStack.remove(gameStackAction);
	}
	
}
