package ar.edu.itba.Magic.Backend.Cards;

import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Abilities.Ability;
import ar.edu.itba.Magic.Backend.Enums.CardType;


public class LandCard extends Card {
	
    public LandCard(CardType cardType, Ability ability) {
        super(cardType, ability);
    }

    public void playCard() {
    	if(Match.getMatch().isLandPlayThisTurn()) {
    		Match.getMatch().awaitMainPhaseActions("You already played a Land card this turn.");
    	} else {
    		this.getAbility().executeOnCasting(this);
    		Match.getMatch().setLandPlayThisTurnTrue();
    	}
     }
}