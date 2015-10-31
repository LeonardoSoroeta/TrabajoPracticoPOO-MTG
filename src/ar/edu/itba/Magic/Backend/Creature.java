package ar.edu.itba.Magic.Backend;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;
import java.util.List;

public class Creature extends Permanent implements DamageTaking, GameStackAction {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	private int attack;
	private int defense;
	private Integer damageMarkers;
																						
	public Creature(Card sourceCard, String name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attack, Integer defense, PermanentAbility ability) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, ability);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}

	public Creature(Card sourceCard, String name, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attack, Integer defense) {
		super(sourceCard, name, color, attributes, coloredManaCost, colorlessManaCost, null);
		this.attack = attack;
		this.defense = defense;
		this.damageMarkers = 0;
	}
	
	/**
	 * Sends the creature to the game stack when it's Card is played.
	 */
	public void sendToStack(){
		//gameStack.add(this);
	}
	
	/**
	 * Places the creature in play. Notifies the event. Executes the ability's executeOnIntroduction method.
	 */
	public void resolveInStack() {
		this.getController().getPermanentsInPlay().add(this);
		
		gameEventHandler.notifyGameEvent(new GameEvent("new_permanent_in_play", this));
		
		if(this.containsAbility())
			((PermanentAbility)this.getAbility()).executeOnIntroduction();
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
