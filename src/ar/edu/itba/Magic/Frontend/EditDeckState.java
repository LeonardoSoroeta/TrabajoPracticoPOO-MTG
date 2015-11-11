package ar.edu.itba.Magic.Frontend;

import java.awt.Image;
import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Player;

public class EditDeckState extends BasicGameState {
	ExtendedImage deckImg;
	ExtendedImage back;
	ExtendedImage setp1;
	ExtendedImage setp2;
	ExtendedImage edit;
	LinkedList<Deck> decks;
	LinkedList<DeckUI> decksUI;
	Input input;
	int mouseWheel = 0;
	boolean wheelMoved = false;
	boolean askForDeck = false;
	static boolean load = false;
	DeckUI ref = null;
		
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		setp1 = new ExtendedImage("res/setp1.png",gc.getWidth()*1/2,gc.getHeight()*1/2);
		setp2 = new ExtendedImage("res/setp2.png",gc.getWidth()*1/2,gc.getHeight()*1/3);
		edit = new ExtendedImage("res/edit2.png",gc.getWidth()*4/8,gc.getHeight()*1/4);
		back = new ExtendedImage("res/back.png",gc.getWidth()*4/8,gc.getHeight()*1/7);
		decksUI = new LinkedList<DeckUI>();
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		input = gc.getInput();
		
		if(load) {
			System.out.println("assaf");
			decks = Deck.loadDecks();
			if(decks != null) {
				int counter = 0;
				for(Deck each: decks) {
					DeckUI aux = new DeckUI(each);
					aux.generateCardsImg(gc);
					//aux.generateCardsImg(gc);
					// sets the position of the deck, with it's first card
					aux.setFirstCard(counter*gc.getWidth()*1/5,gc.getHeight()*1/4);
					decksUI.add(aux);
					counter++;
				}
			}
			load = false;
		}
		
		if(askForDeck) {
			if(setp1.mouseLClicked(input)) {
				ref.generateDeck();
				Match.getMatch().setPlayer1(new Player(ref.getDeck()));
				askForDeck = false;
			}
			else if(setp2.mouseLClicked(input)) {
				ref.generateDeck();
				Match.getMatch().setPlayer2(new Player(ref.getDeck()));
				askForDeck = false;				
			}
			else if(edit.mouseLClicked(input)) {
				NewDeckState.setEditingDeck(ref);
				sbg.enterState(3);
			}
			else if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				askForDeck = false;
			}
		}
		
		if(back.mouseLClicked(input)) {
			sbg.enterState(1);
		}
		
		if(wheelMoved) {
			for(DeckUI each: decksUI) {				
				each.setFirstCard(each.getX() + mouseWheel,each.getY());
			}
			wheelMoved = false;
		}
		
		for(DeckUI each: decksUI) {
			if(each.mouseRClicked(input)) {
				this.askForDeck = true;
				System.out.println("adsad");
				ref = each;
				return;
			}
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics arg2)
			throws SlickException {
		back.draw();
		int count = 0;
		for(DeckUI each: decksUI) {
			each.draw();
		}
		
		if(askForDeck) {
			setp1.draw();
			setp2.draw();
			edit.draw();
		}
		
	}
	
	public static void load() {
		load = true;
	}
	
	public void mouseWheelMoved(int value) {
		mouseWheel = value;
		wheelMoved = true;
	}
	
	public int getID() {
		
		return 4;
	}

}
