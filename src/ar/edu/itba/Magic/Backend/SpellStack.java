package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.SpellMechanics;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

import java.util.LinkedList;

/** The game stack contains a list of spells being currently casted, that have not yet had its action */
public class SpellStack {
	
    private static SpellStack instance = new SpellStack();
	private LinkedList<Spell> gameStack = new LinkedList<>();
	
	private boolean playerDidSomething;

	private SpellStack() {
		
	}

    public static SpellStack getSpellStack(){
        return instance;
    }
    
    public void initiateSpellChain() {
    	Match.getMatch().setPreviousMatchState(Match.getMatch().getMatchState());
    	Match.getMatch().changeActivePlayer();
    	this.playerDidSomething = false;
    	Match.getMatch().setMatchState(MatchState.AWAITING_STACK_ACTIONS);
    	Match.getMatch().resetPlayerMessage();
    }
    
    public void playerDoneClicking() {
    	if(playerDidSomething == false) {
	    	while(!gameStack.isEmpty()) {
	    		gameStack.pop().resolveInStack();
	    	}
	    	Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
	    	Match.getMatch().setMatchState(Match.getMatch().getPreviousMatchState());
	    	Match.getMatch().resetPlayerMessage();
    	} else {
    		Match.getMatch().changeActivePlayer();
	    	this.playerDidSomething = false;
    	}
    }

	public void addStackObject(Spell gameStackObject) {
		if(gameStack.isEmpty()) {
			gameStack.push(gameStackObject);
			this.initiateSpellChain();
		} else {
			gameStack.push(gameStackObject);
			this.playerDidSomething = true;
		}
    }
	
	public void removeStackObject(Spell gameStackObject) {
		gameStack.remove(gameStackObject);
	}
	
	public LinkedList<Spell> getGameStackObjects() {
		LinkedList<Spell> gameStackObjects = new LinkedList<Spell>();
		
		gameStackObjects.addAll(this.gameStack);
		
		return gameStackObjects;
	}
	
	public LinkedList<Spell> getPlayer1STackObjects() {
		LinkedList<Spell> gameStackObjects = new LinkedList<Spell>();
		
		for(Spell each : gameStack) {
			if(each instanceof SpellMechanics) {
				if(((SpellMechanics)each).getSourceCard().getController().equals(Match.getMatch().getPlayer1())) {
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
	
	public LinkedList<Spell> getPlayer2StackObjects() {
		LinkedList<Spell> gameStackObjects = new LinkedList<Spell>();
		
		for(Spell each : gameStack) {
			if(each instanceof SpellMechanics) {
				if(((SpellMechanics)each).getSourceCard().getController().equals(Match.getMatch().getPlayer2())) {
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
