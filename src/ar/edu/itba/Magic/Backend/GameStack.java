package ar.edu.itba.Magic.Backend;
import java.util.*;

public class GameStack {
	
	List<GameStackAction> gameStack;
	
	public GameStack() {
		gameStack = new LinkedList<GameStackAction>();    //no se si linked list o que tiene q ser
	}
	
	//recursiva va pidiendo mas spells o abilities a los jugadores
	//es solo una idea
	public void stackAction(GameStackAction gameStackAction) {
		gameStack.add(gameStackAction);
		//if(new spell or action)
		//		stackAction(new stackableAction);
		//switchPriority();
		//if(new spell or action)
		//				stackAction(new stackableAction);
		//switchPriority();
		gameStackAction.resolve();
		gameStack.remove(gameStackAction);
	}
	
}
