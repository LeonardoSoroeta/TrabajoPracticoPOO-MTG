package Magic;

public class LandCard extends Card{

	
	
	public LandCard(String nameCard, String typeCard, String color){
		super(nameCard, typeCard, color);
	}


	public InPlayObject playCard(ManaPool manaPool) {
		
		Land land = new Land(this.getNameCard(), this.getColor());
		return Land;
		
	}
	
}
