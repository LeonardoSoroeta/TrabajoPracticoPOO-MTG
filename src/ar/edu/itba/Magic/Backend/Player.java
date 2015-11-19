package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Enchantment;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class contains all data pertaining to one player.
 */
public class Player implements DamageTaking {

	private ManaPool manaPool;
	private Deck deck;
	private List<Card> hand;
	private List<Permanent> permanentsInPlay;
	private List<Card> graveyard;
	private Integer health;
	private Match match = Match.getMatch();
	private GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	
	public Player(Deck deck) {
		this.deck = deck;
		this.hand = new LinkedList<Card>();
		this.permanentsInPlay = new LinkedList<Permanent>();
		this.graveyard = new LinkedList<Card>();
		this.manaPool = new ManaPool();	
		this.health = 20;
	}
	
	/** Sets the player's health. */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/** Gets the player's health. */
	public Integer getHealth() {
		return health;
	}
	/** Increases the palyer's health by a given ammount. */
	public void increaseHealth(int increment) {
		if (increment < 0)
			throw new IllegalArgumentException();
		health += increment;
	}
	
	/** Decreases the player's health by a given ammount. */
	public void decreaseHealth(int decrement) {
		if (decrement < 0)
			throw new IllegalArgumentException();
		health -= decrement;
	}
	
	/** Gets the player's mana pool. */
	public ManaPool getManaPool() {
		return manaPool;
	}
	
	/** Gets the player's deck. */
	public Deck getDeck(){
		return deck;
	}
	
	/** Removes one card from the top of the player's deck and places it in his hand. */
	public Card drawCard() {
		Card card = deck.getCard();
		if(card != null) {
			this.placeCardInHand(card);
		}
		return card;
	}
	
	/** Draws a given ammount of cards. */
	public void drawCards(Integer ammount) {
		for(int i = 0 ; i < ammount ; i++) {
			Card card = deck.getCard();
			this.placeCardInHand(card);
		}
	}
	
	/** Places one card from this player's hand into his graveyard. */
	public void discardCard(Card card) {
		this.removeCardFromHand(card);
		this.placeCardInGraveyard(card);
	}
	
	/** Gets a list of cards in player's hand. */
	public List<Card> getHand() {
		List<Card> hand = new LinkedList<Card>();
		hand.addAll(this.hand);
		
		return hand;
	}
	
	/** Shuffles the player's hand and deck together. */
	public void returnHandToDeckAndShuffle() {
		for(Card each : this.hand) {
			this.deck.addCard(each);
			this.hand.remove(each);
		}
		
		this.deck.shuffleDeck();
	}
	
	/** Places a given card in player's hand. */
	public void placeCardInHand(Card card) {
		hand.add(card);
	}
	
	/** Removes a given card from player's hand. */
	public void removeCardFromHand(Card card) {
		hand.remove(card);
	}
	
	/** Gets a list of cards in player's graveyard. */
	public List<Card> getGraveyard() {
		List<Card> graveyard = new LinkedList<Card>();
		graveyard.addAll(this.graveyard);
		
		return graveyard;
	}
	
	/** Places a given card in player's graveyard. */
	public void placeCardInGraveyard(Card card) {
		graveyard.add(card);
	}
	
	/** Removes a given card from player's graveyard. */
	public void removeCardFromGraveyard(Card card) {
		graveyard.remove(card);
	}
	
	/** Gets a list of all permanents in play belonging to this player. */
	public List<Permanent> getPermanentsInPlay() {
		List<Permanent> permanents = new LinkedList<Permanent>();
		permanents.addAll(permanentsInPlay);
		
		return permanents;
	}
	
	/** Places a permanent into play and triggers a related game event. */
	public void placePermanentInPlay(Permanent permanent) {
		permanent.setSpellState(false);
		permanentsInPlay.add(permanent);
		permanent.getAbility().executeOnEntering();
		gameEventHandler.triggerGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, permanent));
	}
	
	/** Removes a permanent from play and triggers a related game event. */
	public void removePermanentFromPlay(Permanent permanent) {
		gameEventHandler.triggerGameEvent(new GameEvent(Event.PERMANENT_LEAVES_PLAY, permanent));
		
		if(permanentsInPlay.contains(permanent)) {
			permanent.getAbility().executeOnExit();
			permanentsInPlay.remove(permanent);
		}

	}
	
	/** Gets a list of all creatures in play belonging to this player. */
	public List<Creature> getCreatures() {
		List<Creature> creatures = new LinkedList<Creature>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Creature) {
				creatures.add((Creature)permanent);
			}
		}
		
		return creatures;
	}
	
	/** Gets a list of all lands in play belonging to this player. */
	public LinkedList<Land> getLands() {
		LinkedList<Land> lands = new LinkedList<Land>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Land) {
				lands.add((Land)permanent);
			}
		}
		
		return lands;
	}
	
	/** Gets a list of all artifacts in play belonging to this player. */
	public List<Artifact> getArtifacts() {
		List<Artifact> artifacts = new LinkedList<Artifact>();
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Artifact) {
				artifacts.add((Artifact)permanent);
			}
		}
		
		return artifacts;
	}
	
	/** Gets a list of all enchantments in play belonging to this player. */
	public List<Enchantment> getEnchantments() {
		List<Enchantment> enchantments = new LinkedList<Enchantment>();	
		
		for(Permanent permanent : permanentsInPlay) {
			if(permanent instanceof Enchantment) {
				enchantments.add((Enchantment)permanent);
			}
		}
	
		return enchantments;
	}
	
	/** Returns true if player has a given permanent */
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
	
	/** Shuffles the player's deck. */
	public void shuffleLibrary() {
		deck.shuffleDeck();
	}
	
	/** Deals a given ammount of damage to this player. */
	public void takeDamage(Integer damage) {
		if(damage >= 0)
			health -= damage;
		if(this.lost()) {
			Match.getMatch().newMessageToPlayer("GAME OVER: Press ENTER to exit.");
			match.setMatchState(MatchState.GAME_OVER);
		}
	}
	
	/** Returns true if this player has less than 1 health */
	public boolean lost(){
		if(health <= 0)
			return true;
		return false;
	}
	
	/** Untaps all tapped cards belonging to this player. Must execute once per this player's upkeep. */
	public void untapDuringUnkeep(){
		for(Permanent each : this.getPermanentsInPlay()){
			if(each.isTapped() && each.containsAttribute(Attribute.UNTAPS_DURING_UPKEEP)){
				each.untap();
			}
		}
	}	
	
	/** Remvoes summoning sickness from all creatures belonging to this player. Must execute once per this player's
	 * upkeep.
	 */
	public void removeAllSummoningSickness() {
		for(Creature each : this.getCreatures()) {
			each.removeAttribute(Attribute.SUMMONING_SICKNESS);
		}
	}
	
}
