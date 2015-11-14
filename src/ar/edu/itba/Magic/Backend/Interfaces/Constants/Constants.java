package ar.edu.itba.Magic.Backend.Interfaces.Constants;

public interface Constants {

	public static final int VIRTUAL_WIDTH = 800;
	public static final int VIRTUAL_HEIGHT = 600;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	public static final Integer START_HEALTH = 20;
	public static final String MAIN_PHASE_PROMPT = "MAIN PHASE: Cast spells, activate abilities.";
	public static final String SPELL_STACK_PROMPT = "SPELL CHAIN: Fast actions?";
}
