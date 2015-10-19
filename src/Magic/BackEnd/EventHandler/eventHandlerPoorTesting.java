package Magic.BackEnd.EventHandler;

import Magic.BackEnd.Effects.EventEffect;
import Magic.BackEnd.Interfaces.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 18/10/2015.
 */
public class eventHandlerPoorTesting {
    public static void main(String args[]){

        // Creo un efecto que podría estar dentro de un spell/artefact/creature
        EventEffect efecto = new EventEffect(new Effect() {
            @Override
            public void effect() {
                System.out.println("¡Algo sucederá!");
            }
        });

        //Suppongamos que el juego es un array de integers que se van dando
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(21);


    }
}
