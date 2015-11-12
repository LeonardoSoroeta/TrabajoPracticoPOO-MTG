package ar.edu.itba.Magic.Backend.Enums;

/**
 * This enum represents the 6 different color types in the game.
 */
public enum Color {

    WHITE("White"), 
    BLACK("Black"), 
    RED("Red"), 
    GREEN("Green"), 
    BLUE("Blue"), 
    COLORLESS("Colorless");
	
	String color;
	
	private Color(String color) {
		this.color = color;
	}

}
