package backend;

public class ArtifactCard extends SpellCard {

	public ArtifactCard(String nameCard, String typeCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, typeCard, color, colorMana, colorlessMana, ability);
	}
	

	public InPlayObject playCard(ManaPool manaPool) {
		
		if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			Artifact artifact = new Artifact(this.getNameCard(), this.getColor(), this.getAttributes(), this.getAbility());
			return artifact;
		}else{
			//tira Excepsion!!!
		}
	}
}
