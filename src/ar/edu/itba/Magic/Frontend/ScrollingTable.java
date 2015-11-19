package ar.edu.itba.Magic.Frontend;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import ar.edu.itba.Magic.Backend.Mechanics.SpellMechanics;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

public class ScrollingTable {
	
	float x;
	float y;
	float w;
	float h;
	
	float xStart;
	
	float xb = 0;
	float yb = 0;
	float wb = 0;
	float hb = 0;
	private ExtendedImage backcard;
	
	
	
	public ScrollingTable(float x,float y) throws SlickException{
		this.backcard= new ExtendedImage("res/Match/backCard.png");
		this.x = x;
		this.y = y;
		this.xStart = x;
	}
	
	
	
	public void drawPermanents( List<Permanent> permanents, DeckList dl, float w, float h, Input input, Graphics g){
		
		this.w = w;
		
		for ( Permanent permanent: permanents){
			
			
			if((x+(permanents.indexOf(permanent)*w)) >= xStart){
			
			dl.getTinyCard(permanent).draw(x+(permanents.indexOf(permanent)*w), y, w, h);
			
			
			g.drawString(permanent.isTapped()?"    tapped":"",x+(permanents.indexOf(permanent)*w), y);
			
			if( permanent instanceof Creature){
				
				
				
				g.drawString(((Creature)permanent).getAttack().toString()+ "/" + ((Creature)permanent).getDefense().toString() , x+(permanents.indexOf(permanent)*w), y);
			}
			
			
			}
			
			if( dl.getTinyCard(permanent).mouseOver(input))
				dl.getBigCard(permanent).draw(xb, yb, wb, hb);
		}
		
		
	}
	
	
	public void drawCards( List<Card> cards, DeckList dl, float w, float h, Input input){
		
		for( Card card: cards){
			
			dl.getTinyCard(card).draw(x+(cards.indexOf(card)*w), y, w, h);
			
			if( dl.getTinyCard(card).mouseOver(input))
			dl.getBigCard(card).draw(xb, yb, wb, hb);
		}
	}
	
	
	public void hideCards(List<Card> cards, DeckList dl, float w, float h, Input input){
for( Card card: cards){
			
			backcard.draw(x+(cards.indexOf(card)*w), y, w, h);
			
			
		}
		
	}
		
		
	
	
	public void updateLeft(){
		this.x -= this.w;
	}
	
	public void updateRight(){
		
		this.x+=this.w;
		
	}
	
	
	public void setBigCard(float x, float y, float w, float h){
		this.xb = x;
		this.yb = y;
		this.wb = w;
		this.hb = h;
	}



	public void drawCards(List<Creature> creatures, DeckList dl, int w, int h, Input input){
	
		for ( Creature creature: creatures){
			
			dl.getTinyCard((Permanent)creature).draw(x+(creatures.indexOf(creature)*w), y, w, h);
			
			if( dl.getTinyCard(creature).mouseOver(input)){
				dl.getBigCard(creature).draw(xb, yb, wb, hb);
			}
		}
		
	}
	
	
	public void drawObject(List<Spell> objects, DeckList dl, float w, float h, Input input){
		for ( Spell object: objects){
			
			if(object instanceof Permanent ){
				
				
			dl.getTinyCard((Permanent)object).draw(x+(objects.indexOf(objects)*w), y, w, h);
			
			}
		
		else if(object instanceof SpellMechanics ){
			dl.getTinyCard(((SpellMechanics)object).getSourceCard()).draw(x+(objects.indexOf(objects)*w), y, w, h);
		
			}
			
				
			
			
			
			
		}
		
		
	}	
			
}