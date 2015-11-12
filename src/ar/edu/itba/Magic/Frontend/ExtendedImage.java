package ar.edu.itba.Magic.Frontend;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

/*
 *  class extended from Image (Slick2d) with added behavior
 *  like input interaction
 */
public class ExtendedImage extends Image implements Drawable {
	private float x, y, w, h;
	
	public ExtendedImage(Image other) {
		super(other);
	}
	
	public ExtendedImage(String ref, float x, float y) throws SlickException {
		super(ref);
		this.x = x;
		this.y = y;
		this.w = this.getWidth();
		this.h = this.getHeight();
	}
	
	public ExtendedImage(String ref) throws SlickException {
		this(ref, 0f, 0f);
	}
	
	/*
	 * changes the position of the image on the screen
	 */
	public void update(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		super.draw(this.x, this.y);
	}
	
	public void draw(float x, float y) {
		super.draw(x, y);
	}

	public void draw(float scale) {
		super.draw(this.x, this.y, scale);
	}
	
	public void drawScaled(float w, float h) {
		super.draw(this.x, this.y, w, h);
	}
	
	public void drawScaled(float x, float y, float w, float h) {
		
		super.draw(x, y, w, h);
	}
	
	public boolean mouseOver(int x, int y) {
		if(x >= this.x && y >= this.y) {
			if(x <= this.x + this.getWidth() && y <= this.y + this.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(float x, float y, float w, float h){
		super.draw(x, y, w, h);
		update(x,y);
		this.setWeight(w);
		this.setHeight(h);
		
	}
	
	public boolean mouseOver(float x, float y,float w,float h) {
		if(x >= this.x && y >= this.y) {
			if(x <= this.x + w && y <= this.y + h) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * See if the mouse click is pressed over the image 
	 */
	public boolean mouseLClicked(Input input) {
		if(mouseOver(input)) {
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				return true;
		}
		return false;
	}

	
	public boolean mouseRClicked(Input input) {
		if(mouseOver(input)) {
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				return true;
		}
		return false;
	}
	
	/**
	 * See if the mouse is over the image 
	 */
	public boolean mouseOver(Input input) {
		return mouseOver(input.getMouseX(), input.getMouseY(), w, h);
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	
	
	
	public void setWeight(float w){
		this.w = w;
	}
	
	public void setHeight(float h){
		this.h = h;
	}
	
}