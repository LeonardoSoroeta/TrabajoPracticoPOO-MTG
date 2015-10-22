package ar.edu.itba.Magic.Backend;

public class Ability {
	
	Object source;
	
	public Ability(Object source) {
		this.source = source;
	}

	public void activate() {
		
	}
	
	public Object getSource() {
		return source;
	}
	
	public GameEvent getTriggerEvent() {
		return new GameEvent("hola");
	}
}