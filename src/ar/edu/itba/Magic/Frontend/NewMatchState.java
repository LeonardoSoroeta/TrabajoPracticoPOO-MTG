package ar.edu.itba.Magic.Frontend;

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
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
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
	private ScrollingTable sthandpl2;
	private ScrollingTable stpermanentpl2;
	
	
	private Deck deckpl1;
	private Deck deckpl2;
	
	
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		backgroundBL = new Image("res/Match/TERR_BLACKpict.bmp"); 
		backgroundRE = new Image("res/Match/TERR_REDpict.bmp"); 
		manapool = new Image("res/Match/manapool.png");
		backcard = new Image("res/Match/backCard.png");
		button = new Image("res/Match/button.png");
		
		
	
		hand = new Image("res/Match/hand.png");
	
		cancelbutton = new ExtendedImage("res/Match/button.png");
		
		
		mananumberPL1 = new ManaNumber();
		mananumberPL2 = new ManaNumber();
		
		
		input = gc.getInput();
		match = Match.getMatch();
		
		// E S T O    E S   D E     D E C K     S E L E C T I O N
		//
		//
		//
		//
		//
		
		deckpl1 = new Deck();
			
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.ACID_RAIN.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.AIR_ELEMENTAL.createCardOfThisType());
			deckpl1.addCard(CardType.BIRD_MAIDEN.createCardOfThisType());
			deckpl1.addCard(CardType.CARNIVOROUS_PLANT.createCardOfThisType());
			deckpl1.addCard(CardType.HURLOON_MINOTAUR.createCardOfThisType());
			deckpl1.addCard(CardType.MOX_JET.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.SWAMP.createCardOfThisType());
			deckpl1.addCard(CardType.AIR_ELEMENTAL.createCardOfThisType());
			deckpl1.addCard(CardType.BIRD_MAIDEN.createCardOfThisType());
			deckpl1.addCard(CardType.MOUNTAINS.createCardOfThisType());
			
		
		player1 = new Player(deckpl1);
		
		for(Card card: deckpl1.getCards()){
			
			card.setController(player1);
			
		}
		
		decklistpl1 = new DeckList(deckpl1, gc.getWidth()/128*7, gc.getHeight()/128*14);
		
		//
		sthandpl1 = new ScrollingTable(500,900);
		sthandpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		stpermanentpl1 = new ScrollingTable( 500, 800);
		stpermanentpl1.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
		
		deckpl2 = new Deck();
		
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.ACID_RAIN.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.AIR_ELEMENTAL.createCardOfThisType());
		deckpl2.addCard(CardType.BIRD_MAIDEN.createCardOfThisType());
		deckpl2.addCard(CardType.CARNIVOROUS_PLANT.createCardOfThisType());
		deckpl2.addCard(CardType.HURLOON_MINOTAUR.createCardOfThisType());
		deckpl2.addCard(CardType.MOX_JET.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.SWAMP.createCardOfThisType());
		deckpl2.addCard(CardType.AIR_ELEMENTAL.createCardOfThisType());
		deckpl2.addCard(CardType.BIRD_MAIDEN.createCardOfThisType());
		deckpl2.addCard(CardType.MOUNTAINS.createCardOfThisType());
		
		player2 = new Player(deckpl2);
		
		
		
		for(Card card: deckpl2.getCards()){
			
				card.setController(player2);
			
			}
		
			decklistpl2 = new DeckList(deckpl2, gc.getWidth()/128*7, gc.getHeight()/128*14);
		
		
			sthandpl2 = new ScrollingTable(500,50);
			sthandpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
			stpermanentpl2 = new ScrollingTable( 500, 200);
			stpermanentpl2.setBigCard(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		
			match.setPlayer1(player1);
			match.setPlayer2(player2);
		///
		////
		///	
		///
		
		//
		//
		//
		//
		//
	}

	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		
		input = gc.getInput();
		
		if (match.getMatchState().equals(MatchState.GAME_OVER))
			match.update();
		
		//mana
		if ( match.getMatchState().equals(MatchState.AWAITING_CASTING_MANA_PAYMENT) || match.getMatchState().equals(MatchState.AWAITING_ABILITY_MANA_PAYMENT)){
			
			// ver si este for va, sino de a uno
			for ( Color color: Color.values()){
				if( match.getPlayerPlaying() == 1){
					if (mananumberPL1.getNumber().get(color).get(0).mouseLClicked(input)){
						match.returnSelectedTarget(color);
						match.update();
					}
				}
				else{
					if (mananumberPL2.getNumber().get(color).get(0).mouseLClicked(input)){	
					match.returnSelectedTarget(color);
					match.update();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input))
				match.cancelManaRequest();
				match.update();
			
			}
		
		//permanent  match.getMatchState().equals(MatchState.AWAITING_ABILITY_TARGET_SELECTION) || match.getMatchState().equals(MatchState.AWAITING_CASTING_TARGET_SELECTION) || match.getMatchState().equals(MatchState.AWAITING_ATTACKER_SELECTION) ||  match.getMatchState().equals(MatchState.AWAITING_ATTACKER_TO_BLOCK_SELECTION)
		if (match.getMatchState().equals(MatchState.AWAITING_ATTACKER_SELECTION)){
			
			if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
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
			
			if (cancelbutton.mouseLClicked(input))
				match.playerDoneClicking();
				match.update();
		}
			
			
		//permanent invertida		
		if ( match.getMatchState().equals(MatchState.AWAITING_BLOCKER_SELECTION)){
			
			if ( match.getPlayerPlaying() == 2){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
						match.returnSelectedTarget(permanent);
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input))
				match.cancelTargetSelection();
		
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
						//System.out.println("CARTA JUGADA" + card.getCardType().getCardName().toString());
						card.playCard();
					}
				}
			}
			
			if ( match.getPlayerPlaying() == 2){
				for( Card card: player2.getHand()){
					
					if (decklistpl2.getTinyCard(card).mouseLClicked(input)){
						//System.out.println("CARTA JUGADA" + card.getCardType().getCardName().toString());
						card.playCard();
					}
				}
			}
			
			if ( match.getPlayerPlaying() == 1){
				for(Permanent permanent: player1.getPermanentsInPlay()){
				
					if( decklistpl1.getTinyCard(permanent).mouseLClicked(input)){
					//	permanent.getAbility().executeOnActivation();
					}
				}
			}
			
			else if ( match.getPlayerPlaying() == 2){
				for(Permanent permanent: player2.getPermanentsInPlay()){
				
					if( decklistpl2.getTinyCard(permanent).mouseLClicked(input)){
					//	permanent.getAbility().executeOnActivation();
					}
				}
			}
			
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
			
		}
	
				
				
		if ( match.getMatchState().equals(MatchState.AWAITING_MANA_BURN_ACKNOWLEDGEMENT)){
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
				
		}
		
		if ( match.getMatchState().equals(MatchState.AWAITING_STACK_ACTIONS)){
			if (cancelbutton.mouseLClicked(input)){
				match.playerDoneClicking();
				match.update();
			}
			
		}
		
	
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
			
		backgroundBL.draw(0,0, gc.getWidth(), gc.getHeight()/2);
		backgroundRE.draw(0,gc.getHeight()/2, gc.getWidth(), gc.getHeight()/2 );
		manapool.draw(0, 0, gc.getWidth()/4, gc.getHeight());
		backcard.draw(gc.getWidth()/64, (gc.getHeight()/4),gc.getWidth()/16*3, (gc.getHeight()/4)*2);
		button.draw(gc.getWidth()/4, gc.getHeight()/128*63,gc.getWidth()/2, gc.getHeight()/64*3);
		g.drawString( match.getMessageToPlayer(), gc.getWidth()/4, gc.getHeight()/128*63);
		cancelbutton.draw(gc.getWidth()/74*60, gc.getHeight()/128*63,gc.getWidth()/7, gc.getHeight()/64*3);
		
													
		mananumberPL2.getNumber().get(Color.BLACK).get(player2.getManaPool().getAvailableManaOfThisColor(Color.BLACK)).draw(gc.getWidth()/64*10, gc.getHeight()/64*1, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.BLUE).get(player2.getManaPool().getAvailableManaOfThisColor(Color.BLUE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*4, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.GREEN).get(player2.getManaPool().getAvailableManaOfThisColor(Color.GREEN)).draw(gc.getWidth()/64*10, gc.getHeight()/64*7, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.RED).get(player2.getManaPool().getAvailableManaOfThisColor(Color.RED)).draw(gc.getWidth()/64*10, gc.getHeight()/64*9, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.WHITE).get(player2.getManaPool().getAvailableManaOfThisColor(Color.WHITE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*12, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL2.getNumber().get(Color.COLORLESS).get(player2.getManaPool().getAvailableManaOfThisColor(Color.BLACK)).draw(gc.getWidth()/64*10, gc.getHeight()/64*15, gc.getWidth()/64, gc.getHeight()/64);
		
		
		
		mananumberPL1.getNumber().get(Color.BLACK).get(player1.getManaPool().getAvailableManaOfThisColor(Color.BLACK)).draw(gc.getWidth()/64*10, gc.getHeight()/64*52, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.BLUE).get(player1.getManaPool().getAvailableManaOfThisColor(Color.BLUE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*55, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.GREEN).get(player1.getManaPool().getAvailableManaOfThisColor(Color.GREEN)).draw(gc.getWidth()/64*10, gc.getHeight()/64*57, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.RED).get(player1.getManaPool().getAvailableManaOfThisColor(Color.RED)).draw(gc.getWidth()/64*10, gc.getHeight()/64*60, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.WHITE).get(player1.getManaPool().getAvailableManaOfThisColor(Color.WHITE)).draw(gc.getWidth()/64*10, gc.getHeight()/64*63, gc.getWidth()/64, gc.getHeight()/64);
		mananumberPL1.getNumber().get(Color.COLORLESS).get(player1.getManaPool().getAvailableManaOfThisColor(Color.COLORLESS)).draw(gc.getWidth()/64*10, gc.getHeight()/64*66, gc.getWidth()/64, gc.getHeight()/64);
		
		
		
		
		sthandpl1.drawCards(player1.getHand(), decklistpl1,gc.getWidth()/128*7, gc.getHeight()/128*14, input );
		stpermanentpl1.drawPermanents(player1.getPermanentsInPlay(), decklistpl1,gc.getWidth()/128*7, gc.getHeight()/128*14, input);
		
		
		sthandpl2.drawCards(player2.getHand(), decklistpl2,gc.getWidth()/128*7, gc.getHeight()/128*14 , input );
		stpermanentpl2.drawPermanents(player2.getPermanentsInPlay(), decklistpl2,gc.getWidth()/128*7, gc.getHeight()/128*14, input);
		
		
		
	}


	public int getID() {
		
		return 2;
	}

}