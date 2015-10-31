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
	private PermanentAbility ability;
	private List<Attribute> attributes;
	public List<LastingEffect> appliedEffects = new LinkedList<LastingEffect>();
	public List<Enchantment> attachedEnchantments = new LinkedList<Enchantment>();
	
	public Permanent(Card sourceCard, String name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		this.sourceCard = sourceCard;
		this.name = name;
		this.attributes = attributes;
		this.ability = ability;
		this.color = color;
		this.coloredManacost = coloredManaCost;
		this.colorlessManacost = colorlessManaCost;
	}
	
	public void addAppliedEffect(LastingEffect lastingEffect) {
		appliedEffects.add(lastingEffect);
	}
	
	public void addAttachedEnchantment(Enchantment enchantment) {
		attachedEnchantments.add(enchantment);
	}

	public List<LastingEffect> getAppliedEffects()	{
		return appliedEffects;
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
		if (this.ability == null)
			return false;
		return true;
	}
	
	public Ability getAbility() {
		return ability;
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
		//quitar del juego
		//signalGameEvent(new GameEvent("permanent_leaves_play", this);
		//for all applied effects y attached enchantments : quitarlos del juego tambien
		//agregar al cementerio this.getSourceCard
	}
	
}