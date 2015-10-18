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
	
	
	//alguna excepcion le meto aca
	public void drawCard(Card card){
		if (this.library.contains(card)){
			this.library.remove(card);
			this.hand.add(card);
		}	
	}
	
	
	
	
	//estos metodos quiza no son de player y son de otra clase
	//
	public void playCard(Card card){
		if(this.hand.contains(card)){
			this.hand.remove(card);
		
		if (card.isPermanent){
		// playcard no es un metodo de Card , hay que arreglarlo
			this.objectsInPlay.add(card.playCard);
		}
		
		else
			
			this.graveyard.add(card);
		}	
	}
	
	public void KillObjectInPlay(InPlayObject obj){
		if(this.objectsInPlay.contains(obj)){
			this.objectsInPlay.remove(obj);
			this.graveyard.add(obj.dead);
		}
	}
	
	public void reviveCard(Card card){
		if(this.graveyard.contains(card)){
			this.graveyard.remove(card);
			this.objectsInPlay.add(card.playCard);
		}
	}


}
