package ar.edu.itba.Magic.Backend;

public class Event {
	
	private String description;
	//private Object target;
	//private Object source;
	//private Integer turn;
	
	/* hay que tener otros, pero este es un tipo de constructor, ejemplo:
	 * 		new Event("upkeep_phase");
	 * pero tambien habria que tener Events por ejemplo del estilo:
	 * 		new Event("card_tapped", laCarta, porTalJugador);
	 * y luego se lo manda al event handler para que se fije si tiene que pasar algo
	 */
	public Event(String description) {
		this.description = description;
	}
	
	public Event(String description, Permanent permanent) {
		
	}
	
	/* seria como un equals para saber si un evento satisface a otro. por ejemplo todo evento deberia 
	 * satisfacer el evento generico new Event("all_events") que gatilla una habilidad cuando
	 * sucede cualquier evento. o quizas el evento new Event("permanent_into_play", byPlayer);
	 * deberia satisfacer el evento mas general new Event("permanent_into_play") que necesita gatillar
	 * una habilidad cuando algun un permanent entra en juego sin importar el jugador.
	 */
	public Boolean satisfies(Event event) {
		
		if(event.getDescription().equals("all_events"))
			return true;
		
		return false;
		
	}
	
	public String getDescription() {
		return description;
	}

}
