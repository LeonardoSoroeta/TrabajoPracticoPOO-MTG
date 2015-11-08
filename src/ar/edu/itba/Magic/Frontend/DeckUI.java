package ar.edu.itba.Magic.Frontend;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class DeckUI implements Drawable {
	
	List<CardUI> deckUI;
	
	public DeckUI() {
		deckUI = new LinkedList<CardUI>();
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
	
	public int size() {
		return deckUI.size();
	}

}
