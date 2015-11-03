package ar.edu.itba.Magic.Backend.Interfaces.Enum;

/**
 * Created by Martin on 29/10/2015.
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
