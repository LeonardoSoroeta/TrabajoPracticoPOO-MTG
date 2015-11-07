package ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.Enchantment;
import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.PermanentAbility;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardType;

/**
 * Created by Martin on 31/10/2015.
 */
public class EnchantmentCard extends Card {
    public EnchantmentCard(CardType cardName, Ability ability) {
        super(cardName, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Enchantment enchantment = new Enchantment(this, (PermanentAbility)this.getAbility());
            enchantment.getAbility().setSourcePermanent(enchantment);
            enchantment.setController(this.getController());
            this.getController().getHand().remove(this);
            enchantment.sendToStack();
        }
    }
}
