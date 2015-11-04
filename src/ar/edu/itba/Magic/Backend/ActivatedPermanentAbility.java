package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;

public abstract class ActivatedPermanentAbility extends PermanentAbility implements Activator {

	public abstract void executeOnActivation();

}
