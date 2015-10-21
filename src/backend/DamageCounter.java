package backend;

/*
 * damage va al stack. asi el otro tiene chance de prevenirlo 
 * menos el damage de combate que se aplica de una. ba esa es la regla nueva
 * de magic y pinta mas facil pero podemos ponerlo en el stack tambien
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
