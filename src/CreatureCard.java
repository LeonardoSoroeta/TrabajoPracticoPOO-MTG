
public class CreatureCard extends SpellCard{

	private int attackPoints;
	private int defencePoints;
	
	public CreatureCard(String nameCard, String color, int colorMana, int colorlessMana, int attackPoints, int defencePoints, Ability ability) {
		super(nameCard, color, colorMana, colorlessMana, ability);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}

	public CreatureCard(String nameCard, String color, int colorMana, int colorlessMana, int attackPoints, int defencePoints) {
		super(nameCard, color, colorMana, colorlessMana);
		this.attackPoints = attackPoints;
		this.defencePoints = defencePoints;
	}
	
	
	public InPlayObject playCard(ManaPool manaPool) {
		
		if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			Creature creature = new Creature(this.getNameCard(), attackPoints, defencePoints, this.getColor(), this.getAttributes(), this.getAbilities());
			return creature;
		}else{
			//tira Excepsion!!!
		}
	}
	
	
}