package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;

public class GameEvent {
	
	private Event event;
	private Object object1;
	private Object object2;		
	
	public GameEvent(Event event) {
		this.event = event;
	}
	
	public GameEvent(Event event, Object object1) {
		this.event = event;
		this.object1 = object1;
	}
	
	public GameEvent(Event event, Object object1, Object object2) {
		this.event = event;
		this.object1 = object1;
		this.object2 = object2;
	}
	
	public Event getDescriptor() {
		return event;
	}
	
	public Object getObject1() {
		return object1;
	}
	
	public Object getObject2() {
		return object2;
	}

}
