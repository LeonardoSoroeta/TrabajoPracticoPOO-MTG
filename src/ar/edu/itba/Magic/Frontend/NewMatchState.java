package ar.edu.itba.Magic.Frontend;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Enums.MatchState;

public class NewMatchState extends BasicGameState {
	
	private Match match;
	private Input input;
	
	private Image backgroundBL;
	private Image backgroundRE;
	private Image manapool;
	private Image backcard;
	private Image button;
	private ManaNumber mananumberPL1;
	private ManaNumber mananumberPL2;
	
	private Image abomination;
	private Image hand;
	
	private Player player1;
	private Player player2;
	
	private DeckList decklistpl1;
	private DeckList decklistpl2;
	
	//private ExtendedImage numerito;

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		backgroundBL = new Image("res/Match/TERR_BLACKpict.bmp"); 
		backgroundRE = new Image("res/Match/TERR_REDpict.bmp"); 
		manapool = new Image("res/Match/manapool2.png");
		backcard = new Image("res/Match/backCard.png");
		button = new Image("res/Match/button.png");
		
		hand = new Image("res/Match/hand.png");
		
		
		mananumberPL1 = new ManaNumber();
		mananumberPL2 = new ManaNumber();
		
		//decklistpl1 = new DeckList(match.getPlayer1().getDeck());
		//decklistpl2 = new DeckList(match.getPlayer2().getDeck());
		
		abomination = new Image("res/tinycards/abomination.jpg");
		
		input = gc.getInput();
		
		
		
		
		
		
		abomination.rotate(90);
		
		//numerito = new ExtendedImage("res/Match/numerito.png");
		match = Match.getMatch();
		
		//match.start();
		
		
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		
		/*
		if ( match.getMatchState().equals(MatchState.REQUESTING_MANA_PAYMENT)){
			
			for ( Color color: Color.values()){
				if( match.getPlayerPlaying() == 1){
				mananumberPL1.getNumber().get(color).get(0).mouseLClicked(input);
				match.returnSelectedTarget(color);
				}
				else{
				mananumberPL2.getNumber().get(color).get(0).mouseLClicked(input);	
				match.returnSelectedTarget(color);
				}
			}
			
			}
		
		
		if ( match.getMatchState().equals(MatchState.REQUESTING_TARGET_SELECTION_BY_ABILITY)){
			
		}
		
		
		if ( match.getMatchState().equals(MatchState.REQUESTING_MAIN_PHASE_ACTIONS)){
			
		}
				
				
				
		if ( match.getMatchState().equals(MatchState.REQUESTING_STACK_ACTIONS)){
			
		}
						
						
		if ( match.getMatchState().equals(MatchState.REQUESTING_ATTACKER_SELECTION)){
			
		}
								
								
		if ( match.getMatchState().equals(MatchState.REQUESTING_BLOCKER_SELECTION)){
			
		}
	
				
				
		if ( match.getMatchState().equals(MatchState.REQUESTING_ATTACKER_TO_BLOCK_SELECTION)){
			
			
		}
		
		if ( match.getMatchState().equals(MatchState.REQUESTING_CARD_TO_DISCARD_SELECTION)){
			
			
		}
		
		*/
		
		
	
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
			
		backgroundBL.draw(0,0, gc.getWidth(), gc.getHeight()/2);
		backgroundRE.draw(0,gc.getHeight()/2, gc.getWidth(), gc.getHeight()/2 );
		manapool.draw(0, 0, gc.getWidth()/4, gc.getHeight());
		backcard.draw(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		button.draw(gc.getWidth()/4, gc.getHeight()/128*63,gc.getWidth()/2, gc.getHeight()/64*3);
		
		
													//get(0) seria un player2.getmanapool().getmana(Color) //// 0 a 9 esta hecho
		mananumberPL2.getNumber().get(Color.BLACK).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*1, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.BLUE).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*4, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.GREEN).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*7, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.RED).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*9, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.WHITE).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*12, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.COLORLESS).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*15, gc.getWidth()/64, gc.getHeight()/64);
		
		
		
		mananumberPL1.getNumber().get(Color.BLACK).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*52, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.BLUE).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*55, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.GREEN).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*57, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.RED).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*60, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.WHITE).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*63, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.COLORLESS).get(0).draw(gc.getWidth()/64*10, gc.getHeight()/64*66, gc.getWidth()/64, gc.getHeight()/64);
		
		
		abomination.setCenterOfRotation((gc.getWidth()/128*7)/2, (gc.getHeight()/128*15)/2);
		hand.draw(gc.getWidth()/128*34, gc.getHeight()/128*72, gc.getWidth()/128*18, gc.getHeight()/128*64);
		abomination.draw(gc.getWidth()/128*35, gc.getHeight()/128*74, gc.getWidth()/128*7, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*35, gc.getHeight()/128*89, gc.getWidth()/128*7, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*35, gc.getHeight()/128*104, gc.getWidth()/128*7, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*35, gc.getHeight()/128*119, gc.getWidth()/128*7, gc.getHeight()/128*15);
		
		abomination.draw(gc.getWidth()/128*52, gc.getHeight()/128*74, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*52, gc.getHeight()/128*89, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*52, gc.getHeight()/128*104, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*52, gc.getHeight()/128*119, gc.getWidth()/128*15, gc.getHeight()/128*15);
		
		abomination.draw(gc.getWidth()/128*69, gc.getHeight()/128*74, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*69, gc.getHeight()/128*89, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*69, gc.getHeight()/128*104, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*69, gc.getHeight()/128*119, gc.getWidth()/128*15, gc.getHeight()/128*15);
		
		abomination.draw(gc.getWidth()/128*86, gc.getHeight()/128*74, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*86, gc.getHeight()/128*89, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*86, gc.getHeight()/128*104, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*86, gc.getHeight()/128*119, gc.getWidth()/128*15, gc.getHeight()/128*15);
		
		abomination.draw(gc.getWidth()/128*103, gc.getHeight()/128*74, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*103, gc.getHeight()/128*89, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*103, gc.getHeight()/128*104, gc.getWidth()/128*15, gc.getHeight()/128*15);
		abomination.draw(gc.getWidth()/128*103, gc.getHeight()/128*119, gc.getWidth()/128*15, gc.getHeight()/128*15);
		
		//numerito.draw(gc.getWidth()/64*1, gc.getHeight()/64*1, gc.getWidth()/64*3, gc.getHeight()/64*3);
		//numerito.draw(gc.getWidth()/64*1, gc.getHeight()/64*62, gc.getWidth()/64*3, gc.getHeight()/64*3);
		
	}


	public int getID() {
		
		return 2;
	}

}
