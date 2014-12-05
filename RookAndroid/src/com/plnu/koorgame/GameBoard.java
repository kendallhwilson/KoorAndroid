package com.plnu.koorgame;

import com.plnu.gamecode.Card;
import com.plnu.gamecode.Game;
import com.plnu.koorgame.AlertTrickWinnerDialogFragment.onTrickListener;
import com.plnu.koorgame.BidFragment.onBidListener;
import com.plnu.koorgame.ChooseTrumpDialogFragment.onTrumpListener;
import com.plnu.koorgame.DiscardFragment.onDiscardListener;
import com.plnu.koorgame.GameFragment.onGamePlayListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
/*
 * GameBoard class stores methods to change fragments or display alerts
 */
public class GameBoard extends Activity implements onBidListener, onTrumpListener,
	onDiscardListener, onGamePlayListener, onTrickListener {
	
	private BidFragment bidFragment;
	private DiscardFragment discardFragment;
	private GameFragment gameFragment;
	private String trumpColor;
	
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
		
		bidFragment = new BidFragment();
		discardFragment = new DiscardFragment();
		gameFragment = new GameFragment();
		
		game = new Game();
		setupNewRound();
	}
	
	/*
	 * Allows us to create a new deck and restart a round if we have not reached 500 or it is the first round of the game.
	 */
	public void setupNewRound(){
		game.makeDeck();
		game.dealCards();
		int [] bids = game.advanceBidding();
		
		if(bids[0] == -2 && bids [1] == -2 && bids [2] == -2){
			
			chooseTrumpColor();
		}else{
			startBiddingFragment(bids);
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
		fragmentTransaction.add(R.id.fragment_container, bidFragment);
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
			chooseTrumpColor();
		}
	}
	
	/*
	 * Displays dialog to allow user to choose trump color
	 */
	public void chooseTrumpColor(){
		ChooseTrumpDialogFragment chooseTrumpFragment = new ChooseTrumpDialogFragment();
		chooseTrumpFragment.show(getFragmentManager(), "trumpdialogtag");
	}
	
	/*
	 * Listener for trump value
	 */
	@Override
	public void trumpPass(int color) {
		String cardColors[] = {"BLACK", "RED", "GREEN", "BLUE"};
		game.setTrumpAndInformAI(cardColors[color]);
		startDiscardFragment();
	}	
	
	@Override
	public void resetAllTrickCards()
	{
		gameFragment.resetAllTrickCards();
		game.resetCurrentPlayersTurn();
		game.incrementNumberOfTricks();
		game.cleanTrickArray();	
		
		Card[] playedCards = game.getCurrentTrick();
		int[] playerHand = game.getPlayerCardsUI();
		
		if(game.getTrickWinnerLocation() != 3){
			game.advanceGameState();
			playedCards = game.getCurrentTrick();
			playerHand = game.getPlayerCardsUI();
			
			gameFragment.displayPlayerCards(playerHand);
			gameFragment.setOpponentsCards(playedCards, game.getTrickWinnerLocation());
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
	public void doneDiscarding(int[] playerHand, int[] kitty) {
		game.setKitty(kitty);
		game.setPlayerHand(playerHand);
		startGameFragment();
	}	
	
	public void startGameFragment() {
		
		Bundle args = new Bundle();
		args.putIntArray("playerHandArray", game.getPlayerCardsUI());
		args.putString("currentTrump", game.getTrump());
		args.putIntArray("CurrentTeamScores", game.getCurrentTeamScores());
		
		
		gameFragment.setArguments(args);
		
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, gameFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
		
	}
	
	public void playerDidntLeadInitialize(){
		if(game.getTrickWinnerLocation() != 3){
		game.advanceGameState();
		Card[]playedCards = game.getCurrentTrick();
		
		gameFragment.setOpponentsCards(playedCards, game.getTrickWinnerLocation());
		}
	}
	
	public void onPlayerPlayed(int indexToPlay){
		int currentTrickWinnerLocation = game.getTrickWinnerLocation();
		gameFragment.setWhoLed(game.getTrickWinnerLocation());
		if(game.getTrickWinnerLocation() == 3){
			game.playerPlayed(indexToPlay);
			game.advanceGameState();
		} else{
			game.playerPlayed(indexToPlay);
			game.advanceGameState();
		}
		
		Card[] playedCards = game.getCurrentTrick();
		int[] playerHand = game.getPlayerCardsUI();
		
		gameFragment.displayPlayerCards(playerHand);
		gameFragment.setOpponentsCards(playedCards, currentTrickWinnerLocation);
		
		
		if(playedCards[0].getValue() != -1 && playedCards[1].getValue() != -1 && playedCards[2].getValue() != -1 && playedCards[3].getValue() != -1){
			AlertTrickWinnerDialogFragment trickWinnerFragment = new AlertTrickWinnerDialogFragment();
			Bundle args2 = new Bundle();
			args2.putString("WinnerName", "Player " + (game.getTrickWinnerLocation()+1));
			trickWinnerFragment.setArguments(args2);
			trickWinnerFragment.show(getFragmentManager(), "trickwinnertag");
			

		}
		
		if(game.getNumberOfCompletedTricks() == 10){
			showFinalScores(game.getCurrentTeamScores());
			
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
		game.cleanRoundScores();
		
		Bundle args = new Bundle();
		if (scores[0] > scores[1]) {
			args.putString("WINNERNAME", "Computer Team");
			args.putString("LOSERNAME", "Your Team");
			args.putInt("WINNERSCORE", scores[0]);
			args.putInt("LOSERSCORE", scores[1]);
		}
		else {
			args.putString("WINNERNAME", "Your Team");
			args.putString("LOSERNAME", "Computer Team");
			args.putInt("WINNERSCORE", scores[1]);
			args.putInt("LOSERSCORE", scores[0]);
		}

		FinalScoreDialogFragment finalScoreFragment = new FinalScoreDialogFragment();
		finalScoreFragment.setArguments(args);
		finalScoreFragment.show(getFragmentManager(), "finaldialogtag");
		
		if(scores[0] < 500 && scores[1] < 500){
			setupNewRound();
		}
		
	}
	
	public void cardClick(View v) {
		discardFragment.cardClick(v);
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
