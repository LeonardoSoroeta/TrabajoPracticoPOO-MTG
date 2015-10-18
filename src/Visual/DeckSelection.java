package Visual;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import Magic.Deck;

public class DeckSelection extends BasicGameState {
	
	Deck deckplayer1;
	Deck deckplayer2;
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		deckplayer1 = new Deck();
		deckplayer2 = new Deck();
		
		
		
		
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		
		
	}
	
	
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		
		
		
	}

	


	
	
	public int getID() {
		
		return 1;
	}

}