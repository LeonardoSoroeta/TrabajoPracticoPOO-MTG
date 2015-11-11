package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Class for the deck menu state 
 */
public class DeckSelectionState extends BasicGameState {
	ExtendedImage backGround;
	ExtendedImage back;
	ExtendedImage createNewDeck;
	ExtendedImage editExistingDeck;
	Input input;

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		backGround = new ExtendedImage("res/magiclogo.png",gc.getWidth()*2/5,gc.getHeight()*1/3);
		createNewDeck = new ExtendedImage("res/newdeck.png",gc.getWidth()*2/9,gc.getHeight()*2/3);
		editExistingDeck = new ExtendedImage("res/edit.png",gc.getWidth()*2/9,gc.getHeight()*3/4);
		back = new ExtendedImage("res/back.png",gc.getWidth()*2/9,gc.getHeight()*5/6);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		input = gc.getInput();
		
		if(back.mouseLClicked(input)) {
			sbg.enterState(0);
		}
		else if(createNewDeck.mouseLClicked(input)) {
			sbg.enterState(3);
		}
		else if(editExistingDeck.mouseLClicked(input)) {
			sbg.enterState(4);
		}
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		backGround.draw();
		createNewDeck.draw();
		editExistingDeck.draw();
		back.draw();
	}
	
	
	
	public int getID() {
		
		return 1;
	}

}