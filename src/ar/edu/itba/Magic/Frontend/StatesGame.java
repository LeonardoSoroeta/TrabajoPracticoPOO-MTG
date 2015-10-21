package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.GameContainer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Frontend.DeckSelection;
import ar.edu.itba.Magic.Frontend.Match;
import ar.edu.itba.Magic.Frontend.Menu;

//slick
public class StatesGame extends StateBasedGame  
{
	public static int MENU = 0;
	public static int DECKSELECTION = 1;
	public static int MATCH = 2;
	
	
	public StatesGame(String name) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new DeckSelection(DECKSELECTION));
		this.addState(new Match(MATCH));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc,this);
		this.getState(DECKSELECTION).init(gc, this);
		this.getState(MATCH).init(gc, this);
	}
	
	
	
}