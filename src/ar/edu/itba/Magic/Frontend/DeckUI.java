package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class DeckUI implements Drawable {
	
	List<CardUI> deckUI;
	
	public void deckUI() {
		deckUI = new ArrayList<CardUI>();
	}
	
	public void draw() {
		
	}
	
	public void add(CardUI cardUI) {
		deckUI.add(cardUI);
	}

}
