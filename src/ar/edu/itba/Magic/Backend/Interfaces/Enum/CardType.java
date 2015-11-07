package ar.edu.itba.Magic.Backend.Interfaces.Enum;

import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.Magic.Backend.GameEvent;
import ar.edu.itba.Magic.Backend.GameEventHandler;
import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Abilities.AutomaticPermanentAbility;
import ar.edu.itba.Magic.Backend.Card.ArtifactCard;
import ar.edu.itba.Magic.Backend.Card.Card;
import ar.edu.itba.Magic.Backend.Card.CreatureCard;
import ar.edu.itba.Magic.Backend.Card.EnchantmentCard;
import ar.edu.itba.Magic.Backend.Card.SorceryCard;
import ar.edu.itba.Magic.Backend.Effects.LastingEffect;
import ar.edu.itba.Magic.Backend.Effects.StaticStatModifier;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Land;
import ar.edu.itba.Magic.Backend.Permanents.Permanent;

/**
 * Created by Martin on 02/11/2015.
 */
/*
 * Red Cards:		20
 * Green Cards:		20
 * Blue Cards:		13
 * Black Cards: 	20
 * White Cards:		12
 * Artifacts:		10
 * Lands:			5
 * 
 * Total:			100
 */
public enum CardType {
	
	AIR_ELEMENTAL("Air Elemental", Color.BLUE, 2, 3) { public Card createCard() {
			List<Attribute> attributes = new LinkedList<Attribute>();
	        attributes = Creature.getDefaultCreatureAttributes();
	        attributes.add(Attribute.FLYING);
			return new CreatureCard(CardType.AIR_ELEMENTAL, attributes, 4, 4);
		}
	},
	
	ANKH_OF_MISHRA("Ankh of Mishra", Color.COLORLESS, 0, 2) { public Card createCard() {
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
	
	ASPECT_OF_WOLF("Aspect of Wolf", Color.GREEN, 1, 1) { public Card createCard() {
		return new SorceryCard(CardType.ASPECT_OF_WOLF,
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
							if(land.getCardType().equals(CardType.FOREST)) {
								forests++;
							}
						}
						if(forests != previousForests) {
							// TODO me fui a dormir - terminar maniana
						}	
					}
				});
		}
	},			
	
    BAD_MOON("Bad Moon", Color.BLACK, 1, 1) { public Card createCard() {
    	return new EnchantmentCard(CardType.BAD_MOON, 
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
    } },	
    
    BALL_LIGHTNING("Ball Lightning") { public Card createCard() {} },
    
    BIRD_MAIDEN("Bird Maiden") { public Card createCard() {} },	
    
    BIRDS_OF_PARADISE("Birds of Paradise") { public Card createCard() {} },		
    
    BLACK_LOTUS("Black Lotus") { public Card createCard() {} },						
    
    BLIGHT("Blight") { public Card createCard() {} },		
    
    BLOOD_LUST("Blood Lust") { public Card createCard() {} },	
    
    BOG_IMP("Bog Imp") { public Card createCard() {} },								
    
    BOG_WRAITH("Bog Wraith") { public Card createCard() {} },					
    
    BURROWING("Burrowing") { public Card createCard() {} },				
    
    CARNIVOROUS_PLANT("Carnivorous Plant") { public Card createCard() {} },		
    
    CARRION_ANTS("Carrion Ants") { public Card createCard() {} },		
    
    CRAW_WURM("Craw Wurm") { public Card createCard() {} },		
    
    CRUMBLE("Crumble") { public Card createCard() {} },
    
    CRUSADE("Crusade") { public Card createCard() {} },
    
    DARK_RITUAL("Dark Ritual") { public Card createCard() {} },
    
    DESERT_TWISTER("Desert Twister") { public Card createCard() {} },
    
    DINGUS_EGG("Dingus Egg") { public Card createCard() {} },
    
    DIVINE_TRANSFORMATION("Divine Transformation") { public Card createCard() {} },
    
    DRAIN_POWER("Drain Power") { public Card createCard() {} },
    
    DURKWOOD_BOARS("Durkwood Boars") { public Card createCard() {} },
    
    DWARVEN_DEMOLITION_TEAM("Dwarven Demolition Team") { public Card createCard() {} },
    
    EARTH_ELEMENTAL("Earth Elemental") { public Card createCard() {} },
    
    ELVISH_ARCHERS("Elvish Archers") { public Card createCard() {} },
    
    FIRE_ELEMENTAL("Fire Elemental") { public Card createCard() {} },
    
    FISSURE("Fissure") { public Card createCard() {} },
    
    FLIGHT("Flight") { public Card createCard() {} },
    
    FLOOD("Flood") { public Card createCard() {} },
    
    FOREST("Forest") { public Card createCard() {} },
    
    FORCE_OF_NATURE("Force of Nature") { public Card createCard() {} },
    
    FROZEN_SHADE("Frozen Shade") { public Card createCard() {} },
    
    GRAY_OGRE("Gray Ogre") { public Card createCard() {} },
    
    GRANITE_GARGOYLE("Granite Gargoyle") { public Card createCard() {} },
    
    GRIZZLY_BEARS("Grizzly Bears") { public Card createCard() {} },
    
    HOLY_STRENGTH("Holy Strength") { public Card createCard() {} },
    
    HOWL_FROM_BEYOND("Howl from Beyond") { public Card createCard() {} },
    
    HILL_GIANT("Hill Giant") { public Card createCard() {} },
    
    HURLOON_MINOTAUR("Hurloon Minotaur") { public Card createCard() {} },
    
    IRONROOT_TREEFOLK("Ironroot Treefolk") { public Card createCard() {} },
    
    ISLAND("Island") { public Card createCard() {} },
    
    JUMP("Jump") { public Card createCard() {} },
    
    JUNUN_EFREET("Junun Efreet") { public Card createCard() {} },
    
    KARMA("Karma") { public Card createCard() {} },
    
    KISMET("Kismet") { public Card createCard() {} },
    
    LANCE("Lance") { public Card createCard() {} },
    
    LAND_LEECHES("Land Leeches") { public Card createCard() {} },
    
    LIGHTNING_BOLT("Lightning Bolt") { public Card createCard() {} },
    
    LLANOWAR_ELVES("Llanowar Elves") { public Card createCard() {} },
    
    LORD_OF_THE_PIT("Lord of the Pit") { public Card createCard() {} },
    
    LOST_SOUL("Lost Soul") { public Card createCard() {} },
    
    MARSH_GAS("Marsh Gas") { public Card createCard() {} },
    
    MONSS_GOBLIN_RAIDERS("Mons's Goblin Raiders") { public Card createCard() {} },
    
    MOUNTAINS("Mountains") { public Card createCard() {} },
    
    MOX_EMERALD("Mox Emerald") { public Card createCard() {} },
    
    MOX_JET("Mox Jet") { public Card createCard() {} },
    
    MOX_PEARL("Mox Pearl") { public Card createCard() {} },
    
    MOX_RUBY("Mox Ruby") { public Card createCard() {} },
    
    MOX_SAPPHIRE("Mox Sapphie") { public Card createCard() {} },
    
    NIGHTMARE("Nightmare") { public Card createCard() {} },
    
    PARALYZE("Paralyze") { public Card createCard() {} },
    
    PEARLED_UNICORN("Pearled Unicorn") { public Card createCard() {} },
    
    PLAINS("Plains") { public Card createCard() {} },
    
    RADJAN_SPIRIT("Radjan Spirit") { public Card createCard() {} },
    
    ROC_OF_KHER_RIDGES("Roc of Kher Ridges") { public Card createCard() {} },
    
    ROYAL_ASSASIN("Royal Assassin") { public Card createCard() {} },
    
    SAVANNAH_LIONS("Savannah Lions") { public Card createCard() {} },
    
    SCRYB_SPRITES("Scryb Sprites") { public Card createCard() {} },
    
    SEA_SERPENT("Sea Serpent") { public Card createCard() {} },
    
    SEGOVIAN_LEVIATHAN("Segovian Leviathan") { public Card createCard() {} },
    
    SERRA_ANGEL("Serra Angel") { public Card createCard() {} },
    
    SHANODIN_DRYADS("Shanodin Dryads") { public Card createCard() {} },
    
    SHIVAN_DRAGON("Shivan Dragon") { public Card createCard() {} },
    
    SINDBAD("Sindbad") { public Card createCard() {} },
    
    SINKHOLE("Sinkhole") { public Card createCard() {} },
    
    SOL_RING("Sol Ring") { public Card createCard() {} },
    
    STONE_RAIN("Stone Rain") { public Card createCard() {} },
    
    SUNKEN_CITY("Sunken City") { public Card createCard() {} },
    
    SWAMP("Swamp") { public Card createCard() {} },
    
    TERROR("Terror") { public Card createCard() {} },	
    
    THE_RACK("The Rack") { public Card createCard() {} },
    	
    TUNDRA_WOLVES("Tundra Wolves") { public Card createCard() {} },
    
    TUNNEL("Tunnel") { public Card createCard() {} },
    
    UNHOLY_STRENGTH("Unholy Strength") { public Card createCard() {} },
    
    UNSTABLE_MUTATION("Unstable Mutation") { public Card createCard() {} },
    
    WALL_OF_FIRE("Wall of Fire") { public Card createCard() {} },
    
    WALL_OF_ICE("Wall of Ice") { public Card createCard() {} },
    
    WALL_OF_STONE("Wall of Stone") { public Card createCard() {} },
    
    WALL_OF_SWORDS("Wall of Swords") { public Card createCard() {} },
    
    WALL_OF_WATER("Wall of Water") { public Card createCard() {} },
    
    WALL_OF_WOOD("Wall of Wood") { public Card createCard() {} },
    
    WANDERLUST("Wanderlust") { public Card createCard() {} },
    
    WAR_MAMMOTH("War Mammoth") { public Card createCard() {} },
    
    WARP_ARTIFACT("Warp Artifact") { public Card createCard() {} },
    
    WATER_ELEMENTAL("Water Elemental") { public Card createCard() {} },
    
    WEAKNESS("Weakness") { public Card createCard() {} },
    
    WRATH_OF_GOD("Wrath of God") { public Card createCard() {} },
    
    ZEPHYR_FALCON("Zephyr Falcon") { public Card createCard() {} }
    
    ;						
	
	GameEventHandler gameEventHandler = GameEventHandler.getGameEventHandler();
	Match match = Match.getMatch();
	
	String cardName;
	int idNumber;
	Color color;
	Integer coloredManaCost;
	Integer colorlessManaCost;
	
	private CardType(String cardName/* , idNumber */, Color color, Integer coloredManaCost, Integer colorlessManaCost) {
		this.cardName = cardName;
		//	this.idNumber = idNumber;		-> colocarlos cuando ya esten todas las cartas
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
	
	public abstract Card createCard();
	
}
