package ar.edu.itba.Magic.Backend;

import java.util.LinkedList;
import java.util.List;

public abstract class Card {
	
	private String cardName;
	private String cardType;
	private String color;
	private Integer coloredManaCost;
	private Integer colorlessManaCost;
	private List<String> attributes;
	private Ability ability;

	public Card(String cardName,String cardType, String color, List<String> attributes, Integer coloredManaCost, Integer colorlessManaCost ,Ability ability){
		this.cardName = cardName;
		this.cardType = cardType;
		this.color = color;
		this.ability = ability;
		this.attributes = attributes;
	}
	
	public void addAttribute(String attribute){
		attributes.add(attribute);
	}
	
	public String getNameCard(){
		return cardName;
	}
	
	public String getTypeCard(){
		return this.cardType;
	}
	
	public String getColor(){
		return color;
	}
	
	public Ability getAbility(){
		return ability;		
	}
	
	public boolean containsAbility(){
		if (this.ability == null)
			return false;
		return true;
	}

	public List<String> getAttributes(){
		
		if(attributes.size() == 0){
			return null;
		}
		else{
			List<String> aux = new LinkedList<String>(attributes);
		
			/*for(Attribute each: attributes){
				aux.add(each);
			}*/

			return aux;
		}
		
	}
	
	public abstract void playCard();	

}

