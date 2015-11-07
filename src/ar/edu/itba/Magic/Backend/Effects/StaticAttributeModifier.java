package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.AttributeModifier;

public class StaticAttributeModifier extends LastingEffect {
	AttributeModifier attributeModifier;
	Attribute attribute;

	public StaticAttributeModifier(Ability sourceAbility, AttributeModifier attributeModifier, Attribute attribute) {
		super(sourceAbility);
		this.attributeModifier = attributeModifier;
		this.attribute = attribute;
	}

	@Override
	public void applyEffect() {
		if(attributeModifier.equals(AttributeModifier.ADD)) {
			this.getTarget().addAttribute(attribute);
		}
		if(attributeModifier.equals(AttributeModifier.REMOVE)) {
			this.getTarget().removeAttribute(attribute);
		}
	}

	@Override
	public void undoEffect() {
		if(attributeModifier.equals(AttributeModifier.ADD)) {
			this.getTarget().removeAttribute(attribute);
		}
		if(attributeModifier.equals(AttributeModifier.REMOVE)) {
			this.getTarget().addAttribute(attribute);
		}
	}
}
