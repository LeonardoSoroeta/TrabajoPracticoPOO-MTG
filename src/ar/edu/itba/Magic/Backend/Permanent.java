import java.util.*;

/**
 * All objects currently in play are Permanents. These objects may be a Creature, an Enchantment, an Artifact or a Land.
 */
public abstract class Permanent {
	
	private Card sourceCard;
	private Player controller;
	private String name;
	private String color;
	private int coloredManacost;
	private int colorlessManacost;
	private PermanentAbility ability;
	private List<String> attributes;
	public List<LastingEffect> appliedEffects = new LinkedList<LastingEffect>();
	public List<Enchantment> attachedEnchantments = new LinkedList<Enchantment>();
	
	public Permanent(Card sourceCard, String name, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, PermanentAbility ability) {
		this.sourceCard = sourceCard;
		this.name = name;
		this.attributes = attributes;
		this.ability = ability;
		this.color = color;
		this.coloredManacost = coloredManaCost;
		this.colorlessManacost = colorlessManaCost;
	}
	
	public void addAppliedEffect(LastingEffect lastingEffect) {
		appliedEffects.add(lastingEffect);
	}
	
	public void addAttachedEnchantment(Enchantment enchantment) {
		attachedEnchantments.add(enchantment);
	}

	public List<LastingEffect> getAppliedEffects()	{
		return appliedEffects;
	}
	
	public  List<Enchantment> getAttachedEnchantments() {
		return attachedEnchantments;
	}
	
	public List<String> getAttributes() {
		return attributes;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setController(Player controller) {
		this.controller = controller;
	}
	
	public Player getController() {
		return controller;
	}
	
	public Card getSourceCard() {
		return sourceCard;
	}
	
	public boolean containsAbility() {
		if (this.ability == null)
			return false;
		return true;
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	public void destroy() {
		//quitar del juego
		//signalGameEvent(new GameEvent("permanent_leaves_play", this);
		//for all applied effects y attached enchantments : quitarlos del juego tambien
		//agregar al cementerio this.getSourceCard
	}
	
}