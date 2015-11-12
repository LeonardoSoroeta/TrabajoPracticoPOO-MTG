package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Abilities.SpellAbility;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

import java.util.LinkedList;

public class GameStack {
	
    private static GameStack instance = new GameStack();
	private LinkedList<GameStackObject> gameStack = new LinkedList<>();
	
	private boolean playerDidSomething;

	private GameStack() {
		
	}

    public static GameStack getGameStackInstance(){
        return instance;
    }
    
    public void initiateSpellChain() {
    	Match.getMatch().setPreviousMatchState(Match.getMatch().getMatchState());
    	Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
    	this.playerDidSomething = false;
    	Match.getMatch().newMessageToPlayer("Fast actions: Instant cards, Abilities:");
    	Match.getMatch().setMatchState(MatchState.AWAITING_STACK_ACTIONS);
    }
    
    public void playerDoneClicking() {
    	if(playerDidSomething == false) {
	    	while(!gameStack.isEmpty()) {
	    		gameStack.pop().resolveInStack();
	    	}
	    	Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
	    	Match.getMatch().setMatchState(Match.getMatch().getPreviousMatchState());
    	} else {
    		Match.getMatch().setActivePlayer(Match.getMatch().getOpposingPlayerFrom(Match.getMatch().getActivePlayer()));
	    	this.playerDidSomething = false;
    	}
    }

	public void addStackObject(GameStackObject gameStackObject) {
		if(gameStack.isEmpty()) {
			gameStack.push(gameStackObject);
			this.initiateSpellChain();
		} else {
			gameStack.push(gameStackObject);
			this.playerDidSomething = true;
		}
    }
	
	public void removeStackObject(GameStackObject gameStackObject) {
		gameStack.remove(gameStackObject);
	}
	
	public LinkedList<GameStackObject> getGameStackObjects() {
		LinkedList<GameStackObject> gameStackObjects = new LinkedList<GameStackObject>();
		
		gameStackObjects.addAll(this.gameStack);
		
		return gameStackObjects;
	}
	
	public LinkedList<GameStackObject> getPlayer1STackObjects() {
		LinkedList<GameStackObject> gameStackObjects = new LinkedList<GameStackObject>();
		
		for(GameStackObject each : gameStack) {
			if(each instanceof SpellAbility) {
				if(((SpellAbility)each).getSourceCard().getController().equals(Match.getMatch().getPlayer1())) {
					gameStackObjects.add(each);
				}
			} else if (each instanceof Permanent) {
				if(((Permanent)each).getSourceCard().getController().equals(Match.getMatch().getPlayer1())) {
					gameStackObjects.add(each);
				}
			}
		}
		
		return gameStackObjects;
	}
	
	public LinkedList<GameStackObject> getPlayer2GameStackObjects() {
		LinkedList<GameStackObject> gameStackObjects = new LinkedList<GameStackObject>();
		
		for(GameStackObject each : gameStack) {
			if(each instanceof SpellAbility) {
				if(((SpellAbility)each).getSourceCard().getController().equals(Match.getMatch().getPlayer2())) {
					gameStackObjects.add(each);
				}
			} else if (each instanceof Permanent) {
				if(((Permanent)each).getSourceCard().getController().equals(Match.getMatch().getPlayer2())) {
					gameStackObjects.add(each);
				}
			}
		}
		
		return gameStackObjects;
	}
	
	
	public void resetData() {
		gameStack.clear();
	}
}
