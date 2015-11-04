package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Activator;

public abstract class ActivatedStackingPermanentAbility extends PermanentAbility implements Activator {

	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 
	
}
