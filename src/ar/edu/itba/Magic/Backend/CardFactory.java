package ar.edu.itba.Magic.Backend;
//import ar.edu.itba.Magic.Frontend.Match;
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
		attributes.add("untaps_on_upkeep");	
		
		return attributes;
	}
	
	public List<String> getDefaultEnchantmentAttributes() {
		List<String> attributes = new ArrayList<String>();
		
		return attributes;
	}
	
	public Card getCard(String cardName) {

		List<String> attributes;
		
		switch(cardName) {
		
			case "Bog Imp":
				attributes = getDefaultCreatureAttributes();
				attributes.add("flying");
				return new CreatureCard("Bog Imp", "creature", "black", attributes, 1, 1, 1, 1);
				
			case "Flood":
				attributes = getDefaultEnchantmentAttributes();
				return new EnchantmentCard("Flood", "enchantment", "blue", attributes, 1, 0, 
						new ActivatedPermanentAbility() {

							public void executeOnActivation() {
								//pay mana cost
								//select target creature without flying
								//creature.tap();
							}
				});
				
			case "Nightmare":
				attributes = getDefaultCreatureAttributes();
				attributes.add("flying");
				return new CreatureCard("Nightmare", "creature", "black", attributes, 1, 5, 1, 1, 
						new AutomaticPermanentAbility() {
							
							/**
							 * Adds Nightmare's automatic ability to the gameEventHandler.
							 */
							public void executeOnIntroduction() {
								gameEventHandler.add(this);
								gameEventHandler.notifyGameEvent(new GameEvent("generic_event"));
							}
							
							/**
							 * Executes on every game event. Sets Nightmare's attack and defense equal to
							 * the ammount of Swamps it's controller has in play.
							 */
							public void executeOnEvent(GameEvent gameEvent) {
								Integer swamps = 0;
								Player controller = ((Permanent)this.getSource()).getController();
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(controller.getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent.getName().equals("Swamp")) 
										swamps++;
								((Creature)this.getSource()).setAttack(swamps);
								((Creature)this.getSource()).setDefense(swamps);
								}
								
							}
				});

			case "Terror":
				return new InstantCard("Terror", "instant", "black", 1, 1, 
						new SpellAbility() {
					
							private Creature target;
							
							public boolean satisfyCastingRequirements() {
								//seleccionar un target
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
				
			default:
				throw new IllegalArgumentException("Error: Carta no pertenece a la coleccion.");
		}
	}
	
}
