package ar.edu.itba.Magic.Backend;

import java.util.List;

public class ArtifactCard extends Card {

	public ArtifactCard(String cardName, String cardType, List<String> attributes, int colorlessManaCost, Ability ability) {
		super(cardName, cardType, "colorless", attributes, 0, colorlessManaCost, ability);
	}
	

	public void playCard() {
		//ManaPool manaPool = ManaPool.getManaPool();
		
		//if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
		//	manaPool.decreaseMana(this.getColor(),  this.getColorMana());
		//	manaPool.decreaseMana("Colorless", this.getColorlessMana());
		//	Artifact artifact = new Artifact(this.getNameCard(), this.getColor(), this.getAttributes(), this.getAbility());
		//	return artifact;
		//}else{
			//tira Excepsion!!!
		//}
	}
}
