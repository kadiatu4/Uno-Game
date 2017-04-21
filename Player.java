import java.util.ArrayList;

public class Player {
	
	//Data Field
	private ArrayList<Card> playerCards = new ArrayList<>();
	
	//Default Constructor
	public Player(){}
	
	//GetPlayerCards
	public ArrayList<Card> getCards(){return playerCards;}
	
	//Number of Cards
		public int getNumberOfCards(){ return playerCards.size();}
		
	//Add Card 
	 public void addCard(Card currentCard){
		playerCards.add(currentCard);
	 }
	 
	 //Place in Discard Pile 
	 public void removeCard(Card currentCard){
		 
		 playerCards.remove(currentCard);
	 }
	 
	 //Check if player has card
	 public boolean hasCard(Card currentCard){
		 boolean foundMatch = false;

		 for(int count = 0; count < playerCards.size(); count++){
			 if(currentCard.getColor() == playerCards.get(count).getColor() ||currentCard.getNumber() == playerCards.get(count).getNumber())
				 foundMatch = true;
			 
		 }
		return foundMatch;
	 }
	 
	 
	
	public String toString(){
		String str = "";
		str = "\nPlayer's current cards: "; 
		for(int count = 0; count < playerCards.size(); count++)
			str += playerCards.get(count).toString();
		return str;
	}
//	public timer(){}
//	public boolean Uno(){
//	
//	}
	
}
