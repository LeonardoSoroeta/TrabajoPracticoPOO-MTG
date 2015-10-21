package frontend;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

@Deprecated
public class Menu extends BasicGameState {
	
	public Menu( int state){
		
	}

	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		
	}
	
	

	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		sbg.enterState(1);
		
	}

	
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		
	}

	
	
	public int getID() {
		
		return 0;
	}

}
