package backend;
import java.util.HashMap;
import java.util.Map;

//la onda es que aca estan todas las cartas de criatura, desps en el visual 
// para armar los deck hago un new CreatureCardsContainer, y cuando clickeas 
// en la imagen "0", veo el mapeado y te agrego la creaturecard al deck
// lo mismo deberia hacer para el otro tipo de cartas
public class CreatureCardsContainer {

	Map<String, CreatureCard> map; 
	
	public CreatureCardsContainer(){
		map = new HashMap();
		
		map.put("0", new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put("1", new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		// etcetera
		
	}
	
	public Map<String, CreatureCard>  getCreatureCardsContainer(){
		return map;
	}
}
