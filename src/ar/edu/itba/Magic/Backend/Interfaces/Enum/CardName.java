package ar.edu.itba.Magic.Backend.Interfaces.Enum;

/**
 * Created by Martin on 02/11/2015.
 */
/*
 * Red Cards:		4
 * Green Cards:		12
 * Blue Cards:		8
 * Black Cards: 	19
 * White Cards:		9
 * Artifacts:		0
 * Lands:			5
 * 
 * Total:			57
 */
public enum CardName {
	
	AIR_ELEMENTAL("Air Elemental"),				// Blue Creature
	ASPECT_OF_WOLF("Aspect of Wolf"),			// Green Enchantment
    BAD_MOON("Bad Moon"),						// Black Enchantment
    BALL_LIGHTNING("Ball Lightning"),			// Red Creature
    BIRD_MAIDEN("Bird Maiden"),					// Red Creature
    BIRDS_OF_PARADISE("Birds of Paradise"),		// Green Creature
    BLIGHT("Blight"),							// Black Enchantment
    BLOOD_LUST("Blood Lust"),					// Red Instant
    BOG_IMP("Bog Imp"),							// Black Creature
    CARNIVOROUS_PLANT("Carnivorous Plant"),		// Green Creature
    CARRION_ANTS("Carrion Ants"),				// Black Creature
    CRAW_WURM("Craw Wurm"),						// Green Creature
    CRUMBLE("Crumble"),							// Green Instant
    CRUSADE("Crusade"),							// White Enchantment
    DARK_RITUAL("Dark Ritual"),					// Black Instant
    DESERT_TWISTER("Desert Twister"),			// Green Sorcery
    DRAIN_POWER("Drain Power"), 				// Blue Sorcery
    DURKWOOD_BOARS("Durkwood Boars"),			// Green Creature
    ELVISH_ARCHERS("Elvish Archers"),			// Green Creature
    FISSURE("Fissure"),							// Red Instant
    FLIGHT("Flight"),							// Blue Enchantment
    FLOOD("Flood"),								// Blue Enchantment
    FOREST("Forest"),							// Land
    FORCE_OF_NATURE("Force of Nature"),			// Green Creature
    FROZEN_SHADE("Frozen Shade"),				// Black Creature
    HOLY_STRENGTH("Holy Strength"),				// White Enchantment
    HOWL_FROM_BEYOND("Howl from Beyond"),		// Black Instant
    ISLAND("Island"),							// Land
    JUMP("Jump"),								// Blue Instant
    JUNUN_EFREET("Junun Efreet"),				// Black Creature
    KARMA("Karma"),								// White Enchantment
    KISMET("Kismet"),							// White Enchantment
    LANCE("Lance"),								// White Enchantment
    LAND_LEECHES("Land Leeches"),				// Green Creature
    LORD_OF_THE_PIT("Lord of the Pit"),			// Black Creature
    LOST_SOUL("Lost Soul"),						// Black Creature
    MARSH_GAS("Marsh Gas"),						// Black Instant
    MOUNTAIN("Mountain"),						// Land
    NIGHTMARE("Nightmare"),						// Black Creature
    PARALYZE("Paralyze"),						// Black Enchantment
    PEARLED_UNICORN("Pearled Unicorn"),			// White Creature
    PLAINS("Plains"),							// Land
    RADJAN_SPIRIT("Radjan Spirit"),				// Green Creature
    ROYAL_ASSASIN("Royal Assassin"),			// Black Creature
    SAVANNAH_LIONS("Savannah Lions"),			// White Creature
    SEGOVIAN_LEVIATHAN("Segovian Leviathan"),	// Blue Creature
    SERRA_ANGEL("Serra Angel"),					// White Creature
    SINKHOLE("Sinkhole"),						// Black Sorcery
    SWAMP("Swamp"),								// Land
    TERROR("Terror"),							// Black Instant
    TUNDRA_WOLVES("Tundra Wolves"),				// White Creature
    UNHOLY_STRENGTH("Unholy Strength"),			// Black Enchantment
    UNSTABLE_MUTATION("Unstable Mutation"),		// Blue Enchantment
    WANDERLUST("Wanderlust"),					// Green Enchantment
    WARP_ARTIFACT("Warp Artifact"),				// Black Enchantment
    WEAKNESS("Weakness"),						// Black Enchantment
    ZEPHYR_FALCON("Zephyr Falcon");				// Blue Creature
	
	String cardName;
	
	private CardName(String cardName) {
		this.cardName = cardName;
	}
	
}
