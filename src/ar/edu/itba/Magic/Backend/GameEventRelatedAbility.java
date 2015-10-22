package ar.edu.itba.Magic.Backend;

/* estas son las que van en el event handler (serian las triggered y las static) */
public abstract class GameEventRelatedAbility extends Ability {
	
	public abstract void setSource(Object source);
	
	public abstract Object getSource();
	
	public abstract GameEvent getRelatedGameEvent();
	
	public abstract void analyzeGameEvent(GameEvent gameEvent);

}
