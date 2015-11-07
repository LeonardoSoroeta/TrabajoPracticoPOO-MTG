package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/**
 * All abilities contained by Permanents extend this class. Every time a Permanent that contains an ability enters play, 
 * it's ability's executeOnIntroduction method must be executed.
 */
public abstract class PermanentAbility extends Ability {
	
	private Permanent sourcePermanent;

	public Permanent getSourcePermanent() {
		return sourcePermanent;
	}

	public void setSourcePermanent(Permanent sourcePermanent) {
		this.sourcePermanent = sourcePermanent;
	}
	
	/**
	 * Must execute this method every time the ability's Permanent source enters play.
	 */
	public void executeOnEntering() {
		
	}
		
	/**
	 * Must execute this method every time the ability's Permanent source leaves play.
	 */
	public void executeOnExit() {
		
	}

}
