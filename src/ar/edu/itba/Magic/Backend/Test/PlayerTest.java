package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.Deck;
import ar.edu.itba.Magic.Backend.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 19/11/2015.
 */
public class PlayerTest {

    private Player player;

    @Before
    public void initializePlayer(){
        player = new Player(new Deck());
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

    }
}
