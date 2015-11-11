package ar.edu.itba.Magic.Frontend;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Match;

public class NewMatchState extends BasicGameState {
	MatchUI matchUI;

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		matchUI = MatchUI.getMatchUI();
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		matchUI.update(gc.getInput());
		
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		matchUI.draw();		
	}


	public int getID() {
		
		return 5;
	}

}
