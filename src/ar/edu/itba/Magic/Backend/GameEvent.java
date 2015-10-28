package ar.edu.itba.Magic.Backend;

public class GameEvent {
	
	private String descriptor;
	private Object object1;
	private Object object2;		
	
	public GameEvent(String description) {
		this.descriptor = description;
	}
	
	public GameEvent(String description, Object object1) {
		this.descriptor = description;
		this.object1 = object1;
	}
	
	public GameEvent(String description, Object object1, Object object2) {
		this.descriptor = description;
		this.object1 = object1;
		this.object2 = object2;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
	
	public Object getObject1() {
		return object1;
	}
	
	public Object getObject2() {
		return object2;
	}

}