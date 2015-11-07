package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

public class OneTurnStatModifier extends AutomaticLastingEffect {
	private Integer attackModifier;
	private Integer defenseModifier;

	public OneTurnStatModifier(Ability sourceAbility, Integer attackModifier, Integer defenseModifier) {
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
		if(attackModifier < 0) {
			((Creature)this.getTarget()).decreaseAttack(attackModifier);
		}
		else {
			((Creature)this.getTarget()).increaseAttack(attackModifier);
		}
		if(defenseModifier < 0) {
			((Creature)this.getTarget()).decreaseDefense(defenseModifier);
		}
		else {
			((Creature)this.getTarget()).increaseDefense(defenseModifier);
		}
	}

	@Override
	public void undoEffect() {
		if(attackModifier < 0) {
			((Creature)this.getTarget()).increaseAttack(attackModifier);
		}
		else {
			((Creature)this.getTarget()).decreaseAttack(attackModifier);
		}
		if(defenseModifier < 0) {
			((Creature)this.getTarget()).increaseDefense(defenseModifier);
		}
		else {
			((Creature)this.getTarget()).decreaseDefense(defenseModifier);
		}
	}
}
