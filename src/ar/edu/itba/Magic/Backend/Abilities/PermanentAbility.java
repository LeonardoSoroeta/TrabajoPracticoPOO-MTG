package ar.edu.itba.Magic.Backend.Abilities;

import java.util.List;

import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Cards.ArtifactCard;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.CreatureCard;
import ar.edu.itba.Magic.Backend.Cards.EnchantmentCard;
import ar.edu.itba.Magic.Backend.Cards.LandCard;
import ar.edu.itba.Magic.Backend.Enums.Attribute;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Interfaces.ManaRequester;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Enchantment;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/**
 * All abilities contained by Permanents extend this class. Every time a Permanent that contains an ability enters play, 
 * it's ability's executeOnIntroduction method must be executed.
 */
public abstract class PermanentAbility extends Ability implements ManaRequester {
	
	Match match = Match.getMatch();

	private Card sourceCard;
	private Permanent sourcePermanent;

	//private Integer coloredManaCache;
	//private Integer colorlessManaCache;
	private Object selectedTarget;

	public Permanent getSourcePermanent() {
		return sourcePermanent;
	}

	public void setSourcePermanent(Permanent sourcePermanent) {
		this.sourcePermanent = sourcePermanent;
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
    	// TODO quitar del mana pool, agregar al cache
    	// TODO seguir cobrando el mana bla bla 
    	// TODO cuando termino de pagar el mana, this.finishCasting(); o elegir un target si hace falta
    	// si cancelo elegir un target, devolverle el mana
    }
	
	@Override 
	public void finishCasting() {
		if(sourceCard instanceof EnchantmentCard) {
	        Enchantment enchantment = new Enchantment(sourceCard, this);
	        this.setSourcePermanent(enchantment);
	        enchantment.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        enchantment.sendToStack();	
		} else if(sourceCard instanceof ArtifactCard) {
	        Artifact artifact = new Artifact(sourceCard, this);
	        this.setSourcePermanent(artifact);
	        artifact.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        artifact.sendToStack();	
		} else if(sourceCard instanceof CreatureCard) {
			Integer attackPoints =  ((CreatureCard)sourceCard).getAttackPoints();
			Integer defencePoints =  ((CreatureCard)sourceCard).getDefencePoints();
		    List<Attribute> attributes =  ((CreatureCard)sourceCard).getAttributes();
	        Creature creature = new Creature(sourceCard, attributes, attackPoints, defencePoints,  this);
	        this.setSourcePermanent(creature);
	        creature.setController(sourceCard.getController());
	        sourceCard.getController().removeCardFromHand(sourceCard);
	        creature.sendToStack();	
		} else if(sourceCard instanceof LandCard) {
            Land land = new Land(sourceCard, this);
            this.setSourcePermanent(land);
            land.setController(sourceCard.getController());
            land.setSpellState(false);
            sourceCard.getController().removeCardFromHand(sourceCard);
            sourceCard.getController().placePermanentInPlay(land);
		}
	}
	
	/** Must override this method if card requires target on casting */
	public void resumeTargetSelecion() {

	}
	
	/** Must override this method if card requires target on casting */
	public void cancelTargetSelection() {
		
	}
	
	/** Must override this method on some Permanents */
	public void executeOnEntering() {
		
	}
	
	/** Must override this method on some Permanents */
	public void executeOnExit() {
		
	}

}
