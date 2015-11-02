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
	
	public List<Card> getHand() {
		List<Card> hand = new LinkedList<Card>();
		hand.addAll(this.hand);
		
		return hand;
	}
	
	
	public List<Card> getGraveyard() {
		List<Card> graveyard = new LinkedList<Card>();
		graveyard.addAll(this.graveyard);
		
		return graveyard;
	}
	
	
	public List<Permanent> getPermanentsInPlay() {
		List<Permanent> permanents = new LinkedList<Permanent>();
		permanents.addAll(permanentsInPlay);
		
		return permanents;
	}
	
	public List<Creature> getCreatures() {
		List<Creature> creatures = new LinkedList<Creature>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Creature) {
				creatures.add((Creature)permanent);
			}
		}
		
		return creatures;
	}
	
	public List<Land> getLands() {
		List<Land> lands = new LinkedList<Land>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Land) {
				lands.add((Land)permanent);
			}
		}
		
		return lands;
	}
	
	public List<Artifact> getArtifacts() {
		List<Artifact> artifacts = new LinkedList<Artifact>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Artifact) {
				artifacts.add((Artifact)permanent);
			}
		}
		
		return artifacts;
	}
	
	public List<Enchantment> getEnchantments() {
		List<Enchantment> enchantments = new LinkedList<Enchantment>();	
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Enchantment) {
				enchantments.add((Enchantment)permanent);
			}
		}
	
	return enchantments;
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
