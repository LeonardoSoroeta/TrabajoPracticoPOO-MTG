
public class InstantCard extends SpellCard {

	
	public InstantCard(String nameCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, color, colorMana, colorlessMana, ability);
	}
	

	public Ability playCard(ManaPool manaPool) {
		
		if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			return this.getAbility();
		}else{
			//tira Excepsion!!!
		}
	}
	
	
}
