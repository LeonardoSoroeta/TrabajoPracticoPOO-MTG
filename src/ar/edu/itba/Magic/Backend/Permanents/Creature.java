package ar.edu.itba.Magic.Backend.Permanents;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Mechanics.PermanentMechanics;

import java.util.LinkedList;
import java.util.List;

/** Creatures are a type of permanent that can attack and defend */
public class Creature extends Permanent implements DamageTaking {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	private Integer baseAttack;
	private Integer baseDefense;
	private Integer attackModifier;
	private Integer defenseModifier;
	private Integer damageMarkers;
																						
	public Creature(Card sourceCard, List<Attribute> attributes, Integer attack, Integer defense, PermanentMechanics ability) {
		super(sourceCard, attributes, ability);
		this.baseAttack = attack;
		this.baseDefense = defense;
		this.damageMarkers = 0;
		this.attackModifier = 0;
		this.defenseModifier = 0;
	}
	
	public Integer getAttack(){
		return this.baseAttack + this.attackModifier;
	}
	
	public Integer getDefense(){
		return this.baseDefense + this.defenseModifier;
	}
	
	public void setBaseAttack(Integer baseAttack){
		this.baseAttack = baseAttack;
	}
	
	public void setBaseDefense(Integer baseDefense){
		this.baseDefense = baseDefense;
		if(baseDefense + defenseModifier < 0) {
			this.destroy();
		}
	}
	
	public void modifyAttack(Integer modifier){
		this.attackModifier += modifier;
	}
	
	public void modifyDefense(Integer modifier){
		this.defenseModifier += modifier;
		if(baseDefense + defenseModifier < 0) {
			this.destroy();
		}
	}

	public void dealDamageTo(Creature creature) {
		if(this.getAttack() < 0) {
			creature.takeDamage(0);
		} else {
			creature.takeDamage(this.getAttack());
		}
	}
	
	public void dealDamageTo(Player player) {
		if(this.getAttack() < 0) {
			player.takeDamage(0);
		} else {
			player.takeDamage(this.getAttack());
		}
	}
	
	public void takeDamage(Integer damage) {		
		damageMarkers += damage;
		if(damageMarkers >= this.getDefense()) {
			this.destroy();
		}
	}
	
	public Integer getDamageMarkers() {		
		return damageMarkers;
	}
	
	
	public void resetDamageMarkers() {
		damageMarkers = 0;
	}
	
	/**
	 * Creates a list of default attributes contained by creatures. 
	 * @return Returns a list of default attributes contained by creatues.
	 */
	public static List<Attribute> getDefaultCreatureAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		attributes.add(Attribute.SUMMONING_SICKNESS);
		attributes.add(Attribute.UNTAPS_DURING_UPKEEP);
		attributes.add(Attribute.CAN_ATTACK);
		attributes.add(Attribute.CAN_BLOCK);
		attributes.add(Attribute.CAN_TAP);
	
		return attributes;
	}
	
}
