package ar.edu.itba.Magic.Backend.Test;

import ar.edu.itba.Magic.Backend.GameStack;
import ar.edu.itba.Magic.Backend.Interfaces.GameStackObject;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 12/11/2015.
 */
public class StackTest {

    private GameStack stack;
    private GameStackObject object;
    private List<GameStackObject> list;

    @Before
    public void initialize(){

        stack = GameStack.getGameStackInstance();
        list = new LinkedList<>();

        //SendToStack and resolveInStack methods will not be implementend. @object It is just a Test object.
        object = new GameStackObject() {
            @Override
            public void sendToStack() {

            }

            @Override
            public void resolveInStack() {

            }
        };
        list.add(object);

    }

    @Test
    public void singletoneClassTest(){
        assertEquals(stack, GameStack.getGameStackInstance());
    }

    @Test
    public void gameStackObjectsTest(){
        stack.addStackObject(object);
       assertEquals(stack.getGameStackObjects(),list);
    }

}
