package ar.edu.itba.Magic.Backend;

public class CardFactory {
	
	private static CardFactory instance = new CardFactory();
	
	private CardFactory() {
		
	}
	
	public static CardFactory getCardFactory() {
        return instance;
	}
	
	public Card getCard(String cardName) {
		
		switch(cardName) {
		
			case "gokuCard":
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10);
				
			default:
				return new CreatureCard("Goku", "saiyan", "black", 10, 10, 10, 10, 
						new ActivatedManaAbility() {
					
							private Object source;
							
							public void activate() {
								System.out.println("Kamehameha");
							}
							
							public void setSource(Object source) {
								this.source = source;
							}
							
							public Object getSource() {
								return source;
							}				
				});
				
		}
	}
	
}
