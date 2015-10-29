package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

import java.util.List;

public class Creature extends Permanent implements DamageTaking, GameStackAction {
	
	private int attack;
	private int defense;
	private Integer damageMarkers;
																						
	public Creature(Card sourceCard, String name, int attack, int defense, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, ability);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}

	public Creature(Card sourceCard, String name, int attack, int defense, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, null);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}
	
	public void sendToStack(){
		//gameStack.add(this);
	}
	
	public void resolveInStack() {
		//colocar en juego
		//ability.executeOnIntroduction();
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
	}
	
	public void updateAttack(int i){
		this.attack += i;
	}
	
	public void updateDefense(int i){
		this.defense += i;
	}
	
	public void takeDamage(Integer damage) {		
		this.placeDamageMarker(damage);	
	}
	
	public void placeDamageMarker(Integer ammount) {	
		damageMarkers += ammount;
		
		if(damageMarkers >= defense) 
			this.destroy();
	}
	
	public void resetDamageMarkers() {
		damageMarkers = 0;
	}
	
}
