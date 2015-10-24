package ar.edu.itba.Magic.Backend;
import java.util.*;

/**
 * Created by Martin on 18/10/2015.
 */
public class GameEventHandler {
    /**
     * This class is implemented based in Singletone Pattern because it will be needed only one instance
     * each game.
     */

    private static GameEventHandler instance = new GameEventHandler();
    
    /* habilidades y los eventos que las ejecutan */
    private Map<ExecutesOnEvent, String> abilities = new HashMap<ExecutesOnEvent, String>();
	
	/* efectos y los eventos donde se acaban */
	private Map<LastingEffect, String> effects = new HashMap<LastingEffect, String>();

    private GameEventHandler(){
       
    }

    public static GameEventHandler getGameEventHandler() {
        return instance;
    }

    /* pasa el evento por los 4 mapas */
    public void signalGameEvent(GameEvent gameEvent) {		
		for(Map.Entry<ExecutesOnEvent, String> entry : abilities.entrySet())
			if(gameEvent.matchesDescriptor(entry.getValue())) {
				entry.getKey().executeOnEvent(gameEvent);
			}
		
		for(Map.Entry<LastingEffect, String> entry : effects.entrySet())
			if(gameEvent.matchesDescriptor(entry.getValue())) {
				entry.getKey().undoEffect();
				effects.remove(entry.getKey());
			}
		
	}
	
	public void newAbility(ExecutesOnEvent ability) {		
		/* se mapea la ability a su trigger */
		abilities.put(ability, ability.getEventDescriptor());
	}
		
	public void newLastingEffect(LastingEffect effect) {		
		/* se mapea el effect a su evento finalizador */
		effects.put(effect, effect.getEventDescriptor());
	}
	
	public void remove(LastingEffect effect) {
		effects.remove(effect);
	}
	
	public void remove(ExecutesOnEvent ability) {
		abilities.remove(ability);
	}
	
	
	/*
	 * ejemplo: un encantamiento tiene "todo Land entra en juego girado"
	 * 		-> se mapea la abilty con el evento 1 new Event("into_play", Permanent)
	 * 		-> se mapea la  con el evento 2 new Event("removed_from_play", Permanent)
	 * 		-> cuando ocurre el evento 1 se gatilla la ability que se fija en el permanent
	 * 		   y si es un Land lo gira
	 * 		-> cuando ocurre el evento 2 (se fue la carta que la contiene) se quita la ability de los 2 mapas
	 *
	 *
	 * ejemplo: "toda criatura negra tuya gana +1/+1" (carta Bad Moon)
	 * 		-> se mapea la ability con el evento new Event("into_play", Permanent, Player) (evento 1)
	 * 		-> se mapea la ability con el evento new Event("removed_from_play", Permanent) (evento 2)
	 * 		-> cada vez que entra un permanent (evento 1) se fija si es una criatura negra tuya
	 * 		   y si es se le aplica el +1/+1
	 * 		-> cuando la carta que posee la habilidad abandona el juego (evento 2) se quita el +1/+1 
	 * 		   de todas las criaturas afectadas y luego se quita la ability de los 2 mapas
	 * 		-> (o quizas la ability lo podria hacer por medio de un effect para cada criatura en vez de 
	 * 		   hacerlo directamente)
	 * 		-> (PD: habia que aplicarle el efecto a las criaturas que ya estaban eso se podria hacer
	 * 			afuera del event handler)
	 * 
	 * 
	 * ejemplo: se castea un sorcery que dice "criatura X gana "volar" hasta fin de turno  (no hay ability)
	 * 		-> el sorcery al resolver aplica el efecto a la criatura y luego le manda al event handler
	 * 		   newEffect(Effect) con el effect conteniendo el Event("fin de turno")
	 * 		-> cuando llega fin de turno el event handler hace effect.remove();
	 * 		-> el metodo del effect effect.apply(Target target) ejecutaria target.addAttribute("flying");
	 * 		   y el metodo del effect effect.remove() ejectuaria target.removeAttribute("flying");
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