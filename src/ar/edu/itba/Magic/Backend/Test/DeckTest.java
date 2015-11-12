package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.MatchState;
import ar.edu.itba.Magic.Backend.Match;
import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Martin on 12/11/2015.
 */
public class DeckTest {

    private Deck deck;
    private Match match;
    private Card card;
    private Card card2;

    @Before
    public void initializeDeck(){
        deck = new Deck();
        match = Match.getMatch();
        card = CardType.ACID_RAIN.createCardOfThisType();
        card2 = CardType.AZURE_DRAKE.createCardOfThisType();
    }


    @Test(expected = IllegalArgumentException.class)
    public void AddNullElemToDeckTest() throws RuntimeException{
        deck.addCard(null);
    }

    @Test
    public void getCardTest(){
        deck.addCard(card);
        assertEquals(deck.getCard(), card);
    }

    @Test(expected = NoSuchElementException.class)
    public void EndOfGameTest(){
        deck.getCard();
        assertEquals(match.getMatchState(), MatchState.GAME_OVER);
    }

    @Test
    public void ContainsCardTest(){
        deck.addCard(card);
        assertTrue(deck.containsCard(card));
        deck.addCard(card2);
        deck.removeCard(card2);
        assertFalse(deck.containsCard(card2));
    }

}
