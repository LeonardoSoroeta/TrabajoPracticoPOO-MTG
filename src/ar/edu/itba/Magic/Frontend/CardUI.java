package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.Input;

import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class CardUI implements Drawable{
	private int cardNumber;
	private ExtendedImage img;
	
	public CardUI (int num, ExtendedImage img) {
		this.cardNumber = num;
		this.img = img;
	}
	
	public CardUI (CardUI cardUI) {
		this.cardNumber = cardUI.getNum();
		this.img = new ExtendedImage(cardUI.getImg());
	}
	
	public void draw() {
		img.draw();
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
	
	
	public int getNum() {
		return cardNumber;
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
		if(this.getNum() != aux.getNum())
			return false;
		
		return true;
	}

	
	
}
