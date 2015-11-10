package ar.edu.itba.Magic.Frontend;

import java.util.Map;

import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.CardType;

public class DeckList {
	
	Map<CardType, ExtendedImage> bigcard;
	Map<CardType, ExtendedImage> tinycard;
	
	public DeckList(Deck deck) throws SlickException{
		
		for( Card card: deck.getDeckList()){
			
			bigcard.put(card.getCardType(), new ExtendedImage("res/cards/"+card.getCardType().toString()+"jpg"));
			tinycard.put(card.getCardType(), new ExtendedImage("res/tinycards/"+card.getCardType().toString()+"jpg"));
			
		}
		
		
	}

}
