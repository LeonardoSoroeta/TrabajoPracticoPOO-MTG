package ar.edu.itba.Magic.Frontend;

import java.util.LinkedList;

import org.newdawn.slick.Input;

import ar.edu.itba.Magic.Backend.Match;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Interfaces.Drawable;

public class MatchUI implements Drawable {
	private Match match;
	//deckUI representa la clase deck de la interfaz grafica, donde guarda cardUI que son
	//la parte grafica de las cartas
	private PlayerUI p1UI, p2UI;
	private static MatchUI self = null;
	
	static MatchUI getMatchUI() {
		if(self == null) {
			self = new MatchUI();
		}
		return self;
	}
	
	
	public void init(){
		//se cargan en memoria las imagenes del match
		//imagen = new ExtendedImage(....);
		match = Match.getMatch();
	}
	
	public void update(Input input) {
		//codigo de ejemplo, isTargetRequired devuelve una varaible booleana
		//que se setea desde la instancia de match, en el caso que sea necesario
		 if(match.isTargetRequired()) {
			 boolean selected = false;
			 while(!selected) {
				 LinkedList<CardUI> matchCardsUI = new LinkedList<CardUI>();
				 //esto esta simplificado em este caso el back deberia saberi si se eligio la carta del oponete
				 //pero se podria hacer que le pregunta a la instancia dle match de quien es el turno y solo
				 //itere en las cartas del oponente, pero la idea es mostrar un ejemplo bien general
				 matchCardsUI.addAll(p1UI.getCardsUI());
				 matchCardsUI.addAll(p2UI.getCardsUI());
				 for(CardUI each: matchCardsUI){
					 //si hizo click en una carta le manda la referencia al match
					 //aca podriamos validar si es enemiga, pero tambien lo puede hacer desde match
					 if(each.mouseLClicked(input)) {
						 //le manda la referencia de la Card, en el caso actual la 
						 //clase CardUI no puede construir la carta que la representa, todavia
						 //hace falta el metodo que vengo pidiendo el cual mapea un numero entero
						 //en una instancia de Card, ahora solo se podria hacer each.getNum()
						 //que representa el numero de la carta,
						 match.sendTarget(each.getCard());
						 //cuando en la mecanica dle match tiene que poner targetRequired = true
						 //en ese contexto se debe quedar loopeando hasta que el metodo sendTarget del
						 //match setee targetRequired = false, y en otra variable la 
						 //card o el objeto que se necesite,
						 
						 //esta hecho los metodos de ej en la clase match, revisenlos!
					}
				 }
			 }
		 }
	}

	public void draw() {
		//imagen.draw()
		
	}


}
