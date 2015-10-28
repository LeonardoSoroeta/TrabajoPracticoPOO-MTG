import java.util.*;

public abstract class Permanent {

	public abstract Card dead();
	
	public abstract void destroy();   // temporario
	
	public abstract List<LastingEffect> getAppliedEffects();	// idea
	
	public abstract List<Enchantment> getAttachedEnchantments();	// idea
	
}