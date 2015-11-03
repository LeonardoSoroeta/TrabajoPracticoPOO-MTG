package ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.Enchantment;
import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;

/**
 * Created by Martin on 31/10/2015.
 */
public class EnchantmentCard extends Card {
    public EnchantmentCard(CardName cardName, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
        super(cardName, color, coloredManaCost, colorlessManaCost, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Enchantment enchantment = new Enchantment(this, this.getCardName(), this.getColor(), this.getColoredManaCost(), this.getColorlessManaCost(), (PermanentAbility)this.getAbility());
            enchantment.getAbility().setSourcePermanent(enchantment);
            enchantment.setController(this.getController());
            enchantment.sendToStack();
            this.getController().getHand().remove(this);
        }

    }
}
