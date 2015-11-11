package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import ar.edu.itba.Magic.Backend.Match;

public class ConfigMatchState extends BasicGameState {
	ExtendedImage backGround;
	ExtendedImage magicLogo;
	ExtendedImage chooseDeck;
	ExtendedImage start;
	ExtendedImage back;
	Input input;
	DeckUI p1, p2;;
	Match match;
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		backGround = new ExtendedImage("res/background.png",0,0);
		magicLogo = new ExtendedImage("res/magic.png",gc.getWidth()*1/4,gc.getHeight()*1/10);
		chooseDeck = new ExtendedImage("res/choosedecks.png",gc.getWidth()*2/9,gc.getHeight()*3/4);
		start = new ExtendedImage("res/snm.png",gc.getWidth()*2/9,gc.getHeight()*2/3);
		back = new ExtendedImage("res/back.png",gc.getWidth()*2/9,gc.getHeight()*5/6);
		match = Match.getMatch();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		input = gc.getInput();
		if(chooseDeck.mouseLClicked(input)) {
			sbg.enterState(4);
		}
		if(start.mouseLClicked(input)) {
			if(match.getPlayer1() == null) {
				
			}
			else if(match.getPlayer2() == null){
				
			}
			else {
				sbg.enterState(5);
			}
		}
		if(back.mouseLClicked(input)) {
			sbg.enterState(0);
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}	
	}
	
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		backGround.drawScaled(gc.getWidth(),gc.getHeight());
		magicLogo.draw(0.5f);
		chooseDeck.draw();
		start.draw();
		back.draw();
	}
	
	public int getID() {
	
		return 2;
	}


}
