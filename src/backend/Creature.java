package backend;
import java.util.List;


public class Creature extends Permanent implements DamageTaking {
	
	private String name;
	private int attack;
	private int defense;
	List<Attribute> attributes;
	Ability ability;
	private String color;
	private Integer damageMarkers;
	
	
	private int colormanacost;
	private int colorlessmanacost;
																						// hay que ver que onda Ability
	public Creature(String name, int attack, int defense, String color, List<Attribute> attributes, Ability ability, int colormanacost, int colorlessmanacost){
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.attributes = attributes;
		this.ability = ability;
		this.color = color;
		this.colormanacost = colormanacost;
		this.colorlessmanacost = colorlessmanacost;
		this.damageMarkers = 0;
	}
	
	
	public Creature(String name, int attack, int defense, String color, List<Attribute> attributes, int colormanacost, int colorlessmanacost){
		this(name, attack, defense, color, attributes, null, colormanacost, colorlessmanacost);
	}
	
	
	
	public void attack(){
		
	}
	
	
	public void defend(){
		
	}
	
	
	
	// cuando muere retorna la carta de la que nacio antes
	public CreatureCard dead(){
		return new CreatureCard(this.name, this.color, colormanacost, colorlessmanacost, this.attack, this.defense, this.ability);
	
}
	
	public boolean containAbility(){
		if (this.ability == null)
			return false;
		return true;
	}
	
	public Ability getAbility(){
		return this.ability;
	}
	
	public List<Attribute> getAttributes(){
		return this.attributes;
		
	}
	
	public String getName(){
		return this.name;
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
			this.destroy(); // ejemplo
	}
	
	public void resetDamageMarkers() {
		damageMarkers = 0;
	}
	
	public void destroy() {
		
	}
}
