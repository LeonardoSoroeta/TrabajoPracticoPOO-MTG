package ar.edu.itba.Magic.Frontend;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.CreatureCard;
import ar.edu.itba.Magic.Backend.Enums.CardType;

/**
 * Class for the deck creation state 
 */
public class NewDeckState extends BasicGameState {
	ExtendedImage library;
	ExtendedImage yourDeck;
	ExtendedImage save;
	ExtendedImage back;
	ExtendedImage alert;
	// the total list of cards of the game
	List<CardUI> cardsUI;
	// deck that it's going to be built
	static DeckUI deckUI;
	int mouseWheel = 0;
	int lastCardIndex = 0;
	boolean wheelMoved = false;
	boolean setAlert = false;
	int cardWidth = 312;
	Input input;
	Font awtFont;
	TrueTypeFont ttf;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Font awtFont = new Font("Trajan Pro", Font.BOLD, 24);
		ttf = new TrueTypeFont(awtFont, true);		
		library = new ExtendedImage("res/library.png",gc.getWidth() - cardWidth*3/2,gc.getHeight()*5/6);
		yourDeck = new ExtendedImage("res/yourdeck.png",cardWidth + 10,gc.getHeight()*5/6);
		save = new ExtendedImage("res/save.png",gc.getWidth()*4/8,gc.getHeight()*1/2);
		back = new ExtendedImage("res/back.png",gc.getWidth()*4/8,gc.getHeight()*2/3);
		alert = new ExtendedImage("res/alert.png",gc.getWidth()*1/3,gc.getHeight()*1/2);
		deckUI = new DeckUI();
		cardsUI = new LinkedList<CardUI>();
		int cardNum = 0;
		String ref;
		for(CardType each : CardType.values()) {
			ref = "res/cards/" + each.getCardName() + ".jpg";
			cardsUI.add(new CardUI(each, new ExtendedImage(ref,gc.getWidth() - cardWidth,cardNum*gc.getHeight()*1/13)  ));
			cardNum++;
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		input = gc.getInput();
		if(setAlert) {
			if(alert.mouseLClicked(input)) {
				setAlert = false;
			}
		}
		
		if(back.mouseLClicked(input)) {
			deckUI = new DeckUI();
			sbg.enterState(1);
		}
		
		if(save.mouseLClicked(input)) {
			if(deckUI.size() < 60) {
				setAlert = true;
				return;
			}
			else {
				//saves the deck and goes back to the prev state
				deckUI.generateDeck();
				deckUI.writeDeck();
				deckUI = new DeckUI();
				sbg.enterState(1);
			}
		}
		
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
					CardType aux = cardsUI.get(i).getCardType();
					if(!(aux.equals(CardType.SWAMP) || aux.equals(CardType.FOREST) || aux.equals(CardType.ISLAND) || aux.equals(CardType.MOUNTAINS) || aux.equals(CardType.PLAINS))) {
						for(int j = 0; j < auxDeck.size(); j++) {
							if(cardsUI.get(i).equals(auxDeck.get(j))) {
								repetitions++;
							}
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
		
		ttf.drawString(cardWidth + 10, gc.getHeight()*7/8, deckUI.size() + " Cards", Color.red);
		
		library.draw();
		yourDeck.draw();
		save.draw();
		back.draw();		
		
		if(setAlert) {
			alert.draw();
		}
		
		for(CardUI each: cardsUI) {
			each.draw();
		}
		
		for(CardUI each: deckUI.getCards()) {
			each.draw();
		}
		 
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

	}

	public void mouseWheelMoved(int value) {
		mouseWheel = value;
		wheelMoved = true;
	}

	public static void setEditingDeck(DeckUI d) {
		deckUI = d;
	}
	
	public int getID() {
		return 3;
	}

}