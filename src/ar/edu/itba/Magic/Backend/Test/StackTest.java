package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.Mechanics.AutomaticSpellMechanics;
import ar.edu.itba.Magic.Backend.SpellStack;
import ar.edu.itba.Magic.Backend.Interfaces.Spell;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Martin on 12/11/2015.
 */
public class StackTest {

    private SpellStack stack;
    private Spell object;
    private List<Spell> list;

    @Before
    public void initialize(){

        stack = SpellStack.getSpellStack();
        list = new LinkedList<>();

        //SendToStack and resolveInStack methods will not be implementend. @object It is just a Test object.
        object = new AutomaticSpellMechanics() {
            @Override
            public void resolveInStack() {

            }

            @Override
            public void executeOnEvent(GameEvent gameEvent) {

            }
        };
        list.add(object);

    }

    @Test
    public void singletoneClassTest(){
        assertEquals(stack, SpellStack.getSpellStack());
    }

    @Test
    public void removeSpellTest(){
        Spell testSpell = new Spell() {
            @Override
            public void sendToStack() {

            }

            @Override
            public void resolveInStack() {

            }

            @Override
            public void counterSpell() {

            }
        };
        stack.addSpell(testSpell);
        stack.removeSpell(testSpell);
        assertTrue(stack.stackEmpty());
    }

    @Test
    public void gameStackObjectsTest(){
        stack.addSpell(object);
       assertEquals(stack.getSpells(),list);
    }

}
