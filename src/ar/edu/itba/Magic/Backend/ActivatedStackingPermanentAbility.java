package ar.edu.itba.Magic.Backend;

public abstract class ActivatedStackingPermanentAbility extends PermanentAbility implements Activation {

	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 
	
}
