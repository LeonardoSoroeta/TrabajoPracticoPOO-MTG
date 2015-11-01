package ar.edu.itba.Magic.Backend.Interfaces;

import javax.smartcardio.Card;

public interface GameStackAction {

	/**
	 * Sends the hability or permanent to the stack.
	 */

	public void sendToStack(); // manda al stack y hace lo que tenga que hacer antes (ejemplo determinar targets)

	public void resolveInStack(); // pasa del stack a entrar en juego o hacer lo q tiene q hacer

}
