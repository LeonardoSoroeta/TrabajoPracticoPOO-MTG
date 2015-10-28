package ar.edu.itba.Magic.Backend;

public abstract class ActivatedStackingPermanentAbility extends PermanentAbility {

	public abstract void setSource(Object source);

	public abstract Object getSource();
	
	public abstract void executeOnIntroduction();
	
	public abstract void executeOnActivation();
	
	public abstract void sendToStack(); 
	
	public abstract void resolveInStack(); 
	
}
