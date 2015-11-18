package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.SpellMechanics;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

import java.util.LinkedList;

/** The spell stack contains a list of spells being currently casted, that have not yet executed their action */
public class SpellStack {
	
    private static SpellStack instance = new SpellStack();
	private LinkedList<Spell> spellStack = new LinkedList<>();
	
	private boolean playerDidSomething;

	private SpellStack() {
		
	}

    public static SpellStack getSpellStack(){
        return instance;
    }
    
    public void initiateSpellChain() {
    	Match.getMatch().changeActivePlayer();
    	this.playerDidSomething = false;
    	Match.getMatch().setMatchState(MatchState.AWAITING_STACK_ACTIONS);
    	Match.getMatch().resetPlayerMessage();
    }
    
    public void playerDoneClicking() {
    	if(playerDidSomething == false) {
	    	while(!spellStack.isEmpty()) {
	    		spellStack.pop().resolveInStack();
	    	}
	    	Match.getMatch().setActivePlayer(Match.getMatch().getTurnOwner());
	    	Match.getMatch().setMatchState(MatchState.AWAITING_MAIN_PHASE_ACTIONS);
	    	Match.getMatch().resetPlayerMessage();
    	} else {
    		Match.getMatch().changeActivePlayer();
	    	this.playerDidSomething = false;
    	}
    }

	public void addSpell(Spell spell) {
		if(spellStack.isEmpty()) {
			spellStack.push(spell);
			this.initiateSpellChain();
		} else {
			spellStack.push(spell);
			this.playerDidSomething = true;
		}
    }
	
	public void removeSpell(Spell spell) {
		spellStack.remove(spell);
	}
	
	public LinkedList<Spell> getSpells() {
		LinkedList<Spell> spells = new LinkedList<Spell>();
		
		spells.addAll(this.spellStack);
		
		return spells;
	}
	
	public LinkedList<Spell> getPlayer1Spells() {
		LinkedList<Spell> spells = new LinkedList<Spell>();
		
		for(Spell each : spellStack) {
			if(each instanceof SpellMechanics) {
				if(((SpellMechanics)each).getSourceCard().getController().equals(Match.getMatch().getPlayer1())) {
					spells.add(each);
				}
			} else if (each instanceof Permanent) {
				if(((Permanent)each).getSourceCard().getController().equals(Match.getMatch().getPlayer1())) {
					spells.add(each);
				}
			}
		}
		
		return spells;
	}
	
	public LinkedList<Spell> getPlayer2Spells() {
		LinkedList<Spell> spells = new LinkedList<Spell>();
		
		for(Spell each : spellStack) {
			if(each instanceof SpellMechanics) {
				if(((SpellMechanics)each).getSourceCard().getController().equals(Match.getMatch().getPlayer2())) {
					spells.add(each);
				}
			} else if (each instanceof Permanent) {
				if(((Permanent)each).getSourceCard().getController().equals(Match.getMatch().getPlayer2())) {
					spells.add(each);
				}
			}
		}
		
		return spells;
	}
	
	
	public void resetData() {
		spellStack.clear();
	}
}
