/* esta clase es una idea de un paquete de mana.
 * para hacer mas simple trabajar con costos de mana. 
 * 
 * por ejemplo:
 * 		ManaBatch manaCost = new manaBatch(1,0,0,0,0,5);
 * seria un paquete de 1 rojo y 5 colorless.
 * 
 * incluso el ManaPool quizas seria simplemente un ManaBatch adentro de Player. 
 * (trate de hacer un manapool que use manabatch y parecia al pedo era lo mismo)
 * 
 * una habilidad o spell podria tener un metodo
 * 		public ManaBatch getManaCost();
 * 
 * y se podria usar por ejemplo asi:
 * 		if player1.getManaPool().contains(spell.manaCost())
 * 			then castearlo y luego remove el cost del mana pool;
 */
public class ManaBatch {
	
	private Integer[] mana = new Integer[6];
	
	public ManaBatch(Integer redMana, Integer greenMana, Integer blueMana, Integer blackMana, Integer whiteMana, Integer colorlessMana) {
		this.mana[0] = redMana;
		this.mana[1] = greenMana;
		this.mana[2] = blueMana;
		this.mana[3] = blackMana;
		this.mana[4] = whiteMana;
		this.mana[5] = colorlessMana;
	}
	
	public Integer[] getValues() {
		return mana;
	}
	
	public Boolean isEmpty() {
		for(Integer color : mana)
			if(!color.equals(0)) return false;
		
		return true;
	}
	
	//suma de todos los colores incluido colorless
	public Integer getConvertedManaCost() {
		Integer total = 0;
		
		for(Integer color : mana)
			total += color;
		
		return total;
	}
	
	//primero se fija si alcanza el mana de cada color (menos colorless)
	//luego se fija si alcanza el colorless, tomando en cuenta que los mana de 
	//color sobrantes tambien se pueden usar como colorless
	public Boolean contains(ManaBatch manaBatch) {
		Integer manaSobranteTotal = this.getConvertedManaCost();
		
		for(int i = 0 ; i < 5 ; i++) {
			if(mana[i] < manaBatch.getValues()[i])
				return false;
			else {
				manaSobranteTotal -= manaBatch.getValues()[i];
			}
		}
		
		if(manaBatch.getValues()[5] > manaSobranteTotal)
			return false;
		
		return true;
	}
	
	//remueve colorless primero, luego los de cada color
	//los colorless que quedaron por pagar se pagan con cualquier color
	//no se si tiene sentido esto, en el juego no me acuerdo como se hace;
	//si te pregunta con cual mana de color queres pagar el costo colorless o si
	//lo hace automatico
	public void removeMana(ManaBatch manaBatch) {
		Integer colorlessPendiente = manaBatch.getValues()[5] - mana[5];
		
		mana[5] -= manaBatch.getValues()[5];		
		
		for(int i = 0 ; i < 5 ; i++)
			mana[i] -= manaBatch.getValues()[i];
		
		for(int i = 0 ; i < 5 ; i++) {
			colorlessPendiente -= mana[i];
			mana[i] = 0;
		}
		
	}
	
	public void addMana(ManaBatch manaBatch) {
		for(int i = 0 ; i < 6 ; i++)
			mana[i] += manaBatch.getValues()[i];
	}
	
}
