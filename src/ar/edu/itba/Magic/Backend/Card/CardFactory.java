package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.*;
import ar.edu.itba.Magic.Backend.Abilities.ActivatedPermanentAbility;
import ar.edu.itba.Magic.Backend.Abilities.AutomaticPermanentAbility;
import ar.edu.itba.Magic.Backend.Abilities.AutomaticSpellAbility;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Abilities.SpellAbility;
import ar.edu.itba.Magic.Backend.Effects.AutomaticLastingEffect;
import ar.edu.itba.Magic.Backend.Effects.LastingEffect;
import ar.edu.itba.Magic.Backend.Interfaces.CardImplementation;
import ar.edu.itba.Magic.Backend.Interfaces.DamageTaking;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.AttributeModifier;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardType;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Enchantment;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;
import ar.edu.itba.Magic.Backend.Stack.GameStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class CardFactory {
	
	private Match match = Match.getMatch();
	private GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	private GameStack gameStack = GameStack.getGameStackInstance();
	private HashMap<CardType, CardImplementation> cardImplementations;
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
        cardImplementations = new HashMap<>();
        this.initiateCards();
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	public Card getCard(CardType cardType) {
		
		return cardType.createCard();  //probando
		
       /* if(cardName != null)
            cardImplementations.get(cardName).createCard();
        throw new IllegalArgumentException();*/
	}

    private void initiateCards(){
    	
    	cardImplementations.put(CardType.AIR_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardType.AIR_ELEMENTAL, Color.BLUE, attributes, 2, 3, 4, 4);
            }
        });
    	
    	cardImplementations.put(CardType.ANKH_OF_MISHRA, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
                return new ArtifactCard(CardType.ANKH_OF_MISHRA, 2,
                		new AutomaticPermanentAbility() {

		                	@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.PERMANENT_ENTERS_PLAY)) {
									if(gameEvent.getObject1() instanceof Land) {
										((Permanent)gameEvent.getObject1()).getController().takeDamage(2);
									}
								}
							}
                });
            }
        });

    	cardImplementations.put(CardType.ASPECT_OF_WOLF, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.ASPECT_OF_WOLF, Color.GREEN, 1, 1,
						new AutomaticPermanentAbility() {
							Integer previousForests;
							
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO select target creature
								// return true
								// sino
								return false;
							}
							
							@Override
							public void executeOnEntering() {
								previousForests = 0;
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								List<Land> lands = new LinkedList<Land>();
								Integer forests = 0;
								lands = this.getSourcePermanent().getController().getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardType.FOREST)) {
										forests++;
									}
								}
								if(forests != previousForests) {
									// TODO me fui a dormir - terminar maniana
								}
								
								
								
							}
					
				});
            }
        });
 
        cardImplementations.put(CardType.BAD_MOON, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.BAD_MOON, Color.BLACK, 1, 1, 
                		new AutomaticPermanentAbility() {
                	
                            /**
                             * Adds Bad Moon's automatic ability to the GameEventHandler.
                             */
                            @Override
                            public void executeOnEntering() {
                                gameEventHandler.addListener(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.removeListener(this);
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLACK)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectsFromSourceAbility(this);
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
                            	List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLACK)) {
                                        if(!creature.isAffectedByAbility(this)) {
                                            LastingEffect newEffect = new StaticStatModifier(this, 1, 1);  
                                            creature.applyLastingEffect(newEffect);
                                        }
                                    }
                                 }
                              }
                          });
                }
        });

        cardImplementations.put(CardType.BALL_LIGHTNING, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.TRAMPLE);
                attributes.remove(Attribute.SUMMONING_SICKNESS);
                return new CreatureCard(CardType.BALL_LIGHTNING, Color.RED, attributes, 3, 0, 6, 1);
            }
        });

        cardImplementations.put(CardType.BIRD_MAIDEN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardType.BIRD_MAIDEN, Color.RED, attributes, 1, 2, 1, 2);
            }
        });
        
        cardImplementations.put(CardType.BIRDS_OF_PARADISE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardType.BIRDS_OF_PARADISE, Color.GREEN, attributes, 1, 0, 0, 1,
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped: add one mana of player's choice, tap
							}
                });
            }
        });
        
        cardImplementations.put(CardType.BLACK_LOTUS, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
                return new ArtifactCard(CardType.BLACK_LOTUS, 0,
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO player select one color
								// manapool.add(3 of chosen color)
								this.getSourcePermanent().destroy();
							}
                });
            }
        });
        
        cardImplementations.put(CardType.BLIGHT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.BLIGHT, Color.BLACK, 2, 0,
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
                                gameEventHandler.addListener(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.removeListener(this);
                                target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            }

                            @Override
                            public void executeOnEvent(GameEvent gameEvent) {
                                if (target.isTapped())
                                    destroyAtEndOfTurn = true;

                                if (gameEvent.getDescriptor().equals(Event.END_OF_TURN))
                                    if (destroyAtEndOfTurn == true)
                                        target.destroy();
                            }
                        });
            }
        });

        cardImplementations.put(CardType.BLOOD_LUST, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.BLOOD_LUST, Color.RED, 1, 1,
						new SpellAbility() {
							Creature target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = select target creature
									// return true;
								// else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									AutomaticLastingEffect newEffect = new AutomaticLastingEffect(this) {
										Integer previousTargetDefense;
	
										@Override
										public void executeOnEvent(GameEvent gameEvent) {
											if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
												this.getTarget().removeLastingEffect(this);
											}
										}
	
										@Override
										public void applyEffect() {
											((Creature)this.getTarget()).increaseAttack(4);
											previousTargetDefense = ((Creature)this.getTarget()).getDefense();
											if(previousTargetDefense <= 4) {
												((Creature)this.getTarget()).setDefense(1);
											}
											else {
												((Creature)this.getTarget()).decreaseDefense(4);
											}
										}
	
										@Override
										public void undoEffect() {
											((Creature)this.getTarget()).decreaseAttack(4);
											((Creature)this.getTarget()).setDefense(previousTargetDefense);
										}
									};
									target.applyLastingEffect(newEffect);
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.BOG_IMP, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.BOG_IMP, Color.BLACK, attributes, 1, 1, 1, 1);
            }
        });
        
        cardImplementations.put(CardType.BOG_WRAITH, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.SWAMPWALK);
				return new CreatureCard(CardType.BOG_WRAITH, Color.BLACK, attributes, 1, 3, 3, 3);
            }
        });
        
        cardImplementations.put(CardType.BURROWING, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.BURROWING, Color.RED, 1, 0,
						new PermanentAbility() {
							Creature target;
							
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = selec target creature without flying
									// return true;
								// else
									return false;
							}
	
							@Override
							public void executeOnEntering() {
								LastingEffect newEffect = new StaticAttributeModifier(this, AttributeModifier.ADD, Attribute.MOUNTAINWALK);
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								target.applyLastingEffect(newEffect);
							}
							
							@Override
							public void executeOnExit() {
								target.removeLastingEffectsFromSourceAbility(this);
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
							}
					});
            }
        });

        cardImplementations.put(CardType.CARNIVOROUS_PLANT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.CARNIVOROUS_PLANT, Color.GREEN, attributes, 1, 3, 4, 5);
            }
        });

        cardImplementations.put(CardType.CARRION_ANTS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.CARRION_ANTS, Color.BLACK, attributes, 2, 2, 1, 1,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO request pagar 1 de mana colorless, then {
								AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 1);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });
        
        cardImplementations.put(CardType.CRAW_WURM, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.CRAW_WURM, Color.GREEN, attributes, 2, 4, 6, 4);
            }
        });

        cardImplementations.put(CardType.CRUMBLE, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.CRUMBLE, Color.GREEN, 1, 0,
						new SpellAbility() {
							Artifact target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO select target Artifact
									// return true;
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
									target.getController().increaseHealth(target.getColorlessManaCost());
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.CRUSADE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.CRUSADE, Color.WHITE, 2, 0, 
                		new AutomaticPermanentAbility() {
                	
                            /**
                             * Adds Crusade's automatic ability to the GameEventHandler.
                             */
                            @Override
                            public void executeOnEntering() {
                                gameEventHandler.addListener(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.removeListener(this);
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.WHITE)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectsFromSourceAbility(this);
                                        }
                                    }
                                }
                            }

                            /**
                             * On every GameEvent checks if any black creatures are unaffected by Crusade and
                             * in that case applies them a LastingEffect.
                             */
                            @Override
                            public void executeOnEvent(GameEvent gameEvent) {
                            	List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.WHITE)) {
                                        if(!creature.isAffectedByAbility(this)) {
                                            LastingEffect newEffect = new StaticStatModifier(this, 1, 1);  
                                            creature.applyLastingEffect(newEffect);
                                        }
                                    }
                                 }
                              }
                          });
                }
        });
        
        cardImplementations.put(CardType.DARK_RITUAL, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.DARK_RITUAL, Color.BLACK, 1, 0,
						new SpellAbility() {

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}
		
							@Override
							public void resolveInStack() {
								// TODO add 3 black mana to mana pool
							}
				});
            }
        });

        cardImplementations.put(CardType.DESERT_TWISTER, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.DESERT_TWISTER, Color.GREEN, 2, 4,
						new SpellAbility() {
							Permanent target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO select target Permanent
									// return true;
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.DINGUS_EGG, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.DINGUS_EGG, 4,
						new AutomaticPermanentAbility() {
					
							@Override 
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.PERMANENT_LEAVES_PLAY)) {
									((Permanent)gameEvent.getObject1()).getController().takeDamage(2);
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.DIVINE_TRANSFORMATION, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.DIVINE_TRANSFORMATION, Color.WHITE, 2, 2,
                        new PermanentAbility() {
                            Creature target;
                            
                            public boolean satisfyCastingRequirements() {
	                        	// TODO seleccionar target Creature
	                            //return true
	                            //else
	                            return false;
                            }
                            
                            public void executeOnEntering() {
                            	LastingEffect newEffect = new StaticStatModifier(this, 3, 3);
                            	target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.removeLastingEffectsFromSourceAbility(this);
                            }  
                });
            }
        });
        
        cardImplementations.put(CardType.DRAIN_POWER, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.DRAIN_POWER, Color.BLUE, 2, 0,
						new SpellAbility() {

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								Player opponent = match.getOpposingPlayerFrom(this.getSourceCard().getController());
								List<Land> opponentLands = opponent.getLands();
								for(Land opponentLand : opponentLands) {
									if(!opponentLand.isTapped()) {
										opponentLand.tap();
										// TODO agregar el mana correspondiente a mana pool de this.getController()
									}
								}
							}
				});
            }
        });

        cardImplementations.put(CardType.DURKWOOD_BOARS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.DURKWOOD_BOARS, Color.GREEN, attributes, 1, 4, 4 ,4);
            }
        });
        
        cardImplementations.put(CardType.DWARVEN_DEMOLITION_TEAM, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.DWARVEN_DEMOLITION_TEAM, Color.RED, attributes, 1, 2, 1, 1, 
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								//	TODO Creature target;
								//  select target creature with WALL attribute, then
								//	target.destroy();
							}
					
				});
            }
        });
        
        cardImplementations.put(CardType.EARTH_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.EARTH_ELEMENTAL, Color.RED, attributes, 2, 3, 4, 5);
            }
        });

        cardImplementations.put(CardType.ELVISH_ARCHERS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardType.ELVISH_ARCHERS, Color.GREEN, attributes, 1, 1, 2, 1);
            }
        });
        
        cardImplementations.put(CardType.FIRE_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.FIRE_ELEMENTAL, Color.RED, attributes, 2, 3, 5, 4);
            }
        });

        cardImplementations.put(CardType.FISSURE, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.FISSURE, Color.RED, 2, 3,
						new SpellAbility() {
							Permanent target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = selec target land or creature
									// return true;
								// else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.FLIGHT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.FLIGHT, Color.BLUE, 1, 0,
						new PermanentAbility() {
							Creature target;
							
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = selec target creature without flying
									// return true;
								// else
									return false;
							}

							@Override
							public void executeOnEntering() {
								LastingEffect newEffect = new StaticAttributeModifier(this, AttributeModifier.ADD, Attribute.FLYING);
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								target.applyLastingEffect(newEffect);
							}
							
							@Override
							public void executeOnExit() {
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
								target.removeLastingEffectsFromSourceAbility(this);;
							}
				});
            }
        });

        cardImplementations.put(CardType.FLOOD, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.FLOOD, Color.BLUE, 1, 0,
						new ActivatedPermanentAbility() {
							Creature target;

							/**
							 * Pay 2 blue mana to tap a target creature without flying.
							 */
							@Override
							public void executeOnActivation() {
								// TODO
								//pay mana cost
								//select target creature without flying
								target.tap();
							}
				});
            }
        });
        
        cardImplementations.put(CardType.FOREST, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardType.FOREST, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 green mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardType.FORCE_OF_NATURE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				return new CreatureCard(CardType.FORCE_OF_NATURE, Color.GREEN, attributes, 4, 2, 8, 8,
						new AutomaticPermanentAbility() {

							@Override 
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == this.getSourcePermanent().getController())	{
										// TODO player pay 4 green mana or suffer 8 damage
									}
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.FROZEN_SHADE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.FROZEN_SHADE, Color.BLACK, attributes, 1, 2, 0, 1, 
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								LastingEffect newEffect = new OneTurnStatModifier(this, 1, 1);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
                });
            }
        });
        
        cardImplementations.put(CardType.GRANITE_GARGOYLE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.GRANITE_GARGOYLE, Color.RED, attributes, 1, 2, 2, 2,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO pay 1 red mana, then
								LastingEffect newEffect = new OneTurnStatModifier(this, 0, 1);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });     
        
        cardImplementations.put(CardType.GRAY_OGRE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.GRAY_OGRE, Color.RED, attributes, 1, 2, 2, 2);
            }
        });
        
        cardImplementations.put(CardType.GRIZZLY_BEARS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.GRIZZLY_BEARS, Color.GREEN, attributes, 1, 1, 2, 2);
            }
        });
        
        cardImplementations.put(CardType.HOLY_STRENGTH, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.HOLY_STRENGTH, Color.WHITE, 2, 0,
                        new PermanentAbility() {
                            Creature target;
                            
                            public boolean satisfyCastingRequirements() {
	                        	// TODO seleccionar target Creature
	                            //return true
	                            //else
	                            return false;
                            }
                            
                            public void executeOnEntering() {
                            	LastingEffect newEffect = new StaticStatModifier(this, 1, 2);
                            	target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.removeLastingEffectsFromSourceAbility(this);
                            }  
                });
            }
        });

        cardImplementations.put(CardType.HOWL_FROM_BEYOND, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.HOWL_FROM_BEYOND, Color.BLACK, 1, 1,
						new SpellAbility() {
							Integer attackBonus = 1;
							Creature target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO seleccionar criatura target
									//sino return false
								// TODO pagar extra colorless mana ? hacer asi o sino implementar costo variado

								return true;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, attackBonus, 0);
									target.applyLastingEffect(newEffect);
								}
							}

				});
            }
        });
        
        cardImplementations.put(CardType.HILL_GIANT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.HILL_GIANT, Color.RED, attributes, 1, 3, 3, 3);
            }
        });
        
        cardImplementations.put(CardType.HURLOON_MINOTAUR, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.HURLOON_MINOTAUR, Color.RED, attributes, 2, 1, 2, 3);
            }
        });
        
        cardImplementations.put(CardType.IRONROOT_TREEFOLK, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.IRONROOT_TREEFOLK, Color.GREEN, attributes, 1, 4, 3, 5);
            }
        });
        
        cardImplementations.put(CardType.ISLAND, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardType.ISLAND, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 blue mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardType.JUMP, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.JUMP, Color.BLUE, 1, 0,
						new SpellAbility() {
							Creature target;
					
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = selec target creature without flying
								// return true;
								// else
								return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									AutomaticLastingEffect newEffect = new OneTurnAttributeModifier(this, AttributeModifier.ADD, Attribute.FLYING);
									target.applyLastingEffect(newEffect);
								}
							}
				});
            }
        });

        cardImplementations.put(CardType.JUNUN_EFREET, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.JUNUN_EFREET, Color.BLACK, attributes, 2, 1, 3, 3,
						new AutomaticPermanentAbility() {

							@Override 
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
				
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
            }
        });
        
        cardImplementations.put(CardType.KARMA, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.KARMA, Color.WHITE, 2, 2,
						new AutomaticPermanentAbility() {
					
							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}

							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									List<Land> swamps = new LinkedList<Land>();
									swamps = ((Player)gameEvent.getObject1()).getLands();
									for(Land swamp : swamps) {
										swamp.getController().takeDamage(1);
									}
								}
							}
				});
            }
        });

        cardImplementations.put(CardType.KISMET, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.KISMET, Color.WHITE, 1, 3,
						new AutomaticPermanentAbility() {
							Player targetPlayer;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO select target player
									// return true;
								// else
									return false;
							}

							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}

							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.PERMANENT_ENTERS_PLAY)) {
									if(((Permanent)gameEvent.getObject1()).getController() == targetPlayer){
										if(gameEvent.getObject1() instanceof Creature ||
										   gameEvent.getObject1() instanceof Artifact ||
										   gameEvent.getObject1() instanceof Land		) {
											((Permanent)gameEvent.getObject1()).tap();
										}
									}
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.LANCE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.LANCE, Color.WHITE, 1, 0,
						new PermanentAbility() {
							Creature target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = seleccionar target creature que no tenga First Strike
									//return true
								//else
									return false;
							}

							@Override
							public void executeOnEntering() {
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								target.addAttribute(Attribute.FIRST_STRIKE);
							}

							@Override
							public void executeOnExit() {
								target.removeAttribute(Attribute.FIRST_STRIKE);
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());

							}
				});
            }
        });
        
        cardImplementations.put(CardType.LAND_LEECHES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardType.LAND_LEECHES, Color.GREEN, attributes, 2, 1, 2, 2);
            }
        });
        
        cardImplementations.put(CardType.LIGHTNING_BOLT, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.LIGHTNING_BOLT, Color.RED, 1, 0,
						new SpellAbility() {
							DamageTaking target; // Object target ?

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO select target
									// return true;
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target instanceof Player) {
									target.takeDamage(3);
								}
								else {
									if(((Permanent)target).isStillALegalTarget()) {
										target.takeDamage(3);
								}
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.LLANOWAR_ELVES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.LLANOWAR_ELVES, Color.GREEN, attributes, 1, 0, 1, 1,
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped: add one mana green mana, tap
							}
                });
            }
        });

        cardImplementations.put(CardType.LORD_OF_THE_PIT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.LORD_OF_THE_PIT, Color.BLACK, attributes, 3, 4, 7, 7,
						new AutomaticPermanentAbility() {

							/**
							 * Adds Lord of the Pit's automatic ability to the GameEventHandler.
							 */
							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
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
            }
        });

        cardImplementations.put(CardType.LOST_SOUL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.SWAMPWALK);
				return new CreatureCard(CardType.LOST_SOUL, Color.BLACK, attributes, 2, 1, 2, 1);
            }
        });
        
        cardImplementations.put(CardType.MARSH_GAS, new CardImplementation() {
            @Override
            public InstantCard createCard() {
                return new InstantCard(CardType.MARSH_GAS, Color.BLACK, 1, 0, 
                		new AutomaticSpellAbility() {

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								gameEventHandler.addListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
									gameEventHandler.removeListener(this);
	                                List<Creature> allCreatures = new LinkedList<Creature>();
	                                allCreatures.addAll(match.getPlayer1().getCreatures());
	                                allCreatures.addAll(match.getPlayer2().getCreatures());
	                                for(Creature creature : allCreatures) {
	                                	if(creature.isAffectedByAbility(this)) {
	                                		creature.removeLastingEffectsFromSourceAbility(this);
	                                	}
	                                }
								}
								else {
									List<Creature> allCreatures = new LinkedList<Creature>();
	                                allCreatures.addAll(match.getPlayer1().getCreatures());
	                                allCreatures.addAll(match.getPlayer2().getCreatures());
	                                for(Creature creature : allCreatures) {
                                        if(!creature.isAffectedByAbility(this)) {
                                            LastingEffect newEffect = new StaticStatModifier(this, -2, 0);  
                                            creature.applyLastingEffect(newEffect);
                                        }
	                                 }
								}
							}
                });
            }
        });
        
        cardImplementations.put(CardType.MONSS_GOBLIN_RAIDERS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.MONSS_GOBLIN_RAIDERS, Color.RED, attributes, 1, 0, 1, 1);
            }
        });
        
        cardImplementations.put(CardType.MOUNTAINS, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardType.MOUNTAINS, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 red mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardType.MOX_EMERALD, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.MOX_EMERALD, 0,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add one green mana
							}
				});
            }
        });
        
        cardImplementations.put(CardType.MOX_JET, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.MOX_JET, 0,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add one black mana
							}
				});
            }
        });
        
        cardImplementations.put(CardType.MOX_PEARL, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.MOX_PEARL, 0,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add one white mana
							}
				});
            }
        });
        
        cardImplementations.put(CardType.MOX_RUBY, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.MOX_RUBY, 0,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add one red mana
							}
				});
            }
        });
        
        cardImplementations.put(CardType.MOX_SAPPHIRE, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.MOX_SAPPHIRE, 0,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add one blue mana
							}
				});
            }
        });

        cardImplementations.put(CardType.NIGHTMARE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.NIGHTMARE, Color.BLACK, attributes, 1, 5, 1, 1,
						new AutomaticPermanentAbility() {

							/**
							 * Adds Nightmare's automatic ability to the GameEventHandler.
							 */
							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
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
            }
        });

        /* ver que pasa con esta si la criatura ya no untapeaba en upkeep */
        cardImplementations.put(CardType.PARALYZE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.PARALYZE, Color.BLACK, 1, 0,
						new AutomaticPermanentAbility() {
							Creature target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = seleccionar target creature
									//return true
								//else
									return false;
							}

							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								LastingEffect newEffect = new StaticAttributeModifier(this, AttributeModifier.REMOVE, Attribute.UNTAPS_DURING_UPKEEP);
								target.applyLastingEffect(newEffect);
							}

							@Override
							public void executeOnExit() {
								target.removeLastingEffectsFromSourceAbility(this);
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UNTAP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										// TODO pedir si quiere pagar 4 de colorless mana
										//then untap
									}
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.PEARLED_UNICORN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.PEARLED_UNICORN, Color.WHITE, attributes, 1, 2, 2, 2);
            }
        });
        
        cardImplementations.put(CardType.PLAINS, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardType.PLAINS, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 white mana
							}
        		});
        	}
        });

        cardImplementations.put(CardType.RADJAN_SPIRIT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.RADJAN_SPIRIT, Color.GREEN, attributes, 1, 3, 3, 2,
						new ActivatedPermanentAbility() {
							Creature target;

							@Override
							public void executeOnActivation() {
								// TODO target = select target creature with flying, then {
								AutomaticLastingEffect newEffect = new OneTurnAttributeModifier(this, AttributeModifier.REMOVE, Attribute.FLYING);
								target.applyLastingEffect(newEffect);
							}
						});
            	}
        	});
        
        cardImplementations.put(CardType.ROC_OF_KHER_RIDGES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.ROC_OF_KHER_RIDGES, Color.RED, attributes, 1, 3, 3, 3);
            }
        });

        cardImplementations.put(CardType.ROYAL_ASSASIN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.ROYAL_ASSASIN, Color.BLACK, attributes, 2, 1, 1, 1,
						new ActivatedPermanentAbility() {

							/**
							 * Taps royal assassin to destroy a target tapped creature.
							 */
							@Override
							public void executeOnActivation() {
								if(this.getSourcePermanent().isTapped())
									System.out.println("cannot tap"); //TODO cambiar esto
								else {
									//TODO select target tapped creature, then {
									//this.tap()
									//target.destroy();
								}
							}
				});
            }
        });

        cardImplementations.put(CardType.SAVANNAH_LIONS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardType.SAVANNAH_LIONS, Color.WHITE, attributes, 1, 0, 2, 1);
            }
        });
        
        cardImplementations.put(CardType.SCRYB_SPRITES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardType.SCRYB_SPRITES, Color.GREEN, attributes, 1, 0, 1, 1);
            }
        });
        
        cardImplementations.put(CardType.SEA_SERPENT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.SEA_SERPENT, Color.BLUE, attributes, 1, 5, 5, 5, 
						new AutomaticPermanentAbility() {

							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								boolean destroyThis = true;
								boolean opponentHasIsland = false;
								List<Land> lands;
								Player opponent = match.getOpposingPlayerFrom(this.getSourcePermanent().getController());
								
								lands = opponent.getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardType.ISLAND)) {
										opponentHasIsland = true;
									}
								}
								if(opponentHasIsland) {
									if(!this.getSourcePermanent().containsAttribute(Attribute.CAN_ATTACK)) {
										this.getSourcePermanent().addAttribute(Attribute.CAN_ATTACK);
									}
								}
								else {
									if(this.getSourcePermanent().containsAttribute(Attribute.CAN_ATTACK)) {
										this.getSourcePermanent().removeAttribute(Attribute.CAN_ATTACK);
									}
								}
								
								lands = this.getSourcePermanent().getController().getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardType.ISLAND)) {
										destroyThis = false;
									}
								}
								if(destroyThis == true) {
									this.getSourcePermanent().destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.SEGOVIAN_LEVIATHAN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.ISLANDWALK);
				return new CreatureCard(CardType.SEGOVIAN_LEVIATHAN, Color.BLUE, attributes, 1, 4, 3, 3);
            }
        });

        cardImplementations.put(CardType.SERRA_ANGEL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard(CardType.SERRA_ANGEL, Color.WHITE, attributes, 2, 3, 4, 4);
            }
        });
        
        cardImplementations.put(CardType.SHANODIN_DRYADS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FORESTWALK);
                return new CreatureCard(CardType.SHANODIN_DRYADS, Color.GREEN, attributes, 1, 0, 1, 1);
            }
        });
        
        cardImplementations.put(CardType.SHIVAN_DRAGON, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.SHIVAN_DRAGON, Color.RED, attributes, 2, 4, 5, 5,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO pay 1 red mana, then
								LastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });        
        
        cardImplementations.put(CardType.SINDBAD, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.SINDBAD, Color.BLUE, attributes, 1, 1, 1, 1,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								if(!this.getSourcePermanent().isTapped()) {
									this.getSourcePermanent().tap();
									Card card = this.getSourcePermanent().getController().drawCard();
									if(!(card instanceof LandCard)) {
										this.getSourcePermanent().getController().discardCard(card);
									}
								}
								// TODO else, "cannot activate, sinbad is tapped"
							}
				});
            }
        });
        
        cardImplementations.put(CardType.SINKHOLE, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.SINKHOLE, Color.BLACK, 2, 0,
						new SpellAbility() {
							Land target;
					
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = seleccionar target land
									//return true
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.SOL_RING, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.SOL_RING, 1,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO tap, add 2 colorless mana
							}
				});
            }
        });
        
        cardImplementations.put(CardType.STONE_RAIN, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.STONE_RAIN, Color.RED, 1, 2,
						new SpellAbility() {
							Land target;
					
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = seleccionar target land
									//return true
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.SUNKEN_CITY, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.SUNKEN_CITY, Color.BLUE, 2, 0, 
                		new AutomaticPermanentAbility() {
                	
                            @Override
                            public void executeOnEntering() {
                                gameEventHandler.addListener(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.removeListener(this);
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLUE)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectsFromSourceAbility(this);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void executeOnEvent(GameEvent gameEvent) {
                            	if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
                            		if(gameEvent.getObject1() == this.getSourcePermanent().getController()) {
                            			// TODO player pay 2 blue or this.getSourcePermanent().destroy();
                            		}
                            	}
                            	List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLUE)) {
                                        if(!creature.isAffectedByAbility(this)) {
                                            LastingEffect newEffect = new StaticStatModifier(this, 1, 1);  
                                            creature.applyLastingEffect(newEffect);
                                        }
                                    }
                                 }
                              }
                          });
                }
        });
        
        cardImplementations.put(CardType.SWAMP, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardType.SWAMP, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 black mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardType.TERROR, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.TERROR, Color.BLACK, 1, 1,
						new SpellAbility() {

							private Creature target;

							/**
							 * Player must select a target non black non artifact creature.
							 */
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO
								// seleccionar un target
									// return true
								// else
								return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}

				});
            }
        });
        
        cardImplementations.put(CardType.THE_RACK, new CardImplementation() {
            @Override
            public ArtifactCard createCard() {
				return new ArtifactCard(CardType.THE_RACK, 1,
						new AutomaticPermanentAbility() {
					
							@Override
							public void executeOnEntering() {
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								Player opponent = match.getOpposingPlayerFrom(this.getSourcePermanent().getController());
								Integer handSize;
								Integer damage;
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == opponent) {
										handSize = opponent.getHand().size();
										if(handSize < 3) {
											damage = 3 - handSize;
											opponent.takeDamage(damage);
										}
									}
								}	
							}
				});
            }
        });

        cardImplementations.put(CardType.TUNDRA_WOLVES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardType.TUNDRA_WOLVES, Color.WHITE, attributes, 1, 0, 1, 1);
            }
        });
        
        cardImplementations.put(CardType.TUNNEL, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardType.TUNNEL, Color.RED, 1, 0,
						new SpellAbility() {
							Creature target;
					
							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = seleccionar target wall
									//return true
								//else
									return false;
							}

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								if(target.isStillALegalTarget()) {
									target.destroy();
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardType.UNHOLY_STRENGTH, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.UNHOLY_STRENGTH, Color.BLACK, 2, 0,
                        new PermanentAbility() {
                            Creature target;
                            
                            public boolean satisfyCastingRequirements() {
	                        	// TODO seleccionar target Creature
	                            //return true
	                            //else
	                            return false;
                            }
                            
                            public void executeOnEntering() {
                            	LastingEffect newEffect = new StaticStatModifier(this, 2, 1);
                            	target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeLastingEffectsFromSourceAbility(this);
                            	target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            }  
                });
            }
        });
        
        cardImplementations.put(CardType.UNSTABLE_MUTATION, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.UNSTABLE_MUTATION, Color.BLUE, 1, 0,
                        new AutomaticPermanentAbility() {
                            Creature target;
                            LastingEffect startingBuff;
                            
                            @Override
                            public boolean satisfyCastingRequirements() {
	                        	// TODO seleccionar target Creature
	                            //return true
	                            //else
	                            return false;
                            }
                            
                            @Override
                            public void executeOnEntering() {
                            	gameEventHandler.addListener(this);
                            	startingBuff = new StaticStatModifier(this, 3, 3);
                            	target.applyLastingEffect(startingBuff);
                            }
                            
                            @Override
                            public void executeOnExit() {
                            	gameEventHandler.removeListener(this);
                            	target.removeLastingEffect(startingBuff);
                            }

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										LastingEffect newEffect = new StaticStatModifier(this, -1, -1);
		                            	target.applyLastingEffect(newEffect);
									}
								}
							}  
                });
            }
        });
        
        cardImplementations.put(CardType.WALL_OF_FIRE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.WALL_OF_FIRE, Color.RED, attributes, 2, 1, 0, 5,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO pay 1 red mana, then
								LastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });        
   
        cardImplementations.put(CardType.WALL_OF_ICE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.WALL_OF_ICE, Color.GREEN, attributes, 1, 2, 0, 7);
            }
        });
        
        cardImplementations.put(CardType.WALL_OF_STONE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.WALL_OF_STONE, Color.RED, attributes, 2, 1, 0, 8);
            }
        });
        
        cardImplementations.put(CardType.WALL_OF_SWORDS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardType.WALL_OF_SWORDS, Color.WHITE, attributes, 1, 3, 3, 5);
            }
        });
        
        cardImplementations.put(CardType.WALL_OF_WATER, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.WALL_OF_WATER, Color.BLUE, attributes, 2, 1, 0, 5, 
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO pay 1 blue mana, then
								LastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });
        
        cardImplementations.put(CardType.WALL_OF_WOOD, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardType.WALL_OF_WOOD, Color.GREEN, attributes, 1, 0, 0, 3);
            }
        });
                
        cardImplementations.put(CardType.WANDERLUST, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.WANDERLUST, Color.GREEN, 1 ,2,
						new AutomaticPermanentAbility() {
							Creature target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = select target creature;
									//return true;
								//else
									return false;
							}
							
							@Override
							public void executeOnEntering() {
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										target.getController().takeDamage(1);
									}
								}
							}
						});
            }
        });
        
        cardImplementations.put(CardType.WAR_MAMMOTH, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				return new CreatureCard(CardType.WAR_MAMMOTH, Color.GREEN, attributes, 1, 3, 3, 3);
            }
        });
        
        cardImplementations.put(CardType.WARP_ARTIFACT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardType.WARP_ARTIFACT, Color.BLACK, 2 ,0,
						new AutomaticPermanentAbility() {
							Artifact target;

							@Override
							public boolean satisfyCastingRequirements() {
								// TODO target = select target artifact;
									//return true;
								//else
									return false;
							}
							
							@Override
							public void executeOnEntering() {
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.addListener(this);
							}
							
							@Override
							public void executeOnExit() {
								target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
								gameEventHandler.removeListener(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										target.getController().takeDamage(1);
									}
								}
							}
						});
            }
        });
        
        cardImplementations.put(CardType.WATER_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardType.WATER_ELEMENTAL, Color.BLUE, attributes, 2, 3, 5, 4);
            }
        });
        
        cardImplementations.put(CardType.WEAKNESS, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardType.WEAKNESS, Color.BLACK, 1, 0,
                        new PermanentAbility() {
                            Creature target;
                            
                            public boolean satisfyCastingRequirements() {
	                        	// TODO seleccionar target Creature
	                            //return true
	                            //else
	                            return false;
                            }
                            
                            public void executeOnEntering() {
                            	LastingEffect newEffect = new StaticStatModifier(this, -2, -1);
                            	target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeAttachedEnchantment((Enchantment)this.getSourcePermanent());
                            	target.removeLastingEffectsFromSourceAbility(this);
                            }  
                });
            }
        });
        
        cardImplementations.put(CardType.WRATH_OF_GOD, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardType.WRATH_OF_GOD, Color.WHITE, 2, 2,
						new SpellAbility() {

							@Override
							public void sendToStack() {
								gameStack.addStackAction(this);
							}

							@Override
							public void resolveInStack() {
								List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                	if(creature.isStillALegalTarget()) {
                                    	creature.destroy();
                                	}
                                }
							}
				});
            }
        });

        cardImplementations.put(CardType.ZEPHYR_FALCON, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard(CardType.ZEPHYR_FALCON, Color.BLUE, attributes, 1, 1, 1, 1);
            }
        });
    }
    
}