package com.plnu.koorgame;

import com.plnu.gamecode.Game;

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
public class GameBoard extends Activity {
	
	private BidFragment bidFragment;
	private DiscardFragment discardFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
		
		bidFragment = new BidFragment();
		discardFragment = new DiscardFragment();
		
		Game game = new Game();
		game.makeDeck();
		game.dealCards();
		int [] bids = game.advanceBidding(); //ONLY HAVE AI PLAYERS MAKE THEIR BIDS
		startBiddingFragment(bids);
	}
	
	/*
	 * Replace current fragment with bidding fragment
	 */
	public void startBiddingFragment(int[] bids) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, discardFragment);
		fragmentTransaction.commit();
		bidFragment.displayPlayerBid(1, bids[0]);
		bidFragment.displayPlayerBid(2,  bids[1]);
		bidFragment.displayPlayerBid(3,  bids[1]);
	}
	
	public void startGame() {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, discardFragment);
		fragmentTransaction.commit();
	}
	/*
	 * Displays dialog to allow user to choose trump color
	 */
	public void chooseTrumpColor(View v){
		//ChooseTrumpDialogFragment chooseTrumpFragment = new ChooseTrumpDialogFragment();
		//chooseTrumpFragment.show(getFragmentManager(), "trumpdialogtag");
		int[] myScores = {500, 200};
		showFinalScores(myScores);
		
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