
public class LandCard extends Card{

	
	
	public LandCard(String nameCard, String color){
		super(nameCard, color);
	}


	public InPlayObject playCard(ManaPool manaPool) {
		Land land = new Land(this.getNameCard(), this.getColor());
		
	}
	
}
