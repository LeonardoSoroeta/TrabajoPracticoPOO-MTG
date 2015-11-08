package ar.edu.itba.Magic.Frontend;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Enums.CardType;

/**
 * Class for the deck creation state 
 */
public class NewDeckState extends BasicGameState {
	ExtendedImage library;
	ExtendedImage yourDeck;
	ExtendedImage save;
	ExtendedImage back;
	List<CardUI> cardsUI;
	DeckUI deckUI;
	int mouseWheel = 0;
	int lastCardIndex = 0;
	boolean wheelMoved = false;
	int cardWidth = 312;
	Input input;
	NewGame g;

	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		library = new ExtendedImage("res/library.png",gc.getWidth()*4/6,gc.getHeight()*5/6);
		yourDeck = new ExtendedImage("res/yourdeck.png",gc.getWidth()*4/6,gc.getHeight()*5/6);
		deckUI = new DeckUI();
		cardsUI = new LinkedList<CardUI>();
		int cardNum = 0;
		String ref;
		for(CardType each : CardType.values()) {
			ref = "res/cards/" + each.getCardName() + ".jpg";
			cardsUI.add(new CardUI(each, new ExtendedImage(ref,gc.getWidth() - cardWidth,cardNum*gc.getHeight()*1/13)  ));
			cardNum++;
		}
		
		Deck deck = new Deck();
		for(CardUI each: cardsUI) {
			deck.addCard(each.getCardType().createCardOfThisType());
		}

	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		input = gc.getInput();
		//when the mouse is on the left side of the screen
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
		
		//when the mouse is on the right side of the screen
		if(input.getMouseX() >= gc.getWidth()*4/5) {
			if(wheelMoved) {
				for(CardUI each: cardsUI) {				
					each.update(each.getX(),each.getY() + mouseWheel);
				}
				wheelMoved = false;
			}
			for(int i = cardsUI.size() - 1; i >= 0; i--) {				
				if(cardsUI.get(i).mouseLClicked(input) && deckUI.size() < 60) {
					int repetitions = 0;
					List<CardUI> auxDeck = deckUI.getCards();
					// see if the card about to add it's not repeated more than 4 times
					for(int j = 0; j < auxDeck.size(); j++) {
						if(cardsUI.get(i).equals(auxDeck.get(j))) {
							repetitions++;
						}
					}
					if(repetitions < 4) {
						CardUI auxCard = new CardUI(cardsUI.get(i));
						float posY;
						if(deckUI.getCards().isEmpty())
							posY = 0;
						else
							posY = ((LinkedList<CardUI>) deckUI.getCards()).peekLast().getY();
						auxCard.update(0,posY + gc.getHeight()*1/13);
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
		library.draw();
		
		yourDeck.draw();
		
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