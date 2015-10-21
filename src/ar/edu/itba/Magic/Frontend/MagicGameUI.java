package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;

import ar.edu.itba.Magic.Backend.MagicGame;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MagicGameUI implements Drawable{
	private static MagicGameUI self = null;
//	private MatchUI matchUI;
	private boolean lastState = true;

	
	public static MagicGameUI getInstance(){
		if(self == null){
			self = new MagicGameUI();
		}
		return self;
	}

	public void newGame(){
		//match = new MatchUI(player1, player2);
	}

	
	public void draw(SpriteBatch batch){
		lastState = MagicGame.getInstance().isInMenu();
		if(lastState){
			MenuManagerUI.getInstance().draw(batch);
		}else{
			//MatchUI.draw(batch);
		}
	}
	
}
