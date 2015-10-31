package ar.edu.itba.Magic.Backend.Card;
import java.util.LinkedList;
import java.util.List;
import ar.edu.itba.Magic.Backend.*;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;

public class CardFactory {
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	Match match = Match.getMatch();
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
		
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	/**
	 * Creates a list of default attributes contained by creatures.
	 * 
	 * @return Returns a list of default attributes contained by creatues.
	 */
	public List<Attribute> getDefaultCreatureAttributes() {
		List<Attribute> attributes = new LinkedList<Attribute>();
		//agregar
	
		return attributes;
	}
	
	public Card getCard(String cardName) {

		List<Attribute> attributes;
		
		switch(cardName) {
		
			case "Bad Moon":
				return new EnchantmentCard("Bad Moon", "enchantment", Color.BLACK, 1, 1, 
						new AutomaticPermanentAbility() {
					
							/**
							 * Adds Bad Moon's automatic ability to the GameEventHandler.
							 */
							@Override 
							public void executeOnIntroduction() {
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(match.getPlayer1().getPermanentsInPlay());
								permanents.addAll(match.getPlayer2().getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent instanceof Creature) {
										if(permanent.getColor().equals("black")) {
											
										}
											
									}
								}
								
							}				
				});

			case "Bog Imp":
				attributes = getDefaultCreatureAttributes();
				//attributes.add("flying");
				return new CreatureCard("Bog Imp", "creature", Color.BLACK, attributes, 1, 1, 1, 1);
				
				
			case "Flood":
				return new EnchantmentCard("Flood", "enchantment", Color.BLUE, 1, 0, 
						new ActivatedPermanentAbility() {
							
							/**
							 * Pay 2 blue mana to tap a target creature without flying.
							 */
							@Override
							public void executeOnActivation() {
								//TODO
								//pay mana cost
								//select target creature without flying
								//creature.tap();
							}
				});
				
			case "Lord of the Pit":
				attributes = getDefaultCreatureAttributes();
				//attributes.add("trample");
				//attributes.add("flying");
				return new CreatureCard("Lord Of The Pit", "creature", Color.BLACK, attributes, 3, 4, 7, 7,
						new AutomaticPermanentAbility() {
							
							/**
							 * Adds Lord of the Pit's automatic ability to the GameEventHandler.
							 */
							@Override
							public void executeOnIntroduction() {
								gameEventHandler.add(this);
							}
							
							/**
							 * Activates on Lord of the Pit's controller's upkeep. Requires the player to sacrifice
							 * a creature or Lord of the Pit deals him 7 damage.
							 */
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals("upkeep_step"))
									if(gameEvent.getObject1().equals((this.getSource()).getController())) {
										//TODO 
										//select a creature, destroy it
										//otherwise, suffer 7 damage
									}
							}
				});
				
				
			case "Nightmare":
				attributes = getDefaultCreatureAttributes();
				//attributes.add("flying");
				return new CreatureCard("Nightmare", "creature", Color.BLACK, attributes, 1, 5, 1, 1, 
						new AutomaticPermanentAbility() {
							
							/**
							 * Adds Nightmare's automatic ability to the GameEventHandler. Executes a generic
							 * game event to get Nightmare's attack and defense started.
							 */
							@Override
							public void executeOnIntroduction() {
								gameEventHandler.add(this);
								gameEventHandler.notifyGameEvent(new GameEvent("generic_event"));
							}
							
							/**
							 * Activates on every game event. Sets Nightmare's attack and defense equal to
							 * the ammount of Swamps it's controller has in play.
							 */
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								Integer swamps = 0;
								Player controller = (this.getSource()).getController();
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(controller.getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent.getName().equals("Swamp")) 
										swamps++;
								}
								((Creature)this.getSource()).setAttack(swamps);
								((Creature)this.getSource()).setDefense(swamps);					
							}
				});
				
			case "Royal Assassin":
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard("Royal Assassin", "creature", Color.BLACK, attributes, 2, 1, 1, 1,
						new ActivatedPermanentAbility() {
							
							/**
							 * Taps royal assassin to destroy a target tapped creature.
							 */
							@Override
							public void executeOnActivation() {
								if(this.getSource().isTapped())
									System.out.println("cannot tap"); //TODO cambiar esto
								else {
									//TODO select target tapped creature
									//target.destroy();
								}								
							}		
				});

			case "Terror":
				return new InstantCard("Terror", "instant", Color.BLACK, 1, 1, 
						new SpellAbility() {
					
							private Creature target;
							
							/**
							 * Player must select a target non black non artifact creature.
							 */
							@Override
							public boolean satisfyCastingRequirements() {
								//TODO
								//seleccionar un target
									//return true
								//else
								return false;
							}
							
							@Override
							public void sendToStack() {
								//TODO
								//gamestack.add(this)
							}
							
							@Override
							public void resolveInStack() {
								target.destroy();
							}
					
				});
				
			default:
				throw new IllegalArgumentException("Error: Carta no pertenece a la coleccion.");
		}
	}
	
}
