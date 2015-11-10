package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.Cards.Card;

/** This class determines every Sorcery, Instant or Permanent's behavior */
public abstract class Ability {

	public abstract void executeOnCasting(Card sourceCard);
	
	public abstract void finishCasting();
	
	public abstract void proceedToSelectCastingTarget();
	
	public abstract void cancelCastingTargetSelection();
	
	public abstract void resumeCastingTargetSelection();
	
	public abstract void cancelCastingManaRequest();
	
	public abstract void resumeCastingManaRequest();
	
}