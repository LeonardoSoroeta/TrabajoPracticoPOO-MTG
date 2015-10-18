
public abstract class SpellCard extends Card{

	private int colorMana;//cantidad de mana de sierto color requerido para invocar esta carta
	private int colorlessMana;//cantidad de mana incoloro requerido para invocar esta carta
	
	public SpellCard(String nameCard, String color, int colorMana, int colorlessMana, Ability ability) {
		super(nameCard, color, ability);
		this.colorMana = colorMana;
		this.colorlessMana = colorlessMana;
	}
	
	public SpellCard(String nameCard, String color, int colorMana, int colorlessMana) {
		super(nameCard, color);
		this.colorMana = colorMana;
		this.colorlessMana = colorlessMana;
	}
	
	
	public int getColorMana(){
		return colorMana;
	}
	
	public int getColorlessMana(){
		return colorlessMana;
	}


}
