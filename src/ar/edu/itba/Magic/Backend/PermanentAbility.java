package ar.edu.itba.Magic.Backend;

public abstract class PermanentAbility extends Ability {
	
	public abstract void setSourcePermanent(Permanent sourcePermanent);
	
	public abstract Permanent getSourcePermanent();
	
	public abstract void executeOnIntroduction();

}
