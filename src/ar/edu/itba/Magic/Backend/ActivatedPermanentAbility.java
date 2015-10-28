package ar.edu.itba.Magic.Backend;

public abstract class ActivatedPermanentAbility extends PermanentAbility implements Activation {

	public abstract void setSource(Object source);

	public abstract Object getSource();
	
	public abstract void executeOnIntroduction();
	
	public abstract void executeOnActivation();

}
