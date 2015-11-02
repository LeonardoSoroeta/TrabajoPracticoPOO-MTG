package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Class for the deck creation state 
 */
public class NewDeckState extends BasicGameState {
	ExtendedImage library;
	ExtendedImage yourDeck;
	List<CardUI> cardsUI;
	DeckUI deckUI;
	int mouseWheel = 0;
	int lastCardIndex = 0;
	boolean wheelMoved = false;
	Input input;
	
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		deckUI = new DeckUI();
		int cardsAmount = 15;
		cardsUI = new ArrayList<CardUI>();
		Integer cardNum = 1;
		String ref = "res/cards/" + cardNum.toString() + ".jpg";
		for(int i = 0; i < cardsAmount; i++) {
			cardsUI.add(new CardUI(i,new ExtendedImage(ref,gc.getWidth()*4/5,i*gc.getHeight()*1/13)  ));
			++cardNum;
			ref = "res/cards/" + cardNum.toString() + ".jpg";
		}
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		input = gc.getInput();
		float cardWidth = cardsUI.get(0).getImg().getWidth();

		if(input.getMouseX() <= cardWidth) {
			if(wheelMoved) {
				for(CardUI each: deckUI.getCards()) {				
					each.update(each.getX(),each.getY() + mouseWheel);
				}
				wheelMoved = false;
			}
			List<CardUI> auxDeck = deckUI.getCards();
			for(int i = auxDeck.size() - 1; i >= 0; i--) {	
				if(auxDeck.get(i).mouseRClicked(input)) {
					for(int j = auxDeck.size() - 1; j > i; j--) {
						auxDeck.get(j).update(auxDeck.get(j).getX(), auxDeck.get(j - 1).getY());
					}
					auxDeck.remove(i);
				}
			}
		}
		
		if(input.getMouseX() >= gc.getWidth()*4/5) {
			if(wheelMoved) {
				for(CardUI each: cardsUI) {				
					each.update(each.getX(),each.getY() + mouseWheel);
				}
				wheelMoved = false;
			}
			for(int i = cardsUI.size() - 1; i >= 0; i--) {				
				if(cardsUI.get(i).mouseLClicked(input)) {
					int repetitions = 0; 
					List<CardUI> auxDeck = deckUI.getCards();
					for(int j = 0; j < auxDeck.size(); j++) {
						if(cardsUI.get(i).equals(auxDeck.get(j))) {
							repetitions++;
						}
					}
					if(repetitions < 4) {
						CardUI auxCard = new CardUI(cardsUI.get(i));
						auxCard.update(0,deckUI.getCards().size()*gc.getHeight()*1/13);
						deckUI.add(auxCard);
					}
					
				}
			}
		}
					
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}	
	}
	
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2) throws SlickException {
		for(CardUI each: cardsUI) {
			each.draw();
		}
		 
		deckUI.draw();
		
		for(CardUI each: cardsUI) {
			if(each.mouseOver(gc.getInput())) {
				each.draw();
			}
		}
					
		for(CardUI each: deckUI.getCards()) {
			if(each.mouseOver(gc.getInput())) {
				each.draw();
			}
		}
		
		
		/*for(CardUI each: cardsUI) {
			each.draw();
		}*/
		
	}

	public void mouseWheelMoved(int value) {
		mouseWheel = value;
		wheelMoved = true;
	}

	public int getID() {
		return 3;
	}

}
