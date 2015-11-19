package ar.edu.itba.Magic.Frontend;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.InstantCard;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.SpellMechanics;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

public class NewMatchState extends BasicGameState {
	
	private Match match;   
	private Input input;
	
	private Image backgroundBL;
	private Image backgroundRE;
	private Image manapool;
	private Image backcard;
	private Image button;
	private ExtendedImage cancelbutton;
	private ManaNumber mananumberPL1;
	private ManaNumber mananumberPL2;
	
	private Image hand;
	
	private Player player1;
	private Player player2;
	
	private DeckList decklistpl1;
	private DeckList decklistpl2;
	
	
	private ScrollingTable sthandpl1;
	private ScrollingTable stpermanentpl1;
	private ScrollingTable stattackpl1;
	private ScrollingTable sthandpl2;
	private ScrollingTable stpermanentpl2;
	private ScrollingTable stattackpl2;
	private ScrollingTable ststackpl1;
	private ScrollingTable ststackpl2;
	
	private Deck deckpl1;
	private Deck deckpl2;
	
	
	private ExtendedImage movePermanentpl1;
	private ExtendedImage movePermanentpl2;
	private ExtendedImage HideCardspl1;
	private ExtendedImage HideCardspl2;
	
	
	private Boolean hidecardspl1 = false;
	private Boolean hidecardspl2 = false;
	
	private Boolean setplayers = false; 
	
	private TrueTypeFont live;
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		live = new TrueTypeFont(new Font("Arial", Font.BOLD, 80), false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		backgroundBL = new Image("res/Match/TERR_BLACKpict.bmp"); 
		backgroundRE = new Image("res/Match/TERR_REDpict.bmp"); 
		manapool = new Image("res/Match/manapool2.png");
		backcard = new Image("res/Match/backCard.png");
		button = new Image("res/Match/button.png");
		
		movePermanentpl1 = new ExtendedImage("res/Match/arrow.png");
		movePermanentpl2 = new ExtendedImage("res/Match/arrow.png");
		HideCardspl1 = new ExtendedImage("res/Match/arrow.png");
		HideCardspl2 = new ExtendedImage("res/Match/arrow.png");
	
		hand = new Image("res/Match/hand.png");
	
		cancelbutton = new ExtendedImage("res/Match/button.png");
		
		
		mananumberPL1 = new ManaNumber();
		mananumberPL2 = new ManaNumber();
		
		
		input = gc.getInput();
		match = Match.getMatch();
		
	
		sthandpl1 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*112);
		sthandpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
		stpermanentpl1 = new ScrollingTable( gc.getWidth()/128*35, gc.getHeight()/128*92);
		stpermanentpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
		stattackpl1 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*72);
		stattackpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
		ststackpl1 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*72);
		ststackpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
		
		

	
		
			
			
			sthandpl2 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*6);
			sthandpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
			
			stpermanentpl2 = new ScrollingTable( gc.getWidth()/128*35, gc.getHeight()/128*26);
			stpermanentpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
			
			stattackpl2 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*46);
			stattackpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
			
			
			ststackpl2 = new ScrollingTable(gc.getWidth()/128*35, gc.getHeight()/128*46);
			ststackpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
			
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int ag2)
			throws SlickException {
		
		input = gc.getInput();
		
		if (setplayers.equals(false)){
			player1 = match.getPlayer1();
			deckpl1 = match.getPlayer1().getDeck();
			deckpl2 = match.getPlayer2().getDeck();
			player2 = match.getPlayer2();
			decklistpl2 = new DeckList(deckpl2, gc.getWidth()/128*7, gc.getHeight()/128*14);
			decklistpl1 = new DeckList(deckpl1, gc.getWidth()/128*7, gc.getHeight()/128*14);
			setplayers = true;
			for(Card card: deckpl1.getCards()){
				
				card.setController(player1);
				
			}
			
			for(Card card: deckpl2.getCards()){
			
			card.setController(player2);
		
			}
			match.update();
		}
		
		
		
		if (match.getMatchState().equals(MatchState.GAME_OVER)){
			sbg.enterState(0);
		}
		
		
		
		
		if (match.getMatchState().equals(MatchState.AWAITING_STARTING_PHASE_YES_OR_NO_CONFIRMATION)){
			if(input.isKeyPressed(input.KEY_Y)){
			match.playerSelectedYes();
			match.update();
			}
			if(input.isKeyPressed(input.KEY_N)){
				match.playerSelectedNo();
				match.update();
				}
			
			
		}
			
		
		
		
		
		if(movePermanentpl1.mouseOver(input)){
		if(input.isKeyPressed(input.KEY_LEFT)){
			stpermanentpl1.updateLeft();
		}
		
			if(input.isKeyPressed(input.KEY_RIGHT)){
				stpermanentpl1.updateRight();
			}
			
		}
		
		if( movePermanentpl2.mouseOver(input)){
			if(input.isKeyPressed(input.KEY_LEFT)){
				stpermanentpl2.updateLeft();
			}
			
			if(input.isKeyPressed(input.KEY_RIGHT)){
				stpermanentpl2.updateRight();
				}
				
			}
		
		
		
		
		
		
		
		
		if ( match.getMatchState().equals(MatchState.AWAITING_CASTING_MANA_PAYMENT) || match.getMatchState().equals(MatchState.AWAITING_ABILITY_MANA_PAYMENT)){
			
			
			if ( match.getActivePlayer().equals(player1)){
				for ( Color color: Color.values()){
					if (mananumberPL1.getNumber().get(color).get(0).mouseLClicked(input)){
						match.returnSelectedTarget(color);
						match.update();
					}
				}
			}
			if ( match.getActivePlayer().equals(player2)){
				
				for ( Color color: Color.values()){
					if (mananumberPL2.getNumber().get(color).get(0).mouseLClicked(input)){	
					match.returnSelectedTarget(color);
					match.update();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input)){
				match.cancelManaRequest();
				match.update();
			
			}
		}
		
		
			
		
		
			
		
		if( match.getMatchState().equals(MatchState.AWAITING_ABILITY_TARGET_SELECTION) || match.getMatchState().equals(MatchState.AWAITING_CASTING_TARGET_SELECTION)){
			
					
			for(Permanent permanent: player1.getPermanentsInPlay()){	
				if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
					match.returnSelectedTarget(permanent);
					match.update();
					
					
					}
			}
			
			
			for(Permanent permanent: player2.getPermanentsInPlay()){
			
				if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
					match.returnSelectedTarget(permanent);
					match.update();
					
				}
			}
			
			
			
				for(Object object: match.getGameStack().getPlayer1Spells()){
					
					
					if(object instanceof Permanent ){
						
						if (decklistpl1.getTinyCard((Permanent)object).mouseLClicked(input)){
							match.returnSelectedTarget(object);
							match.update();
						}
					}
					else if(object instanceof SpellMechanics ){
						
						if(decklistpl1.getTinyCard(((SpellMechanics)object).getSourceCard()).mouseLClicked(input)){
							match.returnSelectedTarget(object);
							match.update();
						}
				}
					
				}
						
			
				for(Object object: match.getGameStack().getPlayer2Spells()){
					
					
					if(object instanceof Permanent ){
						
						if (decklistpl2.getTinyCard((Permanent)object).mouseLClicked(input)){
							match.returnSelectedTarget(object);
							match.update();
						}
					}
					else if(object instanceof SpellMechanics ){
						
						if(decklistpl2.getTinyCard(((SpellMechanics)object).getSourceCard()).mouseLClicked(input)){
							match.returnSelectedTarget(object);
							match.update();
						}
				}
					
				}
			
				
			if (cancelbutton.mouseLClicked(input)){
				match.cancelTargetSelection();
				match.update();
			}	
		}
		
		
		
		
		if( match.getMatchState().equals(MatchState.AWAITING_ATTACKER_TO_BLOCK_SELECTION)){
			
			if ( match.getPlayerPlaying() == 1){
				for(Creature creature: match.getCombatPhase().getAttackers()){
				
					if( decklistpl1.getTinyCard(creature).mouseLClicked(input)){
						
						match.returnSelectedTarget(creature);
						match.update();
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 2){
				for(Creature creature: match.getCombatPhase().getAttackers()){
					
					if( decklistpl2.getTinyCard(creature).mouseLClicked(input)){
					
						match.returnSelectedTarget(creature);
						match.update();
					}
				}
			}
		
		
		if (cancelbutton.mouseLClicked(input)){
			match.cancelTargetSelection();
			
			match.update();
			}
			
		}
		
		
		
		
		
		if (match.getMatchState().equals(MatchState.AWAITING_ATTACKER_SELECTION)){
		 
			if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
						match.update();
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 2){
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
						match.update();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input) && match.getMatchState().equals(MatchState.AWAITING_ATTACKER_SELECTION)){
				match.playerDoneClicking();
				match.update();
			}
		}
			
			
		//permanent invertida		
		if ( match.getMatchState().equals(MatchState.AWAITING_BLOCKER_SELECTION)){
			
			
			if ( match.getPlayerPlaying() == 2){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
						match.update();
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
						match.update();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
		}
		
		
		//card				
		if ( match.getMatchState().equals(MatchState.AWAITING_CARD_TO_DISCARD_SELECTION)){
			
			if ( match.getPlayerPlaying() == 1){
				for( Card card: player1.getHand()){
					
					if (decklistpl1.getTinyCard(card).mouseLClicked(input)){
						match.returnSelectedTarget(card);
						match.update();
					}
					
				}
			}
			
			if ( match.getPlayerPlaying() == 2){
				for( Card card: player2.getHand()){
					
					if (decklistpl2.getTinyCard(card).mouseLClicked(input)){
						match.returnSelectedTarget(card);
						match.update();
					}
				}
			}
		}
		
		
		
		
								
								// carta o permanent
		if ( match.getMatchState().equals(MatchState.AWAITING_MAIN_PHASE_ACTIONS)){
			
			if ( match.getPlayerPlaying() == 1){
				for( Card card: player1.getHand()){
					
					if (decklistpl1.getTinyCard(card).mouseLClicked(input)){
						card.playCard();
					}
				}
			}
			
			if ( match.getPlayerPlaying() == 2){
				for( Card card: player2.getHand()){
					
					if (decklistpl2.getTinyCard(card).mouseLClicked(input)){
						card.playCard();
					}
				}
			}
			
			if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						permanent.getAbility().executeOnActivation();
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 2){
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
						permanent.getAbility().executeOnActivation();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
			
		}
		
if ( match.getMatchState().equals(MatchState.AWAITING_STACK_ACTIONS)){
			
			
			if ( match.getActivePlayer().equals(player1)){
				
				
				
				for( Card card: player1.getHand()){
					
					if (decklistpl1.getTinyCard(card).mouseLClicked(input)){
						if(card instanceof InstantCard)
						card.playCard();
						
					}
				}
			}
			
			if ( match.getActivePlayer().equals(player2)){
				for( Card card: player2.getHand()){
					
					if (decklistpl2.getTinyCard(card).mouseLClicked(input)){
						if(card instanceof InstantCard)
						card.playCard();
						
					}
				}
			}
			
			if ( match.getActivePlayer().equals(player1)){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						permanent.getAbility().executeOnActivation();
						
					}
				}
			}
			
			 if ( match.getActivePlayer().equals(player2)) {
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
						permanent.getAbility().executeOnActivation();
						
					}
				}
			}
			
			
			
			
			
			
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
			
		}
		
		if ( HideCardspl1.mouseLClicked(input)){
			if (hidecardspl1.equals(true)){
				hidecardspl1=false;
			}
			
			else if (hidecardspl1.equals(false)){
				hidecardspl1=true;
			}
		}
		
		
		

			if ( HideCardspl2.mouseLClicked(input)){
				if (hidecardspl2.equals(true)){
					hidecardspl2=false;
				}
				
				else if (hidecardspl2.equals(false)){
					hidecardspl2=true;
				}
			}
			
		
		
		
		
		
		
		
	
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
			
		
		backgroundBL.draw(gc.getWidth()/4, 0, gc.getWidth()*3/4, gc.getHeight()/2);
		backgroundRE.draw(gc.getWidth()/4,gc.getHeight()/2, gc.getWidth()*3/4, gc.getHeight()/2 );
		manapool.draw(0, 0, gc.getWidth()/4, gc.getHeight());
		backcard.draw(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		button.draw(gc.getWidth()/4, gc.getHeight()/128*63,gc.getWidth()/2, gc.getHeight()/64*3);
	
		
		g.drawString((match.getActivePlayer().equals(player1)?"Player 1: ":"Player 2: ") +match.getMessageToPlayer(), gc.getWidth()/4, gc.getHeight()/128*64);
		
		
		cancelbutton.draw(gc.getWidth()/74*60, gc.getHeight()/128*63,gc.getWidth()/7, gc.getHeight()/64*3);
		
		g.drawString("Cancel/Done", gc.getWidth()/74*62, gc.getHeight()/128*64);
		
		
		live.drawString(0, 0, player1.getHealth().toString());
		live.drawString(0, gc.getHeight()-100, player2.getHealth().toString());
		
		
		
													
		mananumberPL2.getNumber().get(Color.BLACK).get(player2.getManaPool().getAvailableManaOfThisColor(Color.BLACK)).draw(gc.getWidth()/64*10, gc.getHeight()/64*1, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.BLUE).get(player2.getManaPool().getAvailableManaOfThisColor(Color.BLUE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*4, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.GREEN).get(player2.getManaPool().getAvailableManaOfThisColor(Color.GREEN)).draw(gc.getWidth()/64*10, gc.getHeight()/64*7, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.RED).get(player2.getManaPool().getAvailableManaOfThisColor(Color.RED)).draw(gc.getWidth()/64*10, gc.getHeight()/64*9, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.WHITE).get(player2.getManaPool().getAvailableManaOfThisColor(Color.WHITE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*12, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.COLORLESS).get(player2.getManaPool().getAvailableManaOfThisColor(Color.COLORLESS)).draw(gc.getWidth()/64*10, gc.getHeight()/64*15, gc.getWidth()/64, gc.getHeight()/64);
		
		
		
		mananumberPL1.getNumber().get(Color.BLACK).get(player1.getManaPool().getAvailableManaOfThisColor(Color.BLACK)).draw(gc.getWidth()/64*10, gc.getHeight()/64*52, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.BLUE).get(player1.getManaPool().getAvailableManaOfThisColor(Color.BLUE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*55, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.GREEN).get(player1.getManaPool().getAvailableManaOfThisColor(Color.GREEN)).draw(gc.getWidth()/64*10, gc.getHeight()/64*57, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.RED).get(player1.getManaPool().getAvailableManaOfThisColor(Color.RED)).draw(gc.getWidth()/64*10, gc.getHeight()/64*60, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.WHITE).get(player1.getManaPool().getAvailableManaOfThisColor(Color.WHITE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*63, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.COLORLESS).get(player1.getManaPool().getAvailableManaOfThisColor(Color.COLORLESS)).draw(gc.getWidth()/64*10, gc.getHeight()/64*66, gc.getWidth()/64, gc.getHeight()/64);
		
		
		
		if ( hidecardspl1 == true)
		sthandpl1.drawCards(player1.getHand(), decklistpl1,gc.getWidth()/128*7, gc.getHeight()/128*14, input );
		else
			sthandpl1.hideCards(player1.getHand(), decklistpl1,gc.getWidth()/128*7, gc.getHeight()/128*14, input );
		
		
		stpermanentpl1.drawPermanents(player1.getPermanentsInPlay(), decklistpl1,gc.getWidth()/128*7, gc.getHeight()/128*14, input, g);
		
		   
		if(hidecardspl2 == true)
		sthandpl2.drawCards(player2.getHand(), decklistpl2,gc.getWidth()/128*7, gc.getHeight()/128*14 , input );
		else
			sthandpl2.hideCards(player2.getHand(), decklistpl2,gc.getWidth()/128*7, gc.getHeight()/128*14 , input );
		
		stpermanentpl2.drawPermanents(player2.getPermanentsInPlay(), decklistpl2,gc.getWidth()/128*7, gc.getHeight()/128*14, input, g);		
		
		
		if(match.getPlayerPlaying() == 1){
			stattackpl1.drawCards(match.getCombatPhase().getAttackers(), decklistpl1, gc.getWidth()/128*7, gc.getHeight()/128*14, input);
			stattackpl2.drawCards(match.getCombatPhase().getBlockers(), decklistpl2, (gc.getWidth()/128*7), (gc.getHeight()/128*14), input);
		}
		
		if(match.getPlayerPlaying() == 2){
			stattackpl2.drawCards(match.getCombatPhase().getAttackers(), decklistpl2, (gc.getWidth()/128*7), (gc.getHeight()/128*14), input);
			stattackpl1.drawCards(match.getCombatPhase().getBlockers(), decklistpl1, gc.getWidth()/128*7, gc.getHeight()/128*14, input);
		}
		
		HideCardspl1.draw(gc.getWidth()/128*33, gc.getHeight()/128*112,gc.getWidth()/128*2, gc.getHeight()/128*14);
		movePermanentpl1.draw(gc.getWidth()/128*33,gc.getHeight()/128*92,gc.getWidth()/128*2, gc.getHeight()/128*14);
		
		
		
		HideCardspl2.draw(gc.getWidth()/128*33,gc.getHeight()/128*6,gc.getWidth()/128*2, gc.getHeight()/128*14);
		movePermanentpl2.draw(gc.getWidth()/128*33, gc.getHeight()/128*26,gc.getWidth()/128*2, gc.getHeight()/128*14);
		
	
		ststackpl1.drawObject( match.getGameStack().getPlayer1Spells(), decklistpl1, gc.getWidth()/128*7, gc.getHeight()/128*14, input);
		stattackpl2.drawObject(match.getGameStack().getPlayer2Spells(), decklistpl2, (gc.getWidth()/128*7), (gc.getHeight()/128*14), input);
		
		
		
	}


	public int getID() {
		
		return 5;
		
	}
	
	
	

}
