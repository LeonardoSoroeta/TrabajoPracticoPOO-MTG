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
		attributes.add("untaps_on_upkeep");		// etc...
		
		return attributes;
	}
	
	public Card getCard(String cardName) {
		List<String> attributes;
		
		switch(cardName) {
		
			case "Blight":
				return new EnchantmentCard("Blight", "enchantment", "black", 2, 0, 
						new AutomaticPermanentAbility() {
					
							GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
							
							private Land target;
					
							public void executeOnIntroduction() {
								// determinar target en algun momento
								gameEventHandler.add(this);
							}
							
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals("card_tapped")) {
									if(gameEvent.getObject1() == target) {
										target.destroy();
									}
								}								
							}					
				});
				
			case "Bog Imp":
				attributes = getDefaultCreatureAttributes();
				attributes.add("flying");
				return new CreatureCard("Bog Imp", "creature", "black", attributes, 1, 1, 1, 1);
				
			default:
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard("Goku", "saiyan", "black", attributes, 10, 10, 10, 10);			
		}
	}
	
}
