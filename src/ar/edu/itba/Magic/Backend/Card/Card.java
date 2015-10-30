package ar.edu.itba.Magic.Backend.Card;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Player;

public abstract class Card {
	
	private Player controller;
	private String cardName;
	private String cardType;
	private ColorCard color;
	private Integer coloredManaCost;
	private Integer colorlessManaCost;
	private Ability ability;

	public Card(String cardName,String cardType, ColorCard color, Integer coloredManaCost, Integer colorlessManaCost ,Ability ability){
		this.cardName = cardName;
		this.cardType = cardType;
		this.color = color;
		this.coloredManaCost = coloredManaCost;
		this.colorlessManaCost = colorlessManaCost;
		this.ability = ability;
	}
	
	/**
	 * Must set a Card's controller every time it is instanced. 
	 * 
	 * @param controller The player who contains the Card.
	 */
	public void setController(Player controller) {
		this.controller = controller;
	}
	
	public Player getController() {
		return controller;
	}
	
	public String getCardName(){
		return cardName;
	}
	
	public String getCardType(){
		return this.cardType;
	}
	
	public ColorCard getColor(){
		return color;
	}
	
	public Integer getColoredManaCost() {
		return coloredManaCost;
	}
	
	public Integer getColorlessManaCost() {
		return colorlessManaCost;
	}
	
	public Ability getAbility(){
		return ability;	
	}
	
	public boolean containsAbility(){
		if (this.ability == null)
			return false;
		return true;
	}
	
	public abstract void playCard();	

}

