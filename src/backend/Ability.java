package backend;

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
	
	public Event getTriggerEvent() {
		return new Event("hola");
	}
}