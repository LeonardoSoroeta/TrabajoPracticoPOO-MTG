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
		((Creature)this.getTarget()).modifyAttack(attackModifier);
		((Creature)this.getTarget()).modifyDefense(defenseModifier);
	}

	@Override
	public void undoEffect() {
		((Creature)this.getTarget()).modifyAttack(-attackModifier);
		((Creature)this.getTarget()).modifyDefense(-defenseModifier);
	}
}
