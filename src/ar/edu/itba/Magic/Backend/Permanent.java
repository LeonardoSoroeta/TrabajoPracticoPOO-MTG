package ar.edu.itba.Magic.Backend;

import java.util.*;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;

/**
 * All objects currently in play are Permanents. These objects may be a Creature, an Enchantment, an Artifact or a Land.
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
	
	public  List<Enchantment> getAttachedEnchantments() {
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
	
	public Player getController() {
		return controller;
	}
	
	public Card getSourceCard() {
		return sourceCard;
	}
	
	public boolean containsAbility() {
		if (this.permanentAbility == null)
			return false;
		return true;
	}
    
    public boolean affectedByAbility(Ability ability) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSource() == ability) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void removeLastingEffectFromAbility(Ability ability) {
    	for(LastingEffect lastingEffect : appliedLastingEffects) {
    		if(lastingEffect.getSource() == ability) {
    			lastingEffect.undoEffect();
    			appliedLastingEffects.remove(lastingEffect);
    		}
    	}
    }
	
	public PermanentAbility getAbility() {
		return permanentAbility;
	}
	
	public void tap() {
		if(this.containsAttribute(Attribute.CAN_TAP) && this.isTapped() == false)
			tapped = true;
		else
			System.out.println("no se puede tapear!"); //TODO cambiar esto
	}
	
	public boolean isTapped() {
		return tapped;
	}
	
	public void destroy() {
		//TODO
		if(this.containsAbility()) {
			this.getAbility().executeOnExit();
		}
		gameEventHandler.notifyGameEvent(new GameEvent("permanent_leaves_play", this));
		this.controller.getPermanentsInPlay().remove(this);
		this.controller.getGraveyard().add(this.sourceCard);
		//agregar al cementerio this.getSourceCard
	}
	
}