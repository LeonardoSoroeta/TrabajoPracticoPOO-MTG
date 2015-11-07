package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Enums.CardType;

public class CardFactory {
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
       
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	public Card getCard(CardType cardType) {
		
		return cardType.createCardOfThisType();  //probando
		
       /* if(cardName != null)
            cardImplementations.get(cardName).createCard();
        throw new IllegalArgumentException();*/
	}
    
}