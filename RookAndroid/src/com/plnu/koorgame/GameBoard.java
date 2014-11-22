package com.plnu.koorgame;

import com.plnu.gamecode.Game;
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
	onDiscardListener, onGamePlayListener {
	
	private BidFragment bidFragment;
	private DiscardFragment discardFragment;
	private GameFragment gameFragment;
	
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
			startDiscardFragment();
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
		startDiscardFragment();
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
		bidFragment.displayPlayerBid(1, bids[0]);
		bidFragment.displayPlayerBid(2,  bids[1]);
		bidFragment.displayPlayerBid(3,  bids[2]);
		
		if(game.getNumberOfBiddersRemaining() == 0)
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
		//Code to tell AI players what trump is
		startDiscardFragment();
	}	
	
	/*
	 * Displays fragment allowing player to choose which cards to discard
	 */
	public void startDiscardFragment() {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, discardFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
	}
	
	/*
	 * Listener when discarding is done
	 */
	@Override
	public void doneDiscarding() {
		startGameFragment();
		
	}	
	
	public void startGameFragment() {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, gameFragment);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
	}
	
	/*
	 * Called when a card is played on game play fragment
	 */
	@Override
	public void cardPlayed(int card) {
		// TODO Auto-generated method stub
		
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