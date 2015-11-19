package ar.edu.itba.Magic.Frontend;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.SpellMechanics;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

public class DeckList {
	
	Map<Card, ExtendedImage> bigcard;   
	Map<Card, ExtendedImage> tinycard;

	
	int i= 0;
	
	ExtendedImage queonda;
	
	public DeckList(Deck deck, float h, float w) throws SlickException{
		
		bigcard = new HashMap<Card, ExtendedImage>();
		tinycard = new HashMap<Card, ExtendedImage>();
		
		
		for( Card card: deck.getCards()){
			
			
			bigcard.put(card, new ExtendedImage("res/cards/"+card.getCardType().getCardName().toString()+".jpg"));
			
			tinycard.put(card, new ExtendedImage("res/tinycards/"+card.getCardType().getCardName().toString()+".jpg"));
			
		}
		
		
	}
	
	
	public ExtendedImage getBigCard(Permanent permanent){
		
		return bigcard.get(permanent.getSourceCard()) ;
	}
	
    public ExtendedImage getTinyCard(Permanent permanent){
		
		return tinycard.get(permanent.getSourceCard()) ;
	}
    
   
public ExtendedImage getTinyCard(Creature creature){
		
		return tinycard.get(creature.getSourceCard()) ;
	}


    public ExtendedImage getTinyCard(Card card){
    	
    	return tinycard.get(card);
    	
    }
    
 public ExtendedImage getBigCard(Card card){
    	
    	return bigcard.get(card);
    	
    }
    
    
 public ExtendedImage getTinyStackCard(Spell object){
 	
	 
	 
	 if(object instanceof Permanent ){	
		
 	return tinycard.get((Permanent)object);
	 
	 }
	
 	if(object instanceof SpellMechanics ){
 		 
 		return tinycard.get(((SpellMechanics)object).getSourceCard());
 	}
 	
 	throw new NullPointerException();
 }
 
 		
 		
 		
 		
public ExtendedImage getBigStackCard(Spell object){
	 
	 
	 if(object instanceof Permanent )		
	return bigcard.get((Permanent)object);
	 
	
	if(object instanceof SpellMechanics )
		return bigcard.get(((SpellMechanics)object).getSourceCard());
	
	return bigcard.get((Card)object);
}
}
