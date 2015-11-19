package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.Input;

import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;


/**
 * CardsUI represents the user interface of the Card object, 
 * containing its card type and card image
 */
public class CardUI implements Drawable{
	private CardType cardType;
	private ExtendedImage img;
	private ExtendedImage tinyImg;
	private String name;
	
	
	public CardUI (CardType cardType, ExtendedImage img) {
		this.cardType = cardType;
		this.img = img;
	}
	
	public CardUI (CardUI cardUI) {
		this.cardType = cardUI.getCardType();
		this.img = new ExtendedImage(cardUI.getImg());
	}
	
	public void draw() {
		img.draw();
	}
	
	public void draw(float x, float y) {
		img.draw(x, y);
	}
	
	/**
	 * Updates the position of the image
	 */
	public void update(float x, float y) {
		img.update(x, y);
	}
	
	public float getX() {
		return img.getX();
	}
	
	public float getY() {
		return img.getY();
	}
	
	/**
	 * See if the mouse is over the card image 
	 */
	public boolean mouseOver(Input input) {
		return img.mouseOver(input);
	}
	
	/**
	 * See if the mouse left click is pressed over the card image 
	 */
	public boolean mouseLClicked(Input input) {
		return img.mouseLClicked(input);		
	}
	
	/**
	 * See if the mouse right click is pressed over the card image 
	 */
	public boolean mouseRClicked(Input input) {
		return img.mouseRClicked(input);		
	}
	
	
	public CardType getCardType() {
		return cardType;
	}
	
	public ExtendedImage getImg() {
		return img;
	}
	
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		
		CardUI aux = (CardUI)obj;
		if(this.getCardType() != aux.getCardType())
			return false;
		
		return true;
	}

	
	
}
