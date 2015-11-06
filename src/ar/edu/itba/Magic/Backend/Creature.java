package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;

import java.util.List;

public class Creature extends Permanent implements DamageTaking {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	private int attack;
	private int defense;
	private Integer damageMarkers;
																						
	public Creature(Card sourceCard, CardName name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attack, Integer defense, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, ability);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}

	public Creature(Card sourceCard, CardName name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attack, Integer defense) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, null);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}
	
	public int getAttack(){
		return this.attack;
	}
	
	public int getDefense(){
		return this.defense;
	}
	
	public void setAttack(int i){
		this.attack = i;
	}
	
	public void setDefense(int i){
		this.defense = i;
		// TODO if defense - damageMarkers <= 0 , then this.destroy();
	}
	
	public void increaseAttack(int i){
		this.attack += i;
	}
	
	public void increaseDefense(int i){
		this.defense += i;
	}
	
	public void decreaseAttack(int i){
		this.attack -= i;
	}
	
	public void decreaseDefense(int i){
		this.defense -= i;
		// TODO if defense - damageMarkers <= 0 , then this.destroy();
	}
	
	public void takeDamage(Integer damage) {		
		damageMarkers += damage;
	}
	
	public int getDamageMarker() {		
		return damageMarkers;
	}
	
	
	public void resetDamageMarkers() {
		damageMarkers = 0;
	}
	
}
