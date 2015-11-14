package ar.edu.itba.Magic.Backend.Permanents;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;

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
																						
	public Creature(Card sourceCard, List<Attribute> attributes, Integer attack, Integer defense, PermanentAbility ability) {
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
	
	public void setBaseAttack(Integer i){
		this.baseAttack = i;
	}
	
	public void setBaseDefense(Integer i){
		this.baseDefense = i;
		// TODO if defense - damageMarkers <= 0 , then this.destroy();
	}
	
	public void modifyAttack(Integer i){
		this.attackModifier += i;
	}
	
	public void modifyDefense(Integer i){
		this.defenseModifier += i;
		// TODO if defense - damageMarkers <= 0 , then this.destroy();
	}

	public void dealDamageTo(Creature creature) {
		creature.takeDamage(this.getAttack());
	}
	
	public void dealDamageTo(Player player) {
		player.takeDamage(this.getAttack());
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
