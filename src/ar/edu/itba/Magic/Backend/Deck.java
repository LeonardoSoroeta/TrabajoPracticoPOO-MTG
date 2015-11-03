package ar.edu.itba.Magic.Backend;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import ar.edu.itba.Magic.Backend.Card.Card;

public class Deck {
	
	private LinkedList<Card> deck;
	
	public Deck(){
		deck = new LinkedList<Card>();
	}

    public Deck(LinkedList<Card> list){
        deck.addAll(list);
    }

	public void addCard(Card card){
		if(card != null)
			deck.add(card);
		throw new IllegalArgumentException();
	}
	
	public void removeCard(Card card){
		if(deck.contains(card))
			deck.remove(card);
	}

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }
	
	public void clearDeck(){
		deck.clear();
	}
	
	public Card getCard(){
		return deck.pop();
	}
	public int getSize(){
		return deck.size();
	}
}
