import java.util.List;


public class Creature {
	
	private String name;
	private int attack;
	private int defense;
	List<Attribute> attributes;
	Ability ability;
	private String color;
																						// hay que ver que onda Ability
	public Creature(String name, int attack, int defense, String color, List<Attribute> attributes, Ability ability){
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.attributes = attributes;
		this.ability = ability;
		this.color = color;
	}
	
	
	public Creature(String name, int attack, int defense, String color, List<Attribute> attributes){
		this(name, attack, defense, color, attributes, null);
	}
	
	
	
	public void attack(){
		
	}
	
	
	public void defend(){
		
	}
	
	
	public void dead(){
	
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
}
