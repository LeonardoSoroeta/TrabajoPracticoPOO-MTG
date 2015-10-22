package ar.edu.itba.Magic.Backend;

public abstract class Ability {
	
	private Object source;
	
	public Ability(Object source) {
		this.source = source;
	}
	
	public Object getSource() {
		return source;
	}

}