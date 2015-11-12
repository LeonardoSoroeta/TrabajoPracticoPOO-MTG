package ar.edu.itba.Magic.Frontend;

import java.util.List;

import org.newdawn.slick.Input;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

public class ScrollingTable {
	
	float x;
	float y;
	float w;
	float h;
	
	float xb = 0;
	float yb = 0;
	float wb = 0;
	float hb = 0;
	
	
	
	public ScrollingTable(float x,float y){
		
		this.x = x;
		this.y = y;
	}
	
	
	public void drawPermanents( List<Permanent> permanents, DeckList dl, float w, float h, Input input){
	
		for ( Permanent permanent: permanents){
			
			dl.getTinyCard(permanent).draw(x+(permanents.indexOf(permanent)*w), y, w, h);
			
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
	
	public void updateLeft(){
		this.x +=1;
	}
	
	public void updateRight(){
		
		this.x-=1;
		
	}
	
	
	public void setBigCard(float x, float y, float w, float h){
		this.xb = x;
		this.yb = y;
		this.wb = w;
		this.hb = h;
	}
	

}