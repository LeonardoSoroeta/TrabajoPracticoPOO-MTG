package ar.edu.itba.Magic.Backend;
import java.util.*;

public class CardFactory {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	Match match = Match.getMatch();
	
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
		attributes.add("untaps_on_upkeep");		// etc...
		
		return attributes;
	}
	
	public List<String> getDefaultEnchantmentAttributes() {
		List<String> attributes = new ArrayList<String>();
		
		return attributes;
	}
	
	public List<String> getDefaultInstantAttributes() {
		List<String> attributes = new ArrayList<String>();
		
		return attributes;
	}
	
	public Card getCard(String cardName) {

		List<String> attributes;
		
		switch(cardName) {
		
			case "Flood":
				attributes = getDefaultEnchantmentAttributes();
				return new EnchantmentCard("Flood", "enchantment", "blue", attributes, 1, 0, 
						new ActivatedPermanentAbility() {
							
							private Permanent sourcePermanent;
					
							public void executeOnIntroduction() {
								
							}
							
							public void executeOnActivation() {
								//pay mana cost
								//select target creature without flying
								//creature.tap();
							}
							
							public Permanent getSourcePermanent() {
								return sourcePermanent;
							}
							
							public void setSourcePermanent(Permanent sourcePermanent) {
								this.sourcePermanent = sourcePermanent;
							}
				});

			case "Terror":
				attributes = getDefaultInstantAttributes();
				return new InstantCard("Terror", "instant", "black", attributes, 1, 1, 
						new SpellAbility() {
					
							private Creature target;
							
							public boolean satisfyCastingRequirements() {
								//selecciona un target
									//return true
								//else
								return false;
							}

							public void sendToStack() {
								//gamestack.add(this)
							}
					
							public void resolveInStack() {
								target.destroy();
							}
					
				});
				
			case "Bog Imp":
				attributes = getDefaultCreatureAttributes();
				attributes.add("flying");
				return new CreatureCard("Bog Imp", "creature", "black", attributes, 1, 1, 1, 1);
				
			default:
				throw new IllegalArgumentException("Error: Carta no pertenece a la coleccion.");
		}
	}
	
}
