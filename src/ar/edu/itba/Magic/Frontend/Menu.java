package ar.edu.itba.Magic.Frontend;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//slick
public class Menu extends BasicGameState {
	
	public Menu( int state){
		
	}
	
	Image menu;
	int mouseX;
	int mouseY;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		menu = new Image("res/menu.png");
		
	}
	
	

	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		System.out.println("mouseX: " + gc.getInput().getMouseX() + ", mouseY: " + gc.getInput().getMouseY());
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		
		if ( mouseX > 238 && mouseY > 392 && mouseX < 516 && mouseY < 425){
			if ( gc.getInput().isMousePressed(0))
			sbg.enterState(1);
		}
		
		if ( mouseX > 240 && mouseY > 556 && mouseX < 342 && mouseY < 582){
			if (gc.getInput().isMousePressed(0))
			gc.exit();
		}
	}

	
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		menu.draw(0,0,800,600);
		
	}

	
	
	public int getID() {
		
		return 0;
	}

}

