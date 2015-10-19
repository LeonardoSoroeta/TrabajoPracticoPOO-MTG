package backend;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player {
	
	ManaPool manaPool;
	
	List<Card> library;
	List<Card> hand;
	List<InPlayObject> objectsInPlay;
	List<Card> graveyard;
	
	private int health;
	
	public Player(Deck deck) {
		this.library = deck.getCards();
		this.hand = new LinkedList<Card>();
		this.objectsInPlay = new LinkedList<InPlayObject>();
		this.graveyard = new LinkedList<Card>();
		this.manaPool = new ManaPool();
		
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void increaseHealth(int i) {
		if ( i<0)
			throw new IllegalArgumentException();
		health += i;
	}
	
	public void decreaseHealth(int i) {
		if ( i<0)
			throw new IllegalArgumentException();
		health -= i;
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
	
	public List<InPlayObject> getObjectsInPlay() {
		return objectsInPlay;
	}
	
	public List<Card> getGraveyard() {
		return graveyard;
	}
	
	public void shuffleLibrary() {
		Collections.shuffle(this.library);
	}
	
	
	
	



}
