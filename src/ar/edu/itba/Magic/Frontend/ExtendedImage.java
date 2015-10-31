package ar.edu.itba.Magic.Frontend;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ExtendedImage extends Image {
	private float x, y;
	
	public ExtendedImage(String ref, float x, float y) throws SlickException {
		super(ref);
		this.x = x;
		this.y = y;
	}
	
	public ExtendedImage(String ref) throws SlickException {
		this(ref, 0f, 0f);
	}
	
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
			if(x <= this.x + getWidth() && y <= this.y + getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean mouseClicked(Input input) {
		if(mouseOver(input)) {
			if(input.isMousePressed(TOP_LEFT))
				return true;
		}
		return false;
	}
	
	public boolean mouseOver(Input input) {
		return mouseOver(input.getMouseX(), input.getMouseY());
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
}