package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;
import java.util.Collection;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class NewDeckState extends BasicGameState {
	Collection<CardUI> cardsUI;
	DeckUI deckUI;
	int mouseWheel = 0;
	boolean wheelMoved = false;
	
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		deckUI = new DeckUI();
		int cardsAmount = 10;
		cardsUI = new ArrayList<CardUI>();
		Integer cardNum = 1;
		String ref = "res/cards/" + cardNum.toString() + ".jpg";
		for(int i = 0; i < cardsAmount; i++) {
			cardsUI.add(new CardUI(i,new ExtendedImage(ref,gc.getWidth()*5/8,i*50)  ));
			++cardNum;
			ref = "res/cards/" + cardNum.toString() + ".jpg";
		}
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		/*for(CardUI each: cardsUI) {
			if(each.mouseOver(gc.getInput())) {
				each.draw();
				deckUI.add(each);
			}
		}*/
		if(wheelMoved && gc.getInput().getMouseX() >= gc.getWidth()*5/8) {
			for(CardUI each: cardsUI) {				
				each.update(each.getX(),each.getY() + mouseWheel);
			}
			wheelMoved = false;
		}
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}	
	}
	
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2) throws SlickException {
		for(CardUI each: cardsUI) {
			each.draw();
		}
		
		for(CardUI each: cardsUI) {
			if(each.mouseOver(gc.getInput())) {
				each.draw();
			}
		}
		

	}

	public void mouseWheelMoved(int value) {
		mouseWheel = value;
		wheelMoved = true;
	}

	public int getID() {
		return 3;
	}

}
