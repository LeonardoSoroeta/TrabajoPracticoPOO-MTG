package ar.edu.itba.Magic.Backend;

public class Event {
	
	private String description;
	//private Object target;
	//private Object source;
	
	public Event(String description) {
		this.description = description;
	}
	
	public Event(String description, Object object) {
		
	}
	
	public Boolean satisfies(Event event) {		
		if(event.getDescription().equals("all_events"))
			return true;
		
		return false;		
	}
	
	public String getDescription() {
		return description;
	}

}
