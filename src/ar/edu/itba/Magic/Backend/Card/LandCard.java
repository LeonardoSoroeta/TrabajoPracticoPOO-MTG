package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import ar.edu.itba.Magic.Backend.Land;
import ar.edu.itba.Magic.Backend.PermanentAbility;

/**
 * Created by Martin on 31/10/2015.
 */
public class LandCard extends Card {
    GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();

    public LandCard(CardName cardName, Ability ability) {
        super(cardName, Color.COLORLESS, 0, 0, ability);
    }

    public void playCard() {
        if(this.getAbility().satisfyCastingRequirements()) {
            Land land = new Land(this, this.getCardName(), this.getColor(), (PermanentAbility)this.getAbility());
            land.getAbility().setSourcePermanent(land);
            land.setController(this.getController());
            land.getController().getHand().remove(this);
            land.getController().getPermanentsInPlay().add(land);
            this.gameEventHandler.notifyGameEvent(new GameEvent(Event.PERMANENT_ENTERS_PLAY, land));
            land.getAbility().executeOnEntering();
        }

    }
}