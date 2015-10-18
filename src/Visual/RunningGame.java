package Visual;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RunningGame extends StateBasedGame {

	public RunningGame(String Magic) {
		super(Magic);
		
	}

	@Override
	public void initStatesList(GameContainer appgc) throws SlickException {
		this.addState(new Menu());
		this.addState(new DeckSelection());
		
		
	}

	
}
