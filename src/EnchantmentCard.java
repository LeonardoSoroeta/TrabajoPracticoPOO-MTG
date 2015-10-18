
public class EnchantmentCard extends SpellCard {

	
	public EnchantmentCard(String nameCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, color, colorMana, colorlessMana, ability);
	}
	

	public InPlayObject playCard(ManaPool manaPool) {
		
		if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			Enchantment enchantment = new Enchantment(this.getNameCard(), this.getColor(), this.getAttributes(), this.getAbilities());
			return enchantment;
		}else{
			//tira Excepsion!!!
		}
	}
}
