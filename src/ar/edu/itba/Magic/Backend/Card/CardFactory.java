package ar.edu.itba.Magic.Backend.Card;

import ar.edu.itba.Magic.Backend.*;
import ar.edu.itba.Magic.Backend.Interfaces.CardImplementation;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class CardFactory {
	
	private GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	private HashMap<CardName, CardImplementation> cardImplementations;

	Match match = Match.getMatch();
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
        cardImplementations = new HashMap<>();
        this.initiateCards();
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	public Card getCard(CardName cardName) {
        if(cardName != null)
            cardImplementations.get(cardName).createCard();
        throw new IllegalArgumentException();
	}

    private void initiateCards(){
    	
    	cardImplementations.put(CardName.AIR_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardName.AIR_ELEMENTAL, Color.BLUE, attributes, 2, 3, 4, 4);
            }
        });

    	cardImplementations.put(CardName.ASPECT_OF_WOLF, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardName.ASPECT_OF_WOLF, Color.GREEN, 1, 1,
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
								gameEventHandler.add(this);
								gameEventHandler.notifyGameEvent(new GameEvent(Event.GENERIC_EVENT));
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								List<Land> lands = new LinkedList<Land>();
								Integer forests = 0;
								lands = this.getSourcePermanent().getController().getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardName.FOREST)) {
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
 
        cardImplementations.put(CardName.BAD_MOON, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.BAD_MOON, Color.BLACK, 1, 1, 
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
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLACK)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectFromSourceAbility(this);
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

        cardImplementations.put(CardName.BALL_LIGHTNING, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.TRAMPLE);
                attributes.remove(Attribute.SUMMONING_SICKNESS);
                return new CreatureCard(CardName.BALL_LIGHTNING, Color.RED, attributes, 3, 0, 6, 1);

            }
        });

        cardImplementations.put(CardName.BIRD_MAIDEN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardName.BIRD_MAIDEN, Color.RED, attributes, 1, 2, 1, 2);
            }
        });
        
        cardImplementations.put(CardName.BIRDS_OF_PARADISE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                attributes.add(Attribute.FLYING);
                return new CreatureCard(CardName.BIRDS_OF_PARADISE, Color.WHITE, attributes, 1, 2, 1, 2,
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped: add one mana of player's choice, tap
							}
                });
            }
        });
        
        cardImplementations.put(CardName.BLIGHT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.BLIGHT, Color.BLACK, 2, 0,
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
                                target.addAttachedEnchantment((Enchantment) this.getSourcePermanent());
                                gameEventHandler.add(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.remove(this);
                                target.removeAttachedEnchantment((Enchantment) this.getSourcePermanent());
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

        cardImplementations.put(CardName.BLOOD_LUST, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.BLOOD_LUST, Color.RED, 1, 1,
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
								// TODO stack.add(this);
							}

							@Override
							public void resolveInStack() {
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
				});
            }
        });
        
        cardImplementations.put(CardName.BOG_IMP, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardName.BOG_IMP, Color.BLACK, attributes, 1, 1, 1, 1);
            }
        });

        cardImplementations.put(CardName.CARNIVOROUS_PLANT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.WALL);
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardName.CARNIVOROUS_PLANT, Color.GREEN, attributes, 1, 3, 4, 5);
            }
        });

        cardImplementations.put(CardName.CARRION_ANTS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.CARRION_ANTS, Color.BLACK, attributes, 2, 2, 1, 1,
						new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO request pagar 1 de mana colorless, then {
								AutomaticLastingEffect newEffect = new AutomaticLastingEffect(this) {

									@Override
									public void executeOnEvent(GameEvent gameEvent) {
										if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
											this.getTarget().removeLastingEffect(this);
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

								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
				});
            }
        });
        
        cardImplementations.put(CardName.CRAW_WURM, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.CRAW_WURM, Color.GREEN, attributes, 2, 4, 6, 4);
            }
        });

        cardImplementations.put(CardName.CRUMBLE, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.CRUMBLE, Color.GREEN, 1, 0,
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
								// TODO gamestack.add(this)

							}

							@Override
							public void resolveInStack() {
								target.destroy();
								target.getController().increaseHealth(target.getColorlessManaCost());
							}
				});
            }
        });
        
        cardImplementations.put(CardName.CRUSADE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.CRUSADE, Color.WHITE, 2, 0, 
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
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.WHITE)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectFromSourceAbility(this);
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
        
        cardImplementations.put(CardName.DARK_RITUAL, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.DARK_RITUAL, Color.BLACK, 1, 0,
						new SpellAbility() {

							@Override
							public void sendToStack() {
								// TODO gamestack.add(this)
							}
		
							@Override
							public void resolveInStack() {
								// TODO add 3 black mana to mana pool
							}
				});
            }
        });

        cardImplementations.put(CardName.DESERT_TWISTER, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardName.DESERT_TWISTER, Color.GREEN, 2, 4,
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
								// TODO gamestack.add(this)

							}

							@Override
							public void resolveInStack() {
								target.destroy();
							}
				});
            }
        });
        
        cardImplementations.put(CardName.DRAIN_POWER, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardName.DRAIN_POWER, Color.BLUE, 2, 0,
						new SpellAbility() {

							@Override
							public void sendToStack() {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void resolveInStack() {
								Player opponent = this.getSourceCard().getController().getOpponent();
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

        cardImplementations.put(CardName.DURKWOOD_BOARS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.DURKWOOD_BOARS, Color.GREEN, attributes, 1, 4, 4 ,4);
            }
        });

        cardImplementations.put(CardName.ELVISH_ARCHERS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardName.ELVISH_ARCHERS, Color.GREEN, attributes, 1, 1, 2, 1);
            }
        });

        cardImplementations.put(CardName.FISSURE, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.FISSURE, Color.RED, 2, 3,
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
								// TODO gamestack.add(this);

							}

							@Override
							public void resolveInStack() {
								target.destroy();
							}
				});
            }
        });
        
        cardImplementations.put(CardName.FLIGHT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.FLIGHT, Color.BLUE, 1, 0,
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
								target.addAttribute(Attribute.FLYING);
							}
							
							@Override
							public void executeOnExit() {
								target.removeAttribute(Attribute.FLYING);
							}
				});
            }
        });

        cardImplementations.put(CardName.FLOOD, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.FLOOD, Color.BLUE, 1, 0,
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
        
        cardImplementations.put(CardName.FOREST, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardName.FOREST, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 green mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardName.FORCE_OF_NATURE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				return new CreatureCard(CardName.FORCE_OF_NATURE, Color.GREEN, attributes, 4, 2, 8, 8,
						new AutomaticPermanentAbility() {

							@Override 
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
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
        
        cardImplementations.put(CardName.FROZEN_SHADE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardName.FROZEN_SHADE, Color.BLACK, attributes, 1, 2, 0, 1, 
                		new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								LastingEffect newEffect = new OneTurnStatModifier(this, 1, 1);
								this.getSourcePermanent().applyLastingEffect(newEffect);
							}
                });
            }
        });
        
        cardImplementations.put(CardName.HOLY_STRENGTH, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.HOLY_STRENGTH, Color.WHITE, 2, 0,
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
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeLastingEffectFromSourceAbility(this);
                            }  
                });
            }
        });

        cardImplementations.put(CardName.HOWL_FROM_BEYOND, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.HOWL_FROM_BEYOND, Color.BLACK, 1, 1,
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
								AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, attackBonus, 0);
								target.applyLastingEffect(newEffect);
							}

				});
            }
        });
        
        cardImplementations.put(CardName.ISLAND, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardName.ISLAND, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 blue mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardName.JUMP, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.JUMP, Color.BLUE, 1, 0,
						new AutomaticSpellAbility() {
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
								// TODO stack.add(this);
							}

							@Override
							public void resolveInStack() {
								target.addAttribute(Attribute.FLYING);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.END_OF_TURN))	{
									target.removeAttribute(Attribute.FLYING);
								}
							}
				});
            }
        });

        cardImplementations.put(CardName.JUNUN_EFREET, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardName.JUNUN_EFREET, Color.BLACK, attributes, 2, 1, 3, 3,
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
            }
        });
        
        cardImplementations.put(CardName.KARMA, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.KARMA, Color.WHITE, 2, 2,
						new AutomaticPermanentAbility() {
							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}

							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
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

        cardImplementations.put(CardName.KISMET, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.KISMET, Color.WHITE, 1, 3,
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
								gameEventHandler.add(this);
							}

							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.PERMANENT_ENTERS_PLAY)) {
									if(((Permanent)gameEvent.getObject1()).getController() == targetPlayer){
										if(gameEvent.getObject1() instanceof Creature ||
										   gameEvent.getObject1() instanceof Artifact ||
										   gameEvent.getObject1() instanceof Land	) {
											((Permanent)gameEvent.getObject1()).tap();
										}
									}
								}
							}
				});
            }
        });
        
        cardImplementations.put(CardName.LANCE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.LANCE, Color.WHITE, 1, 0,
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
								target.addAttribute(Attribute.FIRST_STRIKE);
							}

							@Override
							public void executeOnExit() {
								target.removeAttribute(Attribute.FIRST_STRIKE);
							}
				});
            }
        });
        
        cardImplementations.put(CardName.LAND_LEECHES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardName.LAND_LEECHES, Color.GREEN, attributes, 2, 1, 2, 2);
            }
        });

        cardImplementations.put(CardName.LORD_OF_THE_PIT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.TRAMPLE);
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardName.LORD_OF_THE_PIT, Color.BLACK, attributes, 3, 4, 7, 7,
						new AutomaticPermanentAbility() {

							/**
							 * Adds Lord of the Pit's automatic ability to the GameEventHandler.
							 */
							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
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

        cardImplementations.put(CardName.LOST_SOUL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.SWAMPWALK);
				return new CreatureCard(CardName.LOST_SOUL, Color.BLACK, attributes, 2, 1, 2, 1);
            }
        });
        
        cardImplementations.put(CardName.MARSH_GAS, new CardImplementation() {
            @Override
            public InstantCard createCard() {
                return new InstantCard(CardName.MARSH_GAS, Color.BLACK, 1, 0, 
                		new AutomaticSpellAbility() {

							@Override
							public void sendToStack() {
								// TODO stack.add(this);
							}

							@Override
							public void resolveInStack() {
								gameEventHandler.add(this);
							}

							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
									gameEventHandler.remove(this);
	                                List<Creature> allCreatures = new LinkedList<Creature>();
	                                allCreatures.addAll(match.getPlayer1().getCreatures());
	                                allCreatures.addAll(match.getPlayer2().getCreatures());
	                                for(Creature creature : allCreatures) {
	                                	if(creature.isAffectedByAbility(this)) {
	                                		creature.removeLastingEffectFromSourceAbility(this);
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
        
        cardImplementations.put(CardName.MOUNTAIN, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardName.MOUNTAIN, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 red mana
							}
        		});
        	}
        });

        cardImplementations.put(CardName.NIGHTMARE, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				return new CreatureCard(CardName.NIGHTMARE, Color.BLACK, attributes, 1, 5, 1, 1,
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
            }
        });

        cardImplementations.put(CardName.PARALYZE, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.PARALYZE, Color.BLACK, 1, 0,
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
								gameEventHandler.add(this);
								target.addAttachedEnchantment((Enchantment)this.getSourcePermanent());
								LastingEffect newEffect = new LastingEffect(this) {

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
            }
        });
        
        cardImplementations.put(CardName.PEARLED_UNICORN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardName.PEARLED_UNICORN, Color.WHITE, attributes, 1, 2, 2, 2);
            }
        });
        
        cardImplementations.put(CardName.PLAINS, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardName.PLAINS, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 white mana
							}
        		});
        	}
        });

        cardImplementations.put(CardName.RADJAN_SPIRIT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.RADJAN_SPIRIT, Color.GREEN, attributes, 1, 3, 3, 2,
						new ActivatedPermanentAbility() {
							Creature target;

							@Override
							public void executeOnActivation() {
								// TODO target = select target creature with flying, then {
								AutomaticLastingEffect newEffect = new AutomaticLastingEffect(this) {

									@Override
									public void executeOnEvent(GameEvent gameEvent) {
										if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
											this.getTarget().removeLastingEffect(this);
										}
									}

									@Override
									public void applyEffect() {
										this.getTarget().removeAttribute(Attribute.FLYING);
									}

									@Override
									public void undoEffect() {
										this.getTarget().addAttribute(Attribute.FLYING);
									}
								};

								target.applyLastingEffect(newEffect);
							}
						});
            	}
        	});

        cardImplementations.put(CardName.ROYAL_ASSASIN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.ROYAL_ASSASIN, Color.BLACK, attributes, 2, 1, 1, 1,
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
            }
        });

        cardImplementations.put(CardName.SAVANNAH_LIONS, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
            	List<Attribute> attributes = new LinkedList<Attribute>();
                attributes = getDefaultCreatureAttributes();
                return new CreatureCard(CardName.SAVANNAH_LIONS, Color.WHITE, attributes, 1, 0, 2, 1);
            }
        });
        
        cardImplementations.put(CardName.SEA_SERPENT, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.SEA_SERPENT, Color.BLUE, attributes, 1, 5, 5, 5, 
						new AutomaticPermanentAbility() {

							@Override
							public void executeOnEntering() {
								gameEventHandler.add(this);
							}
							
							@Override
							public void executeOnExit() {
								gameEventHandler.remove(this);
							}
					
							@Override
							public void executeOnEvent(GameEvent gameEvent) {
								boolean destroyThis = true;
								boolean opponentHasIsland = false;
								List<Land> lands = this.getSourcePermanent().getController().getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardName.ISLAND)) {
										destroyThis = false;
									}
								}
								if(destroyThis == true) {
									this.getSourcePermanent().destroy();
								}
								lands = this.getSourcePermanent().getController().getOpponent().getLands();
								for(Land land : lands) {
									if(land.getName().equals(CardName.ISLAND)) {
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
							}
				});
            }
        });
        
        cardImplementations.put(CardName.SEGOVIAN_LEVIATHAN, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.ISLANDWALK);
				return new CreatureCard(CardName.SEGOVIAN_LEVIATHAN, Color.BLUE, attributes, 1, 4, 3, 3);
            }
        });

        cardImplementations.put(CardName.SERRA_ANGEL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard(CardName.SERRA_ANGEL, Color.WHITE, attributes, 2, 3, 4, 4);
            }
        });
        
        cardImplementations.put(CardName.SINDBAD, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.SINDBAD, Color.BLUE, attributes, 1, 1, 1, 1,
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
							}
				});
            }
        });
        
        cardImplementations.put(CardName.SINKHOLE, new CardImplementation() {
            @Override
            public SorceryCard createCard() {
				return new SorceryCard(CardName.SINKHOLE, Color.BLACK, 2, 0,
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
								// TODO gamestack.add(this);
							}

							@Override
							public void resolveInStack() {
								target.destroy();
							}
				});
            }
        });
        
        cardImplementations.put(CardName.SUNKEN_CITY, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.SUNKEN_CITY, Color.BLUE, 2, 0, 
                		new AutomaticPermanentAbility() {
                	
                            @Override
                            public void executeOnEntering() {
                                gameEventHandler.add(this);
                            }

                            @Override
                            public void executeOnExit() {
                                gameEventHandler.remove(this);
                                List<Creature> allCreatures = new LinkedList<Creature>();
                                allCreatures.addAll(match.getPlayer1().getCreatures());
                                allCreatures.addAll(match.getPlayer2().getCreatures());
                                for(Creature creature : allCreatures) {
                                    if(creature.getColor().equals(Color.BLUE)) {
                                        if(creature.isAffectedByAbility(this)) {
                                            creature.removeLastingEffectFromSourceAbility(this);
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
        
        cardImplementations.put(CardName.SWAMP, new CardImplementation() {
        	@Override
        	public LandCard createCard() {
        		return new LandCard(CardName.SWAMP, 
        				new ActivatedPermanentAbility() {

							@Override
							public void executeOnActivation() {
								// TODO if not tapped, tap and generate 1 black mana
							}
        		});
        	}
        });
        
        cardImplementations.put(CardName.TERROR, new CardImplementation() {
            @Override
            public InstantCard createCard() {
				return new InstantCard(CardName.TERROR, Color.BLACK, 1, 1,
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
            }
        });

        cardImplementations.put(CardName.TUNDRA_WOLVES, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.FIRST_STRIKE);
				return new CreatureCard(CardName.TUNDRA_WOLVES, Color.WHITE, attributes, 1, 0, 1, 1);
            }
        });
        
        cardImplementations.put(CardName.UNHOLY_STRENGTH, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.UNHOLY_STRENGTH, Color.BLACK, 2, 0,
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
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeLastingEffectFromSourceAbility(this);
                            }  
                });
            }
        });
        
        cardImplementations.put(CardName.UNSTABLE_MUTATION, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.UNSTABLE_MUTATION, Color.BLUE, 1, 0,
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
                            	gameEventHandler.add(this);
                            	startingBuff = new StaticStatModifier(this, 3, 3);
                            	target.applyLastingEffect(startingBuff);
                            }
                            
                            @Override
                            public void executeOnExit() {
                            	gameEventHandler.remove(this);
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
        
        cardImplementations.put(CardName.WALL_OF_WATER, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.remove(Attribute.CAN_ATTACK);
				return new CreatureCard(CardName.WALL_OF_WATER, Color.BLUE, attributes, 2, 1, 0, 5, 
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
                
        cardImplementations.put(CardName.WANDERLUST, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.WANDERLUST, Color.GREEN, 1 ,2,
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
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										// TODO target.getController().takeDamage(1); o decreaseHealth();
									}
								}
							}
						});
            }
        });
        
        cardImplementations.put(CardName.WARP_ARTIFACT, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
				return new EnchantmentCard(CardName.WARP_ARTIFACT, Color.BLACK, 2 ,0,
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
							public void executeOnEvent(GameEvent gameEvent) {
								if(gameEvent.getDescriptor().equals(Event.UPKEEP_STEP)) {
									if(gameEvent.getObject1() == target.getController()) {
										// TODO target.getController().takeDamage(1); o decreaseHealth();
									}
								}
							}
						});
            }
        });
        
        cardImplementations.put(CardName.WATER_ELEMENTAL, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				return new CreatureCard(CardName.WATER_ELEMENTAL, Color.BLUE, attributes, 2, 3, 5, 4);
            }
        });
        
        cardImplementations.put(CardName.WEAKNESS, new CardImplementation() {
            @Override
            public EnchantmentCard createCard() {
                return new EnchantmentCard(CardName.WEAKNESS, Color.BLACK, 1, 0,
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
                            	target.applyLastingEffect(newEffect);
                            }
                            
                            public void executeOnExit() {
                            	target.removeLastingEffectFromSourceAbility(this);
                            }  
                });
            }
        });

        cardImplementations.put(CardName.ZEPHYR_FALCON, new CardImplementation() {
            @Override
            public CreatureCard createCard() {
				List<Attribute> attributes = new LinkedList<Attribute>();
				attributes = getDefaultCreatureAttributes();
				attributes.add(Attribute.FLYING);
				attributes.remove(Attribute.TAPS_ON_ATTACK);
				return new CreatureCard(CardName.ZEPHYR_FALCON, Color.BLUE, attributes, 1, 1, 1, 1);
            }
        });
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
	
	class OneTurnStatModifier extends AutomaticLastingEffect {
		private Integer attackModifier;
		private Integer defenseModifier;

		public OneTurnStatModifier(Ability sourceAbility, Integer attackModifier, Integer defenseModifier) {
			super(sourceAbility);
			this.attackModifier = attackModifier;
			this.defenseModifier = defenseModifier;
		}

		@Override
		public void executeOnEvent(GameEvent gameEvent) {
			if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
				this.getTarget().removeLastingEffect(this);
			}
		}

		@Override
		public void applyEffect() {
			if(attackModifier < 0) {
				((Creature)this.getTarget()).decreaseAttack(attackModifier);
			}
			else {
				((Creature)this.getTarget()).increaseAttack(attackModifier);
			}
			if(defenseModifier < 0) {
				((Creature)this.getTarget()).decreaseDefense(defenseModifier);
			}
			else {
				((Creature)this.getTarget()).increaseDefense(defenseModifier);
			}
		}

		@Override
		public void undoEffect() {
			if(attackModifier < 0) {
				((Creature)this.getTarget()).increaseAttack(attackModifier);
			}
			else {
				((Creature)this.getTarget()).decreaseAttack(attackModifier);
			}
			if(defenseModifier < 0) {
				((Creature)this.getTarget()).increaseDefense(defenseModifier);
			}
			else {
				((Creature)this.getTarget()).decreaseDefense(defenseModifier);
			}
		}
	}
	
	class StaticStatModifier extends LastingEffect {
		private Integer attackModifier;
		private Integer defenseModifier;

		public StaticStatModifier(Ability sourceAbility, Integer attackModifier, Integer defenseModifier) {
			super(sourceAbility);
			this.attackModifier = attackModifier;
			this.defenseModifier = defenseModifier;
		}

		@Override
		public void applyEffect() {
			if(attackModifier < 0) {
				((Creature)this.getTarget()).decreaseAttack(Math.abs(attackModifier));
			}
			else {
				((Creature)this.getTarget()).increaseAttack(attackModifier);
			}
			if(defenseModifier < 0) {
				((Creature)this.getTarget()).decreaseDefense(Math.abs(defenseModifier));
			}
			else {
				((Creature)this.getTarget()).increaseDefense(defenseModifier);
			}
		}

		@Override
		public void undoEffect() {
			if(attackModifier < 0) {
				((Creature)this.getTarget()).increaseAttack(Math.abs(attackModifier));
			}
			else {
				((Creature)this.getTarget()).decreaseAttack(attackModifier);
			}
			if(defenseModifier < 0) {
				((Creature)this.getTarget()).increaseDefense(Math.abs(defenseModifier));
			}
			else {
				((Creature)this.getTarget()).decreaseDefense(defenseModifier);
			}
		}
	}

}