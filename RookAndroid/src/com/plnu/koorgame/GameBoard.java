package com.plnu.koorgame;

import com.plnu.gamecode.Card;
import com.plnu.gamecode.Game;
import com.plnu.koorgame.AlertTrickWinnerDialogFragment.onTrickListener;
import com.plnu.koorgame.BidFragment.onBidListener;
import com.plnu.koorgame.DiscardFragment.onDiscardListener;
import com.plnu.koorgame.GameFragment.onGamePlayListener;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
/*
 * GameBoard class stores methods to change fragments or display alerts
 */
public class GameBoard extends Activity implements onBidListener,
	onDiscardListener, onGamePlayListener, onTrickListener {
	
	private BidFragment bidFragment;
	private DiscardFragment discardFragment;
	private GameFragment gameFragment;
	private String trumpColor;
	private CountDownTimer highlight1, highlight2, advanceGame;
	
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
		
		game = new Game();
		setupNewRound();
	}
	
	/*
	 * Allows us to create a new deck and restart a round if we have not reached 500 or it is the first round of the game.
	 */
	public void setupNewRound(){
		bidFragment = new BidFragment();
		discardFragment = new DiscardFragment();
		gameFragment = new GameFragment();
		
		game.resetCurrentPlayersTurn();
		game.cleanTrickArray();	
		game.CleanAllGamePlayObjects();
		game.cleanRoundScores();
		game.makeDeck();
		game.dealCards();
		game.CleanAllBiddingObjects();
		int [] bids = game.advanceBidding();
		startBiddingFragment(bids);
		if(bids[0] == -2 && bids [1] == -2 && bids [2] == -2){	
			//chooseTrumpColor();
			startDiscardFragment();
		}
	}
	
	/*
	 * Replace current fragment with bidding fragment
	 */
	public void startBiddingFragment(int[] bids) {
		int[] cardNames = game.getPlayerCardsUI();
		
		Bundle args = new Bundle();
        args.putInt("PLAYER1Bid", bids[0]);
        args.putInt("PLAYER2Bid", bids[1]);
	    args.putInt("PLAYER3Bid", bids[2]);
	    args.putIntArray("playerHandArray", cardNames);
	    
		bidFragment.setArguments(args);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, bidFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
	
	}

	public void onPlayerPassedBid(View v) {
		//They passed the bid
		game.playerDroppedFromBidding();
		
		while(game.getNumberOfBiddersRemaining() > 1){
			int [] bids = game.advanceBidding(); 
			bidFragment.displayPlayerBid(1, bids[0]);
			bidFragment.displayPlayerBid(2,  bids[1]);
			bidFragment.displayPlayerBid(3,  bids[2]);
		}
		
		trumpColor = game.setTrumpAndInformAI(null); //This stores the trumpColor that the AI chose.
		
		showBidWinner(game.getBidWinnerLocation(), trumpColor);
		startGameFragment();
		System.out.println("BIDWIN:" + game.bidWinner + "In onPlayerPassedBid.");
	}
	/*
	 * Interface with BidFragment
	 * Called when player bids
	 * @param the bid the real player selected
	 */
	@Override
	public void onBidPlayed(int bid) {
		game.playerEnteredNewBid(bid);
		int [] bids = game.advanceBidding();
		bidFragment.DisplayBidsUsingTimers(bids);

		
		if(game.getBidWinnerLocation() == 3)
		{
			startDiscardFragment();
			//chooseTrumpColor();
		}
	}
	
	
	@Override
	public void resetAllTrickCards()
	{
		game.resetCurrentPlayersTurn();
		game.cleanTrickArray();	
		
		if(game.getNumberOfCompletedTricks() != 10)
		{
			gameFragment.resetAllTrickCards();
		
			Card[] playedCards = game.getCurrentTrick();
			int[] playerHand = game.getPlayerCardsUI();
		
			if(game.getTrickWinnerLocation() != 3){
				game.advanceGameState();
				playedCards = game.getCurrentTrick();
				playerHand = game.getPlayerCardsUI();
			
				gameFragment.displayPlayerCards(playerHand);
				gameFragment.setOpponentsCards(playedCards, game.getTrickWinnerLocation());
			}
		} else {
			game.cleanRoundScores(); //At this point, we know we won't be playing another trick in this round. This needs to go here so that it doesn't asynchronously change completedTricks.
		}
	}
	
	/*
	 * Displays fragment allowing player to choose which cards to discard
	 */
	public void startDiscardFragment() {
		int[] cardNames = game.getPlayerHandWithKitty();
		
		Bundle args = new Bundle();
		args.putIntArray("PlayerHandWithKitty",cardNames);
		
		discardFragment.setArguments(args);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, discardFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
	}
	
	/*
	 * Listener when discarding is done
	 */
	@Override
	public void doneDiscarding(int[] playerHand, int[] playerDiscards) {
		
		//if (!trumpSelect) //they haven't selected trump color dialog says you must do so
		//{
			//chooseTrump();
		//}
		//else //they selected trump and discarded - game moves on to actual playing
		{
			game.setPlayerDiscards(playerDiscards);
		
			for(int i=0; i < 10; i++){
				System.out.println("PH Pre: " + playerHand[i]);
			}
			game.setPlayerHand(playerHand);
		
			int[] player = game.getPlayerCardsUI();
		
			for(int i=0; i < 10; i++){
				System.out.println("PH Post: " + player[i]);
			}
			startGameFragment();
		}
	}	
	
	public void startGameFragment() {
		System.out.println("BIDWIN:" + game.bidWinner + "In startGameFragment.");
		Bundle args = new Bundle();
		args.putIntArray("playerHandArray", game.getPlayerCardsUI());
		args.putString("currentTrump", game.getTrump());
		args.putIntArray("CurrentTeamScores", game.getCurrentTeamScores());
		args.putInt("highBid", game.getHighBid());
		
		
		gameFragment.setArguments(args);
		
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, gameFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
		
	}
	
	public void playerDidntLeadInitialize(){
		if(game.getBidWinnerLocation() != 3){
		game.advanceGameState();
		Card[]playedCards = game.getCurrentTrick();
		
		gameFragment.setOpponentsCards(playedCards, game.getTrickWinnerLocation());
		}
	}
	
	public void onPlayerPlayed(int indexToPlay){
		if(game.getNumberOfCompletedTricks() != 10){
			
			int currentTrickWinnerLocation = game.getTrickWinnerLocation();
			gameFragment.setWhoLed(game.getTrickWinnerLocation());
			if(game.getTrickWinnerLocation() == 3){
				game.playerPlayed(indexToPlay);
				game.advanceGameState();
			} else {
				Card[] tempTrick = game.getCurrentTrick();
				Card leadCard = tempTrick[0];
				
				if(game.players[3].hand[indexToPlay].getValue() == 44){ //We must treat the rook as though it was trump for the following rules.
					game.players[3].hand[indexToPlay].setSuit(game.trumpColor);
				}
				
				if(leadCard.getSuit() == Card.Suit.RED){ //Cascading through each suit. If the player doesn't follow lead and they have cards of that color, we reject their play.
					if(game.players[3].redLength > 0 && game.players[3].hand[indexToPlay].getSuit() != Card.Suit.RED){
						gameFragment.undoDisplayPlayed();
						return;
					}
				} else if(leadCard.getSuit() == Card.Suit.BLACK){
					if(game.players[3].blackLength > 0 && game.players[3].hand[indexToPlay].getSuit() != Card.Suit.BLACK){
						gameFragment.undoDisplayPlayed();
						return;
					}
				} else if(leadCard.getSuit() == Card.Suit.GREEN){
					if(game.players[3].greenLength > 0 && game.players[3].hand[indexToPlay].getSuit() != Card.Suit.GREEN){
						gameFragment.undoDisplayPlayed();
						return;
					}
				} else {
					if(game.players[3].blueLength > 0 && game.players[3].hand[indexToPlay].getSuit() != Card.Suit.BLUE){
						gameFragment.undoDisplayPlayed();
						return;
					}
				}
				
				game.playerPlayed(indexToPlay);
				game.advanceGameState();
			}
		
			Card[] playedCards = game.getCurrentTrick();
			int[] playerHand = game.getPlayerCardsUI();
		
			gameFragment.displayPlayerCards(playerHand);
			gameFragment.setOpponentsCards(playedCards, currentTrickWinnerLocation);
		
		
			if(playedCards[0].getValue() != -1 && playedCards[1].getValue() != -1 && playedCards[2].getValue() != -1 && playedCards[3].getValue() != -1){			
				highlight2 = new CountDownTimer(4000, 1000) { //We have to wait for all the cards to be drawn and such. This was previously called in the dialog.
					public void onTick(long millisTillFinished) {
					}		
					public void onFinish() {
						if(game.getNumberOfCompletedTricks() < 10){
							resetAllTrickCards();
						}
					}
				}.start();
				
				highlight1 = new CountDownTimer(2000, 1000) { //We have to wait for all the cards to be drawn and such. This was previously called in the dialog.
					public void onTick(long millisTillFinished) {
					}		
					public void onFinish() {
						if(game.getNumberOfCompletedTricks() <= 10){
							gameFragment.winnerHighlightToggle(game.getTrickWinnerLocation()+1);
						}
						
						if (game.getNumberOfCompletedTricks() >= 10){
							showFinalScores(game.getCurrentTeamScores());					
						}
					}
				}.start();
				
				
				}
			}
		}

		
	/*
	 * Called when a card is played on game play fragment
	 */
	public void cardPlayed(View v) {
		gameFragment.cardPlayed(v);
	}
	
	/*
	 * Displays dialog with bid winner and trump color
	 * @param the winner name and trump color
	 */
	public void showBidWinner(int winner, String color) {
		Bundle args = new Bundle();
		args.putInt("WINNER", winner);
		args.putString("TRUMPCOLOR", color);
		AlertTrumpDialogFragment alertTrumpFragment = new AlertTrumpDialogFragment();
		alertTrumpFragment.setArguments(args);
		alertTrumpFragment.show(getFragmentManager(), "biddialogtag");
		
	}
	
	/*
	 * Displays dialog with winner and each player's score
	 * @param int array of all four scores
	 * Computer team is always at index 0
	 */
	public void showFinalScores(int [] scores) {
		game.resetCurrentPlayersTurn();
		
		Bundle args = new Bundle();
		
		args.putString("COMPUTERNAME", "Computer Team");
		args.putString("YOURNAME", "Your Team");
		args.putInt("COMPUTERSCORE", scores[0]);
		args.putInt("YOURSCORE", scores[1]);
		
		
		FinalScoreDialogFragment finalScoreFragment = new FinalScoreDialogFragment();
		finalScoreFragment.setArguments(args);
		finalScoreFragment.show(getFragmentManager(), "finaldialogtag");
		
		if(game.getCurrentTeamScores()[0] < E.WINNING_POINT_TOTAL && game.getCurrentTeamScores()[1] < E.WINNING_POINT_TOTAL){
			advanceGame = new CountDownTimer(6000, 1000) { //We have to wait for all the cards to be drawn and such. This was previously called in the dialog.
				public void onTick(long millisTillFinished) {
				}		
				public void onFinish() {
						setupNewRound();
				}
			}.start();
		} 
		
	}
	
	public void gameEnd(){
		this.finish();
	}
	
	public void cardClick(View v) {
		discardFragment.cardClick(v);
	}
	
	public void blackTrumpSelect(View v) {
		String cardColors[] = {"BLACK", "RED", "GREEN", "BLUE"};
		game.setTrumpAndInformAI(cardColors[0]);
		discardFragment.blackTrump();
	
	}
	public void redTrumpSelect(View v) {
		String cardColors[] = {"BLACK", "RED", "GREEN", "BLUE"};
		game.setTrumpAndInformAI(cardColors[1]);
		discardFragment.redTrump();
	
	}
	public void greenTrumpSelect(View v) {
		String cardColors[] = {"BLACK", "RED", "GREEN", "BLUE"};
		game.setTrumpAndInformAI(cardColors[2]);
		discardFragment.greenTrump();
	
	}
	public void blueTrumpSelect(View v) {
		String cardColors[] = {"BLACK", "RED", "GREEN", "BLUE"};
		game.setTrumpAndInformAI(cardColors[3]);
		discardFragment.blueTrump();
	}
    
    @Override
    protected void onResume() {
        super.onResume();
       
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		
	}
}
