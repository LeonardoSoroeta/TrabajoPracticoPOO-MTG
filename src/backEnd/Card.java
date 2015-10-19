package backEnd;
import java.util.LinkedList;
import java.util.List;

public abstract class Card{
	
	private String nameCard;
	private String typeCard;
	private String color;
	private Ability ability;
	private List<Attribute> attributes;
	
	
	public Card(String nameCard,String typeCard, String color, Ability ability){
		this.nameCard = nameCard;
		this.typeCard = typeCard;
		this.color = color;
		this.ability = ability;
		this.attributes = new LinkedList<Attribute>();
	}
	
	public Card(String nameCard,String typeCard, String color){
		this(nameCard, typeCard, color, null);
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	public String getNameCard(){
		return nameCard;
	}
	
	public String getTypeCard(){
		return this.typeCard;
	}
	
	public String getColor(){
		return color;
	}
	
	public Ability getAbility(){
		return ability;		
	}
	
	public List<Attribute> getAttributes(){
		
		if(attributes.size() == 0){
			return null;
		}
		else{
			List<Attribute> aux = new LinkedList<Attribute>();
		
			for(Attribute each: attributes){
				aux.add(each);
			}

			return aux;
		}
		
	}

}

