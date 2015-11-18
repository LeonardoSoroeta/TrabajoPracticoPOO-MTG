package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

public class OneTurnStatModifier extends AutomaticLastingEffect {
	private Integer attackModifier;
	private Integer defenseModifier;

	public OneTurnStatModifier(Mechanics sourceAbility, Integer attackModifier, Integer defenseModifier) {
		super(sourceAbility);
		this.attackModifier = attackModifier;
		this.defenseModifier = defenseModifier;
	}

	@Override
	public void executeOnEvent(GameEvent gameEvent) {
		if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
			this.getTarget().removeLastingEffect(this);
		}
	}

	@Override
	public void applyEffect() {
		((Creature)this.getTarget()).modifyAttack(attackModifier);
		((Creature)this.getTarget()).modifyDefense(defenseModifier);
	}

	@Override
	public void undoEffect() {
		((Creature)this.getTarget()).modifyAttack(-1*attackModifier);
		((Creature)this.getTarget()).modifyDefense(-1*defenseModifier);
	}
}
