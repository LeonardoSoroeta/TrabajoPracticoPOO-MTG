package ar.edu.itba.Magic.Frontend;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

/*
 *  Class for the user interface representation of a deck
 *  saves a reference of the actual deck
 */
public class DeckUI implements Drawable {
	
	private Deck deck;
	private LinkedList<CardUI> deckUI;
	private ExtendedImage deckImg;
	//used for the expanded list of cards images
	float posY;
	float screenPosX;
	float screenPosY;
	
	public DeckUI() {
		deckUI = new LinkedList<CardUI>();
		deck = new Deck();
	}

	public DeckUI(DeckUI dUI) {
		deckUI = (LinkedList<CardUI>) dUI.getCards();
		deck = dUI.getDeck();
	}
	
	
	public DeckUI(Deck refDeck) {
		deckUI = new LinkedList<CardUI>();
		deck = refDeck;
	}

	/*
	 *  from the list of user interface cards, it generates
	 *  the actual representation of the deck
	 *  if its already generated will replace the cards that are diferent from 
	 *  the previous generated deck
	 */
	public void generateDeck() {
		if(deck.getSize() > 0) {
			for(int i = 0; i < deckUI.size(); i++) {
				if(!deckUI.get(i).getCardType().getCardName().equals(deck.getCards().get(i).getCardType().getCardName())) {
					deck.getCards().remove(i);
					deck.getCards().add(i, deckUI.get(i).getCardType().createCardOfThisType());
				}
			}
		}
		else {
			for(CardUI each: deckUI) {
				deck.addCard(each.getCardType().createCardOfThisType());
			}
		}
	}
	
	public void setFirstCard(float x, float y) {
		this.screenPosX = x;
		this.screenPosY = y;
	}
	
	public Deck getDeck() {
		return this.deck;
	}
	/*
	 * Saves the referenced deck of this object
	 */
	public void writeDeck() {
		this.deck.writeDeck();
	}
	
	/*
	 * Loads the images of the referenced deck of this object
	 */
	public void generateCardsImg(GameContainer gc) throws SlickException {
		screenPosY = gc.getHeight()*1/2;
		if(deckUI.size() == 0) {
			if(deck.getCards().size() == 0) {
				this.generateDeck();
			}
			String ref;
			for(Card each: deck.getCards()) {
				ref = "res/cards/" + each.getCardType().getCardName() + ".jpg";
				if(!deckUI.isEmpty()) {
					posY = deckUI.peekLast().getY();
				}
				deckUI.add(new CardUI(each.getCardType(), new ExtendedImage(ref,0,posY + gc.getHeight()*1/13)  ));
			}
		}
		deckImg = new ExtendedImage(deckUI.get(0).getImg());	
	}
	
	/*
	 *  Asks if the left mouse click has pressed the representation of this deck
	 *  in the edit deck state
	 */
	public boolean mouseRClicked(Input input) {
		if(input.getMouseX() > this.getX() && input.getMouseY() > this.getY() && input.getMouseX() < this.getX() + deckUI.get(0).getImg().getWidth() && input.getMouseY() < this.getY() + deckUI.get(0).getImg().getHeight() ) {
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				return true;
		}
		return false;
	}
	
	/*
	 * Draws the first card of this deck for a reference
	 */
	public void draw() {
		deckImg.draw(screenPosX, screenPosY);
	}
	
	public float getX() {
		return screenPosX;
	}
	
	public float getY() {
		return screenPosY;
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
