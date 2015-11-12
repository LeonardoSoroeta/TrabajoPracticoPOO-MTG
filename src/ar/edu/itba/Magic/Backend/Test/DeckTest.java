package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Match;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 12/11/2015.
 */
public class DeckTest {

    Deck deck;
    Match match;
    Card card;

    @Before
    public void initializeDeck(){
        deck = new Deck();
        match = Match.getMatch();
        card = CardType.ACID_RAIN.createCardOfThisType();
    }


    @Test(expected = IllegalArgumentException.class)
    public void AddNullElemToDeckTest() throws RuntimeException{
        deck.addCard(null);
    }

    public void AddCreatureTest(){
        deck.addCard(card);
    }

    public void EndOfGameTest(){
        deck.getCard();
        assertEquals(match.getMatchState(), MatchState.GAME_OVER);
    }

}
