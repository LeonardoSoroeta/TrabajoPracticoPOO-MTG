package ar.edu.itba.Magic.Backend;

import java.util.List;

public class CreatureCard extends Card {

	private int attackPoints;
	private int defencePoints;
	
	public CreatureCard(String cardName, String cardType, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints, Ability ability) {
		super(cardName, cardType, color, attributes, colorMana, colorlessMana, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}

	public CreatureCard(String cardName, String cardType, String color, List<String> attributes, int colorMana, int colorlessMana, int attackPoints, int defencePoints) {
		super(cardName, cardType, color, attributes, colorMana, colorlessMana, null);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}
		
	public void playCard() {
		//Creature creature = new Creature(...);
		
		//ManaPool manaPool = ManaPool.getManaPool();
		
		//pagar costo 
		
		//creature = new Creature(parametros...);
		
		//creature.sendToStack();	
	}
	
	
}