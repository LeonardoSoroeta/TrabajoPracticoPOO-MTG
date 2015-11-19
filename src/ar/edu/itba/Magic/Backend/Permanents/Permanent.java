package ar.edu.itba.Magic.Backend.Permanents;

import java.util.*;

import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.SpellStack;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Effects.AutomaticLastingEffect;
import ar.edu.itba.Magic.Backend.Effects.LastingEffect;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;
import ar.edu.itba.Magic.Backend.Mechanics.PermanentMechanics;

/**
 * All objects currently in play are Permanents. These objects may be a Creature, an Enchantment, an Artifact or a Land.
 * Permanents may be affected by LastingEffects from a determined Ability.
 */
public abstract class Permanent implements Spell {
	
	private Card sourceCard;
	private Player controller;
	private boolean tapped;
	private boolean legalTarget;
	private boolean spellState;
	private PermanentMechanics permanentMechanics;
	private List<Attribute> attributes;
	private List<LastingEffect> appliedLastingEffects = new LinkedList<LastingEffect>();
	private List<Enchantment> attachedEnchantments = new LinkedList<Enchantment>();
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	SpellStack gameStack = SpellStack.getSpellStack();
	
	public Permanent(Card sourceCard, List<Attribute> attributes, PermanentMechanics mechanics) {
		this.sourceCard = sourceCard;
		this.attributes = attributes;
		this.permanentMechanics = mechanics;
		this.tapped = false;
		this.legalTarget = true;
		this.spellState = true;
	}
	
	/**
	 * Applies a determined lasting effect on this permanent. Sets this permanent as the LastingEffect's target
	 * and executes the LastingEffects applyEffect method. If lasting effect is an AutomaticLastingEffect, 
	 * adds it to the GameEventHandler as well.
	 */
	public void applyLastingEffect(LastingEffect lastingEffect) {
		appliedLastingEffects.add(lastingEffect);
		lastingEffect.setTarget(this);
		lastingEffect.applyEffect();
		if(lastingEffect instanceof AutomaticLastingEffect) {
			gameEventHandler.addListener((AutomaticLastingEffect)lastingEffect);
		}
	}
	
	/**
	 * Removes a determined lasting effect from this permanent. Executes the LastingEffect's undoEffect method.
	 * If lasting effect is an AutomaticLastingEffect, removes it from the GameEventHandler as well.
	 */
	public void removeLastingEffect(LastingEffect lastingEffect) {
		appliedLastingEffects.remove(lastingEffect);
		lastingEffect.undoEffect();
		if(lastingEffect instanceof AutomaticLastingEffect) {
			gameEventHandler.removeListener((AutomaticLastingEffect)lastingEffect);
		}
	}
	
	/**
     * Removes any LastingEffect from this permanent that comes from a specific Mechanics. Executes the
     * LastingEffect's undoEffect method. If lasting effect is an AutomaticLastingEffect, removes 
     * it from the GameEventHandler as well.
     */
    public void removeLastingEffectsFromSourceAbility(Mechanics ability) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSourceMechanics() == ability) {
    			lastingEffect.undoEffect();
    			appliedLastingEffects.remove(lastingEffect);
    			if(lastingEffect instanceof AutomaticLastingEffect) {
    				gameEventHandler.removeListener((AutomaticLastingEffect)lastingEffect);
    			}
    		}
    	}
    }
	
	public void addAttachedEnchantment(Enchantment enchantment) {
		attachedEnchantments.add(enchantment);
	}
	
	public void removeAttachedEnchantment(Enchantment enchantment) {
		attachedEnchantments.remove(enchantment);
	}

	public List<LastingEffect> getAppliedLastingEffects()	{
		return appliedLastingEffects;
	}
	
	public List<Enchantment> getAttachedEnchantments() {
		return attachedEnchantments;
	}
	
	/**
	 * Adds an attribute to this permanent's attribute list.
	 */
	public void addAttribute(Attribute	attribute) {
		attributes.add(attribute);
	}
	
	/**
	 * Removes an attribute from this permanent's attribute list.
	 */
	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute);
	}
	
	/**
	 * Whether this permanent contains a determined attribute.
	 */
	public boolean containsAttribute(Attribute attribute) {
		if(attributes.contains(attribute))
			return true;
		
		return false;
	}
	
	/**
	 * Get's this permanent's attribute list.
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * Gets this permanent's source card type.
	 */
	public CardType getCardType() {
		return this.sourceCard.getCardType();
	}
	
	/**
	 * Gets this permanent's color.
	 */
	public Color getColor() {
		return this.sourceCard.getCardType().getColor();
	}
	
	/**
	 * Gets this permanent's colored mana cost.
	 */
	public Integer getColoredManaCost() {
		return this.sourceCard.getCardType().getColoredManaCost();
	}
	
	/**
	 * Gets this permanent's colorless mana cost.
	 */
	public Integer getColorlessManaCost() {
		return this.sourceCard.getCardType().getColorlessManaCost();
	}
	
	/**
	 * Sets the player who controls this permanent.
	 */
	public void setController(Player controller) {
		this.controller = controller;
	}
	
	/**
	 * Controller is the player that contains the Permanent in his PermanentsInPlay section.
	 */
	public Player getController() {
		return controller;
	}
	
	/**
	 * Gets the Card that created this Permanent.
	 */
	public Card getSourceCard() {
		return sourceCard;
	}
	
	public String getName() {
		return sourceCard.getCardType().getCardName();
	}
    
	/**
	 * Whether this Permanent is affected by a LastingEffect from a specific Mechanics
	 */
    public boolean isAffectedByMechanics(Mechanics mechanics) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSourceMechanics() == mechanics) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Returns the PermanentAbility contained by this permanent.
     * 
     * @return PermanentAbility contained by this permanent.
     */
	public PermanentMechanics getAbility() {
		return permanentMechanics;
	}
	
	/** Sets this Permanent's tapped status to true */
	public void tap() {
		this.tapped = true;
	}
	
	public void untap() {
		this.tapped = false;
	}
	
	/** 
	 * Whether this permanent is tapped.	
	 * 
	 * @return True if this Permanent is tapped. False otherwise.
	 */
	public Boolean isTapped() {
		return tapped;
	}
	
	public boolean isStillALegalTarget() {
		return legalTarget;
	}
	
	public boolean isCurrentlyInSpellState() {
		return spellState;
	}
	
	public void setSpellState(boolean spellState) {
		this.spellState = spellState;
	}
	
	public final void counterSpell() {
		gameStack.removeSpell(this);
	}
	
	public void sendToStack() {
		gameStack.addSpell(this);
	}
	
	public void resolveInStack() {
		spellState = false;
		this.getController().placePermanentInPlay(this);
	}
	
	/**
	 * Destroys this permanent. If the permanent contains an ability, executes PermanentAbility's executeOnExit method.
	 * Destroys any enchantments attached to this permanent. Removes this permanent from controller player's 
	 * permanentsInPlay and adds this permanent's source Card to controller player's graveyard.
	 */
	public void destroy() {
		if(this.isStillALegalTarget()) {
			if(this.isCurrentlyInSpellState()) {
				legalTarget = false;
				gameStack.removeSpell(this);
				this.controller.placeCardInGraveyard(sourceCard);
			}
			else {
				legalTarget = false;
				this.getAbility().executeOnExit();
				for(Enchantment attachedEnchantment : attachedEnchantments) {
					attachedEnchantment.destroy();
				}
				this.controller.removePermanentFromPlay(this);
				this.controller.placeCardInGraveyard(sourceCard);
			}
		}
	}
	
}