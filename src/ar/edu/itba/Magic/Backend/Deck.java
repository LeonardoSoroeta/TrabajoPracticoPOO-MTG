package ar.edu.itba.Magic.Backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.CardType;


public class Deck implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private LinkedList<Card> deck;
	
	public Deck(){
		deck = new LinkedList<Card>();
	}
	
	public Deck(Deck d){
		deck = new LinkedList<Card>();
		deck.addAll(d.getCards());
	}

    public Deck(LinkedList<Card> list){
        deck.addAll(list);
    }
	
	public static void deleteDeck(int num) throws IOException {
		LinkedList<Deck> decks = loadDecks();
		decks.remove(num);
		
		LinkedList<LinkedList<CardType>> listT = new LinkedList<LinkedList<CardType>>();
			
		for(Deck each1: decks) {
			LinkedList<CardType> aux = new LinkedList<CardType>();
			for(Card each2: each1.getCards()) {
				aux.add(each2.getCardType());
			}
			listT.add(aux);
		}
			
		FileOutputStream fos = new FileOutputStream("Decks.out");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		LinkedList<CardType> ct = new LinkedList<CardType>(); 
		   
		oos.writeObject(listT);
		fos.close();
	}
	
	
	/*
	 * deserialize a list of objects and returns a list of decks
	 */
    public static LinkedList<Deck> deserialize() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("Decks.out");
		ObjectInputStream ois = new ObjectInputStream(fis);

		LinkedList<LinkedList<CardType>> listT = (LinkedList<LinkedList<CardType>>) ois.readObject();
		
		LinkedList<Deck> decks = new LinkedList<Deck>();
		for(LinkedList<CardType> each1: listT) {
			Deck aux = new Deck();
			for(CardType each2: each1) {
				aux.addCard(each2.createCardOfThisType());
			}
			decks.add(aux);
		}
		
		ois.close();
		return decks;
    }
    
    public void serialize(Deck d) throws IOException {
    	
    	LinkedList<LinkedList<CardType>> listT = new LinkedList<LinkedList<CardType>>();
    	
    	LinkedList<Deck> decks = loadDecks();
    	if(decks == null) {
    		decks = new LinkedList<Deck>();
    	}
    	else {
			for(Deck each1: decks) {
				LinkedList<CardType> aux = new LinkedList<CardType>();
				for(Card each2: each1.getCards()) {
					aux.add(each2.getCardType());
				}
				listT.add(aux);
			}
    	}
    	
    	
    	LinkedList<CardType> aux = new LinkedList<CardType>();
		for(Card each: d.getCards()) {
			aux.add(each.getCardType());
		}
		listT.add(aux);
    	
        FileOutputStream fos = new FileOutputStream("Decks.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        LinkedList<CardType> ct = new LinkedList<CardType>();
        
        oos.writeObject(listT);
        fos.close();
    }
	
	public void writeDeck(){
	    try {
	        serialize(this);
	    } catch (IOException e) {
	    	//System.out.println("error writing decks");
	        return;
	    }
	}
	
	@SuppressWarnings("finally")
	public static LinkedList<Deck> loadDecks(){
		LinkedList<Deck> decks = null;
		try {
		    decks = (LinkedList<Deck>) deserialize();
		} catch (ClassNotFoundException | IOException e) {
			//System.out.println("error loading decks");
		} finally{
			return decks;
		}
	}

	public void addCard(Card card){
		if(card != null) {
			deck.add(card);
		}
		else
			throw new IllegalArgumentException();
	}
	
	public void removeCard(Card card) {
		if(deck.contains(card)) {
			deck.remove(card);
		} else {
			Match.getMatch().endMatch();
		}
	}

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
	
	public void clearDeck() {
		deck.clear();
	}
	
	public Card getCard(){
		if(this.getSize() > 0){
			return deck.pop();
		}
		else {
			throw new NoSuchElementException();
		}
	}
	
	public boolean containsCard(Card card) {
		return deck.contains(card);
	}
	
	public Card getCard(Card card) {
		if(containsCard(card)) {
			deck.remove(card);
			return card;
		}
		else {
			throw new NoSuchElementException();
		}
		 
	}
	
	public LinkedList<Card> getCards() {
		return deck;
	}
	
	public Integer getSize(){
		return deck.size();
	}
}
