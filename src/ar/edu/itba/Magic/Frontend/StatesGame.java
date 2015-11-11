package ar.edu.itba.Magic.Frontend;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StatesGame extends StateBasedGame  
{
	
	/* NOT STATIC METHODS */
	
	public StatesGame(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {

		this.addState(new MenuState());
		this.addState(new DeckSelectionState());
		this.addState(new NewDeckState());
		this.addState(new EditDeckState());
		this.addState(new ConfigMatchState());
	}
	
}