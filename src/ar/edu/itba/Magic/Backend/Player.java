package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Player implements DamageTaking {

    private String name;
	private ManaPool manaPool;
	private Deck deck;
	private List<Card> hand;
	private List<Permanent> permanentsInPlay;
	private List<Card> graveyard;
	private int health;
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	Match match = Match.getMatch();
	
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
	
	public Deck getDeck(){
		return deck;
	}
	
	public Card drawCard() {
		Card card = deck.getCard();
		this.placeCardInHand(card);
		
		return card;
	}
	
	public void discardCard(Card card) {
		this.removeCardFromHand(card);
		this.placeCardInGraveyard(card);
	}
	
	public List<Card> getHand() {
		List<Card> hand = new LinkedList<Card>();
		hand.addAll(this.hand);
		
		return hand;
	}
	
	public void placeCardInHand(Card card) {
		if(hand.contains(card)) {
			// throw exception card already in hand
		}
		hand.add(card);
	}
	
	public void removeCardFromHand(Card card) {
		if(hand.contains(card)) {
			hand.remove(card);
		}
		// else throw card not in hand exception
	}
	
	public List<Card> getGraveyard() {
		List<Card> graveyard = new LinkedList<Card>();
		graveyard.addAll(this.graveyard);
		
		return graveyard;
	}
	
	public void placeCardInGraveyard(Card card) {
		if(graveyard.contains(card)) {
			// throw exception card already in graveyard
		}
		graveyard.add(card);
	}
	
	public void removeCardFromGraveyard(Card card) {
		if(graveyard.contains(card)) {
			graveyard.remove(card);
		}
		// else throw exception card not in graveyard
	}
	
	public List<Permanent> getPermanentsInPlay() {
		List<Permanent> permanents = new LinkedList<Permanent>();
		permanents.addAll(permanentsInPlay);
		
		return permanents;
	}
	
	public void placePermanentInPlay(Permanent permanent) {
		if(permanentsInPlay.contains(permanent)) {
			// TODO throw exception permanent already in play
		}
		permanentsInPlay.add(permanent);
		if(permanent.containsAbility()) {
			permanent.getAbility().executeOnEntering();
		}
		gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, permanent));
	}
	
	public void removePermanentFromPlay(Permanent permanent) {
		if(permanentsInPlay.contains(permanent)) {
			if(permanent.containsAbility()) {
				permanent.getAbility().executeOnExit();
			}
			permanentsInPlay.remove(permanent);
		}
		// TODO else throw exception permanent not in play
		gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_LEAVES_PLAY, permanent));
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
	
	public Player getOpponent() {
		if(this == match.getPlayer1()) {
			return match.getPlayer2();
		}
		else {
			return match.getPlayer1();
		}
	}
	
	public boolean containsPermanentsInPlay(Permanent permanent){
		
		if(this.permanentsInPlay.contains(permanent)){
			return true;
		}
		return false;
	}
	
	public Permanent getPermanentInPlay(Permanent permanent){
		
		if(this.containsPermanentsInPlay(permanent)){
			return this.permanentsInPlay.get(this.permanentsInPlay.indexOf(permanent));
		}else{
			throw new NoSuchElementException();
		}
	}
	
	public void shuffleLibrary() {
		deck.shuffleDeck();
	}
	
	public void takeDamage(int damage) {
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
