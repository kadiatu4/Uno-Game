import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	//Data Field
	private ArrayList<Card> drawPile = new ArrayList<>();
	private ArrayList<Card> discardPile = new ArrayList<>();
	
	//Default Constructor
	public Game(){}
	
	//Starts Game
	public void startGame(){
		Player player = new Player();
		Computer computer = new Computer();
		Deck deckOfCards = new Deck();
		int goesFirst = -1;
		deckOfCards.addCards();
		deckOfCards.shuffleDeck();
		goesFirst = chooseWhoStarts(deckOfCards);
		give5Cards(deckOfCards, player, computer);
		addToDrawPile(deckOfCards);
		addToDiscardPile();
		if(goesFirst == 0){
			playerTurn(player);
			computerTurn(computer);
		}
		else{
			computerTurn(computer);
			playerTurn(player);
			computerTurn(computer);
		}
		while(player.getCards().size() != 0 || computer.getCards().size() != 0){
			playerTurn(player);
			computerTurn(computer);
		}
		
	}
	public void play(){}
	public void printDiscardCard(){
		System.out.print( "\nDiscard Card: \n" + discardCard() +"\n");
	}
	
	//Chooses the first Player
	public int chooseWhoStarts(Deck deckOfCards){

		Card playerCard = deckOfCards.CardOnTop();
		Card computerCard = deckOfCards.CardAfter();
		while(computerCard.getNumber() > 7 || playerCard.getNumber() > 7){
			deckOfCards.shuffleDeck();
			playerCard = deckOfCards.CardOnTop();
			computerCard = deckOfCards.CardAfter();
		}
		
		System.out.print("The Cards picked: \n\n" + "Player picked: " + playerCard + "\nComputer picked: " + computerCard + "\n");
		
		if(playerCard.getNumber() > computerCard.getNumber())
			return 0;
		else if(playerCard.getNumber() == computerCard.getNumber()){
			int getNum;
			getNum = (int)Math.random() * 2;
			return getNum;
		}
		else	
			return 1;
	}
	
	//Gives each player 5 cards
	public void give5Cards(Deck deckOfCards, Player playerCard, Computer computerCard){
		
		Card card1, card2;
		deckOfCards.shuffleDeck();
		for(int i = 0; i < 5; i++){
			//Give Player Cards
			card1 = deckOfCards.CardOnTop();
			playerCard.addCard(card1);
			deckOfCards.RemoveCardFromDeck();
			
			//Give Computer Cards
			card2 = deckOfCards.CardOnTop();
			computerCard.addCard(card2);
			deckOfCards.RemoveCardFromDeck();
		}

		System.out.println(playerCard);
		System.out.println(computerCard);
	}
	
	//Adds the leftOver Cards to the Pile
	public void addToDrawPile(Deck deckOfCards){
		ArrayList<Card> currentDeck = deckOfCards.getDeck();
		for(int i = 0; i < deckOfCards.getSize(); i++)
			drawPile.add(currentDeck.get(i));
		System.out.print("\nThe Current Cards in Draw Pile");
		for(int i = 0; i < drawPile.size(); i++)
			System.out.print(drawPile.get(i));
			
	}

	//Add a card to DiscardPile
	public void addToDiscardPile(){
		int topCard = drawPile.size() - 1;
		Card card = drawPile.get(topCard);
		while(card.getNumber() > 7){
			card = drawPile.get(topCard - 1);
		}
		discardPile.add(card);
		drawPile.remove(card);
		System.out.print("\n\nThe Discard Card is : " + discardPile.get(0).toString() + "\n\n");
	}

	public void playerTurn(Player playerCards){
		Scanner input = new Scanner(System.in);
		ArrayList<Card> cards = playerCards.getCards();
		String color;
		int number;
		boolean cannotUseCard = true;
		boolean playerHasMatchingCard = playerCards.hasCard(discardCard());
		printDiscardCard();
		if(discardCard().getNumber() == 8 ){
			System.out.print("Draw 1 card");
			Card getCard = drawnCard();
			playerCards.addCard(getCard);
			drawPile.remove(getCard);
			return;
		}
		else if(discardCard().getNumber() == 9){
			System.out.print("Draw 2 cards");
			Card getCard;
			
			getCard = drawnCard();
			playerCards.addCard(getCard);
			drawPile.remove(getCard);
			
			getCard = drawnCard();
			playerCards.addCard(getCard);
			drawPile.remove(getCard);
			return;
		}
		else if(playerHasMatchingCard == true){
			System.out.print("Pick a card you want to play. Here are your cards: \n");
			for(int i = 0; i < cards.size(); i++)
				System.out.print(cards.get(i));
			
			do{
				System.out.print("\nEnter the Card Number: \n");
				number = input.nextInt();
				
				if(number == 10){
					System.out.print("What color you want to make this card (Red, Green, Yellow or Blue):");
					color = input.next();
					for(int i = 0; i < cards.size(); i++){
						if(cards.get(i).getNumber() == 10)
							cards.get(i).setColor(color);
					}
				}
				else{
					System.out.print("Enter the Card Color: \n");
					color = input.next();
				}
				
				Card cardPicked = null;
				for(int i = 0; i < cards.size(); i++){
					if(cards.get(i).getNumber() == number && cards.get(i).getColor().equalsIgnoreCase(color))
						cardPicked = cards.get(i);
				}
				
				if(cardPicked.getNumber() == 10){
					cannotUseCard = false;
					discardPile.add(cardPicked);
					playerCards.removeCard(cardPicked);
					
				}
				else if(cardPicked.getColor().equals(discardCard().getColor()) || cardPicked.getNumber() == discardCard().getNumber()){
					cannotUseCard = false;
					discardPile.add(cardPicked);
					playerCards.removeCard(cardPicked);
				}
				if(cannotUseCard){
					System.out.print("Please Enter a matching Card:");
				}
			}while(cannotUseCard);	
		}
		else{
			System.out.print("You do not have any matching cards. Pick up a card:");
			System.out.print("\nHere's the card you picked up: \n" + drawnCard().toString() + "\n");
			Card cardDrawn = drawnCard();
			if(cardDrawn.getNumber() == 10){
				System.out.print("Enter the Card Color: (Red, Green, Blue, or Yellow)\n");
				color = input.next();
				cardDrawn.setColor(color);
				discardPile.add(cardDrawn);
				drawPile.remove(cardDrawn);
			}
			else if(cardDrawn.getColor().equalsIgnoreCase(discardCard().getColor()) ||  cardDrawn.getNumber() == discardCard().getNumber()){
				discardPile.add(cardDrawn);
				drawPile.remove(cardDrawn);
			}
			else{
				System.out.print("The card you picked does not match");
				playerCards.addCard(cardDrawn);
				drawPile.remove(cardDrawn);
			}
						
		}
	}
	public void computerTurn(Computer computerCards){
		ArrayList<Card> cards = computerCards.getCards();
		boolean computerHasMatchingCard = computerCards.hasCard(discardCard());
		String[] colors = {"Red", "Blue", "Green", "Yellow"};
		printDiscardCard();
		if(discardCard().getNumber() == 8){
			System.out.print("\nDraw 1 card");
			Card getCard = drawnCard();
			computerCards.addCard(getCard);
			drawPile.remove(getCard);
			return;
		}
		else if(discardCard().getNumber() == 9){
			System.out.print("\nDraw 2 cards");
			Card getCard;
		
				getCard = drawnCard();
				computerCards.addCard(getCard);
				drawPile.remove(getCard);
				
				getCard = drawnCard();
				computerCards.addCard(getCard);
				drawPile.remove(getCard);

				return;
		}
		else if(computerHasMatchingCard == true){
			System.out.print("\nHere are the computer cards: \n");
			for(int i = 0; i < cards.size(); i++)
				System.out.print(cards.get(i));
			System.out.print("\nComputer is picking a card... ");	
			
			Card cardPicked = null;
			int getNumber = (int) Math.random() * 2;
			int getColor;
			for(int i = 0; i < cards.size(); i++){
				cardPicked = cards.get(i);
				if(cardPicked.getNumber() == 10 && getNumber == 1){
					getColor = (int) Math.random() * 4;
					cardPicked.setColor(colors[getColor]);
					discardPile.add(cardPicked);
					computerCards.removeCard(cardPicked);
					break;
				}
				else if(cardPicked.getNumber() == 10 && discardCard().getNumber() == 10){
					getColor = (int) Math.random() * 4;
					cardPicked.setColor(colors[getColor]);
					discardPile.add(cardPicked);
					computerCards.removeCard(cardPicked);
					break;
				}
				else if(cardPicked.getNumber() == discardCard().getNumber() || cardPicked.getColor() == discardCard().getColor()){
					discardPile.add(cardPicked);
					computerCards.removeCard(cardPicked);
					break;
				}
				getNumber = (int) Math.random() * 2;
			}
				System.out.print("\nHere is the card Computer picked: \n" + discardCard().toString() + "\n");
				
		}
		else{
			int getColor;
			System.out.print("\nComputer picked up a card");
			System.out.print("\nHere's the card Computer picked up: \n" + drawnCard().toString() + "\n");
			Card cardDrawn = drawnCard();
			if(cardDrawn.getNumber() == 10){
				getColor = (int) Math.random() * 4;
				cardDrawn.setColor(colors[getColor]);
				discardPile.add(cardDrawn);
				drawPile.remove(cardDrawn);
			}
			else if(cardDrawn.getColor().equals(discardCard().getColor()) ||  cardDrawn.getNumber() == discardCard().getNumber()){
				discardPile.add(cardDrawn);
				drawPile.remove(cardDrawn);
			}
			else{
				System.out.print("The card Computer picked does not match");
				computerCards.addCard(cardDrawn);
				drawPile.remove(cardDrawn);
			}
						
		}
	}
	
	public Card discardCard(){
		//get size
		int index = discardPile.size() - 1;
		
		//Copy the current card
		Card topCard = discardPile.get(index);
		
		//Return the copied card
		return topCard;
	}
	
	public Card drawnCard(){
		//get size
		int index = drawPile.size() - 1;
				
		//Copy the current card
		Card topCard = drawPile.get(index);
				
		//Return the copied card
		return topCard;
	}
	
//	public Card drawnCard2(){
//		//get size
//		int index = drawPile.size() - 2;
//				
//		//Copy the current card
//		Card topCard = drawPile.get(index);
//				
//		//Return the copied card
//		return topCard;
//	}
}
