package ar.edu.itba.Magic.Backend;

public class LandCard extends Card{

	//si trabaja como habilidad el girar el land hay q agregar al constructor
	
	public LandCard(String nameCard, String typeCard, String color){
		super(nameCard, typeCard, color);
	}


	public Permanent playCard(ManaPool manaPool) {
		
		Land land = new Land(this.getNameCard(), this.getColor());
		return Land;
		
	}
	
}