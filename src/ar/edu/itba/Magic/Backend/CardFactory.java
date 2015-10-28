package ar.edu.itba.Magic.Backend;
import java.util.*;

public class CardFactory {
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
		
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	/* hacer uno para las demas */
	public List<String> getDefaultCreatureAttributes() {
		List<String> attributes = new ArrayList<String>();
		
		attributes.add("can_attack");
		attributes.add("can_block");
		attributes.add("can_tap");
		attributes.add("can_untap");
		attributes.add("untaps_on_upkeep");
		//etc
		
		return attributes;
	}
	
	public Card getCard(String cardName) {
		switch(cardName) {
		
			case "gokuCard":
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10);
				
			default:
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10);			
		}
	}
	
}
