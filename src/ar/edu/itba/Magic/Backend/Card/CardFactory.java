package ar.edu.itba.Magic.Backend.Card;
import java.util.LinkedList;
import java.util.List;
import ar.edu.itba.Magic.Backend.*;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Constants.Event;

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
		
			case "Blight":
				return new EnchantmentCard("Blight", "enchantment", Color.BLACK, 2, 0,
						new AutomaticPermanentAbility() {
							Land target;
							boolean destroyAtEndOfTurn = false;
							
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO seleccionar target Land
									//return true
								//else 
									return false;
							}
							
							@Override
							public void executeOnEntering() {
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
							}
							
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(target.isTapped()) {
									destroyAtEndOfTurn = true;
								}
								
								if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
									if(destroyAtEndOfTurn == true) {
										target.destroy();
									}
								}
							}
								
				});
		
			case "Bad Moon":
				return new EnchantmentCard("Bad Moon", "enchantment", Color.BLACK, 1, 1, 
						new AutomaticPermanentAbility() {
					
							/**
							 * Adds Bad Moon's automatic ability to the GameEventHandler. Notifies a generic
							 * game event to get Bad Moon's ability started.
							 */
							@Override 
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(match.getPlayer1().getPermanentsInPlay());
								permanents.addAll(match.getPlayer2().getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent instanceof Creature) {
										if(permanent.getColor().equals(Color.BLACK)) 
											if(permanent.affectedByAbility(this)) {
												permanent.removeLastingEffectFromSourceAbility(this);
											}
									}
								}
							}
							
							/** 
							 * On every GameEvent checks if any black creatures are unaffected by Black Moon and
							 * in that case applies them a LastingEffect.
							 */
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(match.getPlayer1().getPermanentsInPlay());
								permanents.addAll(match.getPlayer2().getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent instanceof Creature) {
										if(permanent.getColor().equals(Color.BLACK)) 
											if(!permanent.affectedByAbility(this)) {
											LastingEffect newEffect = new LastingEffect() {
												
												@Override
												public void applyEffect() {
													((Creature)this.getTarget()).increaseAttack(1);
													((Creature)this.getTarget()).increaseDefense(1);
												}
												
												@Override
												public void undoEffect() {
													((Creature)this.getTarget()).decreaseAttack(1);
													((Creature)this.getTarget()).decreaseDefense(1);
												}
												
											};
											
											newEffect.setSourceAbility(this);
											permanent.applyLastingEffect(newEffect);										
										}											
									}
								}								
							}				
				});
				
			case "Bird Maiden":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard("Bird Maiden", "creature", Color.RED, attributes, 1, 2, 1, 2);

			case "Bog Imp":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard("Bog Imp", "creature", Color.BLACK, attributes, 1, 1, 1, 1);
				
			case "Carnivorous Plant":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				return new CreatureCard("Carnivorous Plant", "creature", Color.GREEN, attributes, 1, 3, 4, 5);
				
			case "Carrion Ants":
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard("Carrion Ants", "creature", Color.BLACK, attributes, 2, 2, 1, 1, 
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO request pagar 1 de mana colorless, then {
								AutomaticLastingEffect newEffect = new AutomaticLastingEffect() {

									@Override
									public void executeOnEvent(GameEvent gameEvent) {
										if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
											((PermanentAbility)this.getSourceAbility()).getSourcePermanent().removeLastingEffect(this);
										}
									}

									@Override
									public void applyEffect() {
										((Creature)this.getTarget()).increaseAttack(1);
										((Creature)this.getTarget()).increaseDefense(1);
									}

									@Override
									public void undoEffect() {
										((Creature)this.getTarget()).decreaseAttack(1);
										((Creature)this.getTarget()).decreaseDefense(1);
									}		
								};
								
								newEffect.setSourceAbility(this);
								this.getSourcePermanent().applyLastingEffect(newEffect);
								gameEventHandler.add(newEffect);
							}				
				});
				
			case "Durkwood Boars":
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard("Durkwood Boars", "creature", Color.GREEN, attributes, 1, 4, 4 ,4);
				
			case "Elvish Archers":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard("Elvish Archers", "creature", Color.GREEN, attributes, 1, 1, 2, 1);
				
			case "Flood":
				return new EnchantmentCard("Flood", "enchantment", Color.BLUE, 1, 0, 
						new ActivatedPermanentAbility() {
							
							/**
							 * Pay 2 blue mana to tap a target creature without flying.
							 */
							@Override
							public void executeOnActivation() {
								// TODO
								//pay mana cost
								//select target creature without flying
								//creature.tap();
							}
				});
				
			case "Howl from Beyond":
				return new InstantCard("Howl from Beyond", "instant", Color.BLACK, 1, 1, 
						new SpellAbility() {
							Integer attackBonus = 1;
							Creature target;
							
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO seleccionar criatura target
									//sino return false
								// TODO pagar extra colorless mana ? hacer asi o sino implementar costo variado
								//attackBonus += lo que se pago extra
								
								return true;
							}

							@Override
							public void sendToStack() {
								// TODO gamestack.add(this);						
							}

							@Override
							public void resolveInStack() {
								AutomaticLastingEffect newEffect = new AutomaticLastingEffect() {

									@Override
									public void executeOnEvent(GameEvent gameEvent) {
										if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
											this.undoEffect();
											target.removeLastingEffect(this);
										}								
									}

									@Override
									public void applyEffect() {
										((Creature)this.getTarget()).increaseAttack(attackBonus);
									}

									@Override
									public void undoEffect() {
										((Creature)this.getTarget()).decreaseAttack(attackBonus);
									}
								};
								
								newEffect.setSourceAbility(this);
								target.applyLastingEffect(newEffect);	
								gameEventHandler.add(newEffect);
							}
						
				});
				
			case "Junun Efreet":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard("Junun Efreet", "creature", Color.BLACK, attributes, 2, 1, 3, 3,
						new AutomaticPermanentAbility() {

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == this.getSourcePermanent().getController()) {
										// TODO pedir que pague 2 mana negro
											// else 
												this.getSourcePermanent().destroy();
									}
								}			
							}		
				});
				
			case "Land Leeches":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard("Land Leeches", "creature", Color.GREEN, attributes, 2, 1, 2, 2);
				
			case "Lord of the Pit":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				attributes.add(Attribute.FLYING);
				return new CreatureCard("Lord Of The Pit", "creature", Color.BLACK, attributes, 3, 4, 7, 7,
						new AutomaticPermanentAbility() {
							
							/**
							 * Adds Lord of the Pit's automatic ability to the GameEventHandler.
							 */
							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}
							
							/**
							 * Activates on Lord of the Pit's controller's upkeep. Requires the player to sacrifice
							 * a creature or Lord of the Pit deals him 7 damage.
							 */
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP))
									if(gameEvent.getObject1().equals((this.getSourcePermanent()).getController())) {
										// TODO 
										//select a creature, destroy it
										//otherwise, suffer 7 damage
									}
							}
				});
				
			case "Lost Soul":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.SWAMPWALK);
				return new CreatureCard("Lost Soul", "creature", Color.BLACK, attributes, 2, 1, 2, 1);
				
			case "Nightmare":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard("Nightmare", "creature", Color.BLACK, attributes, 1, 5, 1, 1, 
						new AutomaticPermanentAbility() {
							
							/**
							 * Adds Nightmare's automatic ability to the GameEventHandler. Notifies a generic
							 * game event to get Nightmare's attack and defense started.
							 */
							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
								gameEventHandler.notifyGameEvent(new GameEvent(Event.GENERIC_EVENT));
							}
							
							/**
							 * Activates on every game event. Sets Nightmare's attack and defense equal to
							 * the ammount of Swamps it's controller has in play.
							 */
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								Integer swamps = 0;
								Player controller = (this.getSourcePermanent()).getController();
								List<Permanent> permanents = new LinkedList<Permanent>();
								permanents.addAll(controller.getPermanentsInPlay());
								for(Permanent permanent : permanents) {
									if(permanent.getName().equals("Swamp")) 
										swamps++;
								}
								((Creature)this.getSourcePermanent()).setAttack(swamps);
								((Creature)this.getSourcePermanent()).setDefense(swamps);					
							}
				});
				
			case "Paralyze":
				return new EnchantmentCard("Paralyze", "enchantment", Color.BLACK, 1, 0, 
						new AutomaticPermanentAbility() {
							Creature target;
					
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO seleccionar target creature
									//return true
								//else 
									return false;
							}
							
							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								LastingEffect newEffect = new LastingEffect() {

									@Override
									public void applyEffect() {
										target.removeAttribute(Attribute.UNTAPS_DURING_UPKEEP);	
										target.tap();
									}

									@Override
									public void undoEffect() {
										target.addAttribute(Attribute.UNTAPS_DURING_UPKEEP);						
									}			
								};
								
								newEffect.setSourceAbility(this);
								target.applyLastingEffect(newEffect);
							}
							
							@Override
							public void executeOnExit() {
								target.removeLastingEffectFromSourceAbility(this);
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.remove(this);
							}
							
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UNTAP_STEP)) {
									if(gameEvent.getObject1() == this.getSourcePermanent().getController()) {
										// TODO pedir si quiere pagar 4 de colorless mana
										//then untap
									}
								}
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
								if(this.getSourcePermanent().isTapped())
									System.out.println("cannot tap"); //TODO cambiar esto
								else {
									//TODO select target tapped creature
									//target.destroy();
								}								
							}		
				});
				
			case "Segovian Leviathan":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.ISLANDWALK);
				return new CreatureCard("Segovian Leviathan", "creature", Color.BLUE, attributes, 1, 4, 3, 3);
				
			case "Serra Angel":
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard("Serra Angel", "creature", Color.WHITE, attributes, 2, 3, 4, 4);		

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
				
			case "Tundra Wolves":
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.FIRST_STRIKE);
				return new CreatureCard("Tunda Wolves", "creature", Color.WHITE, attributes, 1, 0, 1, 1);
				
			case "Zephyr Falcon":
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard("Zephyr Falcon", "creature", Color.BLUE, attributes, 1, 1, 1, 1);
				
			default:
				throw new IllegalArgumentException("Error: Carta no pertenece a la coleccion.");
		}
	}
	
}