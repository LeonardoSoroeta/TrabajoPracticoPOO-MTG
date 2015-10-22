package ar.edu.itba.Magic.Backend;

public class GameEvent {
	
	private String description;
	private Object object;
	
	public GameEvent(String description) {
		this.description = description;
	}
	
	public GameEvent(String description, Object object) {
		this.description = description;
		this.object = object;
	}
	
	public Boolean satisfiesEventRequirement(GameEvent requirement) {		
		if(requirement.getDescription().equals("any_event"))
			return true;
		
		return false;		
	}
	
	public String getDescription() {
		return description;
	}

}
