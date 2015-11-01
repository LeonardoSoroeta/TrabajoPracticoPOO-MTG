package ar.edu.itba.Magic.Backend;

import javax.smartcardio.Card;
import java.util.LinkedList;
import java.util.List;

public class Deck {
	
	List<Card> deck;
	
	public Deck(){
		deck = new LinkedList();
	}

	public void addCard(Card card){
		if(card != null)
			deck.add(card);
		throw new IllegalArgumentException();
	}
	
	public void removeCard(Card card){
		if( deck.contains(card))
			deck.remove(card);
	}

	public void addCardList(List<Card> cardlist){
		deck.addAll(cardlist);
	}
	
	public void clearDeck(){
		deck.clear();
	}
	
	public List<Card> getCards(){
		return deck;
	}
	public int getSize(){
		return deck.size();
	}
}
