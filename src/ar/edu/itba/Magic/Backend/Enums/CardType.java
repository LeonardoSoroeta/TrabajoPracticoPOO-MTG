package ar.edu.itba.Magic.Backend.Enums;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.GameStack;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Abilities.AutomaticPermanentAbility;
import ar.edu.itba.Magic.Backend.Abilities.AutomaticSpellAbility;
import ar.edu.itba.Magic.Backend.Abilities.DefaultCreatureAbility;
import ar.edu.itba.Magic.Backend.Abilities.PermanentAbility;
import ar.edu.itba.Magic.Backend.Abilities.SpellAbility;
import ar.edu.itba.Magic.Backend.Cards.ArtifactCard;
import ar.edu.itba.Magic.Backend.Cards.Card;
import ar.edu.itba.Magic.Backend.Cards.CreatureCard;
import ar.edu.itba.Magic.Backend.Cards.EnchantmentCard;
import ar.edu.itba.Magic.Backend.Cards.InstantCard;
import ar.edu.itba.Magic.Backend.Cards.LandCard;
import ar.edu.itba.Magic.Backend.Cards.SorceryCard;
import ar.edu.itba.Magic.Backend.Effects.AutomaticLastingEffect;
import ar.edu.itba.Magic.Backend.Effects.LastingEffect;
import ar.edu.itba.Magic.Backend.Effects.OneTurnStatModifier;
import ar.edu.itba.Magic.Backend.Effects.StaticStatModifier;
import ar.edu.itba.Magic.Backend.Permanents.Artifact;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/** 
 * This enum contains the implementations of all Cards available in the game. Card enums contain the card's
 * specific name, color, casting mana costs, and the cards Ability implementation.
 */
public enum CardType {
	
	ACID_RAIN("Acid Rain", Color.BLUE, 1, 3) { public Card createCardOfThisType() {
    	return new SorceryCard(CardType.ACID_RAIN,
				new SpellAbility() {

					@Override
					public void resolveInStack() {
						List<Land> lands = new LinkedList<Land>();
						lands.addAll(match.getPlayer1().getLands());
						lands.addAll(match.getPlayer2().getLands());
						for(Land land : lands) {
							if(land.getCardType().equals(CardType.FOREST)) {
								land.destroy();
							}
						}
					}
		});
    } },
	
	AIR_ELEMENTAL("Air Elemental", Color.BLUE, 2, 3) { public Card createCardOfThisType() {
			List<Attribute> attributes = new LinkedList<Attribute>();
	        attributes = Creature.getDefaultCreatureAttributes();
	        attributes.add(Attribute.FLYING);
			return new CreatureCard(CardType.AIR_ELEMENTAL, attributes, 4, 4, new DefaultCreatureAbility());
	} },
	
	ANKH_OF_MISHRA("Ankh of Mishra", Color.COLORLESS, 0, 2) { public Card createCardOfThisType() {
		  return new ArtifactCard(CardType.ANKH_OF_MISHRA,
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
	},					
	
	AZURE_DRAKE("Azure Drake", Color.BLUE, 1, 3) { public Card createCardOfThisType() {
		List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.AZURE_DRAKE, attributes, 2, 4, new DefaultCreatureAbility());
	} },
	
    BAD_MOON("Bad Moon", Color.BLACK, 1, 1) { public Card createCardOfThisType() {
    	return new EnchantmentCard(CardType.BAD_MOON, 
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
                            if(creature.getColor().equals(Color.BLACK)) {
                                if(creature.isAffectedByAbility(this)) {
                                    creature.removeLastingEffectsFromSourceAbility(this);
                                }
                            }
                        }
                    }

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
    } },	
    
    BALL_LIGHTNING("Ball Lightning", Color.RED, 3, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.TRAMPLE);
        attributes.remove(Attribute.SUMMONING_SICKNESS);
        return new CreatureCard(CardType.BALL_LIGHTNING, attributes, 6, 1, new AutomaticPermanentAbility() {

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
				if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
					this.getSourcePermanent().destroy();
				}
			}
        	
        });
    } },
    
    BIRD_MAIDEN("Bird Maiden", Color.RED, 1, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
        return new CreatureCard(CardType.BIRD_MAIDEN, attributes, 1, 2, new DefaultCreatureAbility());
    } },	
    
    BIRDS_OF_PARADISE("Birds of Paradise", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
        return new CreatureCard(CardType.BIRDS_OF_PARADISE, attributes, 0, 1,
        		new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.GREEN);
						}
					}
        });
    } },
    
    BOG_IMP("Bog Imp", Color.BLACK, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.BOG_IMP, attributes, 1, 1, new DefaultCreatureAbility());
    } },								
    
    BOG_WRAITH("Bog Wraith", Color.BLACK, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.SWAMPWALK);
		return new CreatureCard(CardType.BOG_WRAITH, attributes, 3, 3, new DefaultCreatureAbility());
    } },					
    
    CARNIVOROUS_PLANT("Carnivorous Plant", Color.GREEN, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.CARNIVOROUS_PLANT, attributes, 4, 5, new DefaultCreatureAbility());
    } },		
    
    CARRION_ANTS("Carrion Ants", Color.BLACK, 2, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.CARRION_ANTS, attributes, 1, 1,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						this.requestAbilityManaPayment(0, 1);
					}
					
					@Override
					public void executeIfManaPayed() {
						AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 1);
						this.getSourcePermanent().applyLastingEffect(newEffect);
					}
		});
    } },
    
    CRAW_WURM("Craw Wurm", Color.GREEN, 2, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.CRAW_WURM, attributes, 6, 4, new DefaultCreatureAbility());
    } },		
    
    CRUSADE("Crusade", Color.WHITE, 2, 0) { public Card createCardOfThisType() {
    	 return new EnchantmentCard(CardType.CRUSADE,
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
                             if(creature.getColor().equals(Color.WHITE)) {
                                 if(creature.isAffectedByAbility(this)) {
                                     creature.removeLastingEffectsFromSourceAbility(this);
                                 }
                             }
                         }
                     }

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
    } },
    
    DARK_RITUAL("Dark Ritual", Color.BLACK, 1, 0) { public Card createCardOfThisType() {
    	return new InstantCard(CardType.DARK_RITUAL,
				new SpellAbility() {
    				
    				/* Bypass the spell chain for mana generating spells */
    				@Override 
    				public void finishCasting() {
    					this.getSourceCard().getController().getManaPool().addManaOfThisColor(Color.BLACK, 3);
    					this.getSourceCard().getController().discardCard(this.getSourceCard());
    				}

					@Override
					public void resolveInStack() {
						
					}
		});
    } },	
    
    DEVOURING_DEEP("Vodalian Soldiers", Color.BLUE, 1, 2) { public Card createCardOfThisType() {
		List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.ISLANDWALK);
		return new CreatureCard(CardType.DEVOURING_DEEP, attributes, 1, 2, new DefaultCreatureAbility());
	} },
    
    DINGUS_EGG("Dingus Egg", Color.COLORLESS, 0, 4) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.DINGUS_EGG,
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
							if(gameEvent.getObject1() instanceof Land) {
								((Permanent)gameEvent.getObject1()).getController().takeDamage(2);
							}
						}
					}
		});
    } },
    
    DURKWOOD_BOARS("Durkwood Boars", Color.GREEN, 1, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.DURKWOOD_BOARS, attributes, 4 ,4, new DefaultCreatureAbility());
    } },
    
    CLEANSE("Cleanse", Color.WHITE, 2, 2) { public Card createCardOfThisType() {
    	return new SorceryCard(CardType.CLEANSE,
				new SpellAbility() {

					@Override
					public void resolveInStack() {
						List<Creature> allCreatures = new LinkedList<Creature>();
                        allCreatures.addAll(match.getPlayer1().getCreatures());
                        allCreatures.addAll(match.getPlayer2().getCreatures());
                        for(Creature creature : allCreatures) {
                        	if(creature.getColor().equals(Color.BLACK)) {
                        		creature.destroy();
                        	}
                        }
					}
		});
    } },
    
    EARTH_ELEMENTAL("Earth Elemental", Color.RED, 2, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.EARTH_ELEMENTAL, attributes, 4, 5, new DefaultCreatureAbility());
    } },
    
    ELVISH_ARCHERS("Elvish Archers", Color.GREEN, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FIRST_STRIKE);
		return new CreatureCard(CardType.ELVISH_ARCHERS, attributes, 2, 1, new DefaultCreatureAbility());
    } },
    
    FIRE_ELEMENTAL("Fire Elemental", Color.RED, 2, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.FIRE_ELEMENTAL, attributes, 5, 4, new DefaultCreatureAbility());
    } },
    
    FLYING_MEN("Flying Men", Color.BLUE, 1, 0) { public Card createCardOfThisType() {
		List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.FLYING_MEN, attributes, 1, 1, new DefaultCreatureAbility());
    } },
    
    FOREST("Forest", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new LandCard(CardType.FOREST, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.GREEN);
						}
					}
		});
    } },	
    
    FROZEN_SHADE("Frozen Shade", Color.BLACK, 1, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.FROZEN_SHADE, attributes, 0, 1, 
        		new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						this.requestAbilityManaPayment(1, 0);
					}
					
					@Override
					public void executeIfManaPayed() {
						AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 1);
						this.getSourcePermanent().applyLastingEffect(newEffect);
						Match.getMatch().resetPlayerMessage();
					}
        });
    } },
    
    GIANT_GROWTH("Giant Growth", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	return new InstantCard(CardType.GIANT_GROWTH, 
    			new SpellAbility() {
		    	Object target;
		    	Creature targetCreature;
		    	
		    	@Override
		    	public void proceedToSelectCastingTarget() {
		    		Match.getMatch().awaitCastingTargetSelection(this, "Select target creature");
		    	}
		    	
		    	@Override
		    	public void resumeCastingTargetSelection() {
		    		target = Match.getMatch().getSelectedTarget();
		    		if(!(target instanceof Creature)) {
		    			Match.getMatch().awaitCastingTargetSelection(this, "Select target creature");
		    		} else {
		    			targetCreature = (Creature)target;
		    			this.finishCasting();
		    		}
		    	}
		    	
				@Override
				public void resolveInStack() {
					AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 3, 3);
					targetCreature.applyLastingEffect(newEffect);
				}
    	});
    } },
    
    GRAY_OGRE("Gray Ogre", Color.RED, 1, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.GRAY_OGRE, attributes, 2, 2, new DefaultCreatureAbility());
    } },
    
    GRIZZLY_BEARS("Grizzly Bears", Color.GREEN, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.GRIZZLY_BEARS, attributes, 2, 2, new DefaultCreatureAbility());
    } },
    
    HOLY_LIGHT("Holy Light", Color.WHITE, 1, 2) { public Card createCardOfThisType() {
    	return new InstantCard(CardType.HOLY_LIGHT,
				new SpellAbility() {

					@Override
					public void resolveInStack() {
						List<Creature> allCreatures = new LinkedList<Creature>();
                        allCreatures.addAll(match.getPlayer1().getCreatures());
                        allCreatures.addAll(match.getPlayer2().getCreatures());
                        for(Creature creature : allCreatures) {
                        	if(!creature.getColor().equals(Color.WHITE)) {
	                        	AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, -1, -1);
	                        	creature.applyLastingEffect(newEffect);
                        	}
                        }
					}
		});
    } },
    
    HILL_GIANT("Hill Giant", Color.RED, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.HILL_GIANT, attributes, 3, 3, new DefaultCreatureAbility());
    } },
    
    HURLOON_MINOTAUR("Hurloon Minotaur", Color.RED, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.HURLOON_MINOTAUR, attributes, 2, 3, new DefaultCreatureAbility());
    } },
    
    // TODO ver si aplicar esto para criaturas en spell state
    INFERNO("Inferno", Color.RED, 2, 5) { public Card createCardOfThisType() {
      	 return new InstantCard(CardType.INFERNO,
           		new SpellAbility() {
 
   					@Override
   					public void resolveInStack() {
   						LinkedList<Creature> allCreatures = new LinkedList<Creature>();
   						allCreatures.addAll(Match.getMatch().getPlayer1().getCreatures());
   						allCreatures.addAll(Match.getMatch().getPlayer2().getCreatures());
   						for(Creature each : allCreatures) {
   							each.takeDamage(6);
   						}
   						Match.getMatch().getPlayer1().takeDamage(6);
   						Match.getMatch().getPlayer2().takeDamage(6);
   					}	
           });
      } },
    
    IRONROOT_TREEFOLK("Ironroot Treefolk", Color.GREEN, 1, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.IRONROOT_TREEFOLK, attributes, 3, 5, new DefaultCreatureAbility());
    } },
    
    ISLAND("Island", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new LandCard(CardType.ISLAND, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.BLUE);
						}
					}
		});
    } },
    
    KEEPERS_OF_THE_FAITH("Keepers of the Faith", Color.WHITE, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.RIGHTEOUS_AVENGERS, attributes, 2, 3, new DefaultCreatureAbility());
    } },
    
    KISMET("Kismet", Color.WHITE, 1, 3) { public Card createCardOfThisType() {
    	return new EnchantmentCard(CardType.KISMET,
				new AutomaticPermanentAbility() {
					Player targetPlayer;

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
    } },
    
    LAND_LEECHES("Land Leeches", Color.GREEN, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FIRST_STRIKE);
		return new CreatureCard(CardType.LAND_LEECHES, attributes, 2, 2, new DefaultCreatureAbility());
    } },
    
    LIGHTNING_BOLT("Lightning Bolt", Color.RED, 1, 0) { public Card createCardOfThisType() {
   	 return new InstantCard(CardType.LIGHTNING_BOLT,
        		new SpellAbility() {
			   		Object target;
			    	Creature targetCreature;
			    	
			    	@Override
			    	public void proceedToSelectCastingTarget() {
			    		Match.getMatch().awaitCastingTargetSelection(this, "Select target creature");
			    	}
			    	
			    	@Override
			    	public void resumeCastingTargetSelection() {
			    		target = Match.getMatch().getSelectedTarget();
			    		if(!(target instanceof Creature)) {
			    			Match.getMatch().awaitCastingTargetSelection(this, "Select target creature");
			    		} else {
			    			targetCreature = (Creature)target;
			    			this.finishCasting();
			    		}
			    	}
			    	
					@Override
					public void resolveInStack() {
						targetCreature.takeDamage(3);
					}
						
        });
   } },
 
    LLANOWAR_ELVES("Llanowar Elves", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.LLANOWAR_ELVES, attributes, 1, 1,
        		new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.GREEN);
						}
					}
        });
    } },
    
    LOST_SOUL("Lost Soul", Color.BLACK, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.SWAMPWALK);
		return new CreatureCard(CardType.LOST_SOUL, attributes, 2, 1, new DefaultCreatureAbility());
    } },
    
    MARSH_GAS("Marsh Gas", Color.BLACK, 1, 0) { public Card createCardOfThisType() {
    	 return new InstantCard(CardType.MARSH_GAS,
         		new AutomaticSpellAbility() {

						@Override
						public void resolveInStack() {
							gameEventHandler.addListener(this);
							Match.getMatch().resetPlayerMessage();
							gameEventHandler.refreshListeners();
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
							} else {
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
    } },
    
    MOORISH_CAVALRY("Moorish Cavalry", Color.WHITE, 2, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.TRAMPLE);
		return new CreatureCard(CardType.MOORISH_CAVALRY, attributes, 3, 3, new DefaultCreatureAbility());
    } },
    
    MONSS_GOBLIN_RAIDERS("Mons's Goblin Raiders", Color.RED, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.MONSS_GOBLIN_RAIDERS, attributes, 1, 1, new DefaultCreatureAbility());
    } },
    
    MOUNTAINS("Mountains", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new LandCard(CardType.MOUNTAINS, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.RED);
						}
					}
		});
    } },
    
    MOX_EMERALD("Mox Emerald", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.MOX_EMERALD,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.GREEN);
						}
					}
		});
    } },
    
    MOX_JET("Mox Jet", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.MOX_JET,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.BLACK);
						}
					}
		});
    } },
    
    MOX_PEARL("Mox Pearl", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.MOX_PEARL,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.WHITE);
						}
					}
		});
    } },
    
    MOX_RUBY("Mox Ruby", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.MOX_RUBY,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.RED);
						}
					}
		});
    } },
    
    MOX_SAPPHIRE("Mox Sapphire", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.MOX_SAPPHIRE,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.BLUE);
						}
					}
		});
    } },
    
    NIGHTMARE("Nightmare", Color.BLACK, 1, 5) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.NIGHTMARE, attributes, 1, 1,
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
						Integer swamps = 0;
						Player controller = (this.getSourcePermanent()).getController();
						List<Permanent> permanents = new LinkedList<Permanent>();
						permanents.addAll(controller.getPermanentsInPlay());
						for(Permanent permanent : permanents) {
							if(permanent.getCardType().equals(CardType.SWAMP))
								swamps++;
						}
						((Creature)this.getSourcePermanent()).setBaseAttack(swamps);
						((Creature)this.getSourcePermanent()).setBaseDefense(swamps);
					}
		});
    } },
    
    PEARLED_UNICORN("Pearled Unicorn", Color.WHITE, 1, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.PEARLED_UNICORN, attributes, 2, 2, new DefaultCreatureAbility());
    } },
    
    // TODO ver si aplicar esto a criaturas en spell state
    PESTILENCE("Pestilence", Color.BLACK, 2, 2) { public Card createCardOfThisType() {
    	return new EnchantmentCard(CardType.PESTILENCE, 
    				new AutomaticPermanentAbility() {
    					boolean destroyThis;
    		
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
    						if(gameEvent.getDescriptor().equals(Event.END_OF_TURN)) {
	    						if(Match.getMatch().getPlayer1().getCreatures().isEmpty()) {
		    						if(Match.getMatch().getPlayer2().getCreatures().isEmpty()) {
		    							destroyThis = true;
		    						}
	    						}
	    						if(destroyThis) {
	    							this.getSourcePermanent().destroy();
	    						}
    						}
    					}
    					
    					@Override
    					public void executeOnActivation() {
    						this.requestAbilityManaPayment(1, 0);
    					}
    					
    					@Override 
    					public void executeIfManaPayed() {
    						LinkedList<Creature> allCreatures = new LinkedList<Creature>();
       						allCreatures.addAll(Match.getMatch().getPlayer1().getCreatures());
       						allCreatures.addAll(Match.getMatch().getPlayer2().getCreatures());
       						for(Creature each : allCreatures) {
       							each.takeDamage(1);
       						}
       						Match.getMatch().getPlayer1().takeDamage(1);
       						Match.getMatch().getPlayer2().takeDamage(1);
    					}
    		
    	});
    } },
    
    PLAINS("Plains", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new LandCard(CardType.PLAINS, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.WHITE);
						}
					}
		});
    } },
    
    RIGHTEOUS_AVENGERS("Righteous Avengers", Color.WHITE, 1, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.PLAINSWALK);
		return new CreatureCard(CardType.RIGHTEOUS_AVENGERS, attributes, 3, 1, new DefaultCreatureAbility());
    } },
    
    ROC_OF_KHER_RIDGES("Roc of Kher Ridges", Color.RED, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.ROC_OF_KHER_RIDGES, attributes, 3, 3, new DefaultCreatureAbility());
    } },
    
    SAVANNAH_LIONS("Savannah Lions", Color.WHITE, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        return new CreatureCard(CardType.SAVANNAH_LIONS, attributes, 2, 1, new DefaultCreatureAbility());
    } },
    
    SCRYB_SPRITES("Scryb Sprites", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
        return new CreatureCard(CardType.SCRYB_SPRITES, attributes, 1, 1, new DefaultCreatureAbility());
    } },
    
    SEA_SERPENT("Sea Serpent", Color.BLUE, 1, 5) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.SEA_SERPENT, attributes, 5, 5, 
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
							if(land.getCardType().equals(CardType.ISLAND)) {
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
							if(land.getCardType().equals(CardType.ISLAND)) {
								destroyThis = false;
							}
						}
						if(destroyThis == true) {
							this.getSourcePermanent().destroy();
						}
					}
		});
    } },
    
    SEGOVIAN_LEVIATHAN("Segovian Leviathan", Color.BLUE, 1, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.ISLANDWALK);
		return new CreatureCard(CardType.SEGOVIAN_LEVIATHAN, attributes, 3, 3, new DefaultCreatureAbility());
    } },
    
    SERENDIB_EFREET("Serendib Efreet", Color.BLUE, 1, 2) { public Card createCardOfThisType() {
		List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.SERENDIB_EFREET, attributes, 3, 4, 
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
								this.getSourcePermanent().getController().takeDamage(1);
							}
						}
						
					}
		});
    } },
    
    SERRA_ANGEL("Serra Angel", Color.WHITE, 2, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.remove(Attribute.TAPS_ON_ATTACK);
		return new CreatureCard(CardType.SERRA_ANGEL, attributes, 4, 4, new DefaultCreatureAbility());
    } },
    
    SERRA_AVIARY("Serra Aviary", Color.WHITE, 1, 3) { public Card createCardOfThisType() {
    	return new EnchantmentCard(CardType.SERRA_AVIARY, 
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
                            if(creature.isAffectedByAbility(this)) {
                                creature.removeLastingEffectsFromSourceAbility(this);
                            }
                        }
                    }

                    @Override
                    public void executeOnEvent(GameEvent gameEvent) {
                    	List<Creature> allCreatures = new LinkedList<Creature>();
                        allCreatures.addAll(match.getPlayer1().getCreatures());
                        allCreatures.addAll(match.getPlayer2().getCreatures());
                        for(Creature creature : allCreatures) {
                            if(creature.containsAttribute(Attribute.FLYING)) {
                                if(!creature.isAffectedByAbility(this)) {
                                    LastingEffect newEffect = new StaticStatModifier(this, 1, 1);  
                                    creature.applyLastingEffect(newEffect);
                                }
                            }
                            if(!creature.containsAttribute(Attribute.FLYING)) {
                            	if(creature.isAffectedByAbility(this)) {
                            		creature.removeLastingEffectsFromSourceAbility(this);
                            	}
                            }
                         }
                      }
                  });
    } },	
    
    SHANODIN_DRYADS("Shanodin Dryads", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
        attributes.add(Attribute.FORESTWALK);
        return new CreatureCard(CardType.SHANODIN_DRYADS, attributes, 1, 1, new DefaultCreatureAbility());
    } },
    
    SHIELD_WALL("Shield Wall", Color.WHITE, 1, 1) { public Card createCardOfThisType() {
    	return new InstantCard(CardType.SHIELD_WALL,
				new SpellAbility() {

					@Override
					public void resolveInStack() {
						List<Creature> allCreatures = new LinkedList<Creature>();
                        allCreatures.addAll(this.getSourceCard().getController().getCreatures());
                        for(Creature creature : allCreatures) {
                        	AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 0, 2);
                        	creature.applyLastingEffect(newEffect);
                        }
                        gameEventHandler.refreshListeners();
					}
		});
    } },
    
    SHIVAN_DRAGON("Shivan Dragon", Color.RED, 2, 4) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.SHIVAN_DRAGON, attributes, 5, 5,
				new PermanentAbility() {

				@Override
				public void executeOnActivation() {
					this.requestAbilityManaPayment(1, 0);
				}
				
				@Override
				public void executeIfManaPayed() {
					AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
					this.getSourcePermanent().applyLastingEffect(newEffect);
				}
		});
    } },
    
    SINDBAD("Sindbad", Color.BLUE, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.SINDBAD, attributes, 1, 1,
				new PermanentAbility() {

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
    } },
    
    SINKHOLE("Sinkhole", Color.BLACK, 2, 0) { public Card createCardOfThisType() {
      	 return new SorceryCard(CardType.SINKHOLE,
           		new SpellAbility() {
   			   		Object target;
   			    	Land targetLand;
   			    	
   			    	@Override
   			    	public void proceedToSelectCastingTarget() {
   			    		Match.getMatch().awaitCastingTargetSelection(this, "Select target land");
   			    	}
   			    	
   			    	@Override
   			    	public void resumeCastingTargetSelection() {
   			    		target = Match.getMatch().getSelectedTarget();
   			    		if(!(target instanceof Land)) {
   			    			Match.getMatch().awaitCastingTargetSelection(this, "Select target land");
   			    		} else {
   			    			targetLand = (Land)target;
   			    			this.finishCasting();
   			    		}
   			    	}
   			    	
   					@Override
   					public void resolveInStack() {
   						targetLand.destroy();
   					}
   						
           });
      } },
    
    SOL_RING("Sol Ring", Color.COLORLESS, 0, 1) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.SOL_RING,
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.COLORLESS);
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.COLORLESS);
						}
					}
		});
    } },
    
    SQUIRE("Squire", Color.WHITE, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.SQUIRE, attributes, 1, 2, new DefaultCreatureAbility());
    } },
    
    STONE_RAIN("Stone Rain", Color.RED, 1, 2) { public Card createCardOfThisType() {
     	 return new SorceryCard(CardType.STONE_RAIN,
          		new SpellAbility() {
  			   		Object target;
  			    	Land targetLand;
  			    	
  			    	@Override
  			    	public void proceedToSelectCastingTarget() {
  			    		Match.getMatch().awaitCastingTargetSelection(this, "Select target land");
  			    	}
  			    	
  			    	@Override
  			    	public void resumeCastingTargetSelection() {
  			    		target = Match.getMatch().getSelectedTarget();
  			    		if(!(target instanceof Land)) {
  			    			Match.getMatch().awaitCastingTargetSelection(this, "Select target land");
  			    		} else {
  			    			targetLand = (Land)target;
  			    			this.finishCasting();
  			    		}
  			    	}
  			    	
  					@Override
  					public void resolveInStack() {
  						targetLand.destroy();
  					}
  						
          });
     } },
    
    SWAMP("Swamp", Color.COLORLESS, 0, 0) { public Card createCardOfThisType() {
    	return new LandCard(CardType.SWAMP, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						if(!this.getSourcePermanent().isTapped()) {
							this.getSourcePermanent().tap();
							this.getSourcePermanent().getController().getManaPool().addOneManaOfThisColor(Color.BLACK);
						}
					}
		});
    } },
    
    TERROR("Terror", Color.BLACK, 1, 1) { public Card createCardOfThisType() {
      	 return new InstantCard(CardType.TERROR,
           		new SpellAbility() {
   			   		Object target;
   			    	Creature targetCreature;
   			    	
   			    	@Override
   			    	public void proceedToSelectCastingTarget() {
   			    		Match.getMatch().awaitCastingTargetSelection(this, "Select target non black creature");
   			    	}
   			    	
   			    	@Override
   			    	public void resumeCastingTargetSelection() {
   			    		target = Match.getMatch().getSelectedTarget();
   			    		if(!(target instanceof Creature)) {
   			    			Match.getMatch().awaitCastingTargetSelection(this, "Select target non black creature");
   			    		} else {
   			    			targetCreature = (Creature)target;
   			    			if(!targetCreature.getColor().equals(Color.BLACK)) {
   			    				this.finishCasting();
   			    			} else {
   			    				Match.getMatch().awaitCastingTargetSelection(this, "Select target non black creature");
   			    			}
   			    		}
   			    	}
   			    	
   					@Override
   					public void resolveInStack() {
   						targetCreature.destroy();
   					}
   						
           });
      } },
    
    THE_RACK("The Rack", Color.COLORLESS, 0, 1) { public Card createCardOfThisType() {
    	return new ArtifactCard(CardType.THE_RACK,
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
    } },
    
    THUNDER_SPIRIT("Thunder Spirit", Color.WHITE, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FIRST_STRIKE);
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.THUNDER_SPIRIT, attributes, 2, 2, new DefaultCreatureAbility());
    } },
    	
    TUNDRA_WOLVES("Tundra Wolves", Color.WHITE, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FIRST_STRIKE);
		return new CreatureCard(CardType.TUNDRA_WOLVES, attributes, 1, 1, new DefaultCreatureAbility());
    } },
    
    VODALIAN_SOLDIERS("Vodalian Soldiers", Color.BLUE, 1, 1) { public Card createCardOfThisType() {
		List<Attribute> attributes = new LinkedList<Attribute>();
        attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.VODALIAN_SOLDIERS, attributes, 1, 2, new DefaultCreatureAbility());
	} },
    
    WALL_OF_FIRE("Wall of Fire", Color.RED, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.WALL_OF_FIRE, attributes, 0, 5,
				new PermanentAbility() {

			@Override
			public void executeOnActivation() {
				this.requestAbilityManaPayment(1, 0);
			}
			
			@Override
			public void executeIfManaPayed() {
				AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
				this.getSourcePermanent().applyLastingEffect(newEffect);
				Match.getMatch().resetPlayerMessage();
			}
		});
    } },
    
    WALL_OF_ICE("Wall of Ice", Color.GREEN, 1, 2) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.WALL_OF_ICE, attributes, 0, 7, new DefaultCreatureAbility());
    } },
    
    WALL_OF_STONE("Wall of Stone", Color.RED, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.WALL_OF_STONE, attributes, 0, 8, new DefaultCreatureAbility());
    } },
    
    WALL_OF_SWORDS("Wall of Swords", Color.WHITE, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		attributes.add(Attribute.FLYING);
		return new CreatureCard(CardType.WALL_OF_SWORDS, attributes, 3, 5, new DefaultCreatureAbility());
    } },
    
    WALL_OF_WATER("Wall of Water", Color.BLUE, 2, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.WALL_OF_WATER, attributes, 0, 5, 
				new PermanentAbility() {

					@Override
					public void executeOnActivation() {
						this.requestAbilityManaPayment(1, 0);
					}
					
					@Override
					public void executeIfManaPayed() {
						AutomaticLastingEffect newEffect = new OneTurnStatModifier(this, 1, 0);
						this.getSourcePermanent().applyLastingEffect(newEffect);
						Match.getMatch().resetPlayerMessage();
					}
		});
    } },	
    
    WALL_OF_WOOD("Wall of Wood", Color.GREEN, 1, 0) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.WALL);
		attributes.remove(Attribute.CAN_ATTACK);
		return new CreatureCard(CardType.WALL_OF_WOOD, attributes, 0, 3, new DefaultCreatureAbility());
    } }, 
    
    WAR_MAMMOTH("War Mammoth", Color.GREEN, 1, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.TRAMPLE);
		return new CreatureCard(CardType.WAR_MAMMOTH, attributes, 3, 3, new DefaultCreatureAbility());
    } },
    
    WATER_ELEMENTAL("Water Elemental", Color.BLUE, 2, 3) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		return new CreatureCard(CardType.WATER_ELEMENTAL, attributes, 5, 4, new DefaultCreatureAbility());
    } },
    
    WRATH_OF_GOD("Wrath of God", Color.WHITE, 2, 2) { public Card createCardOfThisType() {
    	return new SorceryCard(CardType.WRATH_OF_GOD,
				new SpellAbility() {

					@Override
					public void resolveInStack() {
						List<Creature> allCreatures = new LinkedList<Creature>();
                        allCreatures.addAll(match.getPlayer1().getCreatures());
                        allCreatures.addAll(match.getPlayer2().getCreatures());
                        for(Creature creature : allCreatures) {
                        	creature.destroy();
                        }
					}
		});
    } },
    
    ZEPHYR_FALCON("Zephyr Falcon", Color.BLUE, 1, 1) { public Card createCardOfThisType() {
    	List<Attribute> attributes = new LinkedList<Attribute>();
		attributes = Creature.getDefaultCreatureAttributes();
		attributes.add(Attribute.FLYING);
		attributes.remove(Attribute.TAPS_ON_ATTACK);
		return new CreatureCard(CardType.ZEPHYR_FALCON, attributes, 1, 1, new DefaultCreatureAbility());
    } }
    
    ;						
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	GameStack gameStack = GameStack.getGameStackInstance();
	Match match = Match.getMatch();
	
	private String cardName;
	private Color color;
	private Integer coloredManaCost;
	private Integer colorlessManaCost;
	
	private CardType(String cardName, Color color, Integer coloredManaCost, Integer colorlessManaCost) {
		this.cardName = cardName;
		this.color = color;
		this.coloredManaCost = coloredManaCost;
		this.colorlessManaCost = colorlessManaCost;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Integer getColoredManaCost() {
		return coloredManaCost;
	}
	
	public Integer getColorlessManaCost() {
		return colorlessManaCost;
	}
	
	public abstract Card createCardOfThisType();
	
}
