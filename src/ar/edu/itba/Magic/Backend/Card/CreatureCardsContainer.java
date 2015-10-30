package ar.edu.itba.Magic.Backend.Card;
import java.util.HashMap;

import java.util.Map;



//la onda es que aca estan todas las cartas de criatura, desps en el visual 
//para armar los deck hago un new CreatureCardsContainer, y cuando clickeas 
//en la imagen "0", veo el mapeado y te agrego la creaturecard al deck
//lo mismo deberia hacer para el otro tipo de cartas
public class CreatureCardsContainer {

	Map<Integer, CreatureCard> map; 
	
	public CreatureCardsContainer(){
		map = new HashMap();
		
		map.put(0, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(1, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(2, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(3, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(4, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(5, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(6, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(7, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(8, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(9, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(10, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(11, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(12, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(13, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(14, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(15, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(16, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(17, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(18, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(19, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(20, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(21, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(22, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(23, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(24, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(25, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(26, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(27, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(28, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(29, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(30, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(31, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(32, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(33, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(34, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(35, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(36, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(37, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(38, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(39, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(40, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(41, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(42, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(43, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(44, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(45, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(46, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(47, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		map.put(48, new CreatureCard(null, null, null, 0, 0, 0, 0, null));
		
		
		// etcetera
		
	}
	
	public Map<Integer, CreatureCard>  getCreatureCardsContainer(){
		return map;
	}
}
