package ar.edu.itba.Magic.Backend.Interfaces.Enum;

/**
 * Created by Martin on 02/11/2015.
 */
/*
 * Red Cards:		14
 * Green Cards:		12
 * Blue Cards:		13
 * Black Cards: 	19
 * White Cards:		12
 * Artifacts:		9
 * Lands:			5
 * 
 * Total:			84
 */
public enum CardName {
	
	AIR_ELEMENTAL("Air Elemental"),						// Blue Creature
	ANKH_OF_MISHRA("Ankh of Mishra"),					// Artifact
	ASPECT_OF_WOLF("Aspect of Wolf"),					// Green Enchantment
    BAD_MOON("Bad Moon"),								// Black Enchantment
    BALL_LIGHTNING("Ball Lightning"),					// Red Creature
    BIRD_MAIDEN("Bird Maiden"),							// Red Creature
    BIRDS_OF_PARADISE("Birds of Paradise"),				// Green Creature
    BLACK_LOTUS("Black Lotus"),							// Artifact
    BLIGHT("Blight"),									// Black Enchantment
    BLOOD_LUST("Blood Lust"),							// Red Instant
    BOG_IMP("Bog Imp"),									// Black Creature
    BURROWING("Burrowing"),								// Red Enchantment
    CARNIVOROUS_PLANT("Carnivorous Plant"),				// Green Creature
    CARRION_ANTS("Carrion Ants"),						// Black Creature
    CRAW_WURM("Craw Wurm"),								// Green Creature
    CRUMBLE("Crumble"),									// Green Instant
    CRUSADE("Crusade"),									// White Enchantment
    DARK_RITUAL("Dark Ritual"),							// Black Instant
    DESERT_TWISTER("Desert Twister"),					// Green Sorcery
    DIVINE_TRANSFORMATION("Divine Transformation"),		// White Enchantment
    DRAIN_POWER("Drain Power"), 						// Blue Sorcery
    DURKWOOD_BOARS("Durkwood Boars"),					// Green Creature
    DWARVEN_DEMOLITION_TEAM("Dwarven Demolition Team"), // Red Creature
    EARTH_ELEMENTAL("Earth Elemental"),					// Red Creature
    ELVISH_ARCHERS("Elvish Archers"),					// Green Creature
    FIRE_ELEMENTAL("Fire Elemental"),					// Red Creature
    FISSURE("Fissure"),									// Red Instant
    FLIGHT("Flight"),									// Blue Enchantment
    FLOOD("Flood"),										// Blue Enchantment
    FOREST("Forest"),									// Land
    FORCE_OF_NATURE("Force of Nature"),					// Green Creature
    FROZEN_SHADE("Frozen Shade"),						// Black Creature
    GRAY_OGRE("Gray Ogre"),								// Red Creature
    HOLY_STRENGTH("Holy Strength"),						// White Enchantment
    HOWL_FROM_BEYOND("Howl from Beyond"),				// Black Instant
    HILL_GIANT("Hill Giant"),							// Red Creature
    HURLOON_MINOTAUR("Hurloon Minotaur"),				// Red Creature
    ISLAND("Island"),									// Land
    JUMP("Jump"),										// Blue Instant
    JUNUN_EFREET("Junun Efreet"),						// Black Creature
    KARMA("Karma"),										// White Enchantment
    KISMET("Kismet"),									// White Enchantment
    LANCE("Lance"),										// White Enchantment
    LAND_LEECHES("Land Leeches"),						// Green Creature
    LORD_OF_THE_PIT("Lord of the Pit"),					// Black Creature
    LOST_SOUL("Lost Soul"),								// Black Creature
    MARSH_GAS("Marsh Gas"),								// Black Instant
    MONSS_GOBLIN_RAIDERS("Mons's Goblin Raiders"),		// Red Creature
    MOUNTAINS("Mountains"),								// Land
    MOX_EMERALD("Mox Emerald"),							// Artifact
    MOX_JET("Mox Jet"),									// Artifact
    MOX_PEARL("Mox Pearl"),								// Artifact
    MOX_RUBY("Mox Ruby"),								// Artifact
    MOX_SAPPHIRE("Mox Sapphie"),						// Artifact
    NIGHTMARE("Nightmare"),								// Black Creature
    PARALYZE("Paralyze"),								// Black Enchantment
    PEARLED_UNICORN("Pearled Unicorn"),					// White Creature
    PLAINS("Plains"),									// Land
    RADJAN_SPIRIT("Radjan Spirit"),						// Green Creature
    ROC_OF_KHER_RIDGES("Roc of Kher Ridges"),			// Red Creature
    ROYAL_ASSASIN("Royal Assassin"),					// Black Creature
    SAVANNAH_LIONS("Savannah Lions"),					// White Creature
    SEA_SERPENT("Sea Serpent"),							// Blue Creature
    SEGOVIAN_LEVIATHAN("Segovian Leviathan"),			// Blue Creature
    SERRA_ANGEL("Serra Angel"),							// White Creature
    SINDBAD("Sindbad"),									// Blue Creature
    SINKHOLE("Sinkhole"),								// Black Sorcery
    SOL_RING("Sol Ring"),								// Artifact
    SUNKEN_CITY("Sunken City"),							// Blue Enchantment
    SWAMP("Swamp"),										// Land
    TERROR("Terror"),									// Black Instant
    THE_RACK("The Rack"), 								// Artifact
    TUNDRA_WOLVES("Tundra Wolves"),						// White Creature
    UNHOLY_STRENGTH("Unholy Strength"),					// Black Enchantment
    UNSTABLE_MUTATION("Unstable Mutation"),				// Blue Enchantment
    WALL_OF_STONE("Wall of Stone"),						// Red Creature
    WALL_OF_SWORDS("Wall of Swords"),					// White Creature
    WALL_OF_WATER("Wall of Water"),						// Blue Creature
    WANDERLUST("Wanderlust"),							// Green Enchantment
    WARP_ARTIFACT("Warp Artifact"),						// Black Enchantment
    WATER_ELEMENTAL("Water Elemental"),					// Blue Creature
    WEAKNESS("Weakness"),								// Black Enchantment
    WRATH_OF_GOD("Wrath of God"),						// White Creature
    ZEPHYR_FALCON("Zephyr Falcon");						// Blue Creature
	
	String cardName;
	int idNumber;
	
	private CardName(String cardName/* , idNumber */) {
		this.cardName = cardName;
		//	this.idNumber = idNumber;		-> colocarlos cuando ya esten todas las cartas
	}
	
	public String getCardName() {
		return cardName;
	}
	
}
