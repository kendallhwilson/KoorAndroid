package com.plnu.gamecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * Game.java
 *
 * 10/17/14
 * Game Class includes functions for gameplay
 */

public class Game {
	Player [] players = new Player[4];
	int bidWinner = 0; 
	int trickWinner = 0;
	Card [] currentTrick = new Card[4];
	Card.Suit trumpColor = null; 
	protected ArrayList<Card> deck = new ArrayList<Card>();
	protected Card kitty[] = new Card[5];
	protected int highBid = 0;
	protected boolean [] playerActive = new boolean[4];
	protected int[] currentTeamScores = new int[2];
	protected int[] roundScore = new int[2];
	int tricksWon = 0;
	int currentPlayersTurn = 0;
	int currentPlaceInTrick = 0;
	int numberOfTricksPlayed = 0;
	
	int count = 1;
	int allAIAndPlayerBids[] = new int[4];
	int playersRemainingInBidding = 4;
	
	public Game(){
	      players[0] = new NPC();
	      players[1] = new NPC();
	      players[2] = new NPC();
	      players[3] = new RPC();
	      players[0].myPartner = players[2];
	      players[1].myPartner = players[3];
	      players[2].myPartner = players[0];
	      players[3].myPartner = players[1];
	      currentTrick[0] = new Card();
	      currentTrick[1] = new Card();
	      currentTrick[2] = new Card();
	      currentTrick[3] = new Card();
	}
	
	public void CleanAllBiddingObjects(){ //Actually cleans bidding and player hand objects.
		//Resetting all values in playerActive to true. This allows us to reuse the class variables we created to store the values between calls.
		for(int i=0; i<4;i++){
			playerActive[i] = true;
			players[i].bidding = true;
			
		}
		
		highBid = 100; //Setting highBid back to minimum.
		bidWinner = 0; //Setting the bid winner to the first bidder.
		playersRemainingInBidding = 4; //4 Players bidding
		allAIAndPlayerBids = new int[] {0,0,0,0};
		
		
	}
	
	public void CleanAllGamePlayObjects(){
		for(int i=0; i < 4; i++){
			for(int j=0; j < 5; j++){ //Resetting discarded cards.
				players[i].discards[j] = null;
			}
		
			for(int j=0; j < 15; j++){
				players[i].hand[j] = new Card();
			}
		}
		
		  players[0] = new NPC(); //Just to be safe, we will reinstantiate all of the objects. 
	      players[1] = new NPC();
	      players[2] = new NPC();
	      players[3] = new RPC();
	      players[0].myPartner = players[2];
	      players[1].myPartner = players[3];
	      players[2].myPartner = players[0];
	      players[3].myPartner = players[1];
	      currentTrick[0] = new Card();
	      currentTrick[1] = new Card();
	      currentTrick[2] = new Card();
	      currentTrick[3] = new Card();
	}
	public int[] advanceBidding() {
		
		for(int i=0; i < 3; i++){ //For each AI PLAYER, we iterate through and see if they want to bid. If they do, they replace the bid. 
			
			if(highBid < 195 && playersRemainingInBidding > 1){
			
			players[i].bidOrPass(players[i].bidding);
			
			if(players[i].bidding == true){ //If the player is still bidding
				highBid = players[i].bid(highBid);
				bidWinner = i;
				allAIAndPlayerBids[i] = highBid;	
			}
			else if(allAIAndPlayerBids[i] != -2){ //If they no longer want to bid, remove them. We check to see if they have already been removed by comparing their score to -2, our removed player value.
				players[i].bidding = false;
				playersRemainingInBidding--;
				allAIAndPlayerBids[i] = -2;
			}
			
		} else if (highBid > 195 && playersRemainingInBidding > 1){
			players[bidWinner].setHighBidder(highBid);
			players[bidWinner].winningBiddingTeam = true;
			players[bidWinner].myPartner.winningBiddingTeam = true;
			trickWinner = bidWinner;
			playersRemainingInBidding = 0;
			currentPlayersTurn = bidWinner;
		}
		
		if(allAIAndPlayerBids[0] == -2 && allAIAndPlayerBids[1] == -2 && allAIAndPlayerBids[2] == -2){
			players[3].setHighBidder(highBid);
			players[3].winningBiddingTeam = true;
			players[3].myPartner.winningBiddingTeam = true;
			bidWinner = 3;
			trickWinner = bidWinner;
			playersRemainingInBidding = 0;
			currentPlayersTurn = bidWinner;
		}
		
		if(playersRemainingInBidding == 1){
			players[bidWinner].setHighBidder(highBid);
			players[bidWinner].winningBiddingTeam = true;
			players[bidWinner].myPartner.winningBiddingTeam = true;
			trickWinner = bidWinner;
			playersRemainingInBidding = 0;
			currentPlayersTurn = bidWinner;
		}
		}
		return allAIAndPlayerBids;
	}
	
	public void playerDroppedFromBidding() { //This allows the GUI to remove the player from bidding and still use advanceBid to let the AI compete.
		allAIAndPlayerBids[3] = -2;
		playersRemainingInBidding--;
	}
	
	public void playerEnteredNewBid(int playerNewBid){ //If the player entered a new bid, add it to the array before the GUI calls advanceBid again.
		highBid = playerNewBid;
		allAIAndPlayerBids[3] = playerNewBid;
		bidWinner = 3;
	}
	
	public int getNumberOfBiddersRemaining(){ //Allows the GUI to find how many players are remaining.
		return playersRemainingInBidding;
	}
	
	public String setTrumpAndInformAI(String chosenColor){
		if(bidWinner < 3){ //Player did not win the bid.
			players[bidWinner].addKittyToHand(kitty);
			players[bidWinner].reorganizeHand((players[bidWinner].chooseDiscards()));
			players[bidWinner].determineSuitLengths();
			players[bidWinner].determineStrongestSuit();
			trumpColor = players[bidWinner].chooseTrump();
			
			chosenColor = trumpColor.toString();
			
			players[0].setTrump(trumpColor);
			players[1].setTrump(trumpColor);
			players[2].setTrump(trumpColor);
		}else{ //Player won the bid.
			
			players[0].setTrump(Card.Suit.valueOf(chosenColor));
			players[1].setTrump(Card.Suit.valueOf(chosenColor));
			players[2].setTrump(Card.Suit.valueOf(chosenColor));
		}
		
		trumpColor = Card.Suit.valueOf(chosenColor);
		
		return chosenColor;
	}
	
	
	
	//----- Code above this line is utilized for gameplay through the GUI. Everything below is only utilized for CMD gameplay and must be converted.----
	
	public void advanceGameState(){
		
		if(numberOfTricksPlayed >= 10)
		{
			return;
		}
		if(currentTrick[0].getValue() == -1 && currentTrick[1].getValue() == -1 && currentTrick[2].getValue() == -1 && currentTrick[3].getValue() == -1){
			//Play the card that the leader of the trick wants to play, at index 0
			if(trickWinner != 3){
		     int indexToPlay = players[trickWinner].lead();
		     
		     //Get the different values of the card that is to be played
		     Card.Suit setSuit = players[trickWinner].hand[indexToPlay].getSuit();
		     int setVal = players[trickWinner].hand[indexToPlay].getCardVal();
		     int setHiddenValue = players[trickWinner].hand[indexToPlay].getValue();
		     
		     //Set the first spot in the trick to the card that was led
		     currentTrick[0] = new Card();
		     currentTrick[0].setCard(setSuit,setHiddenValue);
		     currentTrick[0].setCardValue(setVal);
		     
		     //Discard the card from the players hand(replace with empty card)
		     players[trickWinner].hand[indexToPlay].setCard(Card.Suit.BLANK, 100);
		     players[trickWinner].hand[indexToPlay].setCardValue(0);
		     
		     currentPlayersTurn = (currentPlayersTurn + 1) % 4;
		     currentPlaceInTrick++;
			}
			
			while(currentPlayersTurn != 3 && (currentTrick[0].getValue() == -1 || currentTrick[1].getValue() == -1 || currentTrick[2].getValue() == -1 || currentTrick[3].getValue() == -1)){
				currentTrick=players[currentPlayersTurn].Play(currentTrick,currentPlaceInTrick);
				currentPlayersTurn = (currentPlayersTurn + 1) % 4;
			     currentPlaceInTrick++;
			}
		}
		else {
			while(currentPlayersTurn != 3 && (currentTrick[0].getValue() == -1 || currentTrick[1].getValue() == -1 || currentTrick[2].getValue() == -1 || currentTrick[3].getValue() == -1)){
				currentTrick=players[currentPlayersTurn].Play(currentTrick, currentPlaceInTrick);
				currentPlayersTurn = (currentPlayersTurn + 1) % 4;
			    currentPlaceInTrick++;
			}
		}
		
		if(currentTrick[0].getValue() != -1 && currentTrick[1].getValue() != -1 && currentTrick[2].getValue() != -1 && currentTrick[3].getValue() != -1){
			int MAX =0;
	         boolean trumpFound = false;
	         boolean rookFound = false;
	         int rookAt = 0;
	         
	         // Find highest outright card value that follows suit
	         for(int j=1;j<4;j++)
	         {
	            if(currentTrick[MAX].getCardVal() != 1)
	               if((currentTrick[j].getCardVal()==1|| 
	                     currentTrick[j].getCardVal()>currentTrick[MAX].getCardVal())
	                     && currentTrick[0].getSuit()== currentTrick[j].getSuit())
	               MAX=j;
	         }

	         // If trump wasn't led, check to see if anyone trumped in
	         // and won the suit
	         if(currentTrick[0].getSuit() != trumpColor)
	         {
	            for(int k=0;k<4;k++)
	            {
	               if(currentTrick[k].getSuit() == trumpColor && !trumpFound)
	               {
	                  MAX=k;
	                  trumpFound = true;
	               }
	               else if(currentTrick[k].getSuit() == trumpColor &&
	                   (currentTrick[k].getCardVal()>currentTrick[MAX].getCardVal() ||
	                     currentTrick[k].getCardVal()==1))
	               {
	                  MAX=k;
	               }
	            }
	         }
	         
	         //Check to see if Rook is found, if so it wins unless
	         //a 11,12,13,14 or 1 of trump is played
	         for(int j=0;j<4;j++)
	         {
	            if(currentTrick[j].getCardVal()==44)
	            {
	               rookFound = true;
	               rookAt = j;
	            }
	         }
	         
	         if(rookFound)
	         {
	            if(!(currentTrick[MAX].getSuit()== trumpColor))
	               MAX=rookAt;
	            else if(!(currentTrick[MAX].getCardVal()==1 ||
	                      currentTrick[MAX].getCardVal()==11||
	                      currentTrick[MAX].getCardVal()==12||
	                      currentTrick[MAX].getCardVal()==13||
	                      currentTrick[MAX].getCardVal()==14 ))
	            {
	                      MAX=rookAt;
	            }
	         }                   
	         
	         // The trickWinner is set to the player that won the bid
	         //System.out.println("WINNER WAS PLAYER IN TRICK AT: "+MAX);
	         trickWinner = (trickWinner+MAX)%4;
	         addTrickScore(trickWinner,currentTrick);
	         numberOfTricksPlayed++;
		}
		
		if(numberOfTricksPlayed == 10){
			addDiscardToScore();
		    addRoundScoreToGameScore();
		}
	}
	
/** 
 * makeDeck method iterates through all suits (skipping NOSUIT and BLANK) and creates 1-14 in each suit
 * deck is filled and shuffled after completion of this method
 */
	public void makeDeck(){
				
		deck.clear();
		
		for(int i=0;i<44;i++){
			
			Card card = new Card();
			
			
			
			if(i <= 10){
				card.setCard(Card.Suit.RED,i);
			}
					
			else if(i <= 21){
			card.setCard(Card.Suit.BLUE,i);
			}
					
			else if(i <= 32){
				card.setCard(Card.Suit.GREEN,i);
			}
					
			else if(i <= 43){
				card.setCard(Card.Suit.BLACK,i);
				}
			
			card.setCardVal();
			deck.add(card);
		}
		
		//specific code for rook card
		Card card = new Card();
		card.setCard(Card.Suit.NOSUIT,44);
		card.setCardVal();
		deck.add(card);
		
		//"shuffling" the deck
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
		  
		  count++;
	  }	  
	
public void dealCards(){
	
	int handIndex = 0;
	
	//deal out the 40 cards to the players
	for(int i=0;i<40;){
		
		players[0].hand[handIndex]=deck.get(i);
		i++;
		
		players[1].hand[handIndex]=deck.get(i);
		i++;
	
		players[2].hand[handIndex] = deck.get(i);
		i++;
	
		players[3].hand[handIndex]= deck.get(i);
		i++;	
		handIndex++;
		}
	
	//set blank card objects to the last 5 cards in hand
	for(int i=0;i<5;i++){
		
		Card nullCardPlaceholder=new Card();
		nullCardPlaceholder.setCard(Card.Suit.BLANK,100); //Using 100 as the value so it is sorted to the bottom of the hand later.
		
     
		
		players[0].hand[handIndex]=nullCardPlaceholder;
		players[1].hand[handIndex]=nullCardPlaceholder;
		players[2].hand[handIndex]=nullCardPlaceholder;
		players[3].hand[handIndex]=nullCardPlaceholder;
		handIndex++;
	}
	
	//add the last 5 cards to the kitty
	for(int i=40;i<45;i++){
		int temp = i % 5;
		kitty[temp] = deck.get(i);
		

	}
	
   for(int i = 0; i < 4; i++){
      players[i].sortHand(10);
   }
	
}



public void addTrickScore(int trickWinner, Card[] currentTrick){
	int trickScore=0;
	for(int i =0;i<4;i++){
		trickScore += currentTrick[i].getScore();
	}
	
	if (trickWinner%2==0){
		roundScore[0] += trickScore;
		tricksWon++;
	}
	else{ 
		roundScore[1] += trickScore;
	}	
}

/** 
 * addDiscardToScore() adds the values of the originally discarded cards to the score 
 * of the team that won the last trick
 */
public void addDiscardToScore(){
	/*How are current team scores decided? This assumes that players 0 and 2 are currentTeamScores[0]
	 * and players 1 and 3 are currentTeamScores[1]
	 */
	if(trickWinner == 0 || trickWinner == 2){
		roundScore[0] += discardScore();
	}
	if(trickWinner == 1 || trickWinner == 3){
		roundScore[1] += discardScore();
	}
}
/** 
 * discardScore() grabs the discards array from the original bidWinner and adds the scores
 * of the cards up and returns the total score value of the discards
 */
public int discardScore(){
	int discardScore = 0;
	//Need to get card values from discarded array in the Player
	//Winner of last trick is not necessarily the one who discarded
	for(int i = 0; i < players[bidWinner].discards.length-1; i++){
		discardScore += players[bidWinner].discards[i].getScore();
	}
	return discardScore;
}


public void addRoundScoreToGameScore(){
	
	//tricksWon refers to number of tricks won by computer team, adds 20 pts if its greater than 5
	if(tricksWon!=5){
		if(tricksWon>5){
			roundScore[0] += 20;
		}
		else{
			roundScore[1] +=20;
		}
	}

	//computer team won the bid
	if (bidWinner%2==0){
		
		//updating human score even tho computers won the bid
		currentTeamScores[1] += roundScore[1];
		
		if(roundScore[0]<players[bidWinner].bidAmount){
			currentTeamScores[0] -= players[bidWinner].bidAmount;
		}
	
		else{
		currentTeamScores[0] += roundScore[0];
		}
	}
	//else the human team won the bid
	else{
		
		//updating computer score even though human team won
		currentTeamScores[0] += roundScore[0];
		
		if(roundScore[1]<players[bidWinner].bidAmount){
			currentTeamScores[1] -= players[bidWinner].bidAmount;
		}
	
		else{
		currentTeamScores[1] += roundScore[1];
		}
	}
	
	
	
}
//***
//ACCESSORS AND MUTATORS NECESSARY FOR GUI FUNCTIONALITY.
//***

//Precondition: Player's hand has been instantiated.
//Postcondition: Returns the cards that are within the playerHand array.
public int[] getPlayerCardsUI(){
	int[] cardValues = new int[15];
	for(int i=0;i<15;i++){
		cardValues[i] = players[3].hand[i].getValue();
	}
	return cardValues;
}

//Precondition: currentTeamScores has been instantiated.
//Postcondition: currentTeamScores is returned as an array of two values.
public int[] getCurrentTeamScores()
{
	return currentTeamScores;
}

public int[] getCurrentRoundScores()
{
	return roundScore;
}

//Precondition: highBid has been set
//Postcondition: highBid returned
public int getHighBid()
{
	return highBid;
}

//Precondition: getCurrentTrick has been instantiated.
//Postcondition: currentTrick is returned as an array of cards. This will include any cards that have been played so far.
public Card[] getCurrentTrick()
{
	return currentTrick;
}

//Precondition: kitty has been instantiated.
//Postcondition: kitty is returned as an array of cards.
public int[] getPlayerHandWithKitty()
{
	players[3].addKittyToHand(kitty); //Allows us to sort the hand before shipping to the GUI.
	
	int[] playerHandValues = new int[15];
	for(int i=0; i < 15; i++){
		playerHandValues[i] = players[3].hand[i].getValue(); //Getting the location (hidden?) values of the cards.
	}
	
	return playerHandValues;
}

//Precondition: newKitty is a card array of five cards.
//Postcondition: kitty will be set to point to the values located in newKitty.
public void setKitty(int[] newKitty)
{
	for(int i=0; i<5;i++){
		Card card = new Card();
		if(newKitty[i] <= 10){card.setCard(Card.Suit.RED,newKitty[i]);}		
		else if(newKitty[i] <= 21){card.setCard(Card.Suit.BLUE,newKitty[i]);}		
		else if(newKitty[i] <= 32){card.setCard(Card.Suit.GREEN,newKitty[i]);}		
		else if(newKitty[i] <= 43){card.setCard(Card.Suit.BLACK,newKitty[i]);}
		else if(newKitty[i] == 44){card.setCard(Card.Suit.NOSUIT, newKitty[i]);}
		card.setCardVal();
		
		kitty[i] = card;
	}
}

public void setPlayerHand(int[] newHand)
{
	for(int i=0; i < 10; i++){
		Card card = new Card();
		if(newHand[i] <= 10){card.setCard(Card.Suit.RED,newHand[i]);}		
		else if(newHand[i] <= 21){card.setCard(Card.Suit.BLUE,newHand[i]);}		
		else if(newHand[i] <= 32){card.setCard(Card.Suit.GREEN,newHand[i]);}		
		else if(newHand[i] <= 43){card.setCard(Card.Suit.BLACK,newHand[i]);}
		else if(newHand[i] == 44){card.setCard(Card.Suit.NOSUIT, newHand[i]);}
		card.setCardVal();
		players[3].hand[i] = card;
	}
	
	players[3].sortHand(10);
}

public void setPlayerDiscards(int[] playerDiscards)
{
	for(int i=0; i < 5; i++){
		Card card = new Card();
		if(playerDiscards[i] <= 10){card.setCard(Card.Suit.RED,playerDiscards[i]);}		
		else if(playerDiscards[i] <= 21){card.setCard(Card.Suit.BLUE,playerDiscards[i]);}		
		else if(playerDiscards[i] <= 32){card.setCard(Card.Suit.GREEN,playerDiscards[i]);}		
		else if(playerDiscards[i] <= 43){card.setCard(Card.Suit.BLACK,playerDiscards[i]);}
		else if(playerDiscards[i] == 44){card.setCard(Card.Suit.NOSUIT, playerDiscards[i]);}
		card.setCardVal();
		players[3].discards[i] = card;
	}
}
public String getTrump(){
	if(trumpColor==Card.Suit.BLACK)
		return "Black";
	if(trumpColor==Card.Suit.BLUE)
		return "Blue";
	if(trumpColor==Card.Suit.RED)
		return "Red";
	if(trumpColor==Card.Suit.GREEN)
		return "Green";
	
	return "";
}

public int getBidWinnerLocation(){
	return bidWinner;
}

public int getTrickWinnerLocation(){
	return trickWinner;
}

public void resetCurrentPlayersTurn(){
	currentPlayersTurn = trickWinner;
	currentPlaceInTrick = 0;
}

public void playerPlayed(int indexToPlay){
	Card.Suit setSuit = players[currentPlayersTurn].hand[indexToPlay].getSuit();
    int setHiddenValue = players[currentPlayersTurn].hand[indexToPlay].getValue();
    
	currentTrick[currentPlaceInTrick] = new Card();
    currentTrick[currentPlaceInTrick].setCard(setSuit,setHiddenValue);
    currentTrick[currentPlaceInTrick].setCardVal();
    
	players[3].hand[indexToPlay].setCard(Card.Suit.BLANK, 100);
    players[3].hand[indexToPlay].setCardVal();
    
	currentPlayersTurn = (currentPlayersTurn + 1) % 4;
	currentPlaceInTrick++;
	
}

public void cleanRoundScores(){
	roundScore[0] = 0;
	roundScore[1] = 0;
	numberOfTricksPlayed = 0;
}

public void incrementNumberOfTricks(){
	numberOfTricksPlayed++;
}

public void cleanTrickArray(){
	currentTrick[0] = new Card();
	currentTrick[1] = new Card();
	currentTrick[2] = new Card();
	currentTrick[3] = new Card();
}

public int getNumberOfCompletedTricks(){
	return numberOfTricksPlayed;
}
}//end of class


