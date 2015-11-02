package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player implements DamageTaking {

    private String name;
	private ManaPool manaPool;
	private Deck deck;
	private List<Card> hand;
	private List<Permanent> permanentsInPlay;
	private List<Card> graveyard;
	private int health;
	
	public Player(Deck deck) {
		this.deck = deck;
		this.hand = new LinkedList<Card>();
		this.permanentsInPlay = new LinkedList<Permanent>();
		this.graveyard = new LinkedList<Card>();
		this.manaPool = new ManaPool();		
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void increaseHealth(int ammount) {
		this.health += ammount;
	}
	
	public void decreaseHealth(int ammount) {
		this.health -= ammount;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void increaseHealth(Integer increment) {
		if (increment < 0)
			throw new IllegalArgumentException();
		health += increment;
	}
	
	public void decreaseHealth(Integer decrement) {
		if (decrement < 0)
			throw new IllegalArgumentException();
		health -= decrement;
	}
	
	public ManaPool getManaPool() {
		return manaPool;
	}
	
	
	public List<Card> getLibrary() {
		return library;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<Permanent> getPermanentsInPlay() {
		return permanentsInPlay;
	}
	
	public List<Card> getGraveyard() {
		return graveyard;
	}
	
	/*  no se si es objetoso esto
	public Player getOpponent() {
		if(this == match.getPlayer1()) {
			return match.getPlayer2();
		}
		else {
			return match.getPlayer1();
		}
	}
	*/
	
	public void shuffleLibrary() {
		Collections.shuffle(this.library);
	}
	
	public void takeDamage(Integer damage) {
		if(damage >= 0)
			health -= damage;
		throw new IllegalArgumentException();
		// if (health <= 0) then pierde la partida etc etc
	}

	public boolean lost(){
		if(health <= 0)
			return true;
		return false;
	}
}
