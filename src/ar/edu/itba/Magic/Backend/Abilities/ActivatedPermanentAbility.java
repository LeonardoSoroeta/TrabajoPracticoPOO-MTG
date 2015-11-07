package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;

public abstract class ActivatedPermanentAbility extends PermanentAbility implements Activator {

	public abstract void executeOnActivation();

}
