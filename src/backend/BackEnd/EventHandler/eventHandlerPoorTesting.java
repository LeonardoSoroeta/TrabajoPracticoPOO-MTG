package backend.BackEnd.EventHandler;

import java.util.ArrayList;
import java.util.List;

import backend.Effect;
import backend.BackEnd.Effects.EventEffect;

/**
 * Created by Martin on 18/10/2015.
 */
public class eventHandlerPoorTesting {
    public static void main(String args[]){

        // Creo un efecto que podr�a estar dentro de un spell/artefact/creature
        EventEffect efecto = new EventEffect(new Effect() {
            @Override
            public void effect() {
                System.out.println("�Algo suceder�!");
            }
        });

        //Suppongamos que el juego es un array de integers que se van jugando. Estos numeros podr�an ser las cartas
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(99);
        list.add(103);
        list.add(145);
        list.add(5565);
        list.add(6);

        //Agrego el efecto al eventHandler
        EventHandler eventHandler = EventHandler.getEventHandler();
        eventHandler.addEvent(5,efecto);
        //Verifico funcionamiento del singletone
        EventHandler eventHandler2 = EventHandler.getEventHandler();
        eventHandler.addEvent(6,efecto);

        //chequeo si alguno de los numeros genera algo

        eventHandler.check(list);



    }
}
