package ar.edu.itba.Magic.Backend;

import java.util.List;

public class LandCard extends Card{

	public LandCard(String cardName, String cardType, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
		super(cardName, cardType, color, attributes, coloredManaCost, colorlessManaCost, ability);
	}
	
	public void playCard() {
		//ManaPool manaPool = ManaPool.getManaPool();
		
		//if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
		//	manaPool.decreaseMana(this.getColor(),  this.getColorMana());
		//	manaPool.decreaseMana("Colorless", this.getColorlessMana());
		//	Enchantment enchantment = new Enchantment(this.getNameCard(), this.getColor(), this.getAttributes(), this.getAbility());
		//	return enchantment;
		//}else{
			//tira Exception!!!
		//}
		
		//colocar en juego directamente sin enviar al stack, avisar que ya se coloco un land este turno
	}
	
}
