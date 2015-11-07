package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import ar.edu.itba.Magic.Backend.Match;

public class ConfigMatchState extends BasicGameState {
	ExtendedImage chooseDeck;
	ExtendedImage start;
	ExtendedImage back;
	Input input;
	DeckUI p1, p2;
	MatchUI matchUI;
	Match match;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		chooseDeck = new ExtendedImage("res/choosedeck.png");
		start = new ExtendedImage("res/newmatch.png");
		back = new ExtendedImage("res/back.png");
		match = Match.getMatch();
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		input = gc.getInput();
		if(chooseDeck.mouseLClicked(input)) {
			sbg.enterState(4);
		}
		if(start.mouseLClicked(input)) {
			if(match.getPlayer1() == null || match.getPlayer2() == null) {
				
			}
			else {
				sbg.enterState(5);
			}
		}
		if(back.mouseLClicked(input)) {
			//sbg.enterState(0);
			gc.exit();
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}	
	}
	
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		chooseDeck.draw();
		start.draw();
		back.draw();
	}
	
	public int getID() {
	
		return 2;
	}


}
