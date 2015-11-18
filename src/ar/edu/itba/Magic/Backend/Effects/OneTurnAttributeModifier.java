package ar.edu.itba.Magic.Backend.Effects;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.AttributeModifier;
import ar.edu.itba.Magic.Backend.Enums.Event;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;

public class OneTurnAttributeModifier extends AutomaticLastingEffect {
	AttributeModifier attributeModifier;
	Attribute attribute;

	public OneTurnAttributeModifier(Mechanics sourceAbility, AttributeModifier attributeModifier, Attribute attribute) {
		super(sourceAbility);
		this.attributeModifier = attributeModifier;
		this.attribute = attribute;
	}

	@Override
	public void executeOnEvent(GameEvent gameEvent) {
		if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
			this.getTarget().removeLastingEffect(this);
		}
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
