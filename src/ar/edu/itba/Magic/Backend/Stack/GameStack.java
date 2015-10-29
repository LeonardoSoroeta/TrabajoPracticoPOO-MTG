package ar.edu.itba.Magic.Backend.Stack;

import ar.edu.itba.Magic.Backend.Interfaces.GameStackAction;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class GameStack {

    private GameStack instance = this;
	private LinkedList<GameStackAction> stack = new LinkedList<>();

	private GameStack() {

	}
    public GameStack getGameStackInstance(){
        return this;
    }

	public void stackAction(GameStackAction gameStackAction) {
        if(gameStackAction == null)
            throw new IllegalArgumentException("Null object in stack.");
        stack.push(gameStackAction);
    }

    public GameStackAction pop(){
        if(!stack.isEmpty())
            stack.pop();
        throw new EmptyStackException();
    }

    public void push(GameStackAction gameStackAction){
        if(gameStackAction != null)
            stack.push(gameStackAction);
    }


	
}
