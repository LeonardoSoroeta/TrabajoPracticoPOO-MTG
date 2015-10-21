package ar.edu.itba.Magic.Frontend;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.CreatureCardsContainer;
import ar.edu.itba.Magic.Backend.Deck;




public class DeckSelection extends BasicGameState {
	
	public DeckSelection(int state){
		
	}
	
	Deck deckplayer1;
	Deck deckplayer2;
	Image backCard;
	Image background;
	int quantityOfCreatures;
	Integer pageaux;
	int page;
	List<Image> creatures;
	List<Deck> player;
	CreatureCardsContainer ccc;
	int mouseX;
	int mouseY;
	int playerselecting;
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		deckplayer1 = new Deck();
		deckplayer2 = new Deck();
		quantityOfCreatures = 48;
		background = new Image("res/background.png");
		backCard = new Image("res/backCard.png");
		page = 0;
		ccc = new CreatureCardsContainer();
		creatures = new ArrayList();
		player = new ArrayList();
		player.add(deckplayer1);
		player.add(deckplayer2);
		playerselecting = 0;
		
		for(int i=0; i<quantityOfCreatures; i++){
			Integer number = new Integer(i);
			String creature = number.toString();
			creatures.add(new Image("res/creatures/"+ creature + ".jpg"));
			
		}
		
		
		
		
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0,0,800,600);
		
		//16 cartas entran
		
		creatures.get(pageaux + 0).draw(0,400,100,200);
		creatures.get(pageaux + 1).draw(0,200,100,200);
		creatures.get(pageaux + 2).draw(100,400,100,200);
		creatures.get(pageaux + 3).draw(100,200,100,200);
		creatures.get(pageaux + 4).draw(200,400,100,200);
		creatures.get(pageaux + 5).draw(200,200,100,200);
		creatures.get(pageaux + 6).draw(300,400,100,200);
		creatures.get(pageaux + 7).draw(300,200,100,200);
		creatures.get(pageaux + 8).draw(400,400,100,200);
		creatures.get(pageaux + 9).draw(400,200,100,200);
		creatures.get(pageaux + 10).draw(500,400,100,200);
		creatures.get(pageaux + 11).draw(500,200,100,200);
		creatures.get(pageaux + 12).draw(600,400,100,200);
		creatures.get(pageaux + 13).draw(600,200,100,200);
		creatures.get(pageaux + 14).draw(700,400,100,200);
		creatures.get(pageaux + 15).draw(700,200,100,200);
		
		//muestro las cartas
		
		g.drawString("PLAYER 1 SELECT YOUR DECK     CREATUES         PAGE: " + page, 50, 50);

		g.drawString(" DECK : " + deckplayer1.getSize()+"/60",50,70);
		
	}
	
	
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		
		pageaux = 16 * page;
		
		if( gc.getInput().isKeyPressed(Input.KEY_RIGHT)){
			if( page <  2)
				page +=1;
				
		}
			
			
		if( gc.getInput().isKeyPressed(Input.KEY_LEFT))	{
			if(page > 0)
				page -= 1;
		}
		
		addCartToDeck( 0, 400, 100, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 0));
		addCartToDeck( 0, 200, 100, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 1));
		addCartToDeck( 100, 400, 200, 600, player.get(playerselecting),  gc, ccc.getCreatureCardsContainer().get(pageaux + 2));
		addCartToDeck( 100, 200, 200, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 3));
		addCartToDeck( 200, 400, 300, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 4));
		addCartToDeck( 200, 200, 300, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 5));
		addCartToDeck( 300, 400, 400, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 6));
		addCartToDeck( 300, 200, 400, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 7));
		addCartToDeck( 400, 400, 500, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 8));
		addCartToDeck( 400, 200, 500, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 9));
		addCartToDeck( 500, 400, 600, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 10));
		addCartToDeck( 500, 200, 600, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 11));
		addCartToDeck( 600, 400, 700, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 12));
		addCartToDeck( 600, 200, 700, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 13));
		addCartToDeck( 700, 400, 800, 600, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 14));
		addCartToDeck( 700, 200, 800, 400, player.get(playerselecting), gc, ccc.getCreatureCardsContainer().get(pageaux + 15));
		
		
	}

	
	public void addCartToDeck(int x1, int y1, int x2, int y2, Deck deck, GameContainer gc, Card card){
	
	if ( mouseX > x1 && mouseY > y1 && mouseX < x2 && mouseY < y2){
		if ( gc.getInput().isMousePressed(0))
		deck.addCard(card);
		System.out.println("mousex: " + mouseX + "mouseY: " + mouseY);
	}
	}

	
	
	public int getID() {
		
		return 1;
	}

}