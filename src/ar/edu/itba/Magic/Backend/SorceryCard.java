package ar.edu.itba.Magic.Backend;

public class SorceryCard extends SpellCard {
	
	public SorceryCard(String nameCard, String typeCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, typeCard, color, colorMana, colorlessMana, ability);
	}
	

	public Ability playCard(ManaPool manaPool) {
		
		//if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			return this.getAbility();
		//}else{
			//tira Excepsion!!!
		//}
	}
}