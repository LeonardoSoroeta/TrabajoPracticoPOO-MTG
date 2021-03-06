package ar.edu.itba.Magic.Backend.Mechanics;

import java.util.HashMap;
import java.util.List;

import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Cards.ArtifactCard;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.CreatureCard;
import ar.edu.itba.Magic.Backend.Cards.EnchantmentCard;
import ar.edu.itba.Magic.Backend.Cards.LandCard;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Enchantment;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/** All permanents and their source Cards contain a PermanentAbility */
public abstract class PermanentMechanics extends Mechanics {
	
	Match match = Match.getMatch();
	
	private ManaPool manaPool;
	private Card sourceCard;
	private Permanent sourcePermanent;

	private Integer coloredManaRequired;
	private Integer colorlessManaRequired;
	private HashMap<Color, Integer> manaCache = new HashMap<Color, Integer>();
	private Object selectedTarget;
	
	/** Returns the Permanent that contains this ability */
	public final Permanent getSourcePermanent() {
		return sourcePermanent;
	}
	
	/** If a Permanent is succesfully created by this ability on casting, sets said Permanent as this ability's source */
	public final void setSourcePermanent(Permanent sourcePermanent) {
		this.sourcePermanent = sourcePermanent;
	}
	
	/** Called by source Card method playCard() */
	@Override
	public final void executeOnCasting(Card sourceCard) {
		this.sourceCard = sourceCard;
		this.requestCastingManaPayment();
	}
	
	/** Called by local method executeOnCasting */
	public final void requestCastingManaPayment() {
		this.manaPool = sourceCard.getController().getManaPool();
		for(Color each : Color.values()) {
			manaCache.put(each, 0);
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
	
	/** Must override this method if card requires target on casting */
	public void proceedToSelectCastingTarget() {
		this.finishCasting();
	}
	
	/** Must override this method if card requires target on casting */
	public void resumeCastingTargetSelection() {

	}
	
	/** Executes when player presses Cancel button, if currently requesting a target on casting. */
	public final void cancelCastingTargetSelection() {
		this.resetManaCache();
		Match.getMatch().resetPlayerMessage();
	}
	
    /** When all casting requirements are met, this method finally creates a permanent and sends it to game stack */
	@Override 
	public final void finishCasting() {
		if(sourceCard instanceof EnchantmentCard) {
	        Enchantment enchantment = new Enchantment(sourceCard, this);
	        this.setSourcePermanent(enchantment);
	        enchantment.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        Match.getMatch().resetPlayerMessage();
	        enchantment.sendToStack();	
	        
		} else if(sourceCard instanceof ArtifactCard) {
	        Artifact artifact = new Artifact(sourceCard, this);
	        this.setSourcePermanent(artifact);
	        artifact.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        Match.getMatch().resetPlayerMessage();
	        artifact.sendToStack();	
	        
		} else if(sourceCard instanceof CreatureCard) {
			Integer attackPoints =  ((CreatureCard)sourceCard).getAttackPoints();
			Integer defencePoints =  ((CreatureCard)sourceCard).getDefencePoints();
		    List<Attribute> attributes =  ((CreatureCard)sourceCard).getAttributes();
	        Creature creature = new Creature(sourceCard, attributes, attackPoints, defencePoints,  this);
	        this.setSourcePermanent(creature);
	        creature.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        Match.getMatch().resetPlayerMessage();
	        creature.sendToStack();	
	        
		} else if(sourceCard instanceof LandCard) {
            Land land = new Land(sourceCard, this);
            this.setSourcePermanent(land);
            land.setController(sourceCard.getController());
            land.setSpellState(false);
            sourceCard.getController().removeCardFromHand(sourceCard);
            sourceCard.getController().placePermanentInPlay(land);
		}
	}
	
	/** Must use this method if ability requires mana payment */
	public final void requestAbilityManaPayment(Integer coloredManaCost, Integer colorlessManaCost) {
		this.manaPool = this.getSourcePermanent().getController().getManaPool();
		for(Color each : Color.values()) {
			manaCache.put(each, 0);
		}
		if(sourceCard.getColoredManaCost().equals(0) && sourceCard.getColorlessManaCost().equals(0)) {
			this.proceedToSelectCastingTarget();
		} else {
			this.coloredManaRequired = coloredManaCost;
			this.colorlessManaRequired = colorlessManaCost;
			match.awaitAbilityManaPayment(this, "Activating " + sourceCard.getCardType().getCardName().toUpperCase() + ". " + this.manaPrompt());
		}
	}
	
	/** Executes on each match update, if currently requesting mana from an ability. */
	public final void resumeAbilityManaRequest() {
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
	    		this.executeIfManaPayed();
	    	} else {
	    		match.awaitCastingManaPayment(this, "Casting " + sourceCard.getCardType().getCardName().toUpperCase() + ". " + this.manaPrompt());
	    	}
    	}
	}
	
	/** Executes when player presses Cancel button, if ability currently requesting mana. */
	public final void cancelAbilityManaRequest() {
		this.resetManaCache();
		Match.getMatch().resetPlayerMessage();
	}
	
	/** Must override this method if ability requires mana payment */
	public void executeIfManaPayed() {
		
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
	
	/** Executes when player presses Cancel button, if ability currently requesting a target. */
	public final void cancelAbilityTargetSelection() {
		this.resetManaCache();
		Match.getMatch().resetPlayerMessage();
	}
	
	/** Must override this method if ability requires target selection */
	public void resumeAbilityTargetSelection() {
		
	}
	
	/** Empties mana cache */
	private void resetManaCache() {
		for(Color each : Color.values()) {
    		manaPool.addManaOfThisColor(each, manaCache.get(each));
    	}
	}
	
	/** Must override this method on Permanents that require an action to be executed on entering play. */
	public void executeOnEntering() {
		
	}
	
	/** Must override this method on Permanents that require an action to be executed on leaving play. */
	public void executeOnExit() {
		
	}
	
	/** Must override this method on Permanents that contain an activated ability. */
	public void executeOnActivation() {
		Match.getMatch().newMessageToPlayer("This permanent does not have an activated ability.");
	}

}
