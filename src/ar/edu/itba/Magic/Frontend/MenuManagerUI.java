package ar.edu.itba.Magic.Frontend;

import ar.edu.itba.Magic.Backend.Assets;
import ar.edu.itba.Magic.Backend.Interfaces.Constants;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class manages the user interface of the menu
 *
 */

public class MenuManagerUI implements Drawable{
	private static MenuManagerUI self = null;

	
	private MenuManagerUI() {
	}
	
	public static MenuManagerUI getInstance() {
		if(self == null){
			self = new MenuManagerUI();
		}
		return self;
	}

	@Override
	public void draw(SpriteBatch batch) {
		drawMenu(batch);
	}
	

	
	/**
	 * This method draws the menu, depending on the state of the menu you are in, it is going to print different options.
	 * @param batch
	 */
	private void drawMenu(SpriteBatch batch){
			//Assets.TITLE_FONT.draw(batch, "Magic", Constants.VIRTUAL_WIDTH*2/8, Constants.VIRTUAL_HEIGHT*7/8);
			Assets.FONT.draw(batch, "New Game", Constants.VIRTUAL_WIDTH/8, Constants.VIRTUAL_HEIGHT*5/8);
			Assets.FONT.draw(batch, "Settings", Constants.VIRTUAL_WIDTH/8, Constants.VIRTUAL_HEIGHT*1/2);
			Assets.FONT.draw(batch, "Deck Builder", Constants.VIRTUAL_WIDTH/8, Constants.VIRTUAL_HEIGHT*3/8);
	}	
}
