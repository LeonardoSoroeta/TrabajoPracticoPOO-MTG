package backend;

import java.util.*;

import backend.BackEnd.Effects.EventEffect;

/**
 * Created by Martin on 18/10/2015.
 */
public class EventHandler {
    /**
     * This class is implemented based in Singletone Pattern because it will be needed only one instance
     * each game.
     */

    private static EventHandler instance = new EventHandler();
    
    Map<Ability, Event> eventRelatedAbilities = new HashMap<Ability, Event>();
	Map<Ability, Event> abilityRemover = new HashMap<Ability, Event>();
	Map<Effect, Event> effectRemover = new HashMap<Effect, Event>();

    private EventHandler(){
        events = new LinkedList<LastingEffect>();
    }

    public static EventHandler getEventHandler(){
        return instance;
    }

    public void signalEvent(Event event) {		
		/* pasa el evento por los 3 mapas. en el primero se ejecutaria por ejemplo Ability.activateOnEvent()
		 * en los otros dos se ejecutaria Effect.remove() y Ability.remove()
		 */	
	}
	
	public void newAbility(Ability ability) {
		// mapear ability a su trigger
		// mapear ability a la remocion de su fuente
	}
	
	
	public void newEffect(Effect effect) {
		// mapear effect a su evento de finalizacion
	}
	
	/*
	 * ejemplo: un encantamiento tiene "todo Land entra en juego girado"	(triggered ability)
	 * 		-> se mapea la triggered abilty con el evento new Event("into_play", Permanent, Player)
	 * 		-> se mapea la triggered ability con el evento new Event("removed_from_play", Permanent)
	 * 		-> cuando ocurre el evento 1 se gatilla la ability que se fija en el permanent.
	 * 		   y si es un Land lo gira
	 * 		-> cuando ocurre el evento 2 se quita la ability de los 2 mapas
	 *
	 *
	 * ejemplo: "toda criatura negra tuya gana +1/+1" (carta Bad Moon)	(static ability)
	 * 		-> se mapea la static ability con el evento new Event("into_play", Permanent, Player)
	 * 		-> se mapea la static ability con el evento new Event("removed_from_play", Permanent)
	 * 		-> cada vez que entra un permanent se fija si es una criatura negra tuya
	 * 		-> si es se le aplica el +1/+1
	 * 		-> cuando ocurre abandona el juego la fuente de la habilidad se quita el +1/+1 de todas las criaturas
	 * 		   y luego se quita la ability de los 2 mapas
	 * 		-> (o quizas la ability lo podria hacer por medio de un effect para cada criatura en vez de hacerlo
	 * 		   directamente)
	 * 		-> (tambien habia que aplicarle a todas las que ya existian cuando entro en juego el encantamiento. 
	 * 		   se puede hacer o antes de mandar la ability al event handler o despues)
	 * 
	 * 
	 * ejemplo: se castea un sorcery que dice "criatura X gana "volar" hasta fin de turno  (no hay ability)
	 * 		-> el sorcery al resolver aplica el efecto a la criatura y luego le manda al event handler
	 * 		   newEffect(Effect, new Event("fin de turno", este jugador) o quizas simplemente ("fin de turno")
	 * 		-> cuando llega fin de turno el event handler hace effect.remove();
	 * 		-> el metodo del effect effect.apply(Target target) ejecutaria target.addAttribute("flying");
	 * 		-> el metodo del effect effect.remove() ejectuaria target.removeAttribute("flying");
	 * 
	 * 
	 * ejemplo: "Nigthmare's power and thoughness both equal the number of swamps its controller 
	 * 			 has in play" (carta Nightmare) 
	 * 		-> habria q mapear una o dos habilidades a dos eventos distintos (entra un swamp y sale un swamp)
	 * 		-> o se podria tener un event que satisface todos los eventos. ponele Event("all_events"); 
	 * 		   entonces la habilidad se estaria activando cada vez que sucede cualquier cosa
	 * 		   y cada vez que suecede cualquier cosa cuenta los numeros de swamps y lo actualiza. es medio feo 
	 * 		   pero funcionaria, como un update constante. se puede hacer esto para cualquier cosa dificil
	 * 		   de mapear a un evento en particular o que tiene que estar actualizandose todo el tiempo.
	 */
}