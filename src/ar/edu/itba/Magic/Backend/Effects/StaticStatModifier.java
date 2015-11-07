package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Permanents.Creature;

public class StaticStatModifier extends LastingEffect {
	private Integer attackModifier;
	private Integer defenseModifier;

	public StaticStatModifier(Ability sourceAbility, Integer attackModifier, Integer defenseModifier) {
		super(sourceAbility);
		this.attackModifier = attackModifier;
		this.defenseModifier = defenseModifier;
	}

	@Override
	public void applyEffect() {
		if(attackModifier < 0) {
			((Creature)this.getTarget()).decreaseAttack(Math.abs(attackModifier));
		}
		else {
			((Creature)this.getTarget()).increaseAttack(attackModifier);
		}
		if(defenseModifier < 0) {
			((Creature)this.getTarget()).decreaseDefense(Math.abs(defenseModifier));
		}
		else {
			((Creature)this.getTarget()).increaseDefense(defenseModifier);
		}
	}

	@Override
	public void undoEffect() {
		if(attackModifier < 0) {
			((Creature)this.getTarget()).increaseAttack(Math.abs(attackModifier));
		}
		else {
			((Creature)this.getTarget()).decreaseAttack(attackModifier);
		}
		if(defenseModifier < 0) {
			((Creature)this.getTarget()).increaseDefense(Math.abs(defenseModifier));
		}
		else {
			((Creature)this.getTarget()).decreaseDefense(defenseModifier);
		}
	}
}
