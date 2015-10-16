import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player implements HealthPoints {
	
	ManaPool manaPool;
	
	List<Card> deck;
	List<Card> library;
	List<Card> hand;
	List<Card> cardsInPlay;
	List<Card> graveyard;
	
	private Integer health;
	
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
	
	public void setHealth(Integer health) {
		this.health = health;
	}
	
	public Integer getHealth() {
		return health;
	}
	
	public void increaseHealth(Integer i) {
		health += i;
	}
	
	public void decreaseHealth(Integer i) {
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
