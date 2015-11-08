package ar.edu.itba.Magic.Backend.Abilities;

/** 
 * This class determines a Card's behaviour. Every Card must contain an Ability, except CreatureCards which may or may not 
 * contain one. Every ability has a source. For most abilites, it's source is the Permanent that contains it. In the case of
 * SorceryCards and InstantCards, the ability's source is the Card that contained it.
 */
public abstract class Ability {

	/**
	 * Must override this method for Cards that require an action to be performed before casting. For example, selecting 
	 * a target.
	 * 
	 * @return Returns true if the action was succesfully realized. Otherwise returns false and the Card fails to cast.
	 */
	public boolean satisfyCastingRequirements() {
		return true;
	}
	
	public void getTargetAndContinueExecution() {
		
	}

}