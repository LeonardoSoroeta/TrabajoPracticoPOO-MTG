package ar.edu.itba.Magic.Backend;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import ar.edu.itba.Magic.Backend.Cards.Card;

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
		if(this.getSize() > 0){
			return deck.pop();
		}else{
			throw new NoSuchElementException();
		}
	}
	
	public boolean containsCard(Card card){
		return deck.contains(card);
	}
	
	public Card getCard(Card card){
		if(containsCard(card)){
			deck.remove(card);
			return card;
		}else{
			throw new NoSuchElementException();
		}
		 
	}
	/*estaria bueno que tenga metodos como estos dos para las habilidades raras
	public List<Card> getTypeCard(Card card){
		List<Card> listCardType = new LinkedList<Card>(); 
		for(Card each : deck){
			if(each.getCardType().equals(card.getCardType())){
				listCardType.add(each);
			}
		}
		return listCardType;
	}
	
	public List<Card> getColorCard(Card card){
		List<Card> listCardColor = new LinkedList<Card>(); 
		for(Card each : deck){
			if(each.getColor().equals(card.getColor())){
				listCardColor.add(each);
			}
		}
		return listCardColor;
	}
	*/
	
	public int getSize(){
		return deck.size();
	}
}
