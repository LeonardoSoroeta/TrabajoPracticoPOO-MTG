package ar.edu.itba.Magic.Backend;

/*
 *  esta la tiene que implementar todo lo que va al stack
 */
public interface GameStackAction {
	
	public void sendToStack(); // manda al stack y hace lo que tenga que hacer antes (ejemplo determinar targets)
	
	public void resolve(); // pasa del stack a entrar en juego o hacer lo q tiene q hacer
	
}
