package backend.BackEnd.Interfaces;

/*
 *  esta la tiene que implementar todo lo que va al stack
 */
public interface GameStackAction {
	
	public void toStack(); // capaz al pedo este metodo, se podria llamar directamente stackAtion del GameStack. por ahora queda lindo
	
	public void resolve(); // pasa del stack a entrar en juego o hacer lo q tiene q hacer
	
}
