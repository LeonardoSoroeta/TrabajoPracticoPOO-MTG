package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class DeckUI implements Drawable {
	
	List<CardUI> deckUI;
	
	public DeckUI() {
		deckUI = new ArrayList<CardUI>();
	}
	
	public void draw() {
		for(CardUI each: deckUI) {
			each.draw();
		}
	}
	
	public List<CardUI> getCards() {
		return deckUI;
	}
	
	public void add(CardUI cardUI) {
		deckUI.add(cardUI);
	}

}
