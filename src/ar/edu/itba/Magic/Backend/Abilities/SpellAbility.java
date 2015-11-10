package ar.edu.itba.Magic.Backend.Abilities;

import java.util.HashMap;

import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;


/** All InstantCards and SorceryCards contain a SpellAbility */
public abstract class SpellAbility extends Ability implements GameStackAction {
	
	Match match = Match.getMatch();
	
	private ManaPool manaPool = this.getSourceCard().getController().getManaPool();
	private Card sourceCard;

	private Integer coloredManaRequired;
	private Integer colorlessManaRequired;
	private HashMap<Color, Integer> manaCache = new HashMap<Color, Integer>();
	private Object selectedTarget;
	
	public Card getSourceCard() {
		return sourceCard;
	}

	public void setSourceCard(Card sourceCard) {
		this.sourceCard = sourceCard;
	}
	
	@Override
	public void executeOnCasting(Card sourceCard) {
		this.sourceCard = sourceCard;
		this.requestCastingManaPayment();
	}
	
	public final void requestCastingManaPayment() {
		for(Color color : Color.values()) {
			manaCache.put(color, 0);
		}
		if(sourceCard.getColoredManaCost() == 0) {
			if(sourceCard.getColorlessManaCost() == 0) {
				this.proceedToSelectCastingTarget();
			}
		} else {
			this.coloredManaRequired = sourceCard.getColoredManaCost();
			this.colorlessManaRequired = sourceCard.getColorlessManaCost();
			match.awaitCastingManaPayment(this, "Pay requested mana cost to cast this card: ");
		}
	}
	
	// pedirle al front que no pase nada si el player hace click en un 0
	// por ahora asumiendo q el front le pasa un Color object si o si
    public final void resumeCastingManaRequest() {
    	this.selectedTarget = match.getSelectedTarget();
    	Color selectedColor = (Color)selectedTarget;
    	
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
    	if(coloredManaRequired == 0 && colorlessManaRequired == 0) {
    		this.proceedToSelectCastingTarget();
    	} else {
    		match.awaitCastingManaPayment(this, "Pay requested mana cost to cast this card: ");
    	}
    }
    
    /** Executes when player presses Cancel button, if currently requesting mana on casting. */
    public final void cancelCastingManaRequest() {
    	this.resetManaCache();
    }
    
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
	}
	
	/** Empties mana cache */
	private void resetManaCache() {
		for(Color each : Color.values()) {
    		manaPool.addManaOfThisColor(each, manaCache.get(each));
    	}
	}
	
	public abstract void sendToStack();
	
	public abstract void resolveInStack();
}
