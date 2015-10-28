package ar.edu.itba.Magic.Backend;

public abstract class ActivatedPermanentAbility extends PermanentAbility implements Activation {

	public abstract void executeOnIntroduction();
	
	public abstract void executeOnActivation();

}
