package ar.edu.itba.Magic.Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MagicGame {
	private static MagicGame self = null;
	private Match match;
	private boolean isMenu = true;
	private boolean laststate = true;

	public static MagicGame getInstance() {
		if(self == null){
			self = new MagicGame();
		}
		return self;
	}
	
	public void newGame() {
		//match = new Match(player1, player2);
		isMenu = false;
	}
	
	/**
	 * This method calls the other classes so that they are updated
	 * @param deltaTime 
	 */
	public void update(float deltaTime) {
		//changeState();
		if(isMenu) {
			MenuManager.getInstance().update(deltaTime);
		}
		else {/*
			if(!match.matchEnded()) {//hay que preguntar si termino la partida (agregar metodo)
				match.update(deltaTime);	
			}
			if(match.isOver()) {
				isMenu = true;
				MenuManager.getInstance().reset();
			}*/
		}
		
		
	}

	public Match getMatch(){
		return match; 
	}
	public boolean isInMenu(){
		return isMenu;
	}


}
