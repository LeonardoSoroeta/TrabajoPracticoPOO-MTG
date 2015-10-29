package ar.edu.itba.Magic.Backend;

public abstract class Ability {

	private Object source;
	
	public boolean satisfyCastingRequirements() {
		return true;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}
	
	public Object getSource() {
		return source;
	}

}