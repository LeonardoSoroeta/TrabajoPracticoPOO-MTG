package ar.edu.itba.Magic.Backend;

import java.util.List;

public class CreatureCard extends SpellCard{

	private int attackPoints;
	private int defencePoints;
	
	public CreatureCard(String nameCard, String typeCard, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints, Ability ability) {
		super(nameCard, typeCard, color, colorMana, colorlessMana, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}

	public CreatureCard(String nameCard, String typeCard, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints) {
		super(nameCard, typeCard, color, colorMana, colorlessMana);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}
		
	public Permanent playCard(ManaPool manaPool) {
		
		//if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			Creature creature = new Creature(this.getNameCard(), attackPoints, defencePoints, this.getColor(), this.getAttributes(), this.getAbility(), this.getColorMana(), this.getColorlessMana());
			
			return creature;
		//}else{
			//tira Excepsion!!!
		//}

	}
	
	
}