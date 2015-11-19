package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.ManaPool;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;
import ar.edu.itba.Magic.Backend.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin on 19/11/2015.
 */
public class PlayerTest {

    private Player player;
    private Player player2;
    private Deck deck;
    private Permanent permanent;

    @Before
    public void initializePlayer(){
        player = new Player(new Deck());
        deck.addCard(CardType.BOG_IMP.createCardOfThisType());
        player2 = new Player(deck);
    }

    @Test
    public void getHealthTest(){
        player.setHealth(20);
        assertEquals(player.getHealth(),20);
        player.takeDamage(15);
        assertEquals(player.getHealth(),5);
        player.takeDamage(5);
        assertEquals(player.getHealth(),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void IncreaseAndDrecreaceHealth(){
        player.setHealth(5);
        player.increaseHealth(5);
        assertEquals(player.getHealth(),10);
        player.increaseHealth(0);
        assertEquals(player.getHealth(),10);
        player.decreaseHealth(5);
        assertEquals(player.getHealth(),5);
        player.decreaseHealth(0);
        assertEquals(player.getHealth(),5);
        player.decreaseHealth(-8);
    }

    @Test
    public void getManaPoolTest(){
        ManaPool manapool = player.getManaPool();
        assertEquals(player.getManaPool(),manapool);
    }

    @Test
    public void lostTest(){
        player.setHealth(0);
        assertTrue(player.lost());
        player.setHealth(10);
        assertFalse(player.lost());
    }
}
