package backend;

public class Ability {
	
	Permanent source;

	public void activate() {
		
	}
	
	// hay que implementar esto quizas con una interfaz Sourced de modo que se sepa quitar una habilidad
	// del event handler una vez que el permanent que la posee abandone la partida. esto para las que
	// son contenidas por un permanent
	public Permanent getSource() {
		return source;
	}
	
	public Event getTriggerEvent() {
		return new Event("hola");
	}
}
