import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player {
	
	ManaPool manaPool;
	
	List<Card> deck;
	List<Card> library;
	List<Card> hand;
	List<Card> cardsInPlay;
	List<Card> graveyard;
	
	private int health;
	
	public Player(List<Card> deck) {
		this.deck = deck;
		this.library = new LinkedList<Card>();
		this.hand = new LinkedList<Card>();
		this.cardsInPlay = new LinkedList<Card>();
		this.graveyard = new LinkedList<Card>();
		this.manaPool = new ManaPool();
		
	}
	
	public void drawCard() {
		
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
	
	public List<Card> getDeck() {
		return deck;
	}
	
	public List<Card> getLibrary() {
		return library;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<Card> getCardsInPlay() {
		return cardsInPlay;
	}
	
	public List<Card> getGraveyard() {
		return graveyard;
	}
	
	public void shuffleLibrary() {
		Collections.shuffle(this.library);
	}
}
