package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activation;

public abstract class ActivatedPermanentAbility extends PermanentAbility implements Activation {

	public abstract void executeOnActivation();

}
