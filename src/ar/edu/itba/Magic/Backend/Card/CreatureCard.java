package ar.edu.itba.Magic.Backend.Card;

/**
 * Created by Martin on 31/10/2015.
 */
public class CreatureCard extends Card {
        private Integer attackPoints;
        private Integer defencePoints;
        private List<Attribute> attributes;

        public CreatureCard(String cardName, String cardType, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attackPoints, Integer defencePoints, Ability ability) {
            super(cardName, cardType, color, coloredManaCost, colorlessManaCost, ability);
            this.attackPoints = attackPoints;
            this.defencePoints = defencePoints;
            this.attributes = attributes;
        }

        public CreatureCard(String cardName, String cardType, Color color, List<Attribute> attributes, Integer coloredManaCost, Integer colorlessManaCost, Integer attackPoints, Integer defencePoints) {
            super(cardName, cardType, color, coloredManaCost, colorlessManaCost, (Ability)null);
            this.attackPoints = attackPoints;
            this.defencePoints = defencePoints;
            this.attributes = attributes;
        }

        public void playCard() {
            Creature creature;
            if(this.containsAbility()) {
                if(this.getAbility().satisfyCastingRequirements()) {
                    creature = new Creature(this, this.getCardName(), this.getColor(), this.attributes, this.getColoredManaCost(), this.getColorlessManaCost(), this.attackPoints, this.defencePoints, (PermanentAbility)this.getAbility());
                    creature.getAbility().setSourcePermanent(creature);
                    creature.setController(this.getController());
                    creature.sendToStack();
                    this.getController().getHand().remove(this);
                }
            } else {
                creature = new Creature(this, this.getCardName(), this.getColor(), this.attributes, this.getColoredManaCost(), this.attackPoints, this.defencePoints, this.getColorlessManaCost());
                creature.getAbility().setSourcePermanent(creature);
                creature.setController(this.getController());
                creature.sendToStack();
                this.getController().getHand().remove(this);
            }

        }
    }

}
