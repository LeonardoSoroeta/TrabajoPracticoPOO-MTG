package ar.edu.itba.Magic.Frontend;


import org.newdawn.slick.Input;

import ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class CardUI implements Drawable{
	private int cardNumber;
	private ExtendedImage img;
	
	public CardUI (int num, ExtendedImage img) {
		this.cardNumber = num;
		this.img = img;
	}
	
	public void draw() {
		img.draw();
	}
	
	public void update(float x, float y) {
		img.update(x, y);
	}
	
	public float getX() {
		return img.getX();
	}
	
	public float getY() {
		return img.getY();
	}
	
	public boolean mouseOver(Input input) {
		return img.mouseOver(input);
	}
	
	public boolean mouseClicked(Input input) {
		return img.mouseClicked(input);		
	}

	
	
}
