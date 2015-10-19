package frontend;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StatesGame extends StateBasedGame  
{
	public static int MENU = 0;
	public static int DECKSELECTION = 1;
	
	/* NOT STATIC METHODS */
	
	public StatesGame(String name) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new DeckSelection(DECKSELECTION));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc,this);
		this.getState(DECKSELECTION).init(gc, this);
	}
	
	
	
}