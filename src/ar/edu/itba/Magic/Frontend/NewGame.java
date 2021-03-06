package ar.edu.itba.Magic.Frontend;

import java.util.logging.Level;

import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class NewGame {
	
	public static void main(String[] args)
	{	
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new StatesGame("Magic"));
			appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), true);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(StatesGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
