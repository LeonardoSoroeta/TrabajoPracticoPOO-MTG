package backend.BackEnd.Effects;

import backend.BackEnd.Interfaces.Effect;

/**
 * Created by Martin on 18/10/2015.
 */
public class LastingEffect {

    Effect effect;
    int times;

    public LastingEffect(Effect ef, Integer t){
        effect = ef;
    }

}
