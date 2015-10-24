package ar.edu.itba.Magic.Backend;
import java.util.*;

public class CardFactory {
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
		
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	public List<String> getDefaultCreatureAttributes() {
		List<String> attributes = new ArrayList<String>();
		
		attributes.add("can_attack");
		attributes.add("can_block");
		attributes.add("can_tap");
		attributes.add("can_untap");
		attributes.add("untaps_on_upkeep");
		//etc
		//hacer uno para land, artifact, enchantment
		
		return attributes;
	}
	
	public Card getCard(String cardName) {
		
		switch(cardName) {
		
			case "gokuCard":
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10);
				
			default:
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10, 
						new ActivatedManaAbility() {
					
							private Object source;
							
							public void activate() {
								System.out.println("Kamehameha");
							}
							
							public void setSource(Object source) {
								this.source = source;
							}
							
							public Object getSource() {
								return source;
							}

							public void executeOnAppearance() {
								System.out.println("Llegue");
								
							}				
				});
				
		}
	}
	
}
