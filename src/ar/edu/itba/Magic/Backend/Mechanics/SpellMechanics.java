package ar.edu.itba.Magic.Backend.Mechanics;

import java.util.HashMap;

import ar.edu.itba.Magic.Backend.SpellStack;
import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;


/** All InstantCards and SorceryCards contain a SpellAbility */
public abstract class SpellMechanics extends Mechanics implements Spell {
	
	Match match = Match.getMatch();
	SpellStack gameStack = SpellStack.getSpellStack();
	
	private ManaPool manaPool;
	private Card sourceCard;

	private Integer coloredManaRequired;
	private Integer colorlessManaRequired;
	private HashMap<Color, Integer> manaCache = new HashMap<Color, Integer>();
	private Object selectedTarget;
	
	/** Returns the card that contains this ability */
	public Card getSourceCard() {
		return sourceCard;
	}
	
	/** Sets the card that contains this ability */
	public void setSourceCard(Card sourceCard) {
		this.sourceCard = sourceCard;
	}
	
	@Override
	public void executeOnCasting(Card sourceCard) {
		this.sourceCard = sourceCard;
		this.requestCastingManaPayment();
	}
	
	/** Called by local method executeOnCasting */
	public final void requestCastingManaPayment() {
		this.manaPool = sourceCard.getController().getManaPool();
		for(Color color : Color.values()) {
			manaCache.put(color, 0);
		}
		if(sourceCard.getColoredManaCost().equals(0) && sourceCard.getColorlessManaCost().equals(0)) {
			this.proceedToSelectCastingTarget();
		} else {
			this.coloredManaRequired = sourceCard.getColoredManaCost();
			this.colorlessManaRequired = sourceCard.getColorlessManaCost();
			match.awaitCastingManaPayment(this, "Casting " + sourceCard.getCardType().getCardName().toUpperCase() + ". " + this.manaPrompt());
		}
	}
	
	/** Executes on each match update, if currently requesting mana on casting. */
    public final void resumeCastingManaRequest() {
    	this.selectedTarget = match.getSelectedTarget();
    	Color selectedColor = (Color)selectedTarget;
    	
    	if(!manaPool.containsOneManaOfThisColor(selectedColor)) {
    		match.awaitCastingManaPayment(this, "Casting " + sourceCard.getCardType().getCardName().toUpperCase() + ". " + this.manaPrompt());
    	} else {
	    	if(selectedColor.equals(sourceCard.getColor())) {
	    		if(coloredManaRequired > 0) {
	    			manaCache.put(selectedColor, manaCache.get(selectedColor) +1);
	    			manaPool.removeOneManaOfThisColor(selectedColor);
	    			coloredManaRequired--;
	    		} else {
	    			manaCache.put(selectedColor, manaCache.get(selectedColor) +1);
	    			manaPool.removeOneManaOfThisColor(selectedColor);
	    			colorlessManaRequired--;
	    		}
	    	} else if(colorlessManaRequired > 0){
	    		manaCache.put(selectedColor, manaCache.get(selectedColor) +1);
				manaPool.removeOneManaOfThisColor(selectedColor);
				colorlessManaRequired--;
	    	}
    	
	    	if(coloredManaRequired.equals(0) && colorlessManaRequired.equals(0)) {
	    		this.proceedToSelectCastingTarget();
	    	} else {
	    		match.awaitCastingManaPayment(this, "Casting " + sourceCard.getCardType().getCardName().toUpperCase() + ". " + this.manaPrompt());
	    	}
    	}
    }
    
    /** Executes when player presses Cancel button, if currently requesting mana on casting. */
    public final void cancelCastingManaRequest() {
    	this.resetManaCache();
    	Match.getMatch().resetPlayerMessage();
    }
    
    private String manaPrompt() {
		if(coloredManaRequired.equals(0)) {
			return "Pay " + colorlessManaRequired.toString() + " Colorless.";
		} else {
			if(colorlessManaRequired.equals(0)) {
				return "Pay " + coloredManaRequired.toString() + " " + sourceCard.getColor().getName() + ".";
			} else {
				return "Pay "  + coloredManaRequired.toString() + " " + sourceCard.getColor().getName() + " " + colorlessManaRequired.toString() + " Colorless.";
			}
		}
	}
    
    /** Removes the source card from controllers hand and sends the spell to the stack */
    @Override 
	public void finishCasting() {
    	sourceCard.getController().discardCard(sourceCard);
    	this.sendToStack();
    }
    
    /** By default just finishes casting. If target selection is required, override this method. */
	public void proceedToSelectCastingTarget() {
		this.finishCasting();
	}
	
	/** By default does nothing. If target selection is required, override this method. */
	public void resumeCastingTargetSelection() {

	}
	
	/** Executes when player presses Cancel button, if currently requesting a target on casting. */
	public final void cancelCastingTargetSelection() {
		this.resetManaCache();
		Match.getMatch().resetPlayerMessage();
	}
	
	/** Empties mana cache */
	private void resetManaCache() {
		for(Color each : Color.values()) {
    		manaPool.addManaOfThisColor(each, manaCache.get(each));
    	}
	}
	
	/** Sends spell to stack */
	public final void sendToStack() {
		gameStack.addStackObject(this);
	}
	
	/** Responsible for finally executing the spells action */
	public abstract void resolveInStack();
}
