package ar.edu.itba.Magic.Backend.Abilities;

import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;
import ar.edu.itba.Magic.Backend.Interfaces.ManaRequester;

/* las habilities adentro de InstantCards y SorceryCards extienden de esta */
public abstract class SpellAbility extends Ability implements GameStackAction, ManaRequester {
	
	Match match = Match.getMatch();
	
	private Card sourceCard;
	
	//private Integer coloredManaCache;
	//private Integer colorlessManaCache;
	private Object selectedTarget;
	
	public Card getSourceCard() {
		return sourceCard;
	}

	public void setSourceCard(Card sourceCard) {
		this.sourceCard = sourceCard;
	}
	
	@Override
	public void executeOnCasting(Card sourceCard) {
		this.sourceCard = sourceCard;
		this.requestManaPayment();
	}
	
	public void requestManaPayment() {
	    ManaPool controllerManaPool = sourceCard.getController().getManaPool();
		Color color = sourceCard.getColor();
		Integer coloredManaCost = sourceCard.getColoredManaCost();
		Integer colorlessManaCost = sourceCard.getColorlessManaCost();
		
		if(sourceCard.getColoredManaCost() == 0) {
			if(sourceCard.getColorlessManaCost() == 0) {
				this.finishCasting();
			}
		} else if(controllerManaPool.containsEnoughManaToPay(color, coloredManaCost, colorlessManaCost)) {
			match.awaitManaPayment(this, "Pay requested mana cost to cast this card: ");
		}
	}
	
    public void resumeManaRequesting() {
    	this.selectedTarget = match.getSelectedTarget();
    	// TODO seguir cobrando el mana bla bla 
    	// TODO cuando termino de pagar el mana, this.finishCasting();
    }
    
    @Override 
	public void finishCasting() {
    	this.sendToStack();
    }
    
    /** Must override this method if card requires target on casting */
	public void resumeTargetSelecion() {

	}
	
	public abstract void sendToStack();
	
	public abstract void resolveInStack();
}
