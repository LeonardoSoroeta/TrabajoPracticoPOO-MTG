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
		//}else{
			//tira Excepsion!!!
		//}
		
		Artifact artifact = new Artifact(this, this.getNameCard(), this.getAttributes(), this.getColorlessManaCost(), (PermanentAbility) this.getAbility());
			
		if(artifact.getAbility().satisfyCastingRequirements() == true)
			artifact.sendToStack();
		
	}
}
