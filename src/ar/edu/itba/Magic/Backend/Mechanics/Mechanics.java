package ar.edu.itba.Magic.Backend.Mechanics;

import ar.edu.itba.Magic.Backend.Cards.Card;

/** This class determines every Sorcery, Instant or Permanent's behavior */
public abstract class Mechanics {

	public abstract void executeOnCasting(Card sourceCard);
	
	public abstract void finishCasting();
	
	public abstract void proceedToSelectCastingTarget();
	
	public abstract void cancelCastingTargetSelection();
	
	public abstract void resumeCastingTargetSelection();
	
	public abstract void cancelCastingManaRequest();
	
	public abstract void resumeCastingManaRequest();
	
}