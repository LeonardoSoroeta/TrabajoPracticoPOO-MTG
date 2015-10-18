
public class ArtifactCard extends SpellCard {

	public ArtifactCard(String nameCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, color, colorMana, colorlessMana, ability);
	}
	
	@Override
	public InPlayObject playCard(ManaPool manaPool) {
		
		if(manaPool.getMana(this.getColor()) >= this.getColorMana() && manaPool.getMana("Colorless") >= this.getColorlessMana()){
			manaPool.decreaseMana(this.getColor(),  this.getColorMana());
			manaPool.decreaseMana("Colorless", this.getColorlessMana());
			Artifact artifact = new Artifact(this.getNameCard(), this.getColor(), this.getAttributes(), this.getAbilities());
			return artifact;
		}else{
			//tira Excepsion!!!
		}
	}
}
