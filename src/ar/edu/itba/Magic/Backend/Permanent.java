package ar.edu.itba.Magic.Backend;

import java.util.*;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;

/**
 * All objects currently in play are Permanents. These objects may be a Creature, an Enchantment, an Artifact or a Land.
 * Permanents may be affected by LastingEffects from a determined Ability.
 */
public abstract class Permanent {
	
	private Card sourceCard;
	private Player controller;
	private String name;
	private Color color;
	private Integer coloredManacost;
	private Integer colorlessManacost;
	private boolean tapped;
	private PermanentAbility permanentAbility;
	private List<Attribute> attributes;
	private List<LastingEffect> appliedLastingEffects = new LinkedList<LastingEffect>();
	private List<Enchantment> attachedEnchantments = new LinkedList<Enchantment>();
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	Match match = Match.getMatch();
	
	public Permanent(Card sourceCard, String name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		this.sourceCard = sourceCard;
		this.name = name;
		this.attributes = attributes;
		this.permanentAbility = ability;
		this.color = color;
		this.coloredManacost = coloredManaCost;
		this.colorlessManacost = colorlessManaCost;
	}
	
	public void addAppliedEffect(LastingEffect lastingEffect) {
		appliedLastingEffects.add(lastingEffect);
	}
	
	public void removeAppliedEffect(LastingEffect lastingEffect) {
		appliedLastingEffects.remove(lastingEffect);
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
	
	public void addAttribute(Attribute	attribute) {
		attributes.add(attribute);
	}
	
	public boolean containsAttribute(Attribute attribute) {
		if(attributes.contains(attribute))
			return true;
		
		return false;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	 
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Integer getColoredManaCost() {
		return coloredManacost;
	}
	
	public Integer getColorlessManaCost() {
		return colorlessManacost;
	}
	
	public void setController(Player controller) {
		this.controller = controller;
	}
	
	/**
	 * Controller is the player that contains the Permanent in his PermanentsInPlay section.
	 * @return the Player that controlls this permanent.s
	 */
	public Player getController() {
		return controller;
	}
	
	/**
	 * Gets the Card that created this Permanent.
	 * @return the Card that created this Permanent.
	 */
	public Card getSourceCard() {
		return sourceCard;
	}
	
	/**
	 * Whether this Permanent contains an Ability. Only Creature Permanents may not contain an Ability.
	 * @return True if this Permanent contains an Ability. False otherwise.
	 */
	public boolean containsAbility() {
		if (this.permanentAbility == null)
			return false;
		return true;
	}
    
	/**
	 * Whether this Permanent is affected by a LastingEffect from a specific Ability
	 * @param ability an Ability that may be applying a LastingEffect on this Permanent.
	 * @return True if affected. False otherwise.
	 */
    public boolean affectedByAbility(Ability ability) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSource() == ability) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Removes any LastingEffect from a specific Ability targeting this Permanent.
     * @param ability an Ability that may be applying a LastingEffect on this Permanent.
     */
    public void removeLastingEffectFromAbility(Ability ability) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSource() == ability) {
    			lastingEffect.undoEffect();
    			appliedLastingEffects.remove(lastingEffect);
    		}
    	}
    }
	
    /**
     * Returns the PermanentAbility contained by this permanent.
     * 
     * @return PermanentAbility contained by this permanent.
     */
	public PermanentAbility getAbility() {
		return permanentAbility;
	}
	
	/**
	 * Taps this permanent, only if it contains CAN_TAP attribute and is not already tapped.
	 */
	public void tap() {
		if(this.containsAttribute(Attribute.CAN_TAP) && this.isTapped() == false)
			tapped = true;
		else
			System.out.println("no se puede tapear!"); //TODO cambiar esto
	}
	
	/** 
	 * Whether this permanent is tapped.	
	 * 
	 * @return True if this Permanent is tapped. False otherwise.
	 */
	public boolean isTapped() {
		return tapped;
	}
	
	/**
	 * Destroys this permanent. If the permanent contains an ability, executes PermanentAbility's executeOnExit method.
	 * Removes this permanent from controller player's permanentsInPlay and adds this permanent's source Card to controller
	 * player's graveyard.
	 */
	public void destroy() {
		//TODO
		if(this.containsAbility()) {
			this.getAbility().executeOnExit();
		}
		gameEventHandler.notifyGameEvent(new GameEvent("permanent_leaves_play", this));
		this.controller.getPermanentsInPlay().remove(this);
		this.controller.getGraveyard().add(this.sourceCard);
	}
	
}