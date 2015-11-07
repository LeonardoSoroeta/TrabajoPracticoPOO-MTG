package ar.edu.itba.Magic.Frontend;

import java.util.List;

//falta implementar
public class PlayerUI {
	DeckUI deckUI;
	
	public DeckUI getDeckUI(){
		return deckUI;
	}
	
	public List<CardUI> getCardsUI() {
		return deckUI.getCards();
	}
	

}
