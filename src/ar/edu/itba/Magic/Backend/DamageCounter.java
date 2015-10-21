package ar.edu.itba.Magic.Backend;

/*
 * damage va al stack. asi el otro tiene chance de prevenirlo. hay una regla nueva 
 * de magic que dice que danio de combate no necesita pasar por el stack 
 * podemos hacerlo de cualquiera de las dos formas
 */

public class DamageCounter implements GameStackAction {
	
	DamageTaking target;
	Integer ammount;
	
	public DamageCounter(DamageTaking target, Integer ammount) {
		this.target = target;
		this.ammount = ammount;
	}
	
	public void resolve() {
		target.takeDamage(ammount);
	}
}
