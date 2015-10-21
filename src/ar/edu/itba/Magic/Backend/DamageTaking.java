package ar.edu.itba.Magic.Backend;

/* criaturas y players implementan esta interface. se implementa distinto en los dos.
 * jugadores toman danio e inmediatamente pierden lo mismo en health.
 * a criaturas se les aplica un damage marker que es acumulativo hasta fin de turno 
 */
public interface DamageTaking {
	
	public void takeDamage(Integer damage);
	
}
