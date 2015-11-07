package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MenuState extends BasicGameState {
	ExtendedImage backGround;
	ExtendedImage magicLogo;
	ExtendedImage startNewMatch;
	ExtendedImage deckSelection;
	ExtendedImage exit;
	Input input;
	
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		backGround = new ExtendedImage("res/background.png",0,0);
		magicLogo = new ExtendedImage("res/magic.png",gc.getWidth()*1/4,gc.getHeight()*1/10);
		startNewMatch = new ExtendedImage("res/snm.png",gc.getWidth()*2/9,gc.getHeight()*2/3);
		deckSelection = new ExtendedImage("res/ds.png",gc.getWidth()*2/9,gc.getHeight()*3/4);
		exit = new ExtendedImage("res/exit.png",gc.getWidth()*2/9,gc.getHeight()*5/6);
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		input = gc.getInput();
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		if(startNewMatch.mouseLClicked(input))
			sbg.enterState(2);
		else if(deckSelection.mouseLClicked(input))
			sbg.enterState(1);
		else if(exit.mouseLClicked(input))
			gc.exit();
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}		
	}
	
	
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)	throws SlickException {
		backGround.drawScaled(gc.getWidth(),gc.getHeight());
		magicLogo.draw(0.5f);
		startNewMatch.draw();
		deckSelection.draw();
		exit.draw();

	}
	
	
	public int getID() {
		
		return 0;
	}

}
