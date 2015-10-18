import java.util.LinkedList;
import java.util.List;
//
public abstract class Card{
	
	private String nameCard;
	private String color;
	private Ability ability;
	private List<String> attributes;
	
	public Card(String nameCard, String color, Ability ability){
		this.nameCard = nameCard;
		this.color = color;
		this.ability = ability;
		this.attributes = new LinkedList<String>();
	}
	
	public Card(String nameCard, String color){
		this(nameCard, color, null);
	}
	
	
	
	public void addAttribute(String attribute){
		attributes.add(attribute);
	}
	
	public String getNameCard(){
		return nameCard;
	}
	
	public String getColor(){
		return color;
	}
	
	public Ability getAbility(){
		return ability;		
	}
	
	public List<String> getAttributes(){
		
		if(attributes.size() == 0){
			return null;
		}
		else{
			List<String> aux = new LinkedList<String>();
		
			for(String each: attributes){
				aux.add(each);
			}

			return aux;
		}
		
	}

}

