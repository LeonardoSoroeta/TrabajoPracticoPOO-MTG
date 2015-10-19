package backEnd;
public class ManaPool {
	private int BlackMana;
	private int BlueMana;
	private int GreenMana;
	private int RedMana;
	private int WhiteMana;
	private int ColorlessMana;
	
	
	public ManaPool(){
		BlackMana = 0;
		BlueMana = 0;
		GreenMana = 0;
		RedMana = 0;
		WhiteMana = 0;
		ColorlessMana = 0;
	}

	//si te paso mal el color IllegalArgumentExeption
	public int getMana(String color){
		if ( color.equals("Black"))
			return this.BlackMana;
		if ( color.equals("Blue"))
			return this.BlackMana;
		if ( color.equals("Green"))
			return this.BlackMana;
		if ( color.equals("Red"))
			return this.BlackMana;
		if ( color.equals("White"))
			return this.BlackMana;
		if ( color.equals("Colorless"))
			return this.BlackMana;
		throw new IllegalArgumentException();
		
	}
	
	public void setMana(String color, int quantity){
		
		if ( color.equals("Black"))
			this.BlackMana = quantity;
		else if ( color.equals("Blue"))
			this.BlackMana = quantity;
		else if ( color.equals("Green"))
			this.BlackMana = quantity;
		else if ( color.equals("Red"))
			this.BlackMana = quantity;
		else if ( color.equals("White"))
			this.BlackMana = quantity;
		else if ( color.equals("Colorless"))
			this.BlackMana = quantity;
		else
			throw new IllegalArgumentException();
	}
	
	//suma o resta quantity mana de color
	public void increaseMana(String color, int quantity){
		if ( color.equals("Black"))
			this.BlackMana += quantity;
		else if ( color.equals("Blue"))
			this.BlackMana += quantity;
		else if ( color.equals("Green"))
			this.BlackMana += quantity;
		else if ( color.equals("Red"))
			this.BlackMana += quantity;
		else if ( color.equals("White"))
			this.BlackMana += quantity;
		else if ( color.equals("Colorless"))
			this.BlackMana += quantity;
		else
			throw new IllegalArgumentException();
	}

	public void decreaseMana(String color, int quantity){
		if ( color.equals("Black"))
			this.BlackMana -= quantity;
		else if ( color.equals("Blue"))
			this.BlackMana -= quantity;
		else if ( color.equals("Green"))
			this.BlackMana -= quantity;
		else if ( color.equals("Red"))
			this.BlackMana -= quantity;
		else if ( color.equals("White"))
			this.BlackMana -= quantity;
		else if ( color.equals("Colorless"))
			this.BlackMana -= quantity;
		else
			throw new IllegalArgumentException();
	}

}
