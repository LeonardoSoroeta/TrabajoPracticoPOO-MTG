package frontend;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import backend.Deck;

public class DeckSelection extends BasicGameState {
	
	public DeckSelection(int state){
		
	}
	
	Deck deckplayer1;
	Deck deckplayer2;
	Image backCard;
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		deckplayer1 = new Deck();
		deckplayer2 = new Deck();
		backCard = new Image("res/backCard.png");
		
		
		
		
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		
		backCard.draw(0,400,100,200);
		backCard.draw(0,200,100,200);
		backCard.draw(100,400,100,200);
		backCard.draw(100,200,100,200);
		backCard.draw(200,400,100,200);
		backCard.draw(200,200,100,200);
		backCard.draw(300,400,100,200);
		backCard.draw(300,200,100,200);
		backCard.draw(400,400,100,200);
		backCard.draw(400,200,100,200);
		backCard.draw(500,400,100,200);
		backCard.draw(500,200,100,200);
		backCard.draw(600,400,100,200);
		backCard.draw(600,200,100,200);
		backCard.draw(700,400,100,200);
		backCard.draw(700,200,100,200);
		
		//muestro las cartas
		
		
	}
	
	
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		
		
	}

	


	
	
	public int getID() {
		
		return 1;
	}

}