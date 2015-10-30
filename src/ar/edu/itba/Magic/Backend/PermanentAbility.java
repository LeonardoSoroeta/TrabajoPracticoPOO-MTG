package ar.edu.itba.Magic.Backend;

/**
 * All abilities contained by Permanents extend this class. Every time a Permanent that contains an ability enters play, 
 * it's ability's executeOnIntroduction method must be executed.
 */
public abstract class PermanentAbility extends Ability {
	
	private Permanent source;

	public Permanent getSource() {
		return source;
	}

	public void setSource(Permanent source) {
		this.source = source;
	}
	
	/**
	 * Must execute this method every time the ability's Permanent source enters play.
	 */
	public void executeOnIntroduction() {
		
	}

}
